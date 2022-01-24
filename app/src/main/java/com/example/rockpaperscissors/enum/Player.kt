package com.example.rockpaperscissors.enum

import android.widget.ImageView

abstract class Player {
    var choice: Int = -1
    lateinit var heroes: ArrayList<ImageView>
}