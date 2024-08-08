package net.natsucamellia.multichrome.data

import com.google.gson.GsonBuilder
import net.natsucamellia.multichrome.network.ColormindApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {
    val multichromeRepository: MultichromeRepository
}

class DefaultAppContainer: AppContainer {

    private val baseUrl = "http://colormind.io/"
    private val gson = GsonBuilder()
        .setLenient()
        .create()
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .build()
    private val colormindApiService: ColormindApiService by lazy {
        retrofit.create(ColormindApiService::class.java)
    }

    override val multichromeRepository: MultichromeRepository by lazy {
        NetworkMultichromeRepository(colormindApiService)
    }
}