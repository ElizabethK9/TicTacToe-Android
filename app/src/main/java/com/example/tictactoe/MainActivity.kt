package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var isPlayerXTurn = true // Track whose turn it is
    private lateinit var buttons: Array<Array<Button>> // Grid of buttons for TicTacToe
    private lateinit var turnTextView: TextView // TextView for displaying turn info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Edge-to-edge layout adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the UI components
        turnTextView = findViewById(R.id.textView)
        turnTextView.text = "Player X's Turn"

        // Initialize the TicTacToe grid buttons
        buttons = arrayOf(
            arrayOf(findViewById(R.id.button_00), findViewById(R.id.button_01), findViewById(R.id.button_02)),
            arrayOf(findViewById(R.id.button_10), findViewById(R.id.button_11), findViewById(R.id.button_12)),
            arrayOf(findViewById(R.id.button_20), findViewById(R.id.button_21), findViewById(R.id.button_22))
        )

        // Set click listeners for each TicTacToe button
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].setOnClickListener {
                    onButtonClick(buttons[i][j])
                }
            }
        }

        // New Game Button setup
        val newGameButton: Button = findViewById(R.id.newGameButton)
        newGameButton.setOnClickListener {
            resetGame()
        }
    }

    // Handle button clicks for TicTacToe grid
    private fun onButtonClick(button: Button) {
        if (button.text.isEmpty()) { // If the button is blank
            if (isPlayerXTurn) {
                button.text = "X"
                turnTextView.text = "Player O's Turn"
            } else {
                button.text = "O"
                turnTextView.text = "Player X's Turn"
            }
            isPlayerXTurn = !isPlayerXTurn // Switch turn
        }
    }

    // Reset the game board and turn indicator
    private fun resetGame() {
        isPlayerXTurn = true
        turnTextView.text = "Player X's Turn"

        // Clear all buttons
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].text = ""
            }
        }
    }
}
