package main.battleship

import scala.collection.mutable
class ShipFactory {
  val usedShips: mutable.HashSet[ShipType.ShipType] = mutable.HashSet.empty

  def checkIfUsed(shipType: ShipType.ShipType): Boolean = usedShips.contains(shipType)

  def addToUsed(shipType: ShipType.ShipType): Unit = usedShips += shipType
  def everyTypeUsed(): Boolean = usedShips.size == ShipType.values.size
  def removeFromUsed(shipType: ShipType.ShipType): Unit = usedShips.remove(shipType)

  def createShip(shipType: String): Option[Ship] = {
    try {
      val shipTypeEnum = ShipType.withName(shipType)
      if (checkIfUsed(shipTypeEnum)) {
        println("Ten typ statku został już użyty.")
        None
      } else {
        addToUsed(shipTypeEnum)
        ShipType.attributesOf(shipTypeEnum) match {
          case Some(attr) =>
            val ship = new Ship(shipTypeEnum, attr.length, attr.width)
            Some(ship)
          case None =>
            println("Nie znaleziono atrybutów dla podanego typu statku.")
            None
        }
      }
    } catch {
      case _: NoSuchElementException =>
        println("Nie ma takiego typu statku.")
        None
    }
  }

  def printShips(): Unit = {
    println("Dostępne typy statków:")
    ShipType.values.foreach { shipType =>
      if (!usedShips.contains(shipType)) {
        ShipType.attributesOf(shipType) match {
          case Some(attr) =>
            println(s"Typ: $shipType, Długość: ${attr.length}, Szerokość: ${attr.width}")
          case None =>
            println(s"Typ: $shipType, brak dostępnych atrybutów.")
        }
      }
    }
  }
}