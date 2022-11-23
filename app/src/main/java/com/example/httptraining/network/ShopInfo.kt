package com.example.httptraining.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ShopInfo(
    val api_version: String,
    val results_available: Int,
    val results_returned: String,
    val results_start: Int,
    val shop: List<Shop>
)

@Parcelize
data class Shop(
    // 変数名とJSONのキー名を一致させている
    val access: String?,
    val address: String?,
    val budget_memo: String?,
    val catch: String?,
    val food: Food?,
    val genre: Genre?,
    val id: String?,
    val lat: Float?,
    val lng: Float?,
    val name: String?,
    val other_memo: String?,
    val photo: Photo?,
    val shop_detail_memo: String?,
    val urls: Urls?
) : Parcelable {
    @Parcelize
    data class Food(
        val aa: String?
    ) : Parcelable

    @Parcelize
    data class Genre(
        val catch: String?,
        val name: String?
    ) : Parcelable

    @Parcelize
    data class Photo(
        val pc: PhotoSize?
    ) : Parcelable {
        @Parcelize
        data class PhotoSize(
            val l: String?,
            val m: String?,
            val s: String?
        ) : Parcelable
    }

    @Parcelize
    data class Urls(
        val pc: String?
    ) : Parcelable
}
