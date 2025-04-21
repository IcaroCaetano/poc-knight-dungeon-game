## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).

# 🏰 Dungeon Game - Scala 3

## 📜 Problem Description

The demons have captured the princess and imprisoned her in the bottom-right corner of a dungeon.

The dungeon is an `m x n` 2D grid of rooms. Each room contains:
- A **negative** integer: a demon that reduces the knight’s health,
- A **zero**: an empty room,
- A **positive** integer: a magic orb that increases the knight’s health.

The knight starts in the **top-left** room and wants to rescue the princess in the **bottom-right** room.  
He can only move **right** or **down**.

The knight **dies immediately** if his health drops to **0 or less** at any point.

### 🎯 Goal

Return the **minimum initial health** the knight needs so that he can rescue the princess **alive**, assuming he follows the **optimal path**.

---

## 📥 Example

```scala
val dungeon = Array(
  Array(-2, -3, 3),
  Array(-5, -10, 1),
  Array(10, 30, -5)
)
println(calculateMinimumHP(dungeon)) // Output: 7
```

### ⚙️ Requirements

- Java JDK 17 or higher

- Scala 3

- sbt (Scala Build Tool)

- VS Code with Metals plugin

### 🚀 Installation and Running

1. Clone the project

```
git clone https://github.com/your-user/dungeon-game-scala.git
cd dungeon-game-scala
```

2. Run the project using sbt

```
sbt run
```
You should see:


```
Minimum initial health: 7
Minimum initial health: 1
```

### 🧠 Logic Explanation
We use Dynamic Programming to calculate the minimum health needed at each cell starting from the bottom-right back to the top-left.

We define a matrix dp(i)(j) that stores the minimum health the knight needs to survive and reach the princess from that cell.

The value is calculated as:

```
dp(i)(j) = max(1, min(dp(i+1)(j), dp(i)(j+1)) - dungeon(i)(j))
```

Where:

- min(dp(i+1)(j), dp(i)(j+1)) ensures we take the path that requires less health.

- max(..., 1) ensures that the knight never drops below 1 HP


### 📂 Project Structure

```
.
├── build.sbt
├── project/
├── src/
│   └── main/
│       └── scala/
│           └── DungeonGame.scala
└── README.md
```
