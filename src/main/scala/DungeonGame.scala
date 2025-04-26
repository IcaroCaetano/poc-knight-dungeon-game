import scala.io.StdIn.readLine

object DungeonGame:

  /**
   * Prompts the user for an integer with a custom message.
   * Keeps prompting until a valid input is provided.
   *
   * @param prompt Message displayed to the user.
   * @return Integer value provided by the user.
   */
  def promptForInt(prompt: String): Int =
    var valid = false
    var value = 0
    while !valid do
      println(prompt)
      try
        value = readLine().toInt
        valid = true
      catch
        case _: NumberFormatException =>
          println("Invalid input. Please enter an integer.")
    value

  /**
   * Prompts the user for an integer with a specified minimum value.
   * Keeps prompting until a valid and acceptable input is provided.
   *
   * @param prompt Message displayed to the user.
   * @param min Minimum acceptable value.
   * @return Integer value provided by the user.
   */
  def promptForIntWithMin(prompt: String, min: Int): Int =
    var value = 0
    var valid = false
    while !valid do
      value = promptForInt(prompt)
      if value >= min then
        valid = true
      else
        println(s"The value must be at least $min.")
    value

  /**
   * Displays the dungeon matrix in tabular form, highlighting the knight's current position.
   *
   * @param dungeon Matrix representing the dungeon.
   * @param position Tuple representing the knight's current position.
   */
  def printDungeon(dungeon: Array[Array[Int]], position: (Int, Int)): Unit =
    println("\nDungeon Matrix:")
    for i <- dungeon.indices do
      for j <- dungeon(i).indices do
        if (i, j) == position then
          print(f"[${dungeon(i)(j)}%3d]")
        else
          print(f" ${dungeon(i)(j)}%3d ")
      println()
    println()

  /**
   * Main function that runs the program.
   * Prompts for user input, displays the matrix, and simulates the knight's path.
   */
  def main(args: Array[String]): Unit =
    println("Welcome to the Dungeon Game!")

    // Prompt for dungeon size
    val rows = promptForInt("Enter the number of dungeon rows:")
    val cols = promptForInt("Enter the number of dungeon columns:")

    // Prompt for dungeon values
    val dungeon = Array.ofDim[Int](rows, cols)
    println(s"Enter the dungeon values (${rows * cols} values):")
    for i <- 0 until rows do
      for j <- 0 until cols do
        dungeon(i)(j) = promptForInt(s"Value for cell ($i, $j):")

    // Display the dungeon matrix
    val initialPosition = (0, 0)
    printDungeon(dungeon, initialPosition)

    // Prompt for initial knight health
    val initialHealth = promptForIntWithMin("Enter the knight's initial health (minimum 7):", 7)

    // Initialize knight's position and health
    var health = initialHealth + dungeon(initialPosition._1)(initialPosition._2)
    var position = initialPosition
    println(s"Starting position: (${position._1}, ${position._2}), Health: $health")

    // Movement loop
    while position != (rows - 1, cols - 1) && health > 0 do
      println("Enter the next move ('r' for right, 'd' for down):")
      val move = readLine().trim.toLowerCase
      move match
        case "r" =>
          if position._2 + 1 < cols then
            position = (position._1, position._2 + 1)
            health += dungeon(position._1)(position._2)
            printDungeon(dungeon, position)
            println(s"Moved right. Position: (${position._1}, ${position._2}), Health: $health")
          else
            println("Invalid move: out of dungeon bounds.")
        case "d" =>
          if position._1 + 1 < rows then
            position = (position._1 + 1, position._2)
            health += dungeon(position._1)(position._2)
            printDungeon(dungeon, position)
            println(s"Moved down. Position: (${position._1}, ${position._2}), Health: $health")
          else
            println("Invalid move: out of dungeon bounds.")
        case _ =>
          println(s"Unknown move: $move")

    // Check final result
    if health <= 0 then
      println("The knight died during the journey.")
    else if position == (rows - 1, cols - 1) then
       println(s"The knight rescued the princess with $health health remaining!")
       println("""
               _______________
                '._==_==_=_.'
                .-\:      /-.
               | (|:.     |) |
                '-|:.     |-'
                  \::.    /
                   '::. .'
                     ) (
                   _.' '._
                  `-------`

              The Princess is Rescued!""")