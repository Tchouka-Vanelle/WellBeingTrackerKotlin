package edu.ufp.wellbeingtracker.utils

import android.view.Gravity
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.FrameLayout

fun showSnackbar(view: View, message: String) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)

    // Get the Snackbar's view and modify its layout
    val snackbarView = snackbar.view
    val layoutParams = snackbarView.layoutParams as FrameLayout.LayoutParams

    // Set the gravity to center the Snackbar
    layoutParams.gravity = Gravity.CENTER
    snackbarView.layoutParams = layoutParams

    snackbar.show()
}