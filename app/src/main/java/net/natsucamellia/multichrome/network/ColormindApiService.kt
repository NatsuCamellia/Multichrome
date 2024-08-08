package net.natsucamellia.multichrome.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ColormindApiService {
    @POST("api/")
    suspend fun getRandomPalette(@Body postBody: PostBody): PostResponse

    @GET("list/")
    suspend fun getModelList(): ModelList
}