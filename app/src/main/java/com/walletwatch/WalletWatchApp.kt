package com.walletwatch

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class WalletWatchApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        try {
            Log.d("WalletWatchApp", "Initializing Firebase...")
            FirebaseApp.initializeApp(this)
            
            // Configure Firestore settings
            val settings = FirebaseFirestoreSettings.Builder()
                .build()
            FirebaseFirestore.getInstance().firestoreSettings = settings
            
            Log.d("WalletWatchApp", "Firebase initialized successfully")
        } catch (e: Exception) {
            Log.e("WalletWatchApp", "Firebase initialization failed", e)
        }
    }
}
