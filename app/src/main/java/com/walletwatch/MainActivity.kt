package com.walletwatch

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity() {
    private var db: FirebaseFirestore? = null
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalAmountText: TextView
    private lateinit var emptyStateText: TextView
    private var expenses = mutableListOf<Expense>()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate started")
        
        try {
            // Initialize Firebase first
            try {
                com.google.firebase.FirebaseApp.initializeApp(this)
                Log.d(TAG, "Firebase initialized in MainActivity")
            } catch (e: Exception) {
                Log.e(TAG, "Firebase init error (may already be initialized)", e)
            }
            
            setContentView(R.layout.activity_main)
            Log.d(TAG, "Layout set")

            initializeViews()
            setupRecyclerView()
            setupFab()
            initializeFirebase()
            
            Log.d(TAG, "Setup complete")
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initializeViews() {
        try {
            totalAmountText = findViewById(R.id.totalAmountText)
            emptyStateText = findViewById(R.id.emptyStateText)
            recyclerView = findViewById(R.id.recyclerView)
            Log.d(TAG, "Views initialized")
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing views", e)
            throw e
        }
    }

    private fun setupRecyclerView() {
        try {
            expenseAdapter = ExpenseAdapter { expense ->
                deleteExpense(expense)
            }
            recyclerView.adapter = expenseAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)
            updateEmptyState(true)
            Log.d(TAG, "RecyclerView setup complete")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up RecyclerView", e)
            throw e
        }
    }

    private fun setupFab() {
        try {
            val fab: FloatingActionButton = findViewById(R.id.fab)
            fab.setOnClickListener {
                try {
                    AddExpenseDialog().show(supportFragmentManager, "AddExpense")
                } catch (e: Exception) {
                    Log.e(TAG, "Error showing dialog", e)
                    Toast.makeText(this, "Error opening dialog", Toast.LENGTH_SHORT).show()
                }
            }
            Log.d(TAG, "FAB setup complete")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up FAB", e)
            throw e
        }
    }

    private fun initializeFirebase() {
        try {
            // Delay Firebase initialization to ensure app is ready
            recyclerView.postDelayed({
                try {
                    db = FirebaseFirestore.getInstance()
                    Log.d(TAG, "Firebase initialized")
                    loadExpenses()
                } catch (e: Exception) {
                    Log.e(TAG, "Firebase initialization failed", e)
                    Toast.makeText(this, "Running in offline mode", Toast.LENGTH_SHORT).show()
                }
            }, 500)
        } catch (e: Exception) {
            Log.e(TAG, "Firebase initialization failed", e)
            Toast.makeText(this, "Running in offline mode", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadExpenses() {
        db?.let { firestore ->
            try {
                firestore.collection("expenses")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            Log.e(TAG, "Error loading expenses", error)
                            return@addSnapshotListener
                        }
                        
                        snapshot?.let {
                            expenses = it.documents.mapNotNull { doc ->
                                doc.toObject(Expense::class.java)?.copy(id = doc.id)
                            }.toMutableList()
                            expenseAdapter.updateExpenses(expenses)
                            updateTotalAmount(expenses)
                            updateEmptyState(expenses.isEmpty())
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Error setting up listener", e)
            }
        }
    }

    private fun updateTotalAmount(expenses: List<Expense>) {
        try {
            val total = expenses.sumOf { it.amount }
            totalAmountText.text = String.format("$%.2f", total)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating total", e)
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        try {
            if (isEmpty) {
                emptyStateText.visibility = android.view.View.VISIBLE
                recyclerView.visibility = android.view.View.GONE
            } else {
                emptyStateText.visibility = android.view.View.GONE
                recyclerView.visibility = android.view.View.VISIBLE
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating empty state", e)
        }
    }

    private fun deleteExpense(expense: Expense) {
        try {
            if (expense.id.isNotEmpty()) {
                db?.collection("expenses")?.document(expense.id)?.delete()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting expense", e)
            Toast.makeText(this, "Error deleting expense", Toast.LENGTH_SHORT).show()
        }
    }
}
