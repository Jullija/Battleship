package main.battleship

import main.battleship.Constants.SIZE

import scala.collection.mutable
import scala.math.abs
import main.battleship.ShipType.ShipType

class Board{
  var occupied = new mutable.HashSet[(Int, Int)]();
  var ships = new mutable.HashSet[Ship]();
  var checked = new mutable.HashSet[(Int, Int)]();

  def isEmpty(): Boolean = {
    occupied.isEmpty;
  }

  def addShip(xs: Int, ys: Int, xe: Int, ye: Int, ship: Option[Ship]): Boolean ={
//    var length = abs(ys - ye);  to jest w shipFactory
//    var width = abs(xs - xe); to jest w shipFactory
    //jesli ship jest none to nie dodajemy statku, tylko wypisujemy komunikat ze statek nie zostal dodany
    if (ship == None){
      println("Statek nie zostal dodany")
      return false
    }
    ships.addOne(ship.get);
    for (i <- math.min(xs, xe) to math.max(xs, xe)){
      for (j <- math.min(ys, ye) to math.max(ys, ye)){
        occupied.add((i, j));
      }
    }
    true
  }

  def printBoard(): Unit ={
    for (i <- 0 until SIZE){
      for (j <- 0 until SIZE){
        if (occupied.contains((i, j))){
          print("x")
        }
        else{
          print(" ")
        }
      }
    }
  }


  def removeShip(ship: Ship): Unit ={ //dodałabym od razu usuwanie z setu statków oraz usuwanie z occupied, wystarczy w
    //ship trzymac dodatkowo jego koordynaty i wtedy można to zrobić za jednym razem
    ships.remove(ship);
  }



  def alreadyChecked(x: Int, y: Int): Boolean = {
    checked.contains(x, y)
  }

  def tryAttack(x: Int, y: Int): Boolean = {
    if (occupied.contains((x, y))) {
      occupied.remove((x, y))
      checked.add((x, y)) //wykorzystane pole wiec dodajemy do checked
      true
    } else {
      false
    }
  }


}

