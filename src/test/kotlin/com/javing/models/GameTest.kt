package com.javing.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameTest {
    
    @Test
    fun `test initial game state`() {
        val game = Game()
        
        // Check initial game status
        assertEquals(GameStatus.IN_PROGRESS, game.gameStatus)
        
        // Check that player starts with X
        assertEquals(Cell.X, game.currentPlayer)
        
        // Check that statistics are initialized to zero
        assertEquals(0, game.playerWins)
        assertEquals(0, game.computerWins)
        assertEquals(0, game.draws)
        
        // Check that the board is empty
        val gameState = game.getGameState()
        for (row in 0..2) {
            for (col in 0..2) {
                assertEquals(Cell.EMPTY, gameState.board[row][col])
            }
        }
    }
    
    @Test
    fun `test player move`() {
        val game = Game()
        
        // Make a valid player move
        assertTrue(game.makePlayerMove(0, 0))
        
        // Check that the move was recorded on the board
        assertEquals(Cell.X, game.board.getCell(0, 0))
        
        // Check that the computer made a move in response
        var emptyCellCount = 0
        for (row in 0..2) {
            for (col in 0..2) {
                if (game.board.getCell(row, col) == Cell.EMPTY) {
                    emptyCellCount++
                }
            }
        }
        assertEquals(7, emptyCellCount) // 9 cells - 1 player move - 1 computer move = 7 empty cells
        
        // Check that the game is still in progress
        assertEquals(GameStatus.IN_PROGRESS, game.gameStatus)
    }
    
    @Test
    fun `test invalid player move`() {
        val game = Game()
        
        // Make a valid player move
        assertTrue(game.makePlayerMove(0, 0))
        
        // Try to make a move on an occupied cell
        assertFalse(game.makePlayerMove(0, 0))
        
        // Try to make a move with invalid coordinates
        assertFalse(game.makePlayerMove(3, 0))
        assertFalse(game.makePlayerMove(0, 3))
        assertFalse(game.makePlayerMove(-1, 0))
        assertFalse(game.makePlayerMove(0, -1))
    }
    
    @Test
    fun `test player win`() {
        val game = Game()
        
        // Set up a scenario where player can win
        // X | O | -
        // - | X | O
        // - | - | -
        game.makePlayerMove(0, 0) // Player X at (0,0)
        // Computer O will be placed somewhere, let's say (0,1)
        game.makePlayerMove(1, 1) // Player X at (1,1)
        // Computer O will be placed somewhere, let's say (1,2)
        
        // Player makes winning move
        game.makePlayerMove(2, 2) // Player X at (2,2) to complete diagonal
        
        // Check that player won
        assertEquals(GameStatus.PLAYER_WINS, game.gameStatus)
        assertEquals(1, game.playerWins)
        assertEquals(0, game.computerWins)
        assertEquals(0, game.draws)
    }
    
    @Test
    fun `test computer win`() {
        val game = Game()
        
        // We'll manipulate the board directly to set up a scenario where computer can win
        // This is a bit of a hack for testing purposes
        game.board.makeMove(0, 0, Cell.X) // Player
        game.board.makeMove(0, 1, Cell.O) // Computer
        game.board.makeMove(1, 1, Cell.X) // Player
        game.board.makeMove(1, 0, Cell.O) // Computer
        
        // Now the board looks like:
        // X | O | -
        // O | X | -
        // - | - | -
        
        // Computer needs one more move at (2,0) to win
        // We'll simulate this by making a player move that forces the computer to make the winning move
        game.makePlayerMove(0, 2) // Player X at (0,2)
        
        // Check that computer won
        assertEquals(GameStatus.COMPUTER_WINS, game.gameStatus)
        assertEquals(0, game.playerWins)
        assertEquals(1, game.computerWins)
        assertEquals(0, game.draws)
    }
    
    @Test
    fun `test draw game`() {
        val game = Game()
        
        // We'll manipulate the board directly to set up a draw scenario
        // This is a bit of a hack for testing purposes
        game.board.makeMove(0, 0, Cell.X) // Player
        game.board.makeMove(0, 1, Cell.O) // Computer
        game.board.makeMove(0, 2, Cell.X) // Player
        game.board.makeMove(1, 0, Cell.O) // Computer
        game.board.makeMove(1, 1, Cell.X) // Player
        game.board.makeMove(1, 2, Cell.O) // Computer
        game.board.makeMove(2, 0, Cell.O) // Computer
        game.board.makeMove(2, 1, Cell.X) // Player
        
        // Now the board looks like:
        // X | O | X
        // O | X | O
        // O | X | -
        
        // Player makes the last move to fill the board
        game.makePlayerMove(2, 2) // Player X at (2,2)
        
        // Check that it's a draw
        assertEquals(GameStatus.DRAW, game.gameStatus)
        assertEquals(0, game.playerWins)
        assertEquals(0, game.computerWins)
        assertEquals(1, game.draws)
    }
    
    @Test
    fun `test new game`() {
        val game = Game()
        
        // Make some moves
        game.makePlayerMove(0, 0)
        
        // Start a new game
        game.newGame()
        
        // Check that the game was reset
        assertEquals(GameStatus.IN_PROGRESS, game.gameStatus)
        assertEquals(Cell.X, game.currentPlayer)
        
        // Check that the board is empty
        val gameState = game.getGameState()
        for (row in 0..2) {
            for (col in 0..2) {
                assertEquals(Cell.EMPTY, gameState.board[row][col])
            }
        }
        
        // Check that statistics were preserved
        assertEquals(0, game.playerWins)
        assertEquals(0, game.computerWins)
        assertEquals(0, game.draws)
    }
    
    @Test
    fun `test game state`() {
        val game = Game()
        
        // Make a move
        game.makePlayerMove(0, 0)
        
        // Get the game state
        val gameState = game.getGameState()
        
        // Check that the game state reflects the current state
        assertEquals(GameStatus.IN_PROGRESS, gameState.status)
        assertEquals(0, gameState.playerWins)
        assertEquals(0, gameState.computerWins)
        assertEquals(0, gameState.draws)
        assertEquals(Cell.X, gameState.board[0][0])
    }
}