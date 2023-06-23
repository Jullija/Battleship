package main.battleship


class Ship(val shipType: ShipType.Value,val length: Int, val width: Int) {
  val size: Int = length * width
  var occupied_fields: Int = size

  override def toString: String = {
    "Ship: " + shipType + " size: " + size
  }
}
