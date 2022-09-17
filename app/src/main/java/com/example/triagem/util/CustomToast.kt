package com.example.triagem.util

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.triagem.R
import kotlinx.android.synthetic.main.custom_toast_layout.view.*

class CustomToast() {
    companion object {
        val GRAVITY_TOP = 48
        val GRAVITY_CENTER = 17
        val GRAVITY_BOTTOM = 80
        private lateinit var layoutInflater: LayoutInflater

        fun showBottom(context: Activity, message: String) {
            show(context, message, GRAVITY_BOTTOM)
        }

        private fun show(context: Activity, message: String, position: Int) {
            layoutInflater = LayoutInflater.from(context)
            val layout = layoutInflater.inflate(R.layout.custom_toast_layout, (context).findViewById(R.id.custom_toast_layout))

            val drawable = ContextCompat.getDrawable(context, R.drawable.toast_round_background)
            layout.background = drawable

            layout.custom_toast_message.setTextColor(Color.WHITE)
            layout.custom_toast_message.text = message


            val toast = Toast(context.applicationContext)
            toast.duration = Toast.LENGTH_SHORT
            if (position == GRAVITY_BOTTOM) {
                toast.setGravity(position, 0, 40)
            } else {
                toast.setGravity(position, 0, 20)
            }
            toast.view = layout //setting the view of custom toast layout
            toast.show()
        }
    }
}