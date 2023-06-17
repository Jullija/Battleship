package main.battleship

import scala.collection.mutable
class ShipFactory {
  val usedShips: mutable.HashSet[ShipType.Value] = mutable.HashSet.empty

  def checkIfUsed(shipType: ShipType.Value): Boolean = {
    usedShips.contains(shipType)
  }

  def addToUsed(shipType: ShipType.Value): Unit = {
    usedShips += shipType
  }
  def removeFromUsed(shipType: ShipType.Value):Unit = {
    usedShips.remove(shipType);
  }

  //funkcja sprawdzajaca, czy podany string jest poprawnym typem statku (pokrywa sie z enumem)
  def isValidShipType(shipType: String): Boolean = {
    ShipType.values.exists(_.toString == shipType)
  }

  def createShip(shipType: String): Option[Ship] = {
    if(!isValidShipType(shipType)){
      println("Ten typ statku nie istnieje.")
      None
    }
    val shipTypeEnum = ShipType.withName(shipType)
    if(checkIfUsed(shipTypeEnum)) {
      println("Ten typ statku został już użyty.")
      None
    } else {
      addToUsed(shipTypeEnum)
      val ship = shipTypeEnum match {
        case ShipType.Type1 => new Ship(shipTypeEnum, 5, 1)
        case ShipType.Type2 => new Ship(shipTypeEnum, 4, 2)
        case ShipType.Type3 => new Ship(shipTypeEnum, 3, 1)
        case ShipType.Type4 => new Ship(shipTypeEnum, 2, 1)
        // Dodaj tutaj więcej typów statków, jeśli są potrzebne
      }
      Some(ship)
    }
  }


  def printShips(): Unit = {
    println("Dostępne typy statków:")
    ShipType.values.foreach { shipType =>
      if(!usedShips.contains(shipType)) {
        shipType match {
          case ShipType.Type1 => println(s"Typ: $shipType, Długość: 1, Szerokość: 5")
          case ShipType.Type2 => println(s"Typ: $shipType, Długość: 2, Szerokość: 4")
          case ShipType.Type3 => println(s"Typ: $shipType, Długość: 1, Szerokość: 3")
          case ShipType.Type4 => println(s"Typ: $shipType, Długość: 1, Szerokość: 2")
          // Dodaj tutaj więcej typów statków, jeśli są potrzebne
        }
      }
    }
  }
}
