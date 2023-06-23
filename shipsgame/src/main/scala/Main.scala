import main.battleship.ShipFactory

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world!")
    val factory = new ShipFactory()

    // Wyświetlanie dostępnych statków
    factory.printShips()

    // Próba utworzenia statku nieistniejącego typu
    println(factory.createShip("Type0"))

    // Utworzenie statku typu 1
    println(factory.createShip("Type1"))

    // Próba utworzenia statku typu 1 jeszcze raz
    println(factory.createShip("Type1"))

    // Wyświetlanie dostępnych statków po utworzeniu statku typu 1
    factory.printShips()

    // Utworzenie statku typu 2
    println(factory.createShip("Type2"))

    // Wyświetlanie dostępnych statków po utworzeniu statku typu 2
    factory.printShips()

  }
}

//board
//vector
//ship
//user


//wybierz typ1
//
//
//

//
