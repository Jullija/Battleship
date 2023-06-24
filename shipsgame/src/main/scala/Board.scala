package main.battleship

import main.battleship.ConsoleColors._
import main.battleship.Constants.SIZE

import scala.collection.mutable

class Board{
  var ships = new mutable.HashSet[Ship]();
  var occupied = new mutable.HashSet[(Int, Int)]();
  var checked = new mutable.HashSet[(Int, Int)]();
  var bombardedShipsFields = new mutable.HashSet[(Int, Int)]();//bedzie zawierac trafione POLA statkow
  def isEmpty(): Boolean = {
    occupied.isEmpty;
  }

  def addShip(xs: Int, ys: Int, xe: Int, ye: Int, ship: Option[Ship]): Boolean ={
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

  def placeShip(xs: Int, ys: Int, xe: Int, ye: Int, ship: Option[Ship],flag:Boolean=true): Boolean = {
    if (ship == None) return false
    if (xs < 0 || xs >= SIZE || ys < 0 || ys >= SIZE || xe < 0 || xe >= SIZE || ye < 0 || ye >= SIZE) {
      if(flag)println("Błędne współrzędne, statek wychodzi poza planszę.")
      return false
    }
    // Walidacja rozmiaru statku
    val shipLength = math.abs(xs - xe) + 1
    val shipWidth = math.abs(ys - ye) + 1
    if (!((shipLength == ship.get.length && shipWidth == ship.get.width) ||
      (shipLength == ship.get.width && shipWidth == ship.get.length))) {
      if(flag)println("Rozmiar statku nie zgadza się z podanymi współrzędnymi.")
      return false
    }
    //sprawdzenie czy statek nie pokrywa sie z innym na mapce
    for (i <- math.min(xs, xe) to math.max(xs, xe)){
      for (j <- math.min(ys, ye) to math.max(ys, ye)){
        if (occupied.contains((i, j))){
          if(flag)println("Nie mozna dodac statku na to pole")
          return false
        }
      }
    }
    addShip(xs, ys, xe, ye, ship)
    true
  }


  def printBoard(canShow: Boolean = true): Unit = {
    println("  " + (0 until SIZE).mkString(" "))
    for (y <- 0 until SIZE) {
      print(s"$y ")
      for (x <- 0 until SIZE) {
        if (occupied.contains((x, y)) && canShow) {//canShow -  flaga mowiaca, czy mozna pole pokazac
          print(Green + "N " + Reset)
        } else if (bombardedShipsFields.contains((x, y))) {
          print(Red + "x " + Reset)
        } else if (checked.contains((x, y))) {
          print(Yellow + "- " + Reset)
        } else {
          print(Blue + "o " + Reset)
        }
      }
      println()
    }
    println("  " + (0 until SIZE).mkString(" "))
  }


  def removeShip(ship: Ship): Unit ={
    ships.remove(ship);
  }



  def alreadyChecked(x: Int, y: Int): Boolean = {
    checked.contains(x, y)
  }

  def tryAttack(x: Int, y: Int): Boolean = {
    if(alreadyChecked(x,y)){
      println("To pole juz zostalo sprawdzone")
      return false
    }
    checked.add((x, y))
    if (occupied.contains((x, y))) {
      occupied.remove((x, y))
      bombardedShipsFields.add((x, y))
      true
    } else {
      false
    }
  }


}

