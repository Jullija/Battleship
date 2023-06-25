package main.battleship

import main.battleship.Constants.SIZE
import main.battleship.ShipType._

import java.awt.Color
import scala.swing.MenuBar.NoMenuBar.{listenTo, reactions}
import scala.swing._
import scala.swing.event._
import scala.util.Random


object Engine extends App{
  //dodaj tworzymy statki na mape
  val user: User = SimpleUser
  val computer: User = ComputerUser
  val shipFactUser: ShipFactory = new ShipFactory
  val shipFactComp: ShipFactory = new ShipFactory

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
//  def properGame(): Unit = {
//    while (true) {
//      stats()
//      println("\n\n--------Plansza usera---------\n\n")
//      user.board.printBoard()
//      println("\n\n--------Plansza wroga---------\n\n")
//      computer.board.printBoard(false)
//      println("Podaj koordynaty ataku (x, y):")
//
//      val coordinates = getCoordinates()
//
//      user.attack(coordinates(0), coordinates(1))
//      if (computer.isDefeated()) {
//        println("Gratuluje wygrales!")
//        stats()
//        System.exit(1)
//      }
//
//      computer.attack(-1, -1)
//      if (user.isDefeated()) {
//        println("Gratuluje przegrałeś!")
//        stats()
//        System.exit(1)
//      }
//    }
//  }
//  def stats():Unit = {
//    println(s"Statystyki usera: \nIlosc punktow: ${user.points}\nIlosc prob: ${user.trials}")
//    println(s"Statystyki wroga: \nIlosc punktow: ${computer.points}\nIlosc prob: ${computer.trials}")
//  }
//
//  def getCoordinates(): Array[Int] = {
//    while (true) {
//      try {
//        val input = readLine()
//        val coordinates = input.split(",").map(_.trim.toInt)
//        if (coordinates.length != 2) {
//          throw new IllegalArgumentException("Wprowadzony ciąg znaków powinien zawierać dwie współrzędne oddzielone przecinkiem.")
//        }
//        return coordinates
//      } catch {
//        case _: NumberFormatException =>
//          println("Wprowadzone współrzędne powinny być liczbami całkowitymi. Spróbuj ponownie.")
//        case e: IllegalArgumentException =>
//          println(e.getMessage)
//      }
//    }
//    Array()
//  }

//  def getFourElementCoordinates(): Array[Int] = {
//    while (true) {
//      try {
//        val input = readLine()
//        val coordinates = input.split(",").map(_.trim.toInt)
//        if (coordinates.length != 4) {
//          throw new IllegalArgumentException("Wprowadzony ciąg znaków powinien zawierać cztery współrzędne oddzielone przecinkami.")
//        }
//        return coordinates
//      } catch {
//        case _: NumberFormatException =>
//          println("Wprowadzone współrzędne powinny być liczbami całkowitymi. Spróbuj ponownie.")
//        case e: IllegalArgumentException =>
//          println(e.getMessage)
//      }
//    }
//    Array()
//  }


