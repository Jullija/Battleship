package main.battleship
import Constants._
import main.battleship.SimpleUser.trials

import scala.util.Random

object ComputerUser extends User {
  var board: Board = new Board
  var enemy_board: Board = new Board
  var points: Int = 0
  var trials: Int = 0
  var state: String = "Hunt"
  var lastHitX: Int = -1
  var lastHitY: Int = -1

  override def attack(x_x: Int = 1, y_y: Int = 1): Unit = {
    trials+=1
    if (state == "Hunt") {
      var x = Random.nextInt(SIZE)
      var y = Random.nextInt(SIZE)

      if (enemy_board.tryAttack(x, y)) {
        println(s"Komputer trafił na polu ($x, $y)!")
        points += ACQUIRED_POINTS
        state = "Target"
        lastHitX = x
        lastHitY = y
      } else {
        println(s"Komputer chybił na polu ($x, $y).")
        points = Math.max(points - LOST_POINTS,0)
      }
    } else if (state == "Target") {
      val directions = Array((-1, 0), (1, 0), (0, -1), (0, 1)) // left, right, up, down
      for ((dx, dy) <- directions) {
        val x = lastHitX + dx
        val y = lastHitY + dy
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE && !enemy_board.alreadyChecked(x, y)) {
          if (enemy_board.tryAttack(x, y)) {
            println(s"Komputer trafił na polu ($x, $y)!")
            points += 5
            lastHitX = x
            lastHitY = y
          } else {
            println(s"Komputer chybił na polu ($x, $y).")
            points -= 1
          }
          return
        }
      }
      state = "Hunt"
    }
  }

  override def isDefeated(): Boolean = {
    board.isEmpty
  }
}
