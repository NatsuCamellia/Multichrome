package net.natsucamellia.multichrome.network

data class PostBody(
    val input: List<Any>? = null,
    val model: String = "default"
)