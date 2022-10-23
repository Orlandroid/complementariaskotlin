package com.example.complementariasmx.presentacion.ui.util

import android.content.Context
import android.widget.Toast

fun sendToast(context: Context, message: String) =
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()