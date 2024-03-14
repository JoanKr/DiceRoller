package com.example.diceroller

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(localClassName, "onCreate")
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.rollButton)

        button.setOnClickListener{
            rollDice()
        }
    }

    fun rollDice() {
        val dice = Dice()
        val diceRoll1 = dice.roll()
        val diceRoll2 = dice.roll()
        val crash = findViewById<View>(R.id.crash_test)
        val screenH = crash?.height
        updateText(diceRoll1, diceRoll2)
        updateImg(diceRoll1, diceRoll2)
    }


    private fun updateText(diceRoll1: Int, diceRoll2: Int) {
        val rollResultText = findViewById<TextView>(R.id.rollResultText)

        val r = (0..255).random()
        val g = (((diceRoll1 + diceRoll2) / 12.0) * 255).toInt()
        val b = (((diceRoll1 * diceRoll2) / 36.0) * 255).toInt()

        rollResultText.setTextColor(Color.rgb(r, g, b))
        Log.i(localClassName, "Text color: R:$r, G:$g, B:$b")

    }


    private fun updateImg(diceRoll1: Int, diceRoll2: Int) {
        val dice1Img = findViewById<ImageView>(R.id.dice1Img)
        val dice2Img = findViewById<ImageView>(R.id.dice2Img)

        dice1Img.setImageResource(resolveDrawable(diceRoll1))
        dice2Img.setImageResource(resolveDrawable(diceRoll2))


    }

    private fun resolveDrawable(diceValue: Int): Int {
        return when(diceValue){
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6

        }
    }


    override fun onStart(){
        super.onStart()
        Log.i(localClassName, "onStart")
    }

    override fun onResume(){
        super.onResume()
        Log.i(localClassName, "onResume")
    }

    override fun onPause(){
        super.onPause()
        Log.i(localClassName, "onPause")
    }

    override fun onStop(){
        super.onStop()
        Log.i(localClassName, "onStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i(localClassName, "onDestroy")
    }

}