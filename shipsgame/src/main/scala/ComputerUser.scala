package main.battleship
import Constants._

import scala.collection.mutable
import scala.util.Random

object ComputerUser extends User {
  var board: Board = new Board
  var enemy_board: Board = new Board
  var points: Int = 0
  var trials: Int = 0
  var state: String = "Hunt"
  var lastHitX: Int = -1
  var lastHitY: Int = -1
  var possibleTargets = new mutable.HashSet[(Int, Int)](); // Set to store possible target coordinates

  // Initialize the set with all coordinates
  for {
    x <- 0 until SIZE
    y <- 0 until SIZE
  } possibleTargets.add((x,y))

  override def attack(x_x: Int = 1, y_y: Int = 1): Boolean = {
    trials += 1
    var hit = true
    if (state == "Hunt") {
      val targetIndex = Random.nextInt(possibleTargets.size) // Randomly select an index
      val target = possibleTargets.toVector(targetIndex) // Convert set to vector and select the target
      possibleTargets.remove(target) // Remove the target from the set
      val (x, y) = target
      if (enemy_board.tryAttack(x, y)) {
        println(s"Komputer trafił na polu ($x, $y)!")
        points += ACQUIRED_POINTS
        state = "Target"
        lastHitX = x
        lastHitY = y
        return hit;
      } else {
        println(s"Komputer chybił na polu ($x, $y).")
        points = Math.max(points - LOST_POINTS, 0)
        return !hit;
      }
    } else if (state == "Target") {
      val directions = Array((-1, 0), (1, 0), (0, -1), (0, 1)) // left, right, up, down
      for ((dx, dy) <- directions) {
        val x = lastHitX + dx
        val y = lastHitY + dy
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE && possibleTargets.contains(x,y)) {
          if (enemy_board.tryAttack(x, y)) {
            println(s"Komputer trafił na polu ($x, $y)!")
            points += ACQUIRED_POINTS
            lastHitX = x
            lastHitY = y
            hit = true
          } else {
            println(s"Komputer chybił na polu ($x, $y).")
            points -= LOST_POINTS
            hit = false
          }
          possibleTargets.remove((x,y))
          return hit
        }
      }
      state = "Hunt"
      trials -= 1//wejdziemy do attack wiec tu zmniejszymy trials a  tam zwiekszymy wiec wyjdzie na zero
      return attack();
    }
    hit
  }

  override def isDefeated(): Boolean = {
    board.isEmpty
  }
}
