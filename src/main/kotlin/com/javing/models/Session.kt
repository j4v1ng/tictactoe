package com.javing.models

import kotlinx.serialization.Serializable

/**
 * Represents a user session for the tic-tac-toe game.
 * @property id The unique identifier for the session
 */
@Serializable
data class TicTacToeSession(val id: String)
