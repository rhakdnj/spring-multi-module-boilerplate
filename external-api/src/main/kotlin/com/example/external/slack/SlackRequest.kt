package com.example.external.slack

data class SlackRequest(
    val attachments: List<Attachment>,
) {
    companion object {
        fun good(message: String): SlackRequest =
            SlackRequest(
                listOf(
                    Attachment(
                        color = "good",
                        text = message,
                    ),
                ),
            )

        fun warning(message: String): SlackRequest =
            SlackRequest(
                listOf(
                    Attachment(
                        color = "warning",
                        text = message,
                    ),
                ),
            )

        fun danger(message: String): SlackRequest =
            SlackRequest(
                listOf(
                    Attachment(
                        color = "danger",
                        text = message,
                    ),
                ),
            )
    }
}

data class Attachment(
    val color: String,
    val text: String,
)
