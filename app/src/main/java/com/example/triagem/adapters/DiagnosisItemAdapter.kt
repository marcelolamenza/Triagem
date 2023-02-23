package com.example.triagem.adapters

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.triagem.R
import com.example.triagem.diagnosis.DiagnosisCallback
import com.example.triagem.util.inflate


class DiagnosisItemAdapter(private var diagnosisCallback: DiagnosisCallback) :
    RecyclerView.Adapter<DiagnosisItemAdapter.DiagnosisItemViewHolder>() {

    var lastClickedPos = -1

    private var diagnosisItemList = mutableListOf<DiagnosisItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItem(item: DiagnosisItem) {
        diagnosisItemList.add(item)
        notifyDataSetChanged()
    }

    fun addMultipleItems(list: List<String>) {
        for (item in list) {
            diagnosisItemList.add(DiagnosisItem(item))
        }

        notifyDataSetChanged()
    }

    fun addTransparencyToOtherOptions(
        position: Int,
        isSelected: Boolean,
        neutralColor: ColorStateList,
        neutralTextColor: ColorStateList
    ) {
        for (item in diagnosisItemList) {
            if (isSelected) {
                if (item != diagnosisItemList[position]) {
                    item.transparency = 0.5f
                    item.buttonColor = neutralColor
                    item.textColor = neutralTextColor
                    item.isSelected = false
                } else {
                    item.transparency = 1f
                    item.buttonColor = null
                    item.textColor = null
                    item.isSelected = true
                }
            } else {
                item.transparency = 1f
                item.buttonColor = neutralColor
                item.textColor = neutralTextColor
                item.isSelected = false
            }
        }

        notifyDataSetChanged()
    }

    fun clean() {
        diagnosisItemList.removeAll(diagnosisItemList)
        notifyDataSetChanged()
    }

    class DiagnosisItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diseaseButton: Button by lazy { itemView.findViewById<Button>(R.id.label) }
        var isSelected: Boolean = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiagnosisItemViewHolder {
        val rowView = parent.inflate(R.layout.sickness_card, false)
        return DiagnosisItemViewHolder(
            rowView
        )
    }

    override fun onBindViewHolder(holder: DiagnosisItemViewHolder, position: Int) {
        val debugItem = diagnosisItemList.toList()[position]

        holder.diseaseButton.text = debugItem.sickness
        holder.diseaseButton.alpha = debugItem.transparency
        holder.isSelected = debugItem.isSelected

        if (debugItem.buttonColor != null) {
            holder.diseaseButton.backgroundTintList = debugItem.buttonColor
        }

        if (debugItem.textColor != null) {
            holder.diseaseButton.setTextColor(debugItem.textColor)
        }

        holder.diseaseButton.setOnClickListener {
            holder.isSelected = !holder.isSelected

            diagnosisCallback.clickAction(holder.diseaseButton, holder.isSelected, position)
        }
    }

    override fun getItemCount(): Int {
        return diagnosisItemList.toList().size
    }

    data class DiagnosisItem(
        val sickness: String,
        var transparency: Float = 1f,
        var buttonColor: ColorStateList? = null,
        var textColor: ColorStateList? = null,
        var isSelected: Boolean = false
    )

}