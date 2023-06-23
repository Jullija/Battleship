package main.battleship
import Constants._
import main.battleship.ComputerUser.points
object SimpleUser extends User {
  var board: Board = new Board
  var enemy_board: Board = new Board
  var points: Int = 0
  var trials: Int = 0

  override def attack(x: Int, y: Int): Unit = {
    if(enemy_board.alreadyChecked(x, y)) println("Juz tu trafiales! Wybierz inne pole")
    else if (enemy_board.tryAttack(x, y)) { //od razu usuwanie wektora z enemy_board jesli sie powiedzie atak
      println(s"Trafiłeś na polu ($x, $y)!")
      points += ACQUIRED_POINTS
    } else {
      println(s"Chybiłeś na polu ($x, $y).")
      points = Math.max(points - LOST_POINTS,0)
    }
    trials+=1
  }

  override def isDefeated(): Boolean = {
    board.isEmpty //cos takiego zeby sprawdzic czy plansza jest pusta - nie ma juz zadnych miejsc ze stateczkami
  }

}