class Ship(val shipType: ShipType.Value, val length: Int, val width: Int) {
  val size: Int = length * width
  var occupied_fields: Int = size
}
