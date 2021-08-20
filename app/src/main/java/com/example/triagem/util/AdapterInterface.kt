package com.example.triagem.util

interface AdapterInterface {
    interface  AdapterCallback{
        fun nextFrag()
    }

    fun setCallback(adapterCallback: AdapterCallback)
}