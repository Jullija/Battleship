package main.battleship

import main.battleship.Constants.SIZE
import main.battleship.Engine.{computer, user}

import java.awt.Color
import scala.io.StdIn.readLine
import scala.swing._
import scala.swing.event._
import scala.util.Random

object Engine extends App{ //extends App
  //dodaj tworzymy statki na mape
  var user: User = SimpleUser
  var computer: ComputerUser.type = ComputerUser
  var shipFactUser: ShipFactory = new ShipFactory
  var shipFactComp: ShipFactory = new ShipFactory
  def connectUsers():Unit={

    this.user = SimpleUser
    this.computer = ComputerUser
    user.enemy_board=computer.board
    computer.enemy_board=user.board
  }
  def makeShips() = {
    this.shipFactUser = new ShipFactory
    this.shipFactComp = new ShipFactory
    var coordinates: Array[Int] = Array(0, 0, 0, 0)
    while(!shipFactUser.everyTypeUsed()) {
      var ship: Option[Ship] = None
      user.board.printBoard()
      shipFactUser.printShips()
      var shipTypeInput: String = ""
      do{
        println("Wybierz poprawny typ statku:")
        shipTypeInput = readLine()
        ship = shipFactUser.createShip(shipTypeInput)
      }while(ship == None)
      do{
        println("Podaj współrzędne (x0, y0, x1, y1):")
        coordinates = getFourElementCoordinates()
      }while(!user.board.placeShip(coordinates(0), coordinates(1), coordinates(2), coordinates(3), ship))
    }

    user.board.printBoard()
  }

  def makeShipsComp() = {
        ShipType.values.foreach { shipType =>
          var ship: Option[Ship] = None
          ship = shipFactComp.createShip(shipType.toString)
          var coordinates = Array.fill(4)(Random.nextInt(SIZE))
          while (!computer.board.placeShip(coordinates(0), coordinates(1), coordinates(2), coordinates(3),ship,false)) {
            coordinates = Array.fill(4)(Random.nextInt(SIZE))
          }
        }
  }

  //ta wlasciwa gra
  def properGame(): Unit = {
    while (true) {
      stats()
      println("\n\n--------Plansza usera---------\n\n")
      user.board.printBoard()
      println("\n\n--------Plansza wroga---------\n\n")
      computer.board.printBoard(false)
      println("Podaj koordynaty ataku (x, y):")

      val coordinates = getCoordinates()

      user.attack(coordinates(0), coordinates(1))
      if (computer.isDefeated()) {
        println("Gratuluje wygrales!")
        stats()
        System.exit(1)
      }

      computer.attack(-1, -1)
      if (user.isDefeated()) {
        println("Gratuluje przegrałeś!")
        stats()
        System.exit(1)
      }
    }
  }
  def stats():Unit = {
    println(s"Statystyki usera: \nIlosc punktow: ${user.points}\nIlosc prob: ${user.trials}")
    println(s"Statystyki wroga: \nIlosc punktow: ${computer.points}\nIlosc prob: ${computer.trials}")
  }

  def getCoordinates(): Array[Int] = {
    while (true) {
      try {
        val input = readLine()
        val coordinates = input.split(",").map(_.trim.toInt)
        if (coordinates.length != 2) {
          throw new IllegalArgumentException("Wprowadzony ciąg znaków powinien zawierać dwie współrzędne oddzielone przecinkiem.")
        }
        return coordinates
      } catch {
        case _: NumberFormatException =>
          println("Wprowadzone współrzędne powinny być liczbami całkowitymi. Spróbuj ponownie.")
        case e: IllegalArgumentException =>
          println(e.getMessage)
      }
    }
    Array()
  }

