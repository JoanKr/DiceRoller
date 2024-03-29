package com.example.diceroller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.diceroller.databinding.ActivityMainBinding
import com.example.diceroller.databinding.ActivityMainExtendedBinding
import com.google.android.material.snackbar.Snackbar

class MainActivityExtended : AppCompatActivity() {

    private lateinit var binding: ActivityMainExtendedBinding
    private var numDice: Int = 2
    private var isHoldEnable: Boolean = true

    // Array of dice images ids for easy manipulation via loops
    private val diceImgIdsArray =
        arrayOf(R.id.dice1Img, R.id.dice2Img, R.id.dice3Img, R.id.dice4Img, R.id.dice5Img)
    // Array to keep track whether given dice is held or not
    private val diceStatesArray = arrayOf(false, false, false, false, false) // Array of values rolled.
    private val diceValuesArray = arrayOf(1, 1, 1, 1, 1)
    private var currentPlayer = 0 // Current player id. Only two players
    private val playerScores = arrayOf(0, 0) // scores of the players
    private var rollCount = 0 // variable holding the number of rolls for each player.
// The turn of the player ends when rollCount == numDice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(localClassName, "onCreate")

        binding = ActivityMainExtendedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
// Create a menu with menuInflater and R.menu.menu resource val inflater: MenuInflater = menuInflater inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle the selection of menu items
        when (item.itemId) {
            R.id.settings -> startSettingsActivity() }
        return super.onOptionsItemSelected(item) }

    private fun startSettingsActivity() {
        // creates an Intent to start SettingActivity
        val intent: Intent = Intent(this, SettingsActivity::class.java).apply {
            // Add an extra value of type Int and a key stored in string resource
            // with name num_dice_key
            putExtra(getString(R.string.num_dice_key), numDice)
            // Add an extra value of type Boolean and a key stored in string resource
            // with name hold_enable_key
            putExtra(getString(R.string.hold_enable_key), isHoldEnabled)
            // The extra values can be retrieved in the destination activity with the keys }
            // Start the SettingsActivity with the launchSettingsActivity variable
            launchSettingsActivity.launch(intent)
        }
    }

        // Register a callback for a SettingsActivity result.
        // This code will be called when we return from Settings Activity (after onStart)
        private val launchSettingsActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result -> Log.i(localClassName, "onActivityResult")
            if (result.resultCode == RESULT_OK) {
        // Perform operations only when the resulCode is RESULT_OK
        // Retrieve the data from the result.data Intent (only when it's not null) result.data?.let { data ->
        // This code will be exectued only when result.data is not null,
        // "data" is the argument of a lambda
        // Get the numDice and isHoldEnabled settings
                numDice = data.getIntExtra(getString(R.string.num_dice_key), 2)
                isHoldEnabled = data.getBooleanExtra(getString(R.string.hold_enable_key), true)
            }
        // Apply the settings
            applySettings()
        // Reset the game - each time the user goes to settings activity // and returns back the game will be reset
            resetGame()
        // Display a snackbar pop-up to confirm the settings change
        Snackbar.make(
            binding.root,
            "Current settings: numDice: $numDice, isHoldEnabled: $isHoldEnabled", Snackbar.LENGTH_SHORT
            ).show()
        }


    private fun applySettings() {
// Each time new settings are applied the game is reset
        binding.rollButton.isEnabled =
            true // enable the button so the user can click it again resetGame()
        val diceToHideBegin = numDice + 1
// According to the numDice setting -> hide the remaining dice
        for (num in 1..5) {
// Change the visibility of dices -> the view is found by its id
            if (num in diceToHideBegin..5) findViewById<ImageView>(diceImgIdsArray[num - 1]).apply {
// Making the visibility to GONE make it disappear and
// not take space in the layout
                visibility = View.GONE
// Just in case disable any clicking on the image by disabling clickable // and focusable attributes
                isClickable = false
                isFocusable = false
            }
            else
                findViewById<ImageView>(diceImgIdsArray[num - 1]).apply {
// Make the image visible
                    visibility = View.VISIBLE
// make the image clickable when isHoldEnabled == true
                    isClickable = isHoldEnabled
                    isFocusable = isHoldEnabled
                }
        }
    }


    private fun resetGame() {
        // Reset the variables of the game and initialize labels and buttons
        currentPlayer = 0
        playerScores[0] = 0
        playerScores[1] = 0
        rollCount = 0
        binding.rollResultText.text = getString(R.string.click_start)
        binding.playerLabel.visibility = View.INVISIBLE
        binding.rollButton.text = getString(R.string.button_initial)
        resetTurn()

    }


    private fun resetTurn() {
        // prepare the game for next turn
        for (num in 0..4) {
            diceValuesArray[num] = 1 // Reset the values displayed by each dice
            diceStatesArray[num] = false // Reset the "hold" state of each dice
            findViewById<ImageView>(diceImgIdsArray[num]).let {
                changeDiceTint(it, false) // reset the tint of each dice image view
                it.setImageResource(resolveDrawable(1)) // reset the image of each dice image view
             }
        }
        // Change the game label to show that user has changed
        binding.playerLabel.apply {
            text = getString(R.string.player_label_format, currentPlayer)
        }
    }

    private fun changeDiceTint(img: ImageView, highlight: Boolean) {
// Change the tint of the img. The getColor method is available for API >= M (API 23)
        img.imageTintList =
        ColorStateList.valueOf(getColor(if (highlight) R.color.yellow else R.color.white))
    }

}