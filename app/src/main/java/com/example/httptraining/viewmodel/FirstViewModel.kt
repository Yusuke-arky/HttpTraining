package com.example.httptraining.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.httptraining.network.Shop
import com.example.httptraining.usecase.HpgUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FirstViewModel(
    private val hpgUseCase: HpgUseCase = HpgUseCase()
) : ViewModel() {

    private val _uiModel = MutableStateFlow(FirstUiModel.init)
    val uiModel: StateFlow<FirstUiModel> = _uiModel


    fun onLoad() {
        viewModelScope.launch {
            val result = hpgUseCase.getShopInfoAndBudgetInfo()
            if (result.first != null) {
                _uiModel.value = FirstUiModel(result.first!!)
            }
        }
    }
}

data class FirstUiModel(
    val name: String,
    val link: String?,
    val access: String,
    val address: String,
    val photoUrl: String?,
    val description: String
) {
    companion object {
        val init = FirstUiModel("", null, "", "", null, "")
    }

    constructor(shop: Shop) : this(
        name = shop.name ?: "未設定",
        link = shop.urls?.pc,
        access = shop.access ?: "未設定",
        address = shop.address ?: "未設定",
        photoUrl = shop.photo?.pc?.l,
        description = shop.catch ?: "未設定"
    )
}