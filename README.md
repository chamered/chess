# ♟️Chess
## 👥 Members
- **Samuele Maltauro** ([@chamered](https://github.com/chamered))
- **Nicholas Zappa** ([@JustVibing-JK](https://github.com/JustVibing-JK))
- **Gupta Khushika** ([@Khushika05](https://github.com/Khushika05))
- **Kevin Trolese Joachin** ([@KJT0](https://github.com/KJT0)) 

> **Note:** Nicholas has made some changes using his second account 
> [**NoobDev-Game**](https://github.com/NoobDev-Game), every contribution by such profile it's still his.
# 📥 Installation 
TODO

---
# 🧭 Overview 
Chess is a **Java CLI application** that aims to replicate **modern chess** in an old fashion way by providing it's users 
with a CLI interaction.  
Our chess has **1v1, 1vBot**, and differently from modern chess, has a **Bot vs Bot** mode. 

# 📑 User's Guide 
Our **chess** follows the standard rules of modern chess.   
To play properly, you will be prompted to write a **move** in the console: movement is performed by writing in **chess algebraic notation**, 
which is identified by writing e.g `e4 e5`.  

If the input provided is both not valid and not allowed, you will receive **feedback**. 
You are provided with useful commands you can use aside from playing:
- `undo` - allows the user to undo the last move
- `restart` - allows the user to restart the game directly
- `exit` - exits the program 
# 🛠 Structure of the Project 
```plaintext 
.
├── LICENSE
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   ├── board
    │   │   │   ├── BoardImpl.java
    │   │   │   ├── Board.java
    │   │   │   └── Position.java
    │   │   ├── game
    │   │   │   ├── GameImpl.java
    │   │   │   ├── Game.java
    │   │   │   ├── GameState.java
    │   │   │   ├── InputHandler.java
    │   │   │   ├── Move.java
    │   │   │   └── RulesEngine.java
    │   │   ├── main
    │   │   │   └── Main.java
    │   │   ├── pieces
    │   │   │   ├── Bishop.java
    │   │   │   ├── Color.java
    │   │   │   ├── King.java
    │   │   │   ├── Knight.java
    │   │   │   ├── Pawn.java
    │   │   │   ├── Piece.java
    │   │   │   ├── Queen.java
    │   │   │   └── Rook.java
    │   │   └── players
    │   │       ├── Bot.java
    │   │       ├── BotPlayer.java
    │   │       ├── HumanPlayer.java
    │   │       └── Player.java
    │   └── resources
    │       └── ansi_escape_codes.txt
    └── test
        └── java
            ├── BoardTest.java
            ├── PieceTest.java
            └── RulesEngineTest.java
```
## 🧩 Components
### 📂 Packages 
Components in this project are divided into packages, each containing it's own "component":
The packages are as follows:
- [Board](./src/main/java/board) - contains all components and interfaces for the board logic 
- [Game](./src/main/java/game) - contains all components and interfaces for the game logic 
- [Main](./src/main/java/main) - contains the entry point of this program (main)
- [Pieces](./src/main/java/pieces) - contains an **abstract class** [Piece](./src/main/java/pieces/Piece.java) and all it's subclasses
- [Players](./src/main/java/players) - contains all the logic for the players (bot and human player)

### 🖊️ Interfaces and Abstract Classes  
The **interfaces** of the project are:
- [Board](./src/main/java/board/Board.java) - specifies the behavior of the [BoardImpl](./src/main/java/board/BoardImpl.java) class
- [Game](./src/main/java/game/Game.java) - specifies all the methods of the [GameImpl](./src/main/java/game/GameImpl.java) class
- [Bot](./src/main/java/players/Bot.java) - specifies all the methods implemented by [BotPlayer](./src/main/java/players/BotPlayer.java)

The **abstract** classes are:
- [Player](./src/main/java/players/Player.java) - specifies the methods and the constructor implemented by both [BotPlayer](./src/main/java/players/BotPlayer.java) and [HumanPlayer](./src/main/java/players/HumanPlayer.java)
- [Piece](./src/main/java/pieces/Piece.java) - specifies the standard behavior of all pieces (which then according to the need, override the standard methods).

## 🎮 Game components
The components listed here are the "essential" components, or classes that have 
been used to build **chess**.   
This components include elementary building blocks, such as the **Pieces** on the board, or the **Board** itself, or include classes used to 
check **game state** or apply **rules**.
The components are:
- [Board](./src/main/java/board/BoardImpl.java) - represents the board where the game is played; it has all the methods to handle and manage the board state and pieces' movements. 
- [Position](./src/main/java/board/Position.java) - a record used to store the position of the piece on the board.
- [Game](./src/main/java/game/GameImpl.java) - represents the game, has methods to interact with the user and to manage the game state.
- [GameState](./src/main/java/game/GameState.java) - enum used to represent all the possible game states. 
- [InputHandler](./src/main/java/game/InputHandler.java) - used to handle incoming inputs from the user. 
- [Move](./src/main/java/game/Move.java) - record used to store the movement via the `Position` class. 
- [RulesEngine](./src/main/java/game/RulesEngine.java) - represents all the in-game rules.
- `Pieces` - are all self-explanatory, they represent the various pieces on the board, having all override methods based on the need.

## 📦 External Libraries 
- [**JUnit**](https://junit.org/) - used for unit testing 
- [**JetBrains Annotations**](https://www.jetbrains.com/help/idea/annotating-source-code.html) - adds `@NotNull` notations and other useful notations. 

## 🖥️ Techniques/Algorithms 
For this project in particular, the **minimax** algorithm was implemented to allow the bot to play against the player.  


---
# 🧑‍🧒‍🧒 The Team's Experience 
## 🧰 Distribution of the Workload 
>**Samuele Maltauro** ([@chamered](https://github.com/chamered))
>- `GameImpl::*`
>- `Board::*`
>- `BoardImpl::*`
>- `InputHandler::*`
>- `BotPlayer::chooseMove`
>- `HumanPlayer::*`
>- `Color::*`
>- `BoardTest::*`
>- `PieceTest::*`
>- `RulesEngineTest::*`
>  
>**Nicholas Zappa** ([@JustVibing-JK](https://github.com/JustVibing-JK)) 
>- `BotPlayer::getOpponentColor`
>- `BotPlayer::minimax`
>- `BotPlayer::evalutateBoard`
>- `GameState::*`
>- `RulesEngine::isMoveLegal`
>- `RulesEngine::wouldCauseSelfCheck`
>- `RulesEngine::getAllLegalMoves`
>- `RulesEngine::isKingInCheck`
>- `RulesEngine::isSquaredAttacked`
>- `RulesEngine::isCheckmate`
>- `RulesEngine::isStalemate`
>- `Piece::copy`
>- `Piece:eatOtherPiece`
>
>**Gupta Khushika** ([@Khushika05](https://github.com/Khushika05))
>- `Piece::*`
>- `Bishop::*`
>- `Pawn::*`
>- `Queen::*`
>- `King::*`
>- `Knight::*`
>- `Rook::*`
>
>**Kevin Trolese Joachin** ([@KJT0](https://github.com/KJT0))
>- `Move::*`
>- `Position::*` 
>- `Game::*`
>- `Bot::*`
>- `RulesEngine::isFiftyMoveRule`
>- `RulesEngine::incrementCounterFromMoveHistory`
>- `RulesEngine::resetMoveHistory`
>- `GameImpl::checkGameState`
>- `GameImpl::undoMove`
>- `BoardImpl::isSamePosition`
>- `BoardImpl::zobristKeyWithColorAndType`
>- `BoardImpl::zobristKey`
>- `BoardImpl::updateHistory`
>- `BoardImpl::getPositionHistory`

## 🗃️ Git's Usage
**Git** version control was used to track the state of our project.  
First, we created a [repository](https://github.com/chamered/chess) for our project, then we pushed the 
basic project template. 

---
# 🏆 Individual Challenges 
## Samuele  
> The biggest challenge for me in this project was handling user input properly and displaying the right feedback messages at the right time.
## Nicholas
> The biggest challenge for me was fixing and implmenting the minimax algorithm for the bot, I really like the BotVSBot mode, where I
> can see what the bot still lacks
## Khushika
> The biggest challenge for me was to program a whole project from scratch. 
## Kevin
> For me the biggest challenge was to coincide everyone's personal life with working on the project.  
> I, for one, admit that I had the most hardship on coinciding my time with this project.
