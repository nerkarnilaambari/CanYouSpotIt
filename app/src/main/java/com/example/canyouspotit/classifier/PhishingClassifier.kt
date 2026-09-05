package com.example.canyouspotit.classifier

// Keyword tiers built from real phrases found in the project's 193-row
// curated scam/legitimate message dataset (English, Hinglish, German).
// Hinglish = Hindi words spelled in Latin letters (e.g. "turant", "badhai ho") — NOT Devanagari script.

object PhishingClassifier {

    // ---------- ENGLISH ----------
    private val urgencyPhrasesEnglish = listOf(
        "act now", "expires today", "immediately", "urgent",
        "your account will be", "verify now", "suspended",
        "last chance", "within 24 hours", "limited time",
        "action required", "account blocked", "final notice",
        "right away", "today only", "call now"
    )
    private val rewardLuresEnglish = listOf(
        "congratulations", "you have won", "you've won",
        "selected", "claim your prize", "free gift",
        "lucky winner", "prize money", "reward", "gift card",
        "guaranteed", "jackpot", "lucky draw"
    )
    private val threatIndicatorsEnglish = listOf(
        "click here", "click this link", "bit.ly", "tinyurl",
        "verify your", "confirm your details", "bank account",
        "otp", "one time password", "password", "update your",
        "log in immediately", "sign in now", "your details",
        "personal information", "credit card", "debit card",
        "compromised", "unusual activity", "unusual login",
        "billing information", "payment declined", "reset your password"
    )
    private val legitimateIndicatorsEnglish = listOf(
        "unsubscribe", "privacy policy", "official",
        "no action required", "for your records",
        "automated message", "do not reply", "your order",
        "as usual", "check your account", "in the app",
        "no action needed", "if this was you", "account settings",
        "manage your subscription"
    )

    // ---------- HINGLISH (Latin-script Hindi, not Devanagari) ----------
    private val urgencyPhrasesHinglish = listOf(
        "turant", "abhi", "2 ghante", "jald se jald",
        "turant verify karna hoga", "dhyan se suniye"
    )
    private val rewardLuresHinglish = listOf(
        "badhai ho", "lottery jeeta hai",
        "jeet gaye", "processing fee"
    )
    private val threatIndicatorsHinglish = listOf(
        "police se bol raha hoon", "cyber crime", "cyber cell",
        "fir ho chuki hai", "case register", "legal action",
        "aadhaar", "kyc pending", "account block ho jayega",
        "otp share kijiye", "complaint mili hai", "verify karna hoga",
        "morphed photos", "money laundering", "gande videos",
        "asleel", "processing fee dena hoga"
    )
    private val legitimateIndicatorsHinglish = listOf(
        "order deliver ho chuka hai", "feedback de dena",
        "agenda mail kar diya", "office pahunch gaya",
        "safely pahunch jaunga", "fasting rehna", "cab 3 minute mein"
    )

    // ---------- GERMAN ----------
    private val urgencyPhrasesGerman = listOf(
        "sofort", "dringend", "nur heute",
        "noch heute", "achtung", "warnung",
        "schalten sie den computer nicht aus"
    )
    private val rewardLuresGerman = listOf(
        "herzlichen glückwunsch", "gewinn", "gewonnen",
        "gewinner", "gutschein", "rabatte", "gratis", "kostenlos"
    )
    private val threatIndicatorsGerman = listOf(
        "konto bestätigen", "passwort", "anmelden",
        "virus infiziert", "fehlercode", "konto blockieren",
        "konto gesperrt", "bearbeitungsgebühr", "rechtliche schritte",
        "identität verifizieren", "bankdaten", "betrugsversuch",
        "gestohlen"
    )
    private val legitimateIndicatorsGerman = listOf(
        "wie gewohnt", "offizielle", "app oder unsere webseite",
        "keine handlung erforderlich", "bitte beachte",
        "termin wurde bestätigt", "offizielle webseite"
    )

    private val allUrgency = urgencyPhrasesEnglish + urgencyPhrasesHinglish + urgencyPhrasesGerman
    private val allRewards = rewardLuresEnglish + rewardLuresHinglish + rewardLuresGerman
    private val allThreats = threatIndicatorsEnglish + threatIndicatorsHinglish + threatIndicatorsGerman
    private val allLegitimate = legitimateIndicatorsEnglish + legitimateIndicatorsHinglish + legitimateIndicatorsGerman

    fun classify(message: String): ClassificationResult {
        val lower = message.lowercase()
        var score = 0
        val flags = mutableListOf<String>()

        allUrgency.forEach { phrase ->
            if (lower.contains(phrase)) {
                score += 3
                flags.add("Urgency detected: \"$phrase\"")
            }
        }
        allRewards.forEach { phrase ->
            if (lower.contains(phrase)) {
                score += 2
                flags.add("Reward lure: \"$phrase\"")
            }
        }
        allThreats.forEach { phrase ->
            if (lower.contains(phrase)) {
                score += 2
                flags.add("Suspicious indicator: \"$phrase\"")
            }
        }
        allLegitimate.forEach { phrase ->
            if (lower.contains(phrase)) score -= 2
        }

        return when {
            // A single flag alone — however it's weighted — shouldn't be enough to
            // trigger CAUTION or SCAM. Real personal messages ("its urgent please
            // call me") can trip one generic phrase without being remotely scammy.
            flags.size < 2 -> ClassificationResult(
                "SAFE", score, flags,
                "This message appears safe, but it's always good to stay alert."
            )
            score >= 6 -> ClassificationResult(
                "SCAM", score, flags,
                "This message shows multiple signs of a scam."
            )
            score >= 3 -> ClassificationResult(
                "CAUTION", score, flags,
                "This message has a few things that don't look quite right."
            )
            else -> ClassificationResult(
                "SAFE", score, flags,
                "This message appears safe, but it's always good to stay alert."
            )
        }
    }
}

data class ClassificationResult(
    val verdict: String,
    val score: Int,
    val flags: List<String>,
    val explanation: String
)
