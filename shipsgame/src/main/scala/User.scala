package main.battleship
trait User {
  var board: Board
  var enemy_board: Board
  var points: Int //ilosc nabytych ponktow
  var trials: Int //ilosc strzalow

  def attack(x: Int, y: Int): Unit

  def isDefeated(): Boolean
}