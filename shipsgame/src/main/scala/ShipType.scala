package main.battleship

object ShipType extends Enumeration {
  type ShipType = Value
  val Type1, Type2, Type3, Type4 = Value

  case class ShipAttributes(length: Int, width: Int)

  private val shipAttributes: Map[ShipType, ShipAttributes] = Map(
    Type1 -> ShipAttributes(1, 5),
    Type2 -> ShipAttributes(2, 4),
    Type3 -> ShipAttributes(1, 3),
    Type4 -> ShipAttributes(1, 2)
  )

  def attributesOf(shipType: ShipType): Option[ShipAttributes] = shipAttributes.get(shipType)
}
