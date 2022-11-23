package com.example.httptraining.network

data class BudgetInfo(
    val api_version: String,
    val budget: List<Budget>
) {
    // 予算APIdataクラス
    data class Budget(
        val code: String,
        val name: String
    )
}
