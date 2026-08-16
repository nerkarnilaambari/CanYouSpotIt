package com.example.canyouspotit

data class ScamExample(
    val messageText: String,
    val isScam: Boolean,
    val explanation: String,
    val indicators: List<String>,
    val difficulty: Int,
    val region: String
)

val placeholderExamples = listOf(
    ScamExample(
        messageText = "Congratulations! You've won a free iPhone. Click here to claim your prize now before it expires!",
        isScam = true,
        explanation = "Real companies never give away free phones through random messages. This is designed to make you click without thinking.",
        indicators = listOf("Unrealistic prize", "Urgency", "Suspicious link"),
        difficulty = 1,
        region = "GLOBAL"
    ),
    ScamExample(
        messageText = "Your package could not be delivered. Please confirm your address by clicking the link below.",
        isScam = true,
        explanation = "Delivery scams ask you to click links to steal your personal information. Real delivery companies don't work this way.",
        indicators = listOf("Unexpected delivery", "Asks to click link"),
        difficulty = 2,
        region = "GLOBAL"
    ),
    ScamExample(
        messageText = "Hi, just checking if we're still on for dinner Saturday at 7pm?",
        isScam = false,
        explanation = "This is a normal, everyday message. No links, no urgency, no requests for personal information.",
        indicators = listOf("Casual tone", "No links or requests"),
        difficulty = 1,
        region = "GLOBAL"
    )
)
