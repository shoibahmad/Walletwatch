package com.walletwatch

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class Expense(
    val id: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    val category: String = "",
    @get:PropertyName("timestamp")
    @set:PropertyName("timestamp")
    var timestamp: Any? = null
) {
    fun getTimestampAsFirebaseTimestamp(): Timestamp {
        return when (timestamp) {
            is Timestamp -> timestamp as Timestamp
            is String -> Timestamp.now() // If it's a string, use current time
            null -> Timestamp.now()
            else -> Timestamp.now()
        }
    }
}
