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
        // Ensure the button is blank and the game is not over
        if (button.text.isEmpty() && isGameActive()) {
            button.text = if (isPlayerXTurn) "X" else "O"

            if (checkWin()) {
                turnTextView.text = if (isPlayerXTurn) "Player X Wins!" else "Player O Wins!"
                disableButtons() // Disable all buttons
            } else if (checkTie()) {
                turnTextView.text = "It's a Tie!"
                disableButtons() // Disable all buttons
            } else {
                isPlayerXTurn = !isPlayerXTurn // Switch turn
                turnTextView.text = if (isPlayerXTurn) "Player X's Turn" else "Player O's Turn"
            }
        }
    }

    // Helper to check if the game is active
    private fun isGameActive(): Boolean {
        return buttons.any { row -> row.any { it.isEnabled } } // Any button is still enabled
    }

    // Check if the current player has won
    private fun checkWin(): Boolean {
        val symbol = if (isPlayerXTurn) "X" else "O"

        // Check rows, columns, and diagonals
        for (i in 0..2) {
            // Check rows
            if (buttons[i][0].text == symbol && buttons[i][1].text == symbol && buttons[i][2].text == symbol) return true
            // Check columns
            if (buttons[0][i].text == symbol && buttons[1][i].text == symbol && buttons[2][i].text == symbol) return true
        }
        // Check diagonals
        if (buttons[0][0].text == symbol && buttons[1][1].text == symbol && buttons[2][2].text == symbol) return true
        if (buttons[0][2].text == symbol && buttons[1][1].text == symbol && buttons[2][0].text == symbol) return true

        return false
    }

    // Check if the game is a tie
    private fun checkTie(): Boolean {
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                if (buttons[i][j].text.isEmpty()) return false // Empty button found, not a tie
            }
        }
        return true // No empty buttons, it's a tie
    }

    // Disable all buttons after a win or tie
    private fun disableButtons() {
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    // Reset the game board and turn indicator
    private fun resetGame() {
        isPlayerXTurn = true
        turnTextView.text = "Player X's Turn"

        // Clear all buttons and enable them
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }
    }
}

