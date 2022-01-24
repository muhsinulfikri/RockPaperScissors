package com.example.rockpaperscissors.enum

enum class PlayerChoice(val index: Int) {
    ROCK(0),
    PAPER(1),
    SCISSOR(2);
    companion object{
        fun getValueFromIndex(index: Int) = values()[index]
    }
}