package com.example.canyouspotit.model

data class ScamExample(
    val id: Int,
    val messageText: String,
    val isScam: Boolean,
    val explanation: String,
    val indicators: List<String>,
    val difficulty: Int,          // 1=Easy, 2=Medium, 3=Hard
    val region: String,           // "IN", "DE", or "GLOBAL"
    val scamCategory: String? = null  // e.g. "bank_kyc_otp_scam"; null for legitimate messages
)

val scamExamples = listOf(
    ScamExample(
        id = 1,
        messageText = "Revenue Compliance Office: Unsere Unterlagen zeigen einen offenen Betrag im Zusammenhang mit Ihrer ID. Rufen Sie sofort +1-415-555-0128 an, um Ihre Identität zu verifizieren und rechtliche Schritte zu vermeiden.",
        isScam = true,
        explanation = "This message pretends to be a government or legal office threatening you over unpaid money or an unresolved case. Real government offices do not call and demand you 'verify your identity' over the phone to avoid legal trouble.",
        indicators = listOf("Artificial urgency / countdown", "Asks for OTP, password, or bank/KYC details", "Impersonates police, court, or a government office"),
        difficulty = 3,
        region = "DE",
        scamCategory = "government_debt_threat_scam"
    ),
    ScamExample(
        id = 2,
        messageText = "Federal Verification Office: Unsere Unterlagen zeigen einen offenen Betrag im Zusammenhang mit Ihrer ID. Rufen Sie sofort +1-702-555-0169 an, um Ihre Identität zu verifizieren und rechtliche Schritte zu vermeiden.",
        isScam = true,
        explanation = "This message pretends to be a government or legal office threatening you over unpaid money or an unresolved case. Real government offices do not call and demand you 'verify your identity' over the phone to avoid legal trouble.",
        indicators = listOf("Artificial urgency / countdown", "Asks for OTP, password, or bank/KYC details", "Impersonates police, court, or a government office"),
        difficulty = 3,
        region = "DE",
        scamCategory = "government_debt_threat_scam"
    ),
    ScamExample(
        id = 3,
        messageText = "HERZLICHEN GLÜCKWUNSCH! Ihre Nummer wurde für einen Gewinn von €2500 ausgewählt. Zahlen Sie die Bearbeitungsgebühr von €35 hier: https://support-help-center.invalid/update-info. Referenz: YF3HZJT2.",
        isScam = true,
        explanation = "This message says you've won something but needs you to click a link or pay a small fee first to receive it. Real prizes never require you to pay money or enter personal details to collect them.",
        indicators = listOf("Uses an official-sounding but unverifiable name"),
        difficulty = 1,
        region = "DE",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 4,
        messageText = "HERZLICHEN GLÜCKWUNSCH! Ihre Nummer wurde für einen Gewinn von €5000 ausgewählt. Zahlen Sie die Bearbeitungsgebühr von €75 hier: https://help-center-track-delivery.test/update-info. Referenz: C9VK1FWX.",
        isScam = true,
        explanation = "This message says you've won something but needs you to click a link or pay a small fee first to receive it. Real prizes never require you to pay money or enter personal details to collect them.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 1,
        region = "DE",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 8,
        messageText = "WARNUNG: Ihr Computer wurde mit einem Virus infiziert (Fehlercode 12C2UCXG). Schalten Sie den Computer nicht aus. Rufen Sie sofort den PC Protect Services Support an: +1-617-555-0142.",
        isScam = true,
        explanation = "This message claims your computer has a virus and gives an urgent number to call. Real antivirus software does not ask you to call a phone number. This is a trick to get remote access to your device or your money.",
        indicators = listOf("Artificial urgency / countdown", "Fake virus/tech-support alert", "Uses an official-sounding but unverifiable name"),
        difficulty = 2,
        region = "DE",
        scamCategory = "tech_support_scam"
    ),
    ScamExample(
        id = 10,
        messageText = "WARNUNG: Ihr Computer wurde mit einem Virus infiziert (Fehlercode QXMSVWKY). Schalten Sie den Computer nicht aus. Rufen Sie sofort den TechGuard Support an: +1-305-555-0112.",
        isScam = true,
        explanation = "This message claims your computer has a virus and gives an urgent number to call. Real antivirus software does not ask you to call a phone number. This is a trick to get remote access to your device or your money.",
        indicators = listOf("Artificial urgency / countdown", "Fake virus/tech-support alert", "Uses an official-sounding but unverifiable name"),
        difficulty = 2,
        region = "DE",
        scamCategory = "tech_support_scam"
    ),
    ScamExample(
        id = 11,
        messageText = "Delhi Police se bol raha hoon. Congratulations! Aapne ₹40 lakh ka lottery jeeta hai. Processing fee dena hoga.",
        isScam = true,
        explanation = "This message impersonates police to announce a surprise lottery win, then asks for a 'processing fee' before you can collect it. Real prizes and lotteries never require an upfront payment to release winnings, and police don't call to hand out prize money. This is designed to get a payment from you for a prize that doesn't exist.",
        indicators = listOf("Unexpected prize claim", "Impersonates police, court, or a government office"),
        difficulty = 2,
        region = "IN",
        scamCategory = "police_impersonation_scam"
    ),
    ScamExample(
        id = 12,
        messageText = "Namaste sir Sir kal subah 10 baje meeting hai, agenda mail kar diya.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 13,
        messageText = "Special Investigation Team se bol raha hoon. Aapke father ko accident ho gaya hai. Turant 50,000 bhej dijiye hospital ke liye.",
        isScam = true,
        explanation = "This message pretends a family member has been hospitalised and demands money be sent immediately. Scammers use fake family emergencies deliberately because panic stops people from checking first. Always call your family member directly before sending any money.",
        indicators = listOf("Artificial urgency / countdown", "Impersonates police, court, or a government office", "Exploits fear or a fake emergency"),
        difficulty = 3,
        region = "IN",
        scamCategory = "fake_emergency_scam"
    ),
    ScamExample(
        id = 14,
        messageText = "Ji namaskar Aapka order deliver ho chuka hai. Feedback de dena.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence", "Matches an action you actually took (a real order/purchase)"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 15,
        messageText = "Sir ek minute suniye Mummy main office pahunch gaya, aaj thoda late ho jaunga.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 16,
        messageText = "Delhi Police se bol raha hoon. Aapke Aadhaar card ka istemal karke fake SIM issue ki gayi hai.",
        isScam = true,
        explanation = "This message threatens arrest or legal action unless you act immediately, often over the phone or via video call. Real police never arrest people over a phone call or demand money to avoid arrest.",
        indicators = listOf("Impersonates police, court, or a government office"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_impersonation_scam"
    ),
    ScamExample(
        id = 17,
        messageText = "Hello sir Papa train mein baith gaya hoon, safely pahunch jaunga.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 18,
        messageText = "ACP Sharma bol raha hoon. Aapke Aadhaar se linked account par complaint mili hai.",
        isScam = true,
        explanation = "This message threatens arrest or legal action unless you act immediately, often over the phone or via video call. Real police never arrest people over a phone call or demand money to avoid arrest.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_impersonation_scam"
    ),
    ScamExample(
        id = 20,
        messageText = "ACP Sharma bol raha hoon. Congratulations! Aapne ₹40 lakh ka lottery jeeta hai. Processing fee dena hoga.",
        isScam = true,
        explanation = "This message impersonates police to announce a surprise lottery win, then asks for a 'processing fee' before you can collect it. Real prizes and lotteries never require an upfront payment to release winnings, and police don't call to hand out prize money. This is designed to get a payment from you for a prize that doesn't exist.",
        indicators = listOf("Unexpected prize claim"),
        difficulty = 2,
        region = "IN",
        scamCategory = "police_impersonation_scam"
    ),
    ScamExample(
        id = 21,
        messageText = "Namaste sir Mummy main office pahunch gaya, aaj thoda late ho jaunga.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 22,
        messageText = "Dhyan se suniye Aapke phone number se koi asleel gande images aur videos share kiye ja rahe hain. Ye cyber crime hai.",
        isScam = true,
        explanation = "This message claims police or authorities have compromising information and demands payment to avoid consequences. This is a fear-based scam. Real authorities do not blackmail people for money.",
        indicators = listOf("Impersonates police, court, or a government office"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_blackmail_scam"
    ),
    ScamExample(
        id = 23,
        messageText = "Hello ji Mummy main office pahunch gaya, aaj thoda late ho jaunga.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 24,
        messageText = "Sir ek minute suniye Doctor appointment kal 9 baje hai, fasting rehna.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 25,
        messageText = "Hello ma'am Bhai aaj shaam ko milte hain, coffee pe baat karte hain.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 26,
        messageText = "Dhyan se suniye Cab 3 minute mein pahunch raha hai.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 27,
        messageText = "Ji namaskar Mummy main office pahunch gaya, aaj thoda late ho jaunga.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 28,
        messageText = "Ji namaskar Cab 3 minute mein pahunch raha hai.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 29,
        messageText = "Hello ji Sir kal subah 10 baje meeting hai, agenda mail kar diya.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 30,
        messageText = "Cyber Crime Department se bol raha hoon. Police verification complete karna zaroori hai.",
        isScam = true,
        explanation = "This message threatens arrest or legal action unless you act immediately, often over the phone or via video call. Real police never arrest people over a phone call or demand money to avoid arrest.",
        indicators = listOf("Impersonates police, court, or a government office", "Uses an official-sounding but unverifiable name"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_impersonation_scam"
    ),
    ScamExample(
        id = 31,
        messageText = "Namaste sir Project ka review meeting kal 4 baje hai kya?",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 32,
        messageText = "Madam ek minute Cab 3 minute mein pahunch raha hai.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 33,
        messageText = "Namaste sir Aapka order deliver ho chuka hai. Feedback de dena.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence", "Matches an action you actually took (a real order/purchase)"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 34,
        messageText = "Madam ek minute Aapke Aadhaar se linked account par complaint mili hai.",
        isScam = true,
        explanation = "This message threatens arrest or legal action unless you act immediately, often over the phone or via video call. Real police never arrest people over a phone call or demand money to avoid arrest.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_impersonation_scam"
    ),
    ScamExample(
        id = 35,
        messageText = "Hello ji Aapke father ko accident ho gaya hai. Turant 50,000 bhej dijiye hospital ke liye.",
        isScam = true,
        explanation = "This message pretends a family member has been hospitalised and demands money be sent immediately. Scammers use fake family emergencies deliberately because panic stops people from checking first. Always call your family member directly before sending any money.",
        indicators = listOf("Artificial urgency / countdown", "Exploits fear or a fake emergency"),
        difficulty = 3,
        region = "IN",
        scamCategory = "fake_emergency_scam"
    ),
    ScamExample(
        id = 36,
        messageText = "Dhyan se suniye Aapke khilaaf legal action initiate kiya gaya hai.",
        isScam = true,
        explanation = "This message threatens arrest or legal action unless you act immediately, often over the phone or via video call. Real police never arrest people over a phone call or demand money to avoid arrest.",
        indicators = listOf("Impersonates police, court, or a government office"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_impersonation_scam"
    ),
    ScamExample(
        id = 38,
        messageText = "Dhyan se suniye Papa train mein baith gaya hoon, safely pahunch jaunga.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 39,
        messageText = "Law enforcement se bol raha hoon. Aapki morphed photos viral ho rahi hain. Cyber cell ne case register kar liya hai.",
        isScam = true,
        explanation = "This message claims police or authorities have compromising information and demands payment to avoid consequences. This is a fear-based scam. Real authorities do not blackmail people for money.",
        indicators = listOf("Impersonates police, court, or a government office"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_blackmail_scam"
    ),
    ScamExample(
        id = 40,
        messageText = "Madam ek minute Bhai aaj shaam ko milte hain, coffee pe baat karte hain.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 41,
        messageText = "Special Investigation Team se bol raha hoon. Aapke number se explicit material WhatsApp groups mein bheja ja raha hai. FIR ho chuki hai.",
        isScam = true,
        explanation = "This message falsely accuses you of sending explicit material and claims a police case (FIR) has already been filed, using shame and fear of legal trouble to pressure you into acting immediately. Real police do not open cases over a phone call or contact you this way - verify any such claim directly with local authorities before responding.",
        indicators = listOf("Impersonates police, court, or a government office", "Threatens legal consequences to demand payment"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_blackmail_scam"
    ),
    ScamExample(
        id = 42,
        messageText = "Madam ek minute Doctor appointment kal 9 baje hai, fasting rehna.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 43,
        messageText = "Aap sun rahe hain na? Aapka order deliver ho chuka hai. Feedback de dena.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence", "Matches an action you actually took (a real order/purchase)"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 44,
        messageText = "Ji haan suniye Aapke documents ka misuse karke financial fraud kiya gaya hai.",
        isScam = true,
        explanation = "This message threatens arrest or legal action unless you act immediately, often over the phone or via video call. Real police never arrest people over a phone call or demand money to avoid arrest.",
        indicators = listOf("Exploits fear or a fake emergency"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_impersonation_scam"
    ),
    ScamExample(
        id = 45,
        messageText = "Hello ma'am Aapka order deliver ho chuka hai. Feedback de dena.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence", "Matches an action you actually took (a real order/purchase)"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 46,
        messageText = "Ji namaskar School fees ki last date kal hai, payment kar dena.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 47,
        messageText = "Hello sir Ek gang aapke number ka misuse karke gande videos bana raha hai. Turant verify karna hoga.",
        isScam = true,
        explanation = "This message claims police or authorities have compromising information and demands payment to avoid consequences. This is a fear-based scam. Real authorities do not blackmail people for money.",
        indicators = listOf("Artificial urgency / countdown", "Exploits fear or a fake emergency"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_blackmail_scam"
    ),
    ScamExample(
        id = 49,
        messageText = "Madam ek minute Aapka order deliver ho chuka hai. Feedback de dena.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence", "Matches an action you actually took (a real order/purchase)"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 50,
        messageText = "Namaste ma'am Project ka review meeting kal 4 baje hai kya?",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 51,
        messageText = "National Cyber Cell se bol raha hoon. Aapka KYC pending hai. Account 2 ghante mein block ho jayega. OTP share kijiye.",
        isScam = true,
        explanation = "This message impersonates a police or cyber-crime department and demands your OTP to stop your account being blocked. Real police and banks never ask for your OTP over a phone call. Sharing it hands over full control of your account.",
        indicators = listOf("Artificial urgency / countdown", "Threat to block or suspend account", "Asks for OTP, password, or bank/KYC details"),
        difficulty = 3,
        region = "IN",
        scamCategory = "bank_kyc_otp_scam"
    ),
    ScamExample(
        id = 52,
        messageText = "Cyber Crime Department se bol raha hoon. Aapka KYC pending hai. Account 2 ghante mein block ho jayega. OTP share kijiye.",
        isScam = true,
        explanation = "This message impersonates a police or cyber-crime department and demands your OTP to stop your account being blocked. Real police and banks never ask for your OTP over a phone call. Sharing it hands over full control of your account.",
        indicators = listOf("Artificial urgency / countdown", "Threat to block or suspend account", "Asks for OTP, password, or bank/KYC details"),
        difficulty = 3,
        region = "IN",
        scamCategory = "bank_kyc_otp_scam"
    ),
    ScamExample(
        id = 55,
        messageText = "Inspector Rajesh Kumar bol raha hoon. Aapke number se explicit material WhatsApp groups mein bheja ja raha hai. FIR ho chuki hai.",
        isScam = true,
        explanation = "This message claims police or authorities have compromising information and demands payment to avoid consequences. This is a fear-based scam. Real authorities do not blackmail people for money.",
        indicators = listOf("Impersonates police, court, or a government office", "Threatens legal consequences to demand payment"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_blackmail_scam"
    ),
    ScamExample(
        id = 56,
        messageText = "National Cyber Cell se bol raha hoon. Aapki morphed photos viral ho rahi hain. Cyber cell ne case register kar liya hai.",
        isScam = true,
        explanation = "This message claims police or authorities have compromising information and demands payment to avoid consequences. This is a fear-based scam. Real authorities do not blackmail people for money.",
        indicators = listOf("Impersonates police, court, or a government office"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_blackmail_scam"
    ),
    ScamExample(
        id = 57,
        messageText = "Namaste ma'am Aapke WhatsApp se ladies ko asleel messages bheje ja rahe hain. Aapko suspect maana ja raha hai.",
        isScam = true,
        explanation = "This message claims police or authorities have compromising information and demands payment to avoid consequences. This is a fear-based scam. Real authorities do not blackmail people for money.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_blackmail_scam"
    ),
    ScamExample(
        id = 58,
        messageText = "Aap sun rahe hain na? Beta ghar aa gaya hoon, darwaza khol do.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 60,
        messageText = "Law enforcement se bol raha hoon. Aapke naam par money laundering case register hua hai.",
        isScam = true,
        explanation = "This message threatens arrest or legal action unless you act immediately, often over the phone or via video call. Real police never arrest people over a phone call or demand money to avoid arrest.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 3,
        region = "IN",
        scamCategory = "police_impersonation_scam"
    ),
    ScamExample(
        id = 61,
        messageText = "Subject: Training session notes  I booked the lab for next Wednesday. The calendar invite includes location and equipment details.  Thanks for reviewing the document.  Best, Taylor Khan",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 62,
        messageText = "Subject: Documentation review  Thanks for attending the workshop. Here are the links to references and the slide deck.  Feel free to add notes before we meet.  Best, Jamie Singh",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 63,
        messageText = "Subject: Weekly status report  Quick reminder about the training session tomorrow. The materials are in the shared drive.  Feel free to add notes before we meet.  Best, Avery Kim",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 64,
        messageText = "Subject: Quarterly roadmap  Quick reminder about the training session tomorrow. The materials are in the shared drive.  Feel free to add notes before we meet.  Best, Alex Singh",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 65,
        messageText = "Subject: Budget planning  Please find the minutes from our last meeting. Action items are listed at the end for convenience.  Let me know if the timing works for you.  Best, Casey Brown",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 66,
        messageText = "Subject: Budget planning  Hello, confirming our meeting on Thursday at 2 PM in the conference room. Please bring the draft slides.  Appreciate your feedback on this draft.  Best, Casey Brown",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 67,
        messageText = "Subject: Lab booking confirmation  Attaching the draft proposal. Feedback by Friday would be appreciated so we can finalize for the client.  Appreciate your feedback on this draft.  Best, Avery Kim",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 68,
        messageText = "Subject: Team lunch planning  Hi team, sharing notes from today's sync. Next steps are in the shared doc. Let me know your thoughts.  Feel free to add notes before we meet.  Best, Taylor Nguyen",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 69,
        messageText = "Subject: Budget planning  Thanks for attending the workshop. Here are the links to references and the slide deck.  Appreciate your feedback on this draft.  Best, Taylor Martinez",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 70,
        messageText = "Subject: Code review summary  Thanks for your help on the analysis. I've pushed the changes and added comments in the PR for clarity.  Appreciate your feedback on this draft.  Best, Jamie Nguyen",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 71,
        messageText = "Subject: Research discussion  Hi team, sharing the latest notes from today's sync. Next steps are attached in the document. Let me know your thoughts.  I'll share the slides later today.  Best, Alex Martinez",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 72,
        messageText = "Subject: Lab availability  Please find the minutes from our last meeting. Action items are listed at the end of the note for convenience.  Feel free to add notes before we meet.  Best, Jordan Lee",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 73,
        messageText = "Subject: Workshop recap  Hi team, sharing notes from today's sync. Next steps are in the shared doc. Let me know your thoughts.  Let me know if the timing works for you.  Best, Taylor Singh",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 74,
        messageText = "Subject: Schedule confirmation  Hello, confirming our meeting on Thursday at 2 PM in the conference room. Please bring the draft slides for review.  Feel free to add notes before we meet.  Best, Morgan Nguyen",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 75,
        messageText = "Subject: Code review summary  Thanks for your help on the analysis. I've pushed the changes and added comments in the PR for clarity.  I'll share the slides later today.  Best, Jordan Patel",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 76,
        messageText = "Hello, this is the police security team. There are policy concerns on your account. To remain in good standing, follow the confidential steps to review your account today. Failure to respond may result in a restriction.",
        isScam = true,
        explanation = "This message impersonates a person or organisation with authority to pressure you into acting immediately. Real authorities give you time and official channels to verify who they are. They don't demand instant action over text or call.",
        indicators = listOf("Impersonates police, court, or a government office"),
        difficulty = 3,
        region = "GLOBAL",
        scamCategory = "police_impersonation_scam"
    ),
    ScamExample(
        id = 78,
        messageText = "Dear user, your account requires verification. To restore access, reset your password and enter your username, 2FA verification code, and recovery key.",
        isScam = true,
        explanation = "This message asks you to 'log in' or 'verify' through a link to a fake page designed to steal your username and password. Never enter your login details after clicking a link in a message. Go to the official site or app directly instead.",
        indicators = listOf("Unexpected prize claim", "Asks for OTP, password, or bank/KYC details"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 79,
        messageText = "Hello, I remembered you mentioning the children's hospital charity gala where we ran into each other. I'm a stranded traveler right now; the hotel is asking for payment verification to extend stay. If you could assist with an emergency loan, I can provide replacement cards soon—trust building is important to me.",
        isScam = true,
        explanation = "This message builds trust or urgency through a believable, personalised story to manipulate you into acting fast. The more convincing and specific a message feels, the more important it is to pause and verify it independently.",
        indicators = listOf("Artificial urgency / countdown", "Exploits fear or a fake emergency", "Uses an official-sounding but unverifiable name"),
        difficulty = 3,
        region = "GLOBAL",
        scamCategory = "romance_or_stranded_traveler_scam"
    ),
    ScamExample(
        id = 80,
        messageText = "Dear customer, you have a new notification. Please click here to view the update. For your convenience, a link and attachment are included. Do not reply to this auto-generated message.",
        isScam = true,
        explanation = "This message tries to get you to click a link or hand over personal information under a false pretext. When a message asks you to click, log in, or share details urgently, treat it as a red flag until you verify it independently.",
        indicators = listOf("Pushes you to click or log in"),
        difficulty = 3,
        region = "GLOBAL",
        scamCategory = "generic_phishing"
    ),
    ScamExample(
        id = 81,
        messageText = "Notice, we detected a login attempt on your account. To restore access, please update your information. Your account may be locked until you reactivate it.",
        isScam = true,
        explanation = "This message tries to manipulate your emotions or trust to get you to act without thinking. Genuine organisations don't rely on pressure or guilt to get a response from you.",
        indicators = listOf("Uses an official-sounding but unverifiable name"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 82,
        messageText = "Dear customer, I remembered you mentioning the children's hospital charity gala where we ran into each other. I'm a stranded traveler right now; the hotel is asking for payment verification to extend stay. If you could assist with an emergency loan, I can provide replacement cards soon—trust building is important to me.",
        isScam = true,
        explanation = "This message builds trust or urgency through a believable, personalised story to manipulate you into acting fast. The more convincing and specific a message feels, the more important it is to pause and verify it independently.",
        indicators = listOf("Artificial urgency / countdown", "Exploits fear or a fake emergency"),
        difficulty = 3,
        region = "GLOBAL",
        scamCategory = "romance_or_stranded_traveler_scam"
    ),
    ScamExample(
        id = 83,
        messageText = "Attention, the IT department detected a malware infection on your workstation. Run the antivirus scan and install the security patch from the provided link to fix the issue.",
        isScam = true,
        explanation = "This message impersonates a tech support service urging you to call about a computer problem. Genuine tech companies don't contact you first about a virus. This is designed to scare you into calling a scam number.",
        indicators = listOf("Fake virus/tech-support alert", "Uses an official-sounding but unverifiable name"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "tech_support_scam"
    ),
    ScamExample(
        id = 84,
        messageText = "Attention, your login has been locked. Use the secure link to update password and restore access. Enter your verification code to continue.",
        isScam = true,
        explanation = "This message asks you to 'log in' or 'verify' through a link to a fake page designed to steal your username and password. Never enter your login details after clicking a link in a message. Go to the official site or app directly instead.",
        indicators = listOf("Asks for OTP, password, or bank/KYC details"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 85,
        messageText = "Notice, we detected a login attempt on your account. To authenticate access, please confirm your information. Your account may be temporarily on hold until you reactivate it.",
        isScam = true,
        explanation = "This message tries to manipulate your emotions or trust to get you to act without thinking. Genuine organisations don't rely on pressure or guilt to get a response from you.",
        indicators = listOf("Threat to block or suspend account", "Asks for OTP, password, or bank/KYC details"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 86,
        messageText = "Attention, this is an urgent notice regarding your account. Your access will expire soon. To avoid interruption, act now and follow the instructions immediately. This is time sensitive and ends soon.",
        isScam = true,
        explanation = "This message uses urgent, pressuring language to stop you from thinking it through. Scammers rely on urgency because a calm, careful reader is much harder to trick.",
        indicators = listOf("Unexpected prize claim", "Artificial urgency / countdown"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 88,
        messageText = "Dear user, the IT department detected a malware infection on your workstation. Run the antivirus scan and install the security patch from the provided link to fix the issue.",
        isScam = true,
        explanation = "This message impersonates a tech support service urging you to call about a computer problem. Genuine tech companies don't contact you first about a virus. This is designed to scare you into calling a scam number.",
        indicators = listOf("Fake virus/tech-support alert", "Too-good-to-be-true free offer", "Uses an official-sounding but unverifiable name"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "tech_support_scam"
    ),
    ScamExample(
        id = 89,
        messageText = "Account holder, congratulations! You are the selected recipient of a reward. To collect your gift of £2,500, please provide your credit card to process the sum. This deal is limited time.",
        isScam = true,
        explanation = "This message dangles a financial opportunity or threat to get you to act quickly with money or bank details. Genuine financial matters are never resolved through a rushed text message link.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 90,
        messageText = "Account holder, your account has been suspended. Use the secure link to reset password and restore access. Enter your verification code to continue.",
        isScam = true,
        explanation = "This message asks you to 'log in' or 'verify' through a link to a fake page designed to steal your username and password. Never enter your login details after clicking a link in a message. Go to the official site or app directly instead.",
        indicators = listOf("Unexpected prize claim", "Threat to block or suspend account", "Asks for OTP, password, or bank/KYC details"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 93,
        messageText = "LOL what happens in Vegas stays in vegas",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 94,
        messageText = "Yeah I am, so I'll leave maybe 7ish?",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 95,
        messageText = "Hey you gave them your photo when you registered for driving ah? Tmr wanna meet at yck?",
        isScam = false,
        explanation = "This is a routine event invitation. Attendance is optional and it doesn't ask for money or sensitive information.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 98,
        messageText = "I place all ur points on e cultures module already.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 99,
        messageText = "I take it the post has come then! You must have 1000s of texts now! Happy reading. My one from wiv hello caroline at the end is my favourite. Bless him",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 100,
        messageText = "ok....take care.umma to you too...",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 101,
        messageText = "No need lar i go engin? Cos my sis at arts today...",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 102,
        messageText = "May b approve panalam...but it should have more posts..",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 103,
        messageText = "Er yeah, i will b there at 15:26, sorry! Just tell me which pub/cafe to sit in and come wen u can",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 104,
        messageText = "Ugh hopefully the asus ppl dont randomly do a reformat.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 106,
        messageText = "Have you had your mobile 11+ months? You're entitled to upgrade to the latest colour camera mobile for free! Call the mobile upgrade company free on 08002986906",
        isScam = true,
        explanation = "This message offers a 'free' phone upgrade if you call a number. Free upgrade offers out of nowhere, requiring an urgent call, are a classic bait tactic to charge hidden fees.",
        indicators = listOf("Urgent call-back number", "Too-good-to-be-true free offer", "Uses an official-sounding but unverifiable name"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 107,
        messageText = "You have won £1,000 cash or a £2,000 prize! To claim, call 09050000327",
        isScam = true,
        explanation = "This message claims you've won a cash prize you never entered for and gives a number to call to claim it. You can't win something you never entered. Legitimate prizes are never claimed by calling a premium-rate number.",
        indicators = listOf("Urgent call-back number", "Unexpected prize claim", "Reply/call triggers a premium-rate charge"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 108,
        messageText = "urgent! last weekend's draw shows that you possess gain £1000 cash or a spanish holiday! call now 09050000332 to claim. t&c: rstm, sw7 3ss. 150ppm",
        isScam = true,
        explanation = "This message claims you've won a cash prize or gift and gives a phone number to call to claim it. You can't win something you never entered. Legitimate prizes are never claimed by calling a premium-rate number.",
        indicators = listOf("Urgent call-back number", "Artificial urgency / countdown", "Reply/call triggers a premium-rate charge"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 109,
        messageText = "today's offer! claim ur £150 worth of discount vouchers! text yes to 85023 now! savamob, member offers mobile! t cs 08717898035. £3.00 sub. 16 . unsub reply x",
        isScam = true,
        explanation = "This message claims you've won a cash prize or gift and gives a phone number to call to claim it. You can't win something you never entered. Legitimate prizes are never claimed by calling a premium-rate number.",
        indicators = listOf("Artificial urgency / countdown", "Reply/call triggers a premium-rate charge", "Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 110,
        messageText = "Please call 08712402779 immediately as there is an urgent message waiting for you",
        isScam = true,
        explanation = "This message creates urgency about an 'important message waiting' with no details, just a number to call. Vague urgency with no explanation is a classic trick to get you to call a premium-rate number.",
        indicators = listOf("Urgent call-back number", "Artificial urgency / countdown"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "premium_rate_call_scam"
    ),
    ScamExample(
        id = 111,
        messageText = "free message activate your 500 free text messages by replying to this message with the word free for terms & conditions, visit www.07781482378.com",
        isScam = true,
        explanation = "This message uses an urgent phone number and a vague, exciting claim to get you to call. Urgent unsolicited messages with a phone number to call immediately are a common scam pattern. Don't call. Verify independently instead.",
        indicators = listOf("Suspicious or shortened link", "Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 112,
        messageText = "Today's Vodafone numbers ending 7148 have been selected to win a Rs. 2,00,000 award. If you have a match, please call XXXXXXXXXX and quote claim code 7834. Standard rates apply.",
        isScam = true,
        explanation = "This message claims your phone number was randomly selected to win a large cash award. Real prize draws don't randomly select phone numbers and ask you to call a number to claim. This is a lure to get you calling a premium-rate line.",
        indicators = listOf("Urgent call-back number", "Unexpected prize claim"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 113,
        messageText = "Dear (Paytm) Customer, your Paytm KYC has been suspended. Contact Paytm office on XXXXXXXXXX — your Paytm account will be blocked within 24hr. Thank you.",
        isScam = true,
        explanation = "This message threatens your Paytm account will be blocked unless you contact a number urgently about KYC. Payment apps never threaten account blocks through unsolicited texts. Check your account directly in the official app instead.",
        indicators = listOf("Artificial urgency / countdown", "Threat to block or suspend account", "Asks for OTP, password, or bank/KYC details"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "bank_kyc_otp_scam"
    ),
    ScamExample(
        id = 114,
        messageText = "You have won! As a valued Vodafone customer, our computer has picked you to win a £150 prize. To collect is easy — just call 09061743386",
        isScam = true,
        explanation = "This message claims you've been randomly picked to win a prize as a 'valued customer'. Genuine loyalty rewards are never claimed by calling an unfamiliar phone number. This is a classic prize-lure scam.",
        indicators = listOf("Urgent call-back number", "Unexpected prize claim", "Reply/call triggers a premium-rate charge"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 115,
        messageText = "loa!! homeowners + tenants welcome. have you be previously refused? we can still help. call free 0800 1956669 or text back 'help'",
        isScam = true,
        explanation = "This message uses an urgent phone number and a vague, exciting claim to get you to call. Urgent unsolicited messages with a phone number to call immediately are a common scam pattern. Don't call. Verify independently instead.",
        indicators = listOf("Urgent call-back number", "Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "loan_advance_fee_scam"
    ),
    ScamExample(
        id = 116,
        messageText = "You have a new voicemail message waiting! To listen, call from your mobile or landline: 09064017305, PO Box 75, LDN S7",
        isScam = true,
        explanation = "This message is vague and mysterious on purpose, saying 'someone knows you', to tempt you into calling a paid number out of curiosity. This is a curiosity-bait scam designed to rack up call charges, not a real personal contact.",
        indicators = listOf("Urgent call-back number", "Artificial urgency / countdown", "Reply/call triggers a premium-rate charge"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "premium_rate_call_scam"
    ),
    ScamExample(
        id = 117,
        messageText = "please call our customer service representative on XXXXXXXXXX between 10am-9pm as you have w0n a guaranteed cash prize of rs.7,00,000. fl1pkart team",
        isScam = true,
        explanation = "This message claims you've won a cash prize or gift and gives a phone number to call to claim it. You can't win something you never entered. Legitimate prizes are never claimed by calling a premium-rate number.",
        indicators = listOf("Urgent call-back number", "Unexpected prize claim", "Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 118,
        messageText = "do you want a new video phone? 600 anytime any network mins 400 inclusive video calls and download 5 per week free deltomorrow call 08002888812 or reply now",
        isScam = true,
        explanation = "This message offers a 'free' phone upgrade if you call a number. Free upgrade offers out of nowhere, requiring an urgent call, are a classic bait tactic to charge you hidden fees.",
        indicators = listOf("Urgent call-back number", "Artificial urgency / countdown", "Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 119,
        messageText = "ATM Block: Dear customer, due to a system upgrade your ATM card has just been de-activated. To reactivate, call customer care on XXXXXXXXXX now.",
        isScam = true,
        explanation = "This message claims your ATM card was deactivated due to a 'system upgrade' and gives an urgent number to call. Banks don't deactivate cards through text alerts with a call-back number. Contact your bank directly through their official number instead.",
        indicators = listOf("Urgent call-back number", "Artificial urgency / countdown", "Threat to block or suspend account"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "bank_kyc_otp_scam"
    ),
    ScamExample(
        id = 120,
        messageText = "not heard from u4 a while. call our chat line to catch up: 01223585334. Rates apply.",
        isScam = true,
        explanation = "This message uses an urgent phone number and a vague, exciting claim to get you to call. Urgent unsolicited messages with a phone number to call immediately are a common scam pattern. Don't call. Verify independently instead.",
        indicators = listOf("Urgent call-back number"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "premium_rate_call_scam"
    ),
    ScamExample(
        id = 121,
        messageText = "had your mobile 10 mths? update to latest orange camera/video phones for free. save £s with free texts/weekend calls. text yes for a callback orno to choose out",
        isScam = true,
        explanation = "This is a marketing spam message offering a free phone upgrade to get you to text back. Mostly a nuisance rather than a serious threat, but replying can lead to hidden charges. Best to ignore it.",
        indicators = listOf("Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 122,
        messageText = "price revise soon!! agni property recommends spaze privy the address in sec-93, gurgaon@28 lac onwards. 2/3/4 bhk apts available.to book call XXXXXXXXXX now!",
        isScam = true,
        explanation = "This message asks for your personal address and date of birth in exchange for a vague 'trial' offer. Legitimate trials don't need your date of birth upfront from a random text. Treat this as a data-harvesting attempt.",
        indicators = listOf("Urgent call-back number", "Artificial urgency / countdown", "Requests personal/identity information"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 123,
        messageText = "Latest Nokia mobile or iPod MP3 player + £400 prize guaranteed! Reply with 'WIN' to 83355 now! Norcorp Ltd. £1.50/msg received, 18+",
        isScam = true,
        explanation = "This is a marketing spam message offering a free or discounted item to get you to reply to a premium-rate number. Mostly a nuisance rather than a serious threat, but replying can lead to hidden charges. Best to ignore it.",
        indicators = listOf("Unexpected prize claim", "Artificial urgency / countdown", "Reply/call triggers a premium-rate charge"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 124,
        messageText = "Your exclusive video content is ready! Text back the keyword PICSFREE1 to get the next one.",
        isScam = true,
        explanation = "This is a spam message using a vague 'exclusive content' hook to get you to text back a keyword, which can trigger ongoing charges. There's no real content, just bait to get a reply.",
        indicators = listOf("Too-good-to-be-true free offer", "Reply triggers ongoing charges"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 125,
        messageText = "You have a new connection request on our community app! Reply YES-440 or NO-440 to see who added you: www.sms.ac/u/nat27081980. To stop, text STOP FRND to 62468",
        isScam = true,
        explanation = "This is a spam message imitating a social app notification to get you to reply to a paid text number. Replying can lead to ongoing premium-rate charges. Don't reply, just delete it.",
        indicators = listOf("Suspicious or shortened link", "Reply triggers a premium-rate charge"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 126,
        messageText = "what flower dress dot compare herself to? d= violet e= tulip f= lily txt d e or f to 84025 now 4 chance 2 win £100 cash",
        isScam = true,
        explanation = "This is a spam quiz text designed to get you to reply to a premium-rate number under the guise of a fun game. There's no real prize verification, just a way to generate charges.",
        indicators = listOf("Too-good-to-be-true free offer", "Reply/call triggers a premium-rate charge"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 127,
        messageText = "New members club offer just for you, claim your welcome gift! Please leave a message today with your area: 09099726553. Reply promised, Customer Team. Calls £1/min, more from PO Box 177, HP5 1FL.",
        isScam = true,
        explanation = "This is a spam message pretending to be a personal invitation to get you to call a premium-rate number. Don't call. This is designed to generate call charges, not a real personal contact.",
        indicators = listOf("Urgent call-back number", "Reply/call triggers a premium-rate charge"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "premium_rate_call_scam"
    ),
    ScamExample(
        id = 128,
        messageText = "Hi! We're looking for people to trial new products for Champneys spa. To sign you up, just reply with your full address and date of birth. Thanks!",
        isScam = true,
        explanation = "This message asks for your personal address and date of birth in exchange for a vague 'trial' offer. Legitimate trials don't need your date of birth upfront from a random text. Treat this as a data-harvesting attempt.",
        indicators = listOf("Requests personal/identity information"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "credential_harvesting_scam"
    ),
    ScamExample(
        id = 129,
        messageText = "Unlimited entertainment content direct 2 your mobile txt CONTENT to 69200 & get free access for 24 hrs then chrgd@50p per day txt stop 2exit. this msg is free",
        isScam = true,
        explanation = "This offers 'free' content but the fine print reveals a recurring daily charge once you reply. This bait-and-switch pricing pattern is a common way spam texts generate ongoing charges.",
        indicators = listOf("Too-good-to-be-true free offer", "Hidden recurring charge in the fine print"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "premium_rate_call_scam"
    ),
    ScamExample(
        id = 130,
        messageText = "FREE for 1st week! No1 Nokia tone 4 ur mobile every week just txt NOKIA to 8077  www.getzed.co.uk",
        isScam = true,
        explanation = "This is a promotional spam message offering a free ringtone to get you to text a paid short code. The first week may be free, but charges typically follow.",
        indicators = listOf("Too-good-to-be-true free offer", "Vague terms, no real product named"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 131,
        messageText = "claire here am havin borin time & am now alone, fancy a chat 2nite? call now 09099725823 hope 2 c u luv claire xx calls£1/minmoremobsemspobox45po139wa",
        isScam = true,
        explanation = "This message pretends to be a personal invitation from someone you know to get you to call a premium-rate number. It's designed to generate call charges, not a real personal contact.",
        indicators = listOf("Urgent call-back number", "Artificial urgency / countdown", "Reply/call triggers a premium-rate charge"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "premium_rate_call_scam"
    ),
    ScamExample(
        id = 132,
        messageText = "5p 4 alfie moon's children in need song on ur mob. tell ur m8s. txt tone charity to 8007 for nokias or poly charity for polys: zed 08701417012 profit 2 charity.",
        isScam = true,
        explanation = "This mixes a small charity donation with a paid ringtone service, using a good cause to encourage you to text a premium number. Always donate through a charity's official website instead.",
        indicators = listOf("Reply/call triggers a premium-rate charge", "Uses a good cause to encourage a paid reply"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 133,
        messageText = "i'd love to chat and get to know you. call me 09094646631 just 60p/min. to opt out call 08712460324 (nat rate)",
        isScam = true,
        explanation = "This message pretends to be a personal invitation to get you to call a premium-rate number. Calling racks up charges by the minute, with no real person waiting on the other end the way it implies.",
        indicators = listOf("Urgent call-back number"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "premium_rate_call_scam"
    ),
    ScamExample(
        id = 134,
        messageText = "sppok up ur mob with a halloween collection of nokia logo&pic message plus a free eerie tone, txt card spook to 8007",
        isScam = true,
        explanation = "This is a promotional spam message offering a free ringtone or logo to get you to text a paid short code. What's advertised as free often comes with ongoing charges.",
        indicators = listOf("Too-good-to-be-true free offer", "Vague terms, no real product named"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 135,
        messageText = "Free entry into a weekly competition for a chance to win an iPod! Text POD to 80182 to confirm entry, or call 08452810073",
        isScam = true,
        explanation = "This is a marketing spam message offering entry into a prize competition to get you to text a paid number. Mostly a nuisance rather than a serious threat, but replying can lead to hidden charges. Best to ignore it.",
        indicators = listOf("Urgent call-back number", "Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 136,
        messageText = "Rezept für klassische Kartoffelsuppe: 500 g Kartoffeln, 1 Zwiebel, 200 ml Sahne und frische Petersilie. Schritt‑für‑Schritt-Anleitung im Anhang.",
        isScam = false,
        explanation = "This is just a casual recipe someone is sharing. Nothing about money, accounts, or personal details.",
        indicators = listOf("Casual personal message between people who know each other"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 137,
        messageText = "Guten Abend! Passieren heute Abend etwas Interessantes?",
        isScam = false,
        explanation = "This is an everyday personal message between friends or family. Nothing asks for money or private information.",
        indicators = listOf("Casual personal message between people who know each other"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 138,
        messageText = "Wir treffen uns am Samstag im Park, um gemeinsam zu joggen.",
        isScam = false,
        explanation = "This is an everyday personal message between friends or family. Nothing asks for money or private information.",
        indicators = listOf("Casual personal message between people who know each other"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 139,
        messageText = "Kannst du mir bitte einen guten Restaurantempfehlung in Berlin geben?",
        isScam = false,
        explanation = "This is an everyday personal message between friends or family. Nothing asks for money or private information.",
        indicators = listOf("Casual personal message between people who know each other"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 140,
        messageText = "Gestern habe ich im Park einen kleinen Hund gefunden und ihn versorgt.",
        isScam = false,
        explanation = "This is an everyday personal message between friends or family. Nothing asks for money or private information.",
        indicators = listOf("Casual personal message between people who know each other"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 141,
        messageText = "Guten Morgen! Wie können wir Ihnen heute helfen?",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 142,
        messageText = "Ich freue mich auf das Wochenende, weil ich meine Freunde besuchen will.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 143,
        messageText = "Mein Sohn hat in der Schule ein neues Projekt über erneuerbare Energien begonnen und ist sehr stolz.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 144,
        messageText = "Heutige Wettervorhersage: Sonne den ganzen Tag in München.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 145,
        messageText = "Die Universität hat kürzlich einen paper über künstliche Intelligenz veröffentlicht, der sehr spannend ist.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 146,
        messageText = "Der Wetterbericht prognostiziert milden Regen heute.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 147,
        messageText = "Bitte beachte die neuen Öffnungszeiten der Bibliothek: Montag‑Freitag 9‑18 Uhr.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 148,
        messageText = "Bewundern Sie diese wunderschöne Villa in den Pyrenäen! Nur für ausgewählte Gastgeber.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 149,
        messageText = "Die Tagesschau berichtete heute über das Wetter und die Temperaturen.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 150,
        messageText = "Bitte beachte, dass das Meeting um 15:00 Uhr beginnt.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 151,
        messageText = "Registrieren Sie sich jetzt und sichern Sie sich exklusive Rabatte.",
        isScam = true,
        explanation = "This is a marketing spam message offering a free or discounted item to get you to text a paid number or click a link. It's mostly a nuisance rather than a serious threat, but replying can lead to hidden charges. Best to ignore it.",
        indicators = listOf("Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "DE",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 152,
        messageText = "Exklusive Rabatte nur für kurze Zeit – jetzt anmelden und gratis testen!",
        isScam = true,
        explanation = "This is a vague marketing spam message using a fake 'exclusive discount' hook to get you to sign up or click. These offers often lead to hidden costs or your data being collected.",
        indicators = listOf("Too-good-to-be-true free offer", "Vague terms, no real product named"),
        difficulty = 1,
        region = "DE",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 153,
        messageText = "Wir haben einen Betrugsversuch auf Ihrem Konto festgestellt! Geben Sie Ihre Bankdaten auf diese Seite ein, um Ihr Konto zu blockieren.",
        isScam = true,
        explanation = "This message either asks for your bank details directly or promises a big financial reward if you pay or transfer money first. Real banks and legitimate offers never work this way. Treat any message asking for money upfront or account details as a serious red flag.",
        indicators = listOf("Threat to block or suspend account", "Asks for OTP, password, or bank/KYC details"),
        difficulty = 1,
        region = "DE",
        scamCategory = "credential_harvesting_scam"
    ),
    ScamExample(
        id = 154,
        messageText = "Schnell Geld verdienen! Überweisen Sie uns einmalig eine kleine Gebühr und erhalten Sie das 3-fache Ihres Geldes zurück!",
        isScam = true,
        explanation = "This message either asks for your bank details directly or promises a big financial reward if you pay or transfer money first. Real banks and legitimate offers never work this way. Treat any message asking for money upfront or account details as a serious red flag.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 1,
        region = "DE",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 155,
        messageText = "Kaufen Sie jetzt und erhalten Sie 3 Spiele gratis dazu – mehr Infos auf www.free-games.de",
        isScam = true,
        explanation = "This is a marketing spam message offering a free or discounted item to get you to text a paid number or click a link. It's mostly a nuisance rather than a serious threat, but replying can lead to hidden charges. Best to ignore it.",
        indicators = listOf("Suspicious or shortened link", "Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "DE",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 156,
        messageText = "Entdecken Sie das Geheimnis des raschen Abnehmens – nur 3 Tage, 5 kg weniger! Klicken Sie hier und starten Sie noch heute.",
        isScam = true,
        explanation = "This is a clickbait ad promising unrealistic fast results to get you to click a link. Miracle-result claims like this are almost always designed to sell something or install unwanted software. Best ignored.",
        indicators = listOf("Pushes you to click or log in"),
        difficulty = 1,
        region = "DE",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 157,
        messageText = "Achtung: Ihr Passwort wurde gestohlen (Code: 123alfa456). Dadurch sind Ihre Bonuspunkte auf Ihrem Konto ungültig geworden.",
        isScam = true,
        explanation = "This message is trying to scare you about a 'stolen password' and lost bonus points using invented-sounding codes. Real companies don't threaten you with vague 'account punishment' messages like this. Treat it as a red flag, not a reason to panic.",
        indicators = listOf("Asks for OTP, password, or bank/KYC details"),
        difficulty = 1,
        region = "DE",
        scamCategory = "credential_harvesting_scam"
    ),
    ScamExample(
        id = 158,
        messageText = "WISSEN IST MACHT! LERNEN SIE MEHR! JETZT!",
        isScam = true,
        explanation = "This is unsolicited marketing or nuisance spam rather than a targeted scam. Annoying, but not asking for money or personal details directly. Still best to ignore and not reply.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 1,
        region = "DE",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 159,
        messageText = "Sonderangebot: Exklusive Inhalte - jetzt freischalten!",
        isScam = true,
        explanation = "This is a vague promotional spam message with no real product or company named, just a generic 'unlock now' hook designed to get clicks.",
        indicators = listOf("Vague terms, no real product named", "Urgency to act now with no explanation"),
        difficulty = 1,
        region = "DE",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 160,
        messageText = "Übermäßiger Gewinn: Kaufen Sie jetzt 100 kg Gold zu einem günstigen Preis!!",
        isScam = true,
        explanation = "This message either asks for your bank details directly or promises a big financial reward if you pay or transfer money first. Real banks and legitimate offers never work this way. Treat any message asking for money upfront or account details as a serious red flag.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 1,
        region = "DE",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 161,
        messageText = "Empfehlen Sie unseren neuen KI-Assistenten weiter und laden Sie unsere App 'iPet' kostenlos im App Store herunter!",
        isScam = true,
        explanation = "This is a vague ad for an app or AI assistant with no real details. Vague promotional messages like this are usually just spam trying to get installs or clicks. Safe to ignore.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 1,
        region = "DE",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 162,
        messageText = "Exklusiver Gutschein! Nur heute gültig!",
        isScam = true,
        explanation = "This is a vague 'exclusive voucher, valid today only' message. Fake urgency plus a too-good-to-be-true discount is a common trick to get a click without thinking.",
        indicators = listOf("Artificial urgency / countdown", "Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "DE",
        scamCategory = "nuisance_marketing_spam"
    ),
    ScamExample(
        id = 163,
        messageText = "Achtung! Sie haben einen Preis gewonnen! Klicken Sie hier, um Ihr Glück zu claimen!",
        isScam = true,
        explanation = "This message either asks for your bank details directly or promises a big financial reward if you pay or transfer money first. Real banks and legitimate offers never work this way. Treat any message asking for money upfront or account details as a serious red flag.",
        indicators = listOf("Unexpected prize claim", "Pushes you to click or log in"),
        difficulty = 1,
        region = "DE",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 164,
        messageText = "Werden Sie jetzt zum Experten und gewinnen Sie tolle Preise! Kostenlose Teilnahme – gewinnen Sie sofort!",
        isScam = true,
        explanation = "This is a vague 'become an expert and win prizes instantly' message. Real contests don't promise instant winnings for vague participation. Treat prize messages like this with suspicion.",
        indicators = listOf("Artificial urgency / countdown", "Too-good-to-be-true free offer"),
        difficulty = 1,
        region = "DE",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 165,
        messageText = "Ihr Paket wurde gestohlen! Klicken Sie hier, um die Nachverfolgung zu starten und den Diebstahl zu melden.",
        isScam = true,
        explanation = "This claims your package was 'stolen' and pushes you to click to start tracking it. Legitimate delivery services never ask you to click an unclear link to 'start tracking' a stolen package. This mimics a real delivery scam pattern.",
        indicators = listOf("Pushes you to click or log in"),
        difficulty = 1,
        region = "DE",
        scamCategory = "delivery_scam"
    ),
    ScamExample(
        id = 166,
        messageText = "Hello Alex, here is your weekly update on the project's progress. Please review and provide feedback.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 167,
        messageText = "Dear Morgan, we are pleased to inform you about our upcoming webinar. Register now to secure your spot.",
        isScam = false,
        explanation = "This is a routine event invitation. Attendance is optional and it doesn't ask for money or sensitive information.",
        indicators = listOf("No urgent deadline or threat attached"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 168,
        messageText = "Please find attached the minutes from our last meeting. Let me know if you have any questions.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 169,
        messageText = "Dear Jordan, your subscription has been successfully renewed. Thank you for your continued support.",
        isScam = false,
        explanation = "This looks like a standard subscription confirmation. It's informing you, not demanding you click a link or pay urgently.",
        indicators = listOf("No urgent deadline or threat attached"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 170,
        messageText = "Dear Morgan, thank you for your purchase. Your order will be shipped soon.",
        isScam = false,
        explanation = "This looks like a normal order or purchase confirmation. It doesn't pressure you to click a link or share private details.",
        indicators = listOf("Matches an action you actually took (a real order/purchase)"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 171,
        messageText = "Hi Chris, just a reminder about the project deadline next Friday. Let's ensure everything is on track.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 172,
        messageText = "Hi Chris, it was great meeting you at the conference. Let's catch up for coffee next week.",
        isScam = false,
        explanation = "This reads like a normal work update between colleagues. It doesn't ask for money, passwords, or urgent action.",
        indicators = listOf("Routine work correspondence"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 173,
        messageText = "Dear Morgan, your subscription has been successfully renewed. Thank you for your continued support.",
        isScam = false,
        explanation = "This looks like a standard subscription confirmation. It's informing you, not demanding you click a link or pay urgently.",
        indicators = listOf("No urgent deadline or threat attached"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 174,
        messageText = "Hi Alex, please find the attached report for this month's performance review.",
        isScam = false,
        explanation = "This message is ordinary and doesn't pressure you to click a link, share personal details, or send money. That's a good sign it's genuine.",
        indicators = listOf("No pressure, no suspicious links, no money or OTP request"),
        difficulty = 1,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 176,
        messageText = "You have a new secure message from your bank. Click here to read it.",
        isScam = true,
        explanation = "This pretends to be a 'secure message' from your bank but gives no real details, just a link. Real banks show account information directly in their app, not through a mystery link in a text.",
        indicators = listOf("Pushes you to click or log in"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 177,
        messageText = "Your payment has been declined. Update your billing information to continue using our service.",
        isScam = true,
        explanation = "This claims a payment failed and pushes you to 'update billing' through a link. Scammers use fake payment failures to trick you into typing card details on a fake page. Check your account directly through the official app instead.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 178,
        messageText = "Your subscription is about to expire. Renew now to continue enjoying our services.",
        isScam = true,
        explanation = "This creates urgency about a subscription expiring to make you click without thinking. Legitimate renewal notices don't need you to act within minutes. Check your account directly if unsure.",
        indicators = listOf("Artificial urgency / countdown"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 179,
        messageText = "We noticed some unusual activity in your account. Log in to review recent transactions.",
        isScam = true,
        explanation = "This claims 'unusual activity' on your account to scare you into clicking a link. This is a classic scare tactic. Go to the official app or website yourself rather than clicking a link in the message.",
        indicators = listOf("Pushes you to click or log in"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 180,
        messageText = "Important: Update your email account settings to avoid service interruption.",
        isScam = true,
        explanation = "This creates a vague sense of urgency about 'updating settings' without saying what's actually wrong. Genuine service messages are specific. Vague urgency plus a link is a red flag.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 181,
        messageText = "Your package delivery is pending. Please provide your personal information to confirm delivery.",
        isScam = true,
        explanation = "This asks you to hand over personal information to 'confirm a delivery'. Real delivery services never need your personal details beyond an address you already gave them. This is a data-harvesting trick.",
        indicators = listOf("Requests personal/identity information"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "delivery_scam"
    ),
    ScamExample(
        id = 182,
        messageText = "Alert: Unusual login attempt detected. Verify your account by clicking here.",
        isScam = true,
        explanation = "This claims an 'unusual login' was detected and pushes you to click and verify. This exact wording is one of the most common phishing tricks. Always check account activity by opening the app directly, not through a link.",
        indicators = listOf("Asks for OTP, password, or bank/KYC details", "Pushes you to click or log in"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 183,
        messageText = "Congratulations! You've won a \$500 gift card. Click here to claim your prize.",
        isScam = true,
        explanation = "This promises a surprise gift card for nothing. You can't win a prize you never entered for. This is a classic too-good-to-be-true lure.",
        indicators = listOf("Unexpected prize claim", "Pushes you to click or log in"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "prize_advance_fee_scam"
    ),
    ScamExample(
        id = 184,
        messageText = "Dear Alex, your account has been compromised. Click the link to reset your password immediately.",
        isScam = true,
        explanation = "This says your account is 'compromised' and demands an immediate password reset via a link. Real companies never ask you to reset your password through a link sent by text or email. Go to the site directly instead.",
        indicators = listOf("Artificial urgency / countdown", "Threat to block or suspend account", "Asks for OTP, password, or bank/KYC details"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "account_verification_phishing"
    ),
    ScamExample(
        id = 185,
        messageText = "Your invoice is attached. Please review and pay promptly to avoid penalties.",
        isScam = true,
        explanation = "This mentions an attached invoice with a demand for prompt payment. Unexpected invoices with payment pressure are a common scam pattern. Verify with the sender directly before paying anything.",
        indicators = listOf("Unsolicited message pressuring quick action"),
        difficulty = 2,
        region = "GLOBAL",
        scamCategory = "generic_phishing"
    ),
    ScamExample(
        id = 186,
        messageText = "HDFC Bank: Your credit card bill of Rs. 4,250 is generated. Due date is 15th. Pay through HDFC app or netbanking to avoid late payment charges.",
        isScam = false,
        explanation = "This is a real credit card bill reminder. It gives an exact amount and due date, not a vague threat, and it tells you to pay through your own banking app rather than a link. Real banks send bill reminders like this every month.",
        indicators = listOf("Sounds urgent because of a due date", "Gives exact bill amount, no vague threat", "Tells you to use your own app, not a link"),
        difficulty = 3,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 187,
        messageText = "Swiggy: We could not deliver your order #45213 as the address given seems incomplete. Please update it within 2 hours or the order will be cancelled and refunded in 5-7 days.",
        isScam = false,
        explanation = "This looks urgent because of the 2 hour window, but it is about an order you actually placed and only asks you to fix your delivery address inside the app, nothing about payment or OTP. Real delivery apps do send time-limited notices like this.",
        indicators = listOf("Has a countdown, which feels like a scam pattern", "Only relates to an order you placed", "No request for OTP, card, or bank details"),
        difficulty = 3,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 188,
        messageText = "Aapka Airtel recharge plan 2 din mein khatam ho raha hai. Bina rukawat ke service ke liye Airtel Thanks app se recharge karein.",
        isScam = false,
        explanation = "This is a plan-expiry reminder from a telecom provider, something every prepaid user gets. It tells you to use the official Airtel Thanks app rather than clicking a random link, and it does not ask for OTP or bank details.",
        indicators = listOf("Mentions your plan ending soon, sounds urgent", "Points you to the official app by name", "No OTP, password, or link requested"),
        difficulty = 3,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 189,
        messageText = "Deutsche Bank: Ihr monatlicher Kontoauszug steht bereit. Loggen Sie sich wie gewohnt in Ihr Online-Banking ein, um ihn anzusehen.",
        isScam = false,
        explanation = "This is a routine statement notice that a bank sends every month. It does not include a link, it just tells you to log in the normal way you always do. Real banks send this kind of reminder without asking you to click anything.",
        indicators = listOf("Mentions your bank account, sounds sensitive", "No link included, just told to log in as usual", "Matches a routine monthly notice"),
        difficulty = 3,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 190,
        messageText = "DHL: Ihre Sendung konnte heute nicht zugestellt werden, da niemand zu Hause war. Bitte vereinbaren Sie einen neuen Liefertermin über die offizielle DHL App oder unsere Webseite.",
        isScam = false,
        explanation = "This is what a real missed delivery notice looks like. It explains exactly what happened and tells you to rebook through the official DHL app or website by name, not through a shortened link in the text itself.",
        indicators = listOf("About a delivery, similar wording to scam messages", "Names the official app and website directly", "No shortened link, no payment request"),
        difficulty = 3,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 191,
        messageText = "Ihre Techniker-Termin für die Internetinstallation wurde bestätigt für Donnerstag zwischen 9 und 13 Uhr. Bitte stellen Sie sicher, dass jemand zu Hause ist.",
        isScam = false,
        explanation = "This confirms an appointment you already booked, with a specific day and time window. It just asks you to be home, not to click a link, pay anything, or share personal details.",
        indicators = listOf("Gives a specific date and time, feels official", "Confirms something you already arranged", "Asks nothing except to be present at home"),
        difficulty = 3,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 192,
        messageText = "Netflix: We were unable to process your last payment. Please check your payment method in Account settings to avoid interruption to your membership.",
        isScam = false,
        explanation = "This is close to the real wording Netflix uses when a card payment fails. It does not include a link to click, it tells you to open your account settings yourself, which is the safe way to check something like this.",
        indicators = listOf("Mentions a failed payment, matches common scam wording", "Directs you to check settings yourself, not a link", "Matches a subscription you actually have"),
        difficulty = 3,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 193,
        messageText = "Your package from Amazon could not be delivered because you were not available. It will be redelivered tomorrow, or you can collect it from your nearest pickup point within 7 days.",
        isScam = false,
        explanation = "This reads like a normal missed delivery message. It gives you a real choice, redelivery or pickup, without any link, payment, or personal details required. Genuine delivery updates often sound almost identical to fake ones, which is exactly why this one is worth practising on.",
        indicators = listOf("About a missed delivery, a common scam topic", "Gives real options, no link or payment needed", "No urgency beyond a normal 7 day window"),
        difficulty = 3,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 194,
        messageText = "SBI: Your Fixed Deposit of Rs. 50,000 matures on 24th August. The interest amount will be auto-credited to your linked savings account. No action needed from your end.",
        isScam = false,
        explanation = "This is a routine maturity notice for money you already invested. It tells you something will happen automatically, not something you need to do, and there is no link or request for details.",
        indicators = listOf("Mentions your bank account, sounds sensitive", "Explicitly says no action is needed", "No link, OTP, or payment request"),
        difficulty = 3,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 195,
        messageText = "Zomato: Your order #78234 has been picked up by the delivery partner and is on the way. Estimated delivery in 25 minutes.",
        isScam = false,
        explanation = "This is a normal order-tracking update for food you actually ordered. It gives a real time estimate and does not ask you to click anything or confirm any personal details.",
        indicators = listOf("About a live order, time-sensitive tone", "Only relates to something you actually ordered", "No link, payment, or detail request"),
        difficulty = 3,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 196,
        messageText = "Jio: Your monthly data plan will renew automatically on 20th Aug via your saved payment method. To change your plan, visit the MyJio app.",
        isScam = false,
        explanation = "This is a standard renewal notice telling you something will happen by itself unless you choose to change it. It points you to the official app by name instead of a link.",
        indicators = listOf("Mentions automatic payment, sounds sensitive", "Points to the official app by name", "No link or payment details requested"),
        difficulty = 3,
        region = "IN",
        scamCategory = null
    ),
    ScamExample(
        id = 197,
        messageText = "Amazon: Ihre Bestellung \"Kabelloser Kopfhörer\" wurde versandt und trifft voraussichtlich am Freitag ein. Verfolgen Sie die Sendung in Ihrem Amazon-Konto.",
        isScam = false,
        explanation = "This is a shipping update for something you actually bought. It tells you to check tracking inside your own Amazon account, not through a link in the message.",
        indicators = listOf("About a delivery, common scam topic", "Only relates to a real order you placed", "Tells you to check your own account, not a link"),
        difficulty = 3,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 198,
        messageText = "Sparkasse: Wir informieren Sie, dass die Zinssätze für Ihr Sparkonto zum 1. September angepasst werden. Details finden Sie in Ihrem Online-Banking.",
        isScam = false,
        explanation = "This is a routine notice about your own bank changing an interest rate, something banks are required to inform you about. It does not ask you to click anything or provide any details.",
        indicators = listOf("Mentions your bank account, sounds sensitive", "No link included, just points to online banking", "No OTP, password, or urgent action requested"),
        difficulty = 3,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 199,
        messageText = "Vodafone: Ihr Vertrag verlängert sich automatisch zu den bisherigen Konditionen. Bei Fragen kontaktieren Sie unseren Kundenservice über die offizielle Webseite.",
        isScam = false,
        explanation = "This is a standard contract renewal notice with no change in price or terms. It only tells you where to go if you have questions, and does not ask for any information.",
        indicators = listOf("About a contract, feels like it needs a response", "Names the official website directly", "No request for personal or payment details"),
        difficulty = 3,
        region = "DE",
        scamCategory = null
    ),
    ScamExample(
        id = 200,
        messageText = "Spotify: Your Premium subscription renews on the 14th for 0.99. Manage your subscription anytime in Account settings.",
        isScam = false,
        explanation = "This is a normal renewal reminder for a subscription you actually pay for. It tells you to manage it yourself in your account settings, not through a link in the message.",
        indicators = listOf("Mentions a payment, matches common scam wording", "Matches a subscription you actually have", "Directs you to your own account settings, not a link"),
        difficulty = 3,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 201,
        messageText = "LinkedIn: Someone from your network commented on your recent post. Open the app to see what they said.",
        isScam = false,
        explanation = "This is a routine social notification about something you posted yourself. It does not ask for any details and only invites you to open the app you already use.",
        indicators = listOf("A notification prompting you to open the app", "Only relates to your own activity", "No link, login details, or payment requested"),
        difficulty = 3,
        region = "GLOBAL",
        scamCategory = null
    ),
    ScamExample(
        id = 202,
        messageText = "Google: Your Google Account was accessed from a new device (Windows, Berlin, Germany) on Aug 19. If this was you, no action is needed.",
        isScam = false,
        explanation = "This is a real type of security alert Google sends for new sign-ins. It explicitly says no action is needed if it was you, and does not ask you to click a link or enter your password.",
        indicators = listOf("Mentions account access, sounds like a security threat", "Explicitly says no action is needed if it was you", "No link or password request"),
        difficulty = 3,
        region = "GLOBAL",
        scamCategory = null
    )
)
