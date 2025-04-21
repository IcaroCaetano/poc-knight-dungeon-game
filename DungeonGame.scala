/** 
 * Solves the "Dungeon Game" problem where a knight must traverse a dungeon represented by a 2D grid
 * to rescue a princess imprisoned in the bottom-right cell. Each cell may damage or heal the knight,
 * and he can only move right or down.
 */
object DungeonGame:

  /**
   * Calculates the knight's minimum initial health required to reach the princess alive.
   *
   * The dungeon is represented as a 2D array where:
   * - Negative values represent demons that reduce health,
   * - Zero represents empty rooms,
   * - Positive values represent magic orbs that increase health.
   *
   * The knight dies if his health ever drops to 0 or below during the journey.
   *
   * @param dungeon A 2D array of integers representing the dungeon layout.
   * @return The minimum initial health required to ensure the knight can reach the princess.
   */
  def calculateMinimumHP(dungeon: Array[Array[Int]]): Int =
    val m = dungeon.length
    val n = dungeon(0).length

    // dp(i)(j) stores the minimum health needed to reach the goal from cell (i, j)
    val dp = Array.fill(m + 1, n + 1)(Int.MaxValue)

    // Initialize the virtual borders to simplify edge conditions
    dp(m)(n - 1) = 1
    dp(m - 1)(n) = 1

    for i <- (0 until m).reverse do
      for j <- (0 until n).reverse do
        val minHealth = math.min(dp(i + 1)(j), dp(i)(j + 1)) - dungeon(i)(j)
        dp(i)(j) = math.max(minHealth, 1)

    dp(0)(0)

  /**
   * Entry point of the application. Runs two example scenarios.
   */
  @main def run(): Unit =
    val dungeon1 = Array(
      Array(-2, -3, 3),
      Array(-5, -10, 1),
      Array(10, 30, -5)
    )
    println(s"Minimum initial health: ${calculateMinimumHP(dungeon1)}") // Should print 7

    val dungeon2 = Array(Array(0))
    println(s"Minimum initial health: ${calculateMinimumHP(dungeon2)}") // Should print 1
