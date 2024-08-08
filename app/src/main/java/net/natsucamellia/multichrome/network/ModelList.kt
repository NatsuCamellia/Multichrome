package net.natsucamellia.multichrome.network

import kotlinx.serialization.Serializable

@Serializable
data class ModelList(
    val result: List<String>
)