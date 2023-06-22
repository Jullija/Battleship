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
    val ship5 = shipFactory.createShip("TypeNotExisting");
    assert(ship1.get.size === 5)
    assert(ship2.get.size === 8)
    assert(ship3.get.size === 3)
    assert(ship4.get.size === 2)
    assert(ship5 === None)

  }


  test("addingOnBoard"){//dodajmy komentarze do wszystkich wywolan

    val board = new Board
    val shipFactory = new ShipFactory
    val ship = shipFactory.createShip("Type1")
    //sprawdzenie czy None zostanie dodane na plansze
    assert(board.addShip(2, 2, 6, 2, None) == false)
    //sprawdzenie czy statek zostanie dodany na plansze
    assert(board.addShip(2, 2, 6, 2, ship) == true)
    //sprawdzenie czy plansza nie jest pusta
    assert(board.isEmpty() === false)
    //sprawdzenie czy atak sie powiodl
    assert(board.tryAttack(4, 2) === true)
    //sprawdzenie czy atak na to samo pole sie nie powiedzie
    assert(board.tryAttack(4, 2) === false)
    //sprawdzenie czy atak na pole bez statku sie nie powiedzie
    assert(board.tryAttack(7, 8) === false)
    //sprawdzenie czy occupied zawiera wspolrzedne statku
    assert(board.occupied.contains(3, 2))
    //sprawdzenie czy checked zawiera wspolrzedne poprzednio zaaatakowane
    assert(board.alreadyChecked(4, 2) === true)
    //sprawdzenie czy checked nie zawiera wspolrzednych zaatakowanych z niepowodzeniem
    assert(board.alreadyChecked(7, 8) === false)
  }

}
