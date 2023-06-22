package test.scala

import main.battleship.ShipType.Type1
import main.battleship.{Board, ShipFactory}
import org.scalatest.funsuite.AnyFunSuite

class Test extends AnyFunSuite{

  test("creatingShips"){
    val shipFactory = new ShipFactory
    val ship1 = shipFactory.createShip("Type1");
    val ship2 = shipFactory.createShip("Type2");
    val ship3 = shipFactory.createShip("Type3");
    val ship4 = shipFactory.createShip("Type4");

    assert(ship1.get.size === 5)
    assert(ship2.get.size === 8)
    assert(ship3.get.size === 3)
    assert(ship4.get.size === 2)

  }


  test("addingOnBoard"){
    var board = new Board
    val shipFactory = new ShipFactory
    board.addShip(2, 2, 6, 2, Type1)
    assert(board.isEmpty() === false)
    assert(board.tryAttack(4, 2) === true)
    assert(board.tryAttack(7, 8) === false)
    assert(board.occupied.contains(3, 2))
    assert(board.alreadyChecked(4, 2) === true)

  }

}