  def getFourElementCoordinates(): Array[Int] = {
    while (true) {
      try {
        val input = readLine()
        val coordinates = input.split(",").map(_.trim.toInt)
        if (coordinates.length != 4) {
          throw new IllegalArgumentException("Wprowadzony ciąg znaków powinien zawierać cztery współrzędne oddzielone przecinkami.")
        }
        return coordinates
      } catch {
        case _: NumberFormatException =>
          println("Wprowadzone współrzędne powinny być liczbami całkowitymi. Spróbuj ponownie.")
        case e: IllegalArgumentException =>
          println(e.getMessage)
      }
    }
    Array()
  }


  def run():Unit = {
    connectUsers()
    makeShips()
    makeShipsComp()
//    properGame()
    createGame()
  }

  def buttonCreator(): Button = {
    var button = new Button()
    button.foreground = Color.decode("#FDE3A8")
    button.listenTo(button.mouse.clicks)
    button.preferredSize = new Dimension(50, 50)
    button.minimumSize = new Dimension(50, 50)
    button.maximumSize = new Dimension(50, 50)
    button
  }



  def createGame(): Unit = {
    val user: User = SimpleUser
    val computer: ComputerUser.type = ComputerUser
    val compArr: Array[Array[Button]] = Array.ofDim[Button](10, 10)
    computer.userArr = Array.ofDim[Panel](10, 10)


    def createBoardComp(): BoxPanel = {
      val window = new BoxPanel(Orientation.Vertical){
        border = Swing.EmptyBorder(50)
      }
        for (row <- 0 until SIZE) {
          val rowPanel = new BoxPanel(Orientation.Horizontal){
          }
            for (col <- 0 until SIZE){
              val button = buttonCreator()
              button.reactions += {
                case mc: MouseClicked => handleClick(col, row)
              }
              rowPanel.contents += button
              compArr(col)(row) = button
            }
          window.contents += rowPanel
        }
      window
    }

    def createBoardUser(): BoxPanel = {
      val window = new BoxPanel(Orientation.Vertical) {
        border = Swing.EmptyBorder(50)
      }
      for (row <- 0 until SIZE) {
        val rowPanel = new BoxPanel(Orientation.Horizontal)
        for (col <- 0 until SIZE) {
          val rec = new Panel{
            preferredSize = new Dimension(50, 50)
            background = if(user.board.occupied.contains((col,row))) Color.decode("#0CFF00") else Color.decode("#0070FF")
            border = Swing.LineBorder(Color.decode("#025ab8"))
          }
          rowPanel.contents += rec
          computer.userArr(col)(row) = rec
        }
        window.contents += rowPanel
      }
      window
    }

    def mainWindow: Frame = new MainFrame{
      title = "Battleship"
      contents = createMainView()
      visible = true
      pack()
      centerOnScreen()
    }

    def createBoxPanel(labelText: String): BoxPanel = {
      val label = new Label(labelText)
      val boxPanel = new BoxPanel(Orientation.Vertical)
      boxPanel.contents += label
      labelText match {
        case "User" => boxPanel.contents += createBoardUser()
        case "Computer" => boxPanel.contents += createBoardComp()
      }

      boxPanel
    }


    def createMainView(): BoxPanel =  {
      val userBoard = createBoxPanel("User")
      val compBoard = createBoxPanel("Computer")

      val mainBoxPanel = new BoxPanel(Orientation.Horizontal){
        contents += userBoard
        contents += compBoard

      }
      mainBoxPanel
    }

    def handleClick(row: Int, col: Int): Unit = {

      compArr(row)(col).foreground = if (computer.board.occupied.contains((row, col))) Color.decode("#fc1303") else Color.decode("#FDE3A8")
      compArr(row)(col).background = if (computer.board.occupied.contains((row, col))) Color.decode("#fc1303") else Color.decode("#FDE3A8")
      user.attack(row, col)

      if (computer.isDefeated()) {
        println("Gratuluje wygrales!")
        stats()
        Thread.sleep(1000)
        System.exit(1)
      }

      Thread.sleep(1000)

      computer.attack(-1,-1)
      if (user.isDefeated()) {
        println("Gratuluje przegrałeś!")
        stats()
        Thread.sleep(1000)
        System.exit(1)
      }
      stats()

    }


    mainWindow

}


}
