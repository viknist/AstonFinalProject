package ru.viknist.rickandmorty.baseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.viknist.rickandmorty.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}