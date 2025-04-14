# Chess – Project Plan

## 🧩 Overview

A fully playable chess game in the terminal using Java. The game should support:
- Standard chess rules
- Player vs Bot
- (Optional) Two-player mode (local)
- Text-based board display
- Input handling (e.g., "e2 e4")

---

## 📁 Project Structure

```
chess/
│
├── src/
│   ├── board/
│   │   └── ChessBoard.java
│   ├── pieces/
│   │   ├── Piece.java
│   │   ├── King.java
│   │   ├── Queen.java
│   │   ├── Rook.java
│   │   ├── Bishop.java
│   │   ├── Knight.java
│   │   └── Pawn.java
│   ├── game/
│   │   ├── ChessGame.java
│   │   └── MoveValidator.java
│   └── ui/
│       └── TerminalUI.java
│
├── tests/
│   └── (Unit test files for core logic)
│
├── README.md
└── Main.java
```

---

## ✅ Features

### Core Features
- [ ] Render board in terminal
- [ ] Standard piece movement
- [ ] Turn system (White / Black)
- [ ] Bot
- [ ] Basic move validation
- [ ] Check & checkmate detection
- [ ] Castling, en passant, pawn promotion

### Future Features
- [ ] Undo/Redo
- [ ] Save/Load game
- [ ] AI opponent
- [ ] Highlight legal moves

---

## 🧠 Class Overview

### `ChessBoard`
- 2D array to store board state
- Initialize with default layout
- Methods: `movePiece`, `getPiece`, `isInCheck`

### `Piece` (Abstract)
- Fields: color, position
- Methods: `getLegalMoves()`, `getSymbol()`

#### Concrete Pieces
- `King`, `Queen`, `Rook`, `Bishop`, `Knight`, `Pawn`
- Implement `getLegalMoves()` based on movement rules

### `MoveValidator`
- Check if a move is legal
- Detect check/checkmate

### `ChessGame`
- Manages game flow
- Stores current turn, move history

### `TerminalUI`
- Handles input/output
- Displays board
- Prompts user for move

---

## 🧪 Testing Strategy

- Unit tests for:
  - Each piece’s movement logic
  - Board updates after moves
  - Check and checkmate detection
- Use JUnit for testing

---

## 🎯 Milestones

| Milestone      | Description                           | Target Date |
| -------------- | ------------------------------------- | ----------- |
| Project setup  | Define structure, create base classes | Apr 15      |
| Basic gameplay | Pieces move, board renders, turns     | Apr 18      |
| Rules handling | Check/checkmate, castling, promotion  | Apr 21      |
| Polish & test  | UI improvements, testing, bug fixes   | Apr 24      |