  def run():Unit = {
    //connectUsers()
//    makeShips()
//    makeShipsComp()
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

  def recCreator(): Panel = {
    val rec = new Panel {
      preferredSize = new Dimension(50, 50)
      background = Color.decode("#307ac9")
      border = Swing.LineBorder(Color.decode("#025ab8"))
    }
    rec
  }




  def createGame(): Unit = {
    val user: User = SimpleUser
    val computer: User = ComputerUser
    val userArr: Array[Array[Panel]] = Array.ofDim[Panel](10, 10)
    val compArr: Array[Array[Button]] = Array.ofDim[Button](10, 10)
    val shipFactUser: ShipFactory = new ShipFactory
    val shipFactComp: ShipFactory = new ShipFactory
    user.enemy_board = computer.board
    computer.enemy_board = user.board

    def handleClick(row: Int, col: Int): Unit = {
      if (computer.board.occupied.contains((row, col))) {
        compArr(row)(col).foreground = Color.decode("#fc1303")
        compArr(row)(col).background = Color.decode("#fc1303")
      }
      else {
        compArr(row)(col).foreground = Color.decode("#307ac9")
        compArr(row)(col).background = Color.decode("#307ac9")
      }
    }

    //Creating visuals --------------------------------------------
    def createBoardComp(): BoxPanel = {
      val window = new BoxPanel(Orientation.Vertical) {
        border = Swing.EmptyBorder(50)
      }
      for (row <- 0 until SIZE) {
        val rowPanel = new BoxPanel(Orientation.Horizontal) {
        }
        for (col <- 0 until SIZE) {
          val button = buttonCreator()
          button.reactions += {
            case mc: MouseClicked => handleClick(row, col)
          }
          rowPanel.contents += button
          compArr(row)(col) = button
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
          val rec = recCreator()
          rowPanel.contents += rec
          userArr(row)(col) = rec
        }
        window.contents += rowPanel
      }
      window
    }

    def createShipDesc(): BoxPanel = {
      val window = new BoxPanel(Orientation.Horizontal) {
        border = Swing.EmptyBorder(50)
        for (i <- ShipType.values) {
          val attributes = attributesOf(i)
          val length = attributes.map(_.length).getOrElse(0)
          val width = attributes.map(_.width).getOrElse(1)
          val desc = new BoxPanel(Orientation.Vertical) {
            contents += new Label(i.toString)
            contents += new Label(s"Length: ${length}, width: ${width}")
            border = Swing.EmptyBorder(10)
          }

          contents += desc
        }

      }
      window
    }


    def createBoxPanel(labelText: String): BoxPanel = {
      val label = new Label(labelText)
      val boxPanel = new BoxPanel(Orientation.Vertical)
      boxPanel.contents += label
      labelText match {
        case "User" => boxPanel.contents += createBoardUser()
        case "Computer" => boxPanel.contents += createBoardComp()
        case "Ships" => boxPanel.contents += createShipDesc()
      }

      boxPanel
    }


    def createTextArea(): TextField = {
      val textArea = new TextField()
      textArea.preferredSize = new Dimension(200, 30)
      textArea.minimumSize = new Dimension(200, 30)
      textArea.maximumSize = new Dimension(200, 30)
      textArea
    }

    val info = createShipDesc()
    val textArea = createTextArea()
    val userBoard = createBoxPanel("User")
    val compBoard = createBoxPanel("Computer")

    def createMainView(): BoxPanel = {
      val submit = new Button("Submit")

      listenTo(submit)
      if (!shipFactUser.everyTypeUsed()) {
        reactions += {
          case ButtonClicked(`submit`) => makeShips()
        }
      }
      else {
        submit.visible = false
      }


      val mainBoxPanel = new BoxPanel(Orientation.Vertical) {
        contents += info
        val boardPanel = new BoxPanel(Orientation.Horizontal) {
          contents += userBoard
          contents += compBoard
        }
        contents += boardPanel
        val submitPanel = new BoxPanel(Orientation.Horizontal) {
          contents += textArea
          contents += submit
        }
        contents += submitPanel

      }
      mainBoxPanel
    }
    //----------------------------------------------------------------------------

    //configuration-----------------------------------------------------------

    def changeColor(x0: Int, y0: Int, x1: Int, y1: Int) = {
      for (x <- math.min(x0, x1) to math.max(x0, x1)){
        for (y <- math.min(y0, y1) to math.max(y0, y1)){
          userArr(x)(y).background = Color.decode("#592b00")
          userArr(x)(y).foreground = Color.decode("#592b00")
        }
      }
    }


    def makeShips() = { // Type1, x0, y0, x1, y1
      var ship: Option[Ship] = None
      val input = textArea.text
      val inputChanged = input.split(",").map(_.trim)
      val whichShip = inputChanged(0)
      val x0 = inputChanged(1).toInt
      val y0 = inputChanged(2).toInt
      val x1 = inputChanged(3).toInt
      val y1 = inputChanged(4).toInt
      ship = shipFactUser.createShip(whichShip)

      changeColor(x0: Int, y0: Int, x1: Int, y1: Int)
      textArea.text = ""
    }

    def makeShipsComp() = {
      ShipType.values.foreach { shipType =>
        var ship: Option[Ship] = None
        ship = shipFactComp.createShip(shipType.toString)
        var coordinates = Array.fill(4)(Random.nextInt(SIZE))
        while (!computer.board.placeShip(coordinates(0), coordinates(1), coordinates(2), coordinates(3), ship, false)) {
          coordinates = Array.fill(4)(Random.nextInt(SIZE))
        }
      }
    }

    def play(): Unit ={
      while (true){
        if (textArea.text != ""){
          val input = textArea.text
          val coordinates = input.split(",").map(_.trim)
          val x = coordinates(0).toInt
          val y = coordinates(1).toInt
          textArea.text = ""

          user.attack(x, y)
        }
        computer.attack(-1, -1)

      }

    }

    def mainWindow: Frame = new MainFrame {
      title = "Battleship"
      contents = createMainView()
      makeShipsComp()
      visible = true
      pack()
      centerOnScreen()
      play()


    }

    mainWindow


  }
}
