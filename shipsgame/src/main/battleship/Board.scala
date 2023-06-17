
import scala.collection.mutable
import scala.math
import scala.math.abs
import main.battleship.ShipType

class Board {
  var occupied = new mutable.HashSet[(Int, Int)]();
  var size = 0;
  var ships = new mutable.HashSet[Ship]();
  var checked = new mutable.HashSet[(Int, Int)]();

  def isEmpty(): Boolean = {
    occupied.isEmpty;
  }

  def addShip(xs: Int, ys: Int, xe: Int, ye: Int, shipType: ShipType): Unit ={
    var length = abs(ys - ye);
    var width = abs(xs - xe);
    ships.addOne(new Ship(shipType, length, width));
    for (i <- math.min(xs, xe) to math.max(xs, xe)){
      for (j <- math.min(ys, ye) to math.max(ys, ye)){
          occupied.add((i, j));
      }
    }
  }

  def printBoard(): Unit ={
    for (i <- 0 until size){
      for (j <- 0 until size){
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

  def tryAttack(x: Int, y: Int): Boolean ={
    occupied.contains((x, y));
  }





}
