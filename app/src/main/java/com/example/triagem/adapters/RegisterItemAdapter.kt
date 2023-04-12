package com.example.triagem.adapters

import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.triagem.R
import com.example.triagem.models.UserInfo
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

    fun addMultipleItems(itemList: List<String>) {
        for(item in itemList) {
            registerItemList.add(RegisterItem(item))
        }
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

        holder.label.text = registerItem.name

        if(registerItem.currentInformation != null) {
            holder.writingField.setText(registerItem.currentInformation)
        }

        applyMaskIntoHolder(holder, Constants.User.PHONE, "(##)#####-####")
        applyMaskIntoHolder(holder, Constants.User.CPF, "###.###.###-##")
        applyMaskIntoHolder(holder, Constants.User.RG, "##.###.###-#")

        holder.writingField.addTextChangedListener {
            registerItemList.toList()[position].currentInformation = it.toString()
        }
    }

    private fun applyMaskIntoHolder(
        holder: RegisterItemViewHolder,
        ediTextType: String,
        format: String
    ) {
        if (holder.label.text == ediTextType) {
            holder.writingField.addTextChangedListener(EditTextMask.apply(format, holder.writingField))
            holder.writingField.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }

    fun updateFieldInformation(userInfo: UserInfo) {
        for((i, item) in registerItemList.withIndex()) {
            userInfo.infoMap?.forEach { info ->
                if(item.name == info.key) {
                    item.currentInformation = info.value
                }
            }
            notifyItemChanged(i)
        }

        notifyDataSetChanged()
    }

    class RegisterItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView by lazy { itemView.findViewById(R.id.label) }
        val writingField: EditText by lazy { itemView.findViewById(R.id.info) }
    }

    data class RegisterItem(val name: String, var currentInformation: String? = null)
}