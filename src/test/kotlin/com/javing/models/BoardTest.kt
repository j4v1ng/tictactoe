package com.javing.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BoardTest {
    
    @Test
    fun `test initial board state`() {
        val board = Board()
        
        // Check that all cells are empty
        for (row in 0..2) {
            for (col in 0..2) {
                assertEquals(Cell.EMPTY, board.getCell(row, col))
            }
        }
        
        // Check that there is no winner
        assertNull(board.getWinner())
        
        // Check that the board is not full
        assertFalse(board.isFull())
        
        // Check that there are 9 available moves
        assertEquals(9, board.getAvailableMoves().size)
    }
    
    @Test
    fun `test making valid moves`() {
        val board = Board()
        
        // Make a valid move
        assertTrue(board.makeMove(0, 0, Cell.X))
        assertEquals(Cell.X, board.getCell(0, 0))
        
        // Make another valid move
        assertTrue(board.makeMove(1, 1, Cell.O))
        assertEquals(Cell.O, board.getCell(1, 1))
        
        // Check that there are 7 available moves
        assertEquals(7, board.getAvailableMoves().size)
    }
    
    @Test
    fun `test making invalid moves`() {
        val board = Board()
        
        // Make a valid move
        assertTrue(board.makeMove(0, 0, Cell.X))
        
        // Try to make a move on an occupied cell
        assertFalse(board.makeMove(0, 0, Cell.O))
        assertEquals(Cell.X, board.getCell(0, 0))
        
        // Try to make a move with invalid coordinates
        assertFalse(board.makeMove(3, 0, Cell.O))
        assertFalse(board.makeMove(0, 3, Cell.O))
        assertFalse(board.makeMove(-1, 0, Cell.O))
        assertFalse(board.makeMove(0, -1, Cell.O))
    }
    
    @Test
    fun `test horizontal win detection`() {
        val board = Board()
        
        // Create a horizontal win for X in the top row
        board.makeMove(0, 0, Cell.X)
        board.makeMove(0, 1, Cell.X)
        board.makeMove(0, 2, Cell.X)
        
        assertEquals(Cell.X, board.getWinner())
    }
    
    @Test
    fun `test vertical win detection`() {
        val board = Board()
        
        // Create a vertical win for O in the middle column
        board.makeMove(0, 1, Cell.O)
        board.makeMove(1, 1, Cell.O)
        board.makeMove(2, 1, Cell.O)
        
        assertEquals(Cell.O, board.getWinner())
    }
    
    @Test
    fun `test diagonal win detection`() {
        val board = Board()
        
        // Create a diagonal win for X from top-left to bottom-right
        board.makeMove(0, 0, Cell.X)
        board.makeMove(1, 1, Cell.X)
        board.makeMove(2, 2, Cell.X)
        
        assertEquals(Cell.X, board.getWinner())
        
        // Reset the board
        board.reset()
        
        // Create a diagonal win for O from top-right to bottom-left
        board.makeMove(0, 2, Cell.O)
        board.makeMove(1, 1, Cell.O)
        board.makeMove(2, 0, Cell.O)
        
        assertEquals(Cell.O, board.getWinner())
    }
    
    @Test
    fun `test full board detection`() {
        val board = Board()
        
        // Fill the board with a pattern that has no winner
        board.makeMove(0, 0, Cell.X)
        board.makeMove(0, 1, Cell.O)
        board.makeMove(0, 2, Cell.X)
        board.makeMove(1, 0, Cell.X)
        board.makeMove(1, 1, Cell.O)
        board.makeMove(1, 2, Cell.X)
        board.makeMove(2, 0, Cell.O)
        board.makeMove(2, 1, Cell.X)
        board.makeMove(2, 2, Cell.O)
        
        assertTrue(board.isFull())
        assertNull(board.getWinner())
        assertEquals(0, board.getAvailableMoves().size)
    }
    
    @Test
    fun `test board reset`() {
        val board = Board()
        
        // Make some moves
        board.makeMove(0, 0, Cell.X)
        board.makeMove(1, 1, Cell.O)
        
        // Reset the board
        board.reset()
        
        // Check that all cells are empty
        for (row in 0..2) {
            for (col in 0..2) {
                assertEquals(Cell.EMPTY, board.getCell(row, col))
            }
        }
        
        // Check that there is no winner
        assertNull(board.getWinner())
        
        // Check that the board is not full
        assertFalse(board.isFull())
        
        // Check that there are 9 available moves
        assertEquals(9, board.getAvailableMoves().size)
    }
}