package com.example.triagem.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.triagem.R
import com.example.triagem.diagnosis.DiagnosisCallback
import com.example.triagem.util.inflate


class DiagnosisItemAdapter(private var diagnosisCallback: DiagnosisCallback) :
    RecyclerView.Adapter<DiagnosisItemAdapter.DiagnosisItemViewHolder>() {

    private var diagnosisItemList = mutableListOf<DiagnosisItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItem(item: DiagnosisItem) {
        diagnosisItemList.add(item)
        notifyDataSetChanged()
    }

    fun addItemOnList(list: List<DiagnosisItem>) {

    }

    fun clean() {
        diagnosisItemList.removeAll(diagnosisItemList)
        notifyDataSetChanged()
    }

    class DiagnosisItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diseaseButton: Button by lazy { itemView.findViewById<Button>(R.id.label) }
        var isClicked: Boolean = false
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

        holder.diseaseButton.setOnClickListener {
            holder.isClicked = !holder.isClicked

            diagnosisCallback.clickAction(holder.diseaseButton, holder.isClicked)
        }
    }

    override fun getItemCount(): Int {
        return diagnosisItemList.toList().size
    }

    data class DiagnosisItem(val sickness: String)

}