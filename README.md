
# Chess Project in Java (Chess CLI Bot)

## 🧠 Objective
Create a chess game playable in the terminal using Java, with the possibility to play against a bot. The project will be fully developed as a team, focusing on collaboration, code quality, and task division.

## ⚙️ Requirements
- Java 17+
- Terminal-based gameplay (CLI)
- Mode: 1v1 (player vs bot)
- Follow standard chess rules
- Basic AI (valid moves + simple strategy)

## 🏗️ Project Architecture

```
chess/
│
├── src/
|   ├── main/
|   |   └── java/
|   |       ├── board/
|   |       ├── game/
|   |       └── pieces/
│   └── test/
│
├── .gitignore
├── LICENSE
├── pom.xml
└── README.md
```

## 🧑‍🤝‍🧑 Task Division

### 👤 Nicholas – Game Management
- `Game.java`, `Main.java`
- Turn logic, win/loss conditions, new game setup
- Coordination between board and players

### 👤 Khushika – Piece Modeling
- `Piece.java` + subclasses (`King`, `Queen`, etc.)
- Valid move logic for each piece
- Special rules: castling, promotion, check

### 👤 Samuele – Board and Input
- `Board.java`: CLI rendering, drawing pieces
- `InputHandler.java`: input parsing (e.g., `e2e4`)
- Valid move detection and display

### 👤 Kevin - Unit Tests
- Testing with JUnit

### 👤 All – Bot
- `BotPlayer.java`: legal move generation
- Basic heuristics (e.g., capture, random, defense)
- Simple check/checkmate evaluation
