package com.example.triagem.adapters

import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.triagem.R
import com.example.triagem.util.Constants
import com.example.triagem.util.EditTextMask
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
        val rowView = parent.inflate(R.layout.information_card, false)
        return RegisterItemViewHolder(
            rowView
        )
    }

    override fun getItemCount(): Int = registerItemList.toList().size

    override fun onBindViewHolder(holder: RegisterItemViewHolder, position: Int) {
        val registerItem = registerItemList.toList()[position]

        holder.label.text = registerItem.itemLabel

        applyMaskIntoHolder(holder, Constants.User.PHONE, "(##)#####-####")
        applyMaskIntoHolder(holder, Constants.User.CPF, "###.###.###-##")
        applyMaskIntoHolder(holder, Constants.User.RG, "##.###.###-#")

        holder.info.addTextChangedListener {
            registerItemList.toList()[position].itemDescription = it.toString()
        }
    }

    private fun applyMaskIntoHolder(
        holder: RegisterItemViewHolder,
        ediTextType: String,
        mask: String
    ) {
        if (holder.label.text == ediTextType) {
            holder.info.addTextChangedListener(EditTextMask.mask(mask, holder.info))
            holder.info.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }

    class RegisterItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView by lazy { itemView.findViewById<TextView>(R.id.label) }
        val info: EditText by lazy { itemView.findViewById<EditText>(R.id.info) }
    }

    data class RegisterItem(val itemLabel: String, var itemDescription: String? = null)
}