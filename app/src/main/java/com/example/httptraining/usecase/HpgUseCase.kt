package com.example.httptraining.usecase

import com.example.httptraining.repository.HpgRepository
import com.example.httptraining.network.BudgetInfo
import com.example.httptraining.network.Shop
import kotlinx.coroutines.*

class HpgUseCase(
    private val repository: HpgRepository = HpgRepository(),
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {


    suspend fun getShopInfoAndBudgetInfo(large_area: String = "Z012"): Pair<Shop?, BudgetInfo.Budget?> =
        withContext(defaultDispatcher) {
            val budget = async(defaultDispatcher) {
                repository.fetchBudget()
            }
//            val shop = repository.fetchShopInfo(large_area)
            val shop = async(defaultDispatcher) {
                repository.fetchShopInfo(large_area)
            }
            return@withContext Pair(shop.await()?.get(0), budget.await()?.get((0)))
        }
}
