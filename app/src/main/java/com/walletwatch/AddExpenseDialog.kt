package com.walletwatch

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore

class AddExpenseDialog : DialogFragment() {
    private lateinit var amountEdit: EditText
    private lateinit var descriptionEdit: EditText
    private lateinit var categorySpinner: Spinner

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialog_add_expense, null)
        
        amountEdit = view.findViewById(R.id.amountEdit)
        descriptionEdit = view.findViewById(R.id.descriptionEdit)
        categorySpinner = view.findViewById(R.id.categorySpinner)

        setupCategorySpinner()

        return AlertDialog.Builder(requireContext())
            .setTitle("💰 Add Expense")
            .setView(view)
            .setPositiveButton("Add") { _, _ -> addExpense() }
            .setNegativeButton("Cancel", null)
            .create()
    }

    private fun setupCategorySpinner() {
        val categories = arrayOf(
            "🍔 Food", 
            "🚗 Transport", 
            "🛍️ Shopping", 
            "💡 Bills", 
            "🎬 Entertainment",
            "🏥 Healthcare",
            "📚 Education",
            "📦 Other"
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }

    private fun addExpense() {
        val amountStr = amountEdit.text.toString()
        if (amountStr.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter an amount", Toast.LENGTH_SHORT).show()
            return
        }
        
        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }
        
        val description = descriptionEdit.text.toString().trim()
        val category = categorySpinner.selectedItem.toString()

        val expense = hashMapOf(
            "amount" to amount,
            "description" to description,
            "category" to category,
            "timestamp" to com.google.firebase.firestore.FieldValue.serverTimestamp()
        )

        FirebaseFirestore.getInstance()
            .collection("expenses")
            .add(expense)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Expense added!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
