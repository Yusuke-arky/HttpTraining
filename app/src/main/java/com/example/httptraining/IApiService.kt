package com.example.httptraining


import com.example.httptraining.network.BaseHpgResponse
import com.example.httptraining.network.BudgetInfo
import com.example.httptraining.network.ShopInfo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://webservice.recruit.co.jp/hotpepper/"
private const val KEY = BuildConfig.API_KEY
private const val FORMAT = "json"

// このクラス
interface IApiAPIService {

    // グルメサービスAPI
    @GET("gourmet/v1/?format=$FORMAT&type=lite&count=15")
    suspend fun fetchShopInfo(
        @Query("large_area") large_area: String,
        @Query("key") key: String = KEY
    ): Response<BaseHpgResponse<ShopInfo>>

    // 予算検索API
    @GET("budget/v1/?key=\$KEY&format=$FORMAT")
    suspend fun fetchBudgetInfo(
        @Query("key") key: String = KEY,
    ): Response<BaseHpgResponse<BudgetInfo>>
}

// Clientを作成
val httpBuilder: OkHttpClient.Builder get() {
    // httpClientのBuilderを作る
    val httpClient = OkHttpClient.Builder()

    // create http client リクエストのインタセプター
    httpClient.addInterceptor(
        Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Accept", "application/json")
                .method(original.method, original.body)
                .build()
            // proceedメソッドは再びパーミッション許可ダイアログを表示してその結果を返します

            return@Interceptor chain.proceed(request)
        }
    )
        .readTimeout(30, TimeUnit.SECONDS)

    return httpClient
}

object HpgApiGenerator {
    val createService: IApiAPIService = createService(IApiAPIService::class.java)
    private fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client((httpBuilder.build()))
            .build()

        return retrofit.create(serviceClass)
    }
}
