import scala.io.StdIn.readLine

object DungeonGame:

  /**
   * Solicita ao usuário um número inteiro com uma mensagem personalizada.
   * Continua solicitando até que uma entrada válida seja fornecida.
   *
   * @param prompt Mensagem exibida ao usuário.
   * @return Número inteiro fornecido pelo usuário.
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
          println("Entrada inválida. Por favor, insira um número inteiro.")
    value

  /**
   * Solicita ao usuário um número inteiro com um valor mínimo especificado.
   * Continua solicitando até que uma entrada válida e dentro do limite seja fornecida.
   *
   * @param prompt Mensagem exibida ao usuário.
   * @param min Valor mínimo aceitável.
   * @return Número inteiro fornecido pelo usuário.
   */
  def promptForIntWithMin(prompt: String, min: Int): Int =
    var value = 0
    var valid = false
    while !valid do
      value = promptForInt(prompt)
      if value >= min then
        valid = true
      else
        println(s"O valor deve ser pelo menos $min.")
    value

  /**
   * Exibe a matriz do calabouço de forma tabular, destacando a posição atual do cavaleiro.
   *
   * @param dungeon Matriz representando o calabouço.
   * @param position Tupla representando a posição atual do cavaleiro.
   */
  def printDungeon(dungeon: Array[Array[Int]], position: (Int, Int)): Unit =
    println("\nMatriz do Calabouço:")
    for i <- dungeon.indices do
      for j <- dungeon(i).indices do
        if (i, j) == position then
          print(f"[${dungeon(i)(j)}%3d]")
        else
          print(f" ${dungeon(i)(j)}%3d ")
      println()
    println()

  /**
   * Função principal que executa o programa.
   * Solicita entradas do usuário, exibe a matriz e simula a trajetória do cavaleiro.
   */
  def main(args: Array[String]): Unit =
    println("Bem-vindo ao Jogo do Calabouço!")

    // Solicita o tamanho do calabouço
    val rows = promptForInt("Informe o número de linhas do calabouço:")
    val cols = promptForInt("Informe o número de colunas do calabouço:")

    // Solicita os valores do calabouço
    val dungeon = Array.ofDim[Int](rows, cols)
    println(s"Informe os valores do calabouço (${rows * cols} valores):")
    for i <- 0 until rows do
      for j <- 0 until cols do
        dungeon(i)(j) = promptForInt(s"Valor para a célula ($i, $j):")

    // Exibe a matriz do calabouço
    val initialPosition = (0, 0)
    printDungeon(dungeon, initialPosition)

    // Solicita a saúde inicial do cavaleiro
    val initialHealth = promptForIntWithMin("Informe a vida inicial do cavaleiro (mínimo de 7):", 7)

    // Inicializa a posição e a saúde do cavaleiro
    var health = initialHealth + dungeon(initialPosition._1)(initialPosition._2)
    var position = initialPosition
    println(s"Posição inicial: (${position._1}, ${position._2}), Vida: $health")

    // Loop de movimentos
    while position != (rows - 1, cols - 1) && health > 0 do
      println("Informe o próximo movimento ('r' para direita, 'd' para baixo):")
      val move = readLine().trim.toLowerCase
      move match
        case "r" =>
          if position._2 + 1 < cols then
            position = (position._1, position._2 + 1)
            health += dungeon(position._1)(position._2)
            printDungeon(dungeon, position)
            println(s"Movimento para a direita. Posição: (${position._1}, ${position._2}), Vida: $health")
          else
            println("Movimento inválido: fora dos limites do calabouço.")
        case "d" =>
          if position._1 + 1 < rows then
            position = (position._1 + 1, position._2)
            health += dungeon(position._1)(position._2)
            printDungeon(dungeon, position)
            println(s"Movimento para baixo. Posição: (${position._1}, ${position._2}), Vida: $health")
          else
            println("Movimento inválido: fora dos limites do calabouço.")
        case _ =>
          println(s"Movimento desconhecido: $move")

    // Verifica o resultado final
    if health <= 0 then
      println("O cavaleiro morreu durante a jornada.")
    else if position == (rows - 1, cols - 1) then
      println(s"O cavaleiro resgatou a princesa com $health de vida restante!")
