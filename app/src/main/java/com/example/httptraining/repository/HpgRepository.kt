package com.example.httptraining.repository



import com.example.httptraining.HpgApiGenerator
import com.example.httptraining.IApiAPIService
import com.example.httptraining.network.BudgetInfo
import com.example.httptraining.network.Shop
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HpgRepository(
    private val api: IApiAPIService = HpgApiGenerator.createService
) {

    suspend fun fetchShopInfo(large_area: String): List<Shop>? {
        return try {
            val apiResult = api.fetchShopInfo(large_area = large_area).validateResponse()
            return when (apiResult.isSuccessful) {
                true -> apiResult.body?.results?.shop
                // ここでエラーコードからExceptionを返して、呼び出し元のviewModelでダイアログ出すなど？
                else -> null
            }
        } catch (e: UnknownHostException) {
            println("$e")
            null
        } catch (e: SocketTimeoutException) {
            println("$e")
            null
        } catch (e: IOException) {
            println("$e")
            null
        }
    }

    suspend fun fetchBudget(): List<BudgetInfo.Budget>? {
        return try {
            val apiResult = api.fetchBudgetInfo().validateResponse()
            return when (apiResult.isSuccessful) {
                true -> apiResult.body?.results?.budget
                // ここでエラーコードからExceptionを返して、呼び出し元のviewModelでダイアログ出すなど？
                else -> null
            }
        } catch (e: UnknownHostException) {
            println("$e")
            null
        } catch (e: SocketTimeoutException) {
            println("$e")
            null
        } catch (e: IOException) {
            println("$e")
            null
        }
    }
}

inline fun <reified T> Response<T>.validateResponse(): ApiResult<T> {
    return if (isSuccessful) {
        ApiResult(
            isSuccessful = isSuccessful,
            code = code(),
            body = body(),
            errorBody = null
        )
    } else {
        ApiResult(
            isSuccessful = !isSuccessful,
            code = code(),
            body = null,
            // 本来であればここで独自のエラーボディが入る気がする
            errorBody = errorBody()
        )
    }
}

data class ApiResult<T>(
    val isSuccessful: Boolean,
    val code: Int,
    val body: T?,
    val errorBody: ResponseBody?
)
