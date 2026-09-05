package com.example.canyouspotit

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.example.canyouspotit.data.AppDatabase
import com.example.canyouspotit.data.LearningSession
import com.example.canyouspotit.data.ScanResult
import com.example.canyouspotit.data.UserPreferences
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Lets the user change the data-saving choice they first made on the consent screen -
// in both directions, including full withdrawal (turn off + delete everything saved) -
// and, since the app is fully offline, export what's saved so it can be sent to the
// research team.
// Writes the same app_prefs / "data_collection_enabled" key that BaseActivity's
// isDataCollectionEnabled() reads to gate every Room write.
class SettingsActivity : BaseActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }
    private lateinit var cardExport: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switch = findViewById<SwitchMaterial>(R.id.switchDataSaving)
        cardExport = findViewById(R.id.cardExport)

        // Reflect the stored choice first, THEN attach the listener, so this initial
        // sync doesn't count as a user toggle.
        switch.isChecked = isDataCollectionEnabled()
        updateExportVisibility()

        switch.setOnCheckedChangeListener { _, isChecked ->
            // The choice takes effect immediately, before any follow-up dialog.
            getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("data_collection_enabled", isChecked)
                .apply()

            updateExportVisibility()

            if (isChecked) {
                showThemedSnackbar("Your practice history will be saved from now on.")
            } else {
                // Saving has already stopped; deleting the existing history is a separate,
                // explicit choice.
                showDeleteChoiceDialog()
            }
        }

        findViewById<Button>(R.id.btnExport).setOnClickListener { exportData() }
    }

    override fun onResume() {
        super.onResume()
        // In case the flag was changed elsewhere (e.g. the consent screen) since this
        // screen was created.
        updateExportVisibility()
    }

    // A user who chose not to save data has nothing to export, and offering it would
    // undercut that choice - so the whole card is hidden while saving is off.
    private fun updateExportVisibility() {
        cardExport.visibility = if (isDataCollectionEnabled()) View.VISIBLE else View.GONE
    }

    private fun showDeleteChoiceDialog() {
        val content = layoutInflater.inflate(R.layout.dialog_delete_data, null)

        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(content)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Backing out without choosing = keep (saving is already off regardless).
        dialog.setOnCancelListener {
            showThemedSnackbar("Saving is off. Your saved history is kept.")
        }

        content.findViewById<Button>(R.id.btnKeepData).setOnClickListener {
            dialog.dismiss()
            showThemedSnackbar("Saving is off. Your saved history is kept.")
        }

        content.findViewById<Button>(R.id.btnDeleteData).setOnClickListener {
            dialog.dismiss()
            deleteAllSavedData()
        }

        dialog.show()
    }

    private fun deleteAllSavedData() {
        lifecycleScope.launch {
            db.scanResultDao().deleteAll()
            db.learningSessionDao().deleteAll()
            db.userPreferencesDao().deleteAll()
            // Back on the main thread here (lifecycleScope + suspend DAO calls).
            showThemedSnackbar("Your saved history has been deleted.")
        }
    }

    // --- Export ---------------------------------------------------------------

    private fun exportData() {
        lifecycleScope.launch {
            try {
                val scans = db.scanResultDao().getAll()
                val sessions = db.learningSessionDao().getAll()
                val prefs = db.userPreferencesDao().getAll()

                // user_preferences always holds one row once consent is given - it's a
                // settings singleton, not participant activity - so "nothing to export" is
                // judged on the two history tables. (prefs is still included in the file
                // below when there IS history worth sending.)
                if (scans.isEmpty() && sessions.isEmpty()) {
                    showThemedSnackbar("There's nothing saved to export yet.")
                    return@launch
                }

                val json = buildExportJson(scans, sessions, prefs)

                val file = withContext(Dispatchers.IO) {
                    val dir = File(cacheDir, "exports").apply { mkdirs() }
                    val stamp = SimpleDateFormat("yyyy-MM-dd_HHmmss", Locale.US).format(Date())
                    File(dir, "canyouspotit_export_$stamp.json").apply { writeText(json) }
                }

                val uri = FileProvider.getUriForFile(
                    this@SettingsActivity, "$packageName.fileprovider", file
                )
                val send = Intent(Intent.ACTION_SEND).apply {
                    type = "application/json"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    putExtra(Intent.EXTRA_SUBJECT, "Can You Spot It — data export")
                    putExtra(Intent.EXTRA_TEXT, "My Can You Spot It app data is attached.")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                startActivity(Intent.createChooser(send, "Send your data export"))
            } catch (e: Exception) {
                showThemedSnackbar("Something went wrong preparing your export. Please try again.")
            }
        }
    }

    private fun buildExportJson(
        scans: List<ScanResult>,
        sessions: List<LearningSession>,
        prefs: List<UserPreferences>
    ): String {
        val root = JSONObject()

        root.put("export_metadata", JSONObject().apply {
            put("app", "CanYouSpotIt")
            put("exported_at", SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).format(Date()))
            put("exported_at_epoch_ms", System.currentTimeMillis())
            put("record_counts", JSONObject().apply {
                put("scan_results", scans.size)
                put("learning_sessions", sessions.size)
                put("user_preferences", prefs.size)
            })
        })

        root.put("scan_results", JSONArray().apply {
            scans.forEach { s ->
                put(JSONObject().apply {
                    put("id", s.id)
                    put("messageText", s.messageText)
                    put("verdict", s.verdict)
                    put("timestamp", s.timestamp)
                    put("emotionalResponse", s.emotionalResponse ?: JSONObject.NULL)
                    put("primaryTactic", s.primaryTactic ?: JSONObject.NULL)
                })
            }
        })

        root.put("learning_sessions", JSONArray().apply {
            sessions.forEach { l ->
                put(JSONObject().apply {
                    put("id", l.id)
                    put("exampleId", l.exampleId)
                    put("userAnswer", l.userAnswer)
                    put("isCorrect", l.isCorrect)
                    put("difficultyLevel", l.difficultyLevel)
                    put("timestamp", l.timestamp)
                    put("decisionTimeMs", l.decisionTimeMs)
                })
            }
        })

        root.put("user_preferences", JSONArray().apply {
            prefs.forEach { p ->
                put(JSONObject().apply {
                    put("id", p.id)
                    put("consentGiven", p.consentGiven)
                    put("detectedRegion", p.detectedRegion)
                    put("useDarkMode", p.useDarkMode)
                })
            }
        })

        return root.toString(2)
    }
}
