package com.walletwatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(
    private val onDeleteClick: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    private var expenses = listOf<Expense>()

    fun updateExpenses(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(expenses[position], onDeleteClick)
    }

    override fun getItemCount() = expenses.size

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountText: TextView = itemView.findViewById(R.id.amountText)
        private val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
        private val categoryText: TextView = itemView.findViewById(R.id.categoryText)
        private val dateText: TextView = itemView.findViewById(R.id.dateText)
        private val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

        fun bind(expense: Expense, onDeleteClick: (Expense) -> Unit) {
            amountText.text = String.format("$%.2f", expense.amount)
            descriptionText.text = expense.description.ifEmpty { "No description" }
            categoryText.text = expense.category
            
            try {
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                dateText.text = dateFormat.format(expense.getTimestampAsFirebaseTimestamp().toDate())
            } catch (e: Exception) {
                dateText.text = "Recent"
            }
            
            deleteButton.setOnClickListener {
                onDeleteClick(expense)
            }
        }
    }
}
