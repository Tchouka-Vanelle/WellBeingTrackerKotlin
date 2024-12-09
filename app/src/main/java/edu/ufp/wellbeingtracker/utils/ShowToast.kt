package edu.ufp.wellbeingtracker.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

fun showToast(context: Context, message: String) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.TOP, 0, 200)
    toast.show()
}