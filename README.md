# Tic-Tac-Toe Game

A simple web-based Tic-Tac-Toe game built with Kotlin and Ktor.

## Features

- **Player vs Computer**: Play against a simple AI opponent
- **Player vs Player**: Play against another human player on the same device
- **Responsive Design**: Works on desktop and mobile devices
- **Game Statistics**: Track wins, losses, and draws

## How to Play

1. Open the game in your web browser
2. Select a game mode:
   - **Player vs Computer**: You play as X against the computer (O)
   - **Player vs Player**: Two players take turns (Player 1 is X, Player 2 is O)
3. Click on an empty cell to make a move
4. The game will automatically detect wins or draws
5. Click "New Game" to start a new game

## Game Modes

### Player vs Computer
In this mode, you play as X against the computer, which plays as O. The computer uses a simple algorithm to make its moves.

### Player vs Player
In this mode, two players take turns making moves on the same device. Player 1 plays as X and Player 2 plays as O.

## Technical Details

This game is built with:
- Kotlin
- Ktor web framework
- Thymeleaf templates
- HTML/CSS/JavaScript

## Running the Game

To run the game locally:

1. Clone the repository
2. Make sure you have Java and Gradle installed
3. Run `./gradlew run` from the project root
4. Open your browser and navigate to `http://localhost:8080`

## License

This project is open source and available under the MIT License.