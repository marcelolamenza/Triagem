package com.example.triagem.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.triagem.R
import com.example.triagem.util.AdapterInterface
import com.example.triagem.util.inflate


class CheckItemAdapter : RecyclerView.Adapter<CheckItemAdapter.CheckItemViewHolder>(), AdapterInterface {

    lateinit var adapterCallback: AdapterInterface.AdapterCallback
    private var isClicked = false
    private var checkItemList = mutableListOf<CheckItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItem(item: CheckItem) {
        checkItemList.add(item)
        notifyDataSetChanged()
    }

    fun clean(){
        checkItemList.removeAll(checkItemList)
    }

    class CheckItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: Button by lazy { itemView.findViewById<Button>(R.id.label) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckItemViewHolder {
        val rowView = parent.inflate(R.layout.sickness_card,false)
        return CheckItemViewHolder(
            rowView
        )
    }

    override fun onBindViewHolder(holder: CheckItemViewHolder, position: Int) {
        val debugItem = checkItemList.toList()[position]

        holder.label.text = debugItem.sickness
        holder.label.setOnClickListener {
            adapterCallback.nextFrag()

//            if(isClicked){
//                isClicked = false
//                it.backgroundTintList = ColorStateList.valueOf(R.color.colorAccent)
////                it.setBackgroundColor(ContextCompat.getColor(R.color.colorAccent))
//            }else{
//                isClicked = true
//                it.backgroundTintList = ColorStateList.valueOf(R.color.colorPrimary)
//
////                it.setBackgroundColor(R.color.colorPrimary)
//            }

        }
    }

    override fun getItemCount(): Int {
        return checkItemList.toList().size
    }

    data class CheckItem(val sickness : String)

    override fun setCallback(adapterCallback: AdapterInterface.AdapterCallback) {
        this.adapterCallback = adapterCallback
    }



}