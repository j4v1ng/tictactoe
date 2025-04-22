package com.javing.models

/**
 * Represents the tic-tac-toe game board.
 * The board is a 3x3 grid where each cell can be empty, X, or O.
 */
class Board {
    // The board is represented as a 3x3 grid
    private val grid = Array(3) { Array(3) { Cell.EMPTY } }
    
    /**
     * Makes a move on the board.
     * @param row The row index (0-2)
     * @param col The column index (0-2)
     * @param cell The cell value (X or O)
     * @return true if the move was successful, false if the cell is already occupied
     */
    fun makeMove(row: Int, col: Int, cell: Cell): Boolean {
        if (row !in 0..2 || col !in 0..2) {
            return false
        }
        
        if (grid[row][col] != Cell.EMPTY) {
            return false
        }
        
        grid[row][col] = cell
        return true
    }
    
    /**
     * Gets the value of a cell on the board.
     * @param row The row index (0-2)
     * @param col The column index (0-2)
     * @return The cell value (EMPTY, X, or O)
     */
    fun getCell(row: Int, col: Int): Cell {
        if (row !in 0..2 || col !in 0..2) {
            throw IllegalArgumentException("Invalid row or column index")
        }
        
        return grid[row][col]
    }
    
    /**
     * Checks if the board is full.
     * @return true if the board is full, false otherwise
     */
    fun isFull(): Boolean {
        return grid.all { row -> row.all { it != Cell.EMPTY } }
    }
    
    /**
     * Checks if there is a winner on the board.
     * @return The winning cell (X or O) or null if there is no winner
     */
    fun getWinner(): Cell? {
        // Check rows
        for (row in 0..2) {
            if (grid[row][0] != Cell.EMPTY && 
                grid[row][0] == grid[row][1] && 
                grid[row][1] == grid[row][2]) {
                return grid[row][0]
            }
        }
        
        // Check columns
        for (col in 0..2) {
            if (grid[0][col] != Cell.EMPTY && 
                grid[0][col] == grid[1][col] && 
                grid[1][col] == grid[2][col]) {
                return grid[0][col]
            }
        }
        
        // Check diagonals
        if (grid[0][0] != Cell.EMPTY && 
            grid[0][0] == grid[1][1] && 
            grid[1][1] == grid[2][2]) {
            return grid[0][0]
        }
        
        if (grid[0][2] != Cell.EMPTY && 
            grid[0][2] == grid[1][1] && 
            grid[1][1] == grid[2][0]) {
            return grid[0][2]
        }
        
        return null
    }
    
    /**
     * Resets the board to its initial state.
     */
    fun reset() {
        for (row in 0..2) {
            for (col in 0..2) {
                grid[row][col] = Cell.EMPTY
            }
        }
    }
    
    /**
     * Gets a copy of the current grid.
     * @return A copy of the grid
     */
    fun getGrid(): Array<Array<Cell>> {
        return Array(3) { row -> Array(3) { col -> grid[row][col] } }
    }
    
    /**
     * Gets a list of available moves on the board.
     * @return A list of Pair<Int, Int> representing the available (row, col) positions
     */
    fun getAvailableMoves(): List<Pair<Int, Int>> {
        val moves = mutableListOf<Pair<Int, Int>>()
        
        for (row in 0..2) {
            for (col in 0..2) {
                if (grid[row][col] == Cell.EMPTY) {
                    moves.add(Pair(row, col))
                }
            }
        }
        
        return moves
    }
}

/**
 * Represents the possible values for a cell on the board.
 */
enum class Cell {
    EMPTY, X, O
}