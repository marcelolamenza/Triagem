package com.example.triagem.adapters


import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.triagem.R
import com.example.triagem.util.inflate

class RegisterItemAdapter : RecyclerView.Adapter<RegisterItemAdapter.RegisterItemViewHolder>() {

    private var debugItemList = mutableListOf<DebugItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItem(item: DebugItem) {
        debugItemList.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterItemViewHolder {
        val rowView = parent.inflate(R.layout.information_card,false)
        return RegisterItemViewHolder(
            rowView
        )
    }

    override fun getItemCount(): Int = debugItemList.toList().size

    override fun onBindViewHolder(holder: RegisterItemViewHolder, position: Int) {
        val debugItem = debugItemList.toList()[position]

        holder.label.text = debugItem.itemLabel
    }

    class RegisterItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView by lazy { itemView.findViewById<TextView>(R.id.label) }
        val info: EditText by lazy { itemView.findViewById<EditText>(R.id.info) }
    }

    data class DebugItem(val itemLabel : String)
}