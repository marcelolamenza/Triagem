package com.example.triagem.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.triagem.R
import com.example.triagem.util.inflate

class RegisterItemAdapter : RecyclerView.Adapter<RegisterItemAdapter.RegisterItemViewHolder>() {

    private var registerItemList = mutableListOf<RegisterItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItem(item: RegisterItem) {
        registerItemList.add(item)
        notifyDataSetChanged()
    }

    fun getItems(): MutableList<RegisterItem> {
        return registerItemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterItemViewHolder {
        val rowView = parent.inflate(R.layout.information_card,false)
        return RegisterItemViewHolder(
            rowView
        )
    }

    override fun getItemCount(): Int = registerItemList.toList().size

    override fun onBindViewHolder(holder: RegisterItemViewHolder, position: Int) {
        val registerItem = registerItemList.toList()[position]

        holder.label.text = registerItem.itemLabel
        holder.info.addTextChangedListener {
            registerItemList.toList()[position].itemDescription = it.toString()
        }
    }

    class RegisterItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView by lazy { itemView.findViewById<TextView>(R.id.label) }
        val info: EditText by lazy { itemView.findViewById<EditText>(R.id.info) }
    }

    data class RegisterItem(val itemLabel : String, var itemDescription: String? = null)
}