package ru.viknist.rickandmorty.baseapp

import android.content.Context
import android.widget.Toast
import kotlin.reflect.KClass

fun String.getIntFromUrl(): Int {
    val pathSegments = this.split("/")
    val lastSegment = pathSegments.last()
    return lastSegment.toIntOrNull() ?: 0
}

fun <T : Any> KClass<T>.getTag(): String = this.java.simpleName

fun Context.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}