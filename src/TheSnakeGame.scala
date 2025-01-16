import hevs.graphics.FunGraphics
import java.awt.Color
import java.awt.event.{ActionEvent, KeyAdapter, KeyEvent, MouseAdapter, MouseEvent}
import javax.sound.sampled.{AudioSystem, Clip}
import javax.swing.Timer

//class Audio créé pour l'utilisation des fichiers .wav mis à disposition
class Audio (path : String) {
  var audioClip: Clip = _

  //essayer de trouver le fichier son et de l'ouvrir
  try {
    val url = classOf[Audio].getResource(path)
    val audioStream = AudioSystem.getAudioInputStream(url)

    audioClip = AudioSystem.getClip.asInstanceOf[Clip]
    audioClip.open(audioStream)
  } catch {
    case e: Exception =>
      e.printStackTrace()
  }

  //jouer le son
  def play(): Unit = {
    try {
      if (!audioClip.isOpen) audioClip.open()
      audioClip.stop()
      audioClip.setMicrosecondPosition(0)
      audioClip.start()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  //arrêter le son
  def stop(): Unit = {
    audioClip.stop()
  }
}

object TheSnakeGame {

  //taille de la fenêtre FunGraphics
  val window: FunGraphics = new FunGraphics(700, 770, "The Snake Game")

  // Dimensions de la grille
  val gridWidth = 20
  val gridHeight = 20

  //score et meilleur score du joueur
  var score = 0
  var highScore = 0

  //dimensions du bouton pour commencer la partie
  val buttonx = 250
  val buttony = 400
  val buttonw = 200
  val buttonh = 100

  //valeurs pour aligner le text au bouton verticallement et horizontalement
  val halign = 10
  val valign = 10

  //verifie si le bouton a été appuyé
  var btnprsd = false

  //verifie si c'est la fin du jeu
  var gameOver = false

  //verifie si le jeu a commencer
  var gameStarted = false

  // Initialisation de la grille
  var grid: Array[Array[Int]] = Array.fill(gridHeight, gridWidth)(0)

  // Direction du serpent (initialement vers la droite)
  var direction = (0, 1)

  // Taille actuelle du serpent
  var snakeLength = 2

  //différents sons durant le jeu
  var endSound: Audio = new Audio("/GTA5 Wasted Sound Effect.wav")
  var gameSound: Audio = new Audio("/Driftveil City Music lower.wav")
  var menuSound: Audio = new Audio("/Wii Party Main Menu Music lower.wav")
  var appleEatenSound: Audio = new Audio("/Yoshi Sound Ba-Dum.wav")
  var hereWeGo: Audio = new Audio("/Effet sonore Mario.wav")

  // Position initiale du serpent
  grid(gridHeight/2-1)(1) = 1 //tête
  grid(gridHeight/2-1)(0) = 2 //corps

  //fonction qui affiche le score et le meilleur score sur la fenetre FunGraphics
  def setScore(): Unit ={
    window.drawString(28, 42, s"Score: $score", new Color(0, 53, 106), 28)
    window.drawString(168, 42, s"Highscore: $highScore", new Color(0, 53, 106), 28)
  }

  // reinitialise la position du serpent
  def resetsnake() ={
    for(i <- grid.indices){
      for(j <- grid(i).indices){
        grid(i)(j) = 0
      }
    }

    grid(gridHeight/2-1)(1) = 1
    grid(gridHeight/2-1)(0) = 2
  }

  //dessine la grille d'arrière plan du jeu en vert clair et vert foncé
  //et dessine une marge en vert claire avec un trait bleu qui delimite la marge
  def setBackground() = {
    window.setColor(Color.white)
    window.drawFillRect(0, 0, 700, 770)
    window.setColor(new Color(181, 206, 161))
    window.drawFillRect(0, 0, 700, 69)
    window.setColor(new Color(0, 53, 106))
    window.drawLine(0, 69, 700, 69)
    var count = 1
    for(i <- 0 until 700 by 35){
      for(j <- 70 until 770 by 35){
        if(count % 2 == 0){
          window.setColor(new Color(181, 206, 161))
          window.drawFillRect(i, j, 35, 35)
        }
        else{
          window.setColor(new Color(97, 142, 60))
          window.drawFillRect(i, j, 35, 35)
        }
        count += 1
      }
      count += 1
    }
  }

  //cette fonction dessine la grille d'arrière plan du menu
  //on a besoin de la fonction précédente pour bien dessiner la grille du menu
  def setMenuBackground(): Unit ={
    setBackground()

    var count = 1
    for(i <- 0 until 700 by 35){
      for(j <- 0 until 700 by 35){
        if(count % 2 == 0){
          window.setColor(new Color(181, 206, 161))
          window.drawFillRect(i, j, 35, 35)
        }
        else{
          window.setColor(new Color(97, 142, 60))
          window.drawFillRect(i, j, 35, 35)
        }
        count += 1
      }
      count += 1
    }
  }

  //dessine le menu au complet (l'arrière plan, le titre, le serpent et la pomme du menu)
  def setMenu() = {
    menuSound.play()
    setMenuBackground()

    window.setColor(new Color(181, 206, 161))
    window.drawFillRect(126, 308, 448, 56)
    window.setColor(new Color(0, 53, 106))
    window.drawRect(126, 308, 448, 56)

    window.drawTransformedPicture((window.height/5 + 197), (window.width/2 - 13), 0.0, 0.10, "/the_snake_game.png")

    window.setColor(new Color(0, 53, 106))
    window.drawFillRect(175, 210, 35, 35)
    window.drawFillRect(210, 210, 35, 35)
    window.drawFillRect(245, 210, 35, 35)
    window.drawFillRect(280, 210, 35, 35)

    window.setColor(Color.white)
    window.drawFillRect((350 - 50), (210 + 8), 6, 7)
    window.drawFillRect((350 - 50), (245 - 15), 6, 7)
    window.setColor(Color.BLACK)
    window.drawFillRect((350 - 49), (210 + 9), 5, 5)
    window.drawFillRect((350 - 49), (245 - 14), 5, 5)
    window.setColor(Color.RED)
    window.drawFillRect(315, (245 - 19), 6, 3)
    window.drawLine((315 + 6), (245 - 19), (315 + 8), (245 - 21))
    window.drawLine((315 + 6), (245 - 18), (315 + 8), (245 - 16))
    window.drawLine((315 + 6), (245 - 18), (315 + 8), (245 - 20))
    window.drawLine((315 + 6), (245 - 17), (315 + 8), (245 - 15))

    window.setColor(Color.RED)
    window.drawFillRect((490 - 28), (245 - 28), 21, 21)
    window.setColor(new Color(61, 43, 31))
    window.drawFillRect((490 - 19), (245 - 35), 3, 7)
  }

  //dessine le serpent dépendant de sa position et de sa direction (sa tête change de sens dépendant du sens du serpent)
  def setSnake():Unit = {
    for(i <- grid.indices){
      for(j <- grid(i).indices){
        if(grid(i)(j) == 1){
          window.setColor(new Color(0, 53, 106))
          window.drawFillRect(((j + 1) * 35 - 35), ((i + 1) * 35 - 35 + 70), 35, 35)
          if(direction == (0, 1)){
            window.setColor(Color.white)
            window.drawFillRect(((j + 1) * 35 - 15), ((i + 1) * 35 - 27 + 70), 6, 7)
            window.drawFillRect(((j + 1) * 35 - 15), ((i + 1) * 35 - 15 + 70), 6, 7)
            window.setColor(Color.BLACK)
            window.drawFillRect(((j + 1) * 35 - 14), ((i + 1) * 35 - 26 + 70), 5, 5)
            window.drawFillRect(((j + 1) * 35 - 14), ((i + 1) * 35 - 14 + 70), 5, 5)
            window.setColor(Color.RED)
            window.drawFillRect((j + 1) * 35, ((i + 1) * 35 - 19 + 70), 6, 3)
            window.drawLine(((j + 1) * 35 + 6), ((i + 1) * 35 - 19 + 70), ((j + 1) * 35 + 8), ((i + 1) * 35 - 21 + 70))
            window.drawLine(((j + 1) * 35 + 6), ((i + 1) * 35 - 18 + 70), ((j + 1) * 35 + 8), ((i + 1) * 35 - 16 + 70))
            window.drawLine(((j + 1) * 35 + 6), ((i + 1) * 35 - 18 + 70), ((j + 1) * 35 + 8), ((i + 1) * 35 - 20 + 70))
            window.drawLine(((j + 1) * 35 + 6), ((i + 1) * 35 - 17 + 70), ((j + 1) * 35 + 8), ((i + 1) * 35 - 15 + 70))
          }
          else if(direction == (0, -1)){
            window.setColor(Color.white)
            window.drawFillRect(((j + 1) * 35 - 27), ((i + 1) * 35 - 27 + 70), 6, 7)
            window.drawFillRect(((j + 1) * 35 - 27), ((i + 1) * 35 - 15 + 70), 6, 7)
            window.setColor(Color.BLACK)
            window.drawFillRect(((j + 1) * 35 - 27), ((i + 1) * 35 - 26 + 70), 5, 5)
            window.drawFillRect(((j + 1) * 35 - 27), ((i + 1) * 35 - 14 + 70), 5, 5)
            window.setColor(Color.RED)
            window.drawFillRect((j + 1) * 35 - 41, ((i + 1) * 35 - 19 + 70), 6, 3)
            window.drawLine(((j + 1) * 35 - 41), ((i + 1) * 35 - 19 + 70), ((j + 1) * 35 - 43), ((i + 1) * 35 - 21 + 70))
            window.drawLine(((j + 1) * 35 - 41), ((i + 1) * 35 - 18 + 70), ((j + 1) * 35 - 43), ((i + 1) * 35 - 16 + 70))
            window.drawLine(((j + 1) * 35 - 41), ((i + 1) * 35 - 18 + 70), ((j + 1) * 35 - 43), ((i + 1) * 35 - 20 + 70))
            window.drawLine(((j + 1) * 35 - 41), ((i + 1) * 35 - 17 + 70), ((j + 1) * 35 - 43), ((i + 1) * 35 - 15 + 70))
          }
          else if(direction == (-1, 0)){
            window.setColor(Color.white)
            window.drawFillRect(((j + 1) * 35 - 27), ((i + 1) * 35 - 27 + 70), 7, 6)
            window.drawFillRect(((j + 1) * 35 - 15), ((i + 1) * 35 - 27 + 70), 7, 6)
            window.setColor(Color.BLACK)
            window.drawFillRect(((j + 1) * 35 - 26), ((i + 1) * 35 - 27 + 70), 5, 5)
            window.drawFillRect(((j + 1) * 35 - 14), ((i + 1) * 35 - 27 + 70), 5, 5)
            window.setColor(Color.RED)
            window.drawFillRect((j + 1) * 35 - 19, ((i + 1) * 35 - 41 + 70), 3, 6)
            window.drawLine(((j + 1) * 35 - 19), ((i + 1) * 35 - 41 + 70), ((j + 1) * 35 - 21), ((i + 1) * 35 - 43 + 70))
            window.drawLine(((j + 1) * 35 - 18), ((i + 1) * 35 - 41 + 70), ((j + 1) * 35 - 16), ((i + 1) * 35 - 43 + 70))
            window.drawLine(((j + 1) * 35 - 18), ((i + 1) * 35 - 41 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 - 43 + 70))
            window.drawLine(((j + 1) * 35 - 17), ((i + 1) * 35 - 41 + 70), ((j + 1) * 35 - 15), ((i + 1) * 35 - 43 + 70))
          }
          else if(direction == (1, 0)){
            window.setColor(Color.white)
            window.drawFillRect(((j + 1) * 35 - 27), ((i + 1) * 35 - 15 + 70), 7, 6)
            window.drawFillRect(((j + 1) * 35 - 15), ((i + 1) * 35 - 15 + 70), 7, 6)
            window.setColor(Color.BLACK)
            window.drawFillRect(((j + 1) * 35 - 26), ((i + 1) * 35 - 14 + 70), 5, 5)
            window.drawFillRect(((j + 1) * 35 - 14), ((i + 1) * 35 - 14 + 70), 5, 5)
            window.setColor(Color.RED)
            window.drawFillRect((j + 1) * 35 - 19, ((i + 1) * 35 + 70), 3, 6)
            window.drawLine(((j + 1) * 35 - 19), ((i + 1) * 35 + 6 + 70), ((j + 1) * 35 - 21), ((i + 1) * 35 + 8 + 70))
            window.drawLine(((j + 1) * 35 - 18), ((i + 1) * 35 + 6 + 70), ((j + 1) * 35 - 16), ((i + 1) * 35 + 8 + 70))
            window.drawLine(((j + 1) * 35 - 18), ((i + 1) * 35 + 6 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 + 8 + 70))
            window.drawLine(((j + 1) * 35 - 17), ((i + 1) * 35 + 6 + 70), ((j + 1) * 35 - 15), ((i + 1) * 35 + 8 + 70))
          }
        }
        else if(grid(i)(j) > 1){
          window.setColor(new Color(0, 53, 106))
          window.drawFillRect(((j + 1) * 35 - 35), ((i + 1) * 35 - 35 + 70), 35, 35)
        }
      }
    }
  }

  //cette fonction dessine le serpent quand il meurt (lui dessine des yeux en croix)
  def setSnakeException(): Unit ={
    for(i <- grid.indices) {
      for (j <- grid(i).indices) {
        if (grid(i)(j) == 2) {
          window.setColor(new Color(0, 53, 106))
          window.drawFillRect(((j + 1) * 35 - 35), ((i + 1) * 35 - 35 + 70), 35, 35)
          if (direction == (0, 1)) {
            window.setColor(Color.white)
            window.drawLine(((j + 1) * 35 - 15), ((i + 1) * 35 - 27 + 70), ((j + 1) * 35 - 8), ((i + 1) * 35 - 20 + 70))
            window.drawLine(((j + 1) * 35 - 15), ((i + 1) * 35 - 15 + 70), ((j + 1) * 35 - 8), ((i + 1) * 35 - 8 + 70))
            window.drawLine(((j + 1) * 35 - 15), ((i + 1) * 35 - 20 + 70), ((j + 1) * 35 - 8), ((i + 1) * 35 - 27 + 70))
            window.drawLine(((j + 1) * 35 - 15), ((i + 1) * 35 - 8 + 70), ((j + 1) * 35 - 8), ((i + 1) * 35 - 15 + 70))
            window.setColor(Color.RED)
            window.drawFillRect((j + 1) * 35, ((i + 1) * 35 - 19 + 70), 6, 3)
            window.drawLine(((j + 1) * 35 + 6), ((i + 1) * 35 - 19 + 70), ((j + 1) * 35 + 8), ((i + 1) * 35 - 21 + 70))
            window.drawLine(((j + 1) * 35 + 6), ((i + 1) * 35 - 18 + 70), ((j + 1) * 35 + 8), ((i + 1) * 35 - 16 + 70))
            window.drawLine(((j + 1) * 35 + 6), ((i + 1) * 35 - 18 + 70), ((j + 1) * 35 + 8), ((i + 1) * 35 - 20 + 70))
            window.drawLine(((j + 1) * 35 + 6), ((i + 1) * 35 - 17 + 70), ((j + 1) * 35 + 8), ((i + 1) * 35 - 15 + 70))
          }
          else if (direction == (0, -1)) {
            window.setColor(Color.white)
            window.drawLine(((j + 1) * 35 - 27), ((i + 1) * 35 - 27 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 - 20 + 70))
            window.drawLine(((j + 1) * 35 - 27), ((i + 1) * 35 - 15 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 - 8 + 70))
            window.drawLine(((j + 1) * 35 - 27), ((i + 1) * 35 - 20 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 - 27 + 70))
            window.drawLine(((j + 1) * 35 - 27), ((i + 1) * 35 - 8 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 - 15 + 70))
            window.setColor(Color.RED)
            window.drawFillRect((j + 1) * 35 - 41, ((i + 1) * 35 - 19 + 70), 6, 3)
            window.drawLine(((j + 1) * 35 - 41), ((i + 1) * 35 - 19 + 70), ((j + 1) * 35 - 43), ((i + 1) * 35 - 21 + 70))
            window.drawLine(((j + 1) * 35 - 41), ((i + 1) * 35 - 18 + 70), ((j + 1) * 35 - 43), ((i + 1) * 35 - 16 + 70))
            window.drawLine(((j + 1) * 35 - 41), ((i + 1) * 35 - 18 + 70), ((j + 1) * 35 - 43), ((i + 1) * 35 - 20 + 70))
            window.drawLine(((j + 1) * 35 - 41), ((i + 1) * 35 - 17 + 70), ((j + 1) * 35 - 43), ((i + 1) * 35 - 15 + 70))
          }
          else if (direction == (-1, 0)) {
            window.setColor(Color.white)
            window.drawLine(((j + 1) * 35 - 27), ((i + 1) * 35 - 27 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 - 20 + 70))
            window.drawLine(((j + 1) * 35 - 15), ((i + 1) * 35 - 27 + 70), ((j + 1) * 35 - 8), ((i + 1) * 35 - 20 + 70))
            window.drawLine(((j + 1) * 35 - 27), ((i + 1) * 35 - 20 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 - 27 + 70))
            window.drawLine(((j + 1) * 35 - 15), ((i + 1) * 35 - 20 + 70), ((j + 1) * 35 - 8), ((i + 1) * 35 - 27 + 70))
            window.setColor(Color.RED)
            window.drawFillRect((j + 1) * 35 - 19, ((i + 1) * 35 - 41 + 70), 3, 6)
            window.drawLine(((j + 1) * 35 - 19), ((i + 1) * 35 - 41 + 70), ((j + 1) * 35 - 21), ((i + 1) * 35 - 43 + 70))
            window.drawLine(((j + 1) * 35 - 18), ((i + 1) * 35 - 41 + 70), ((j + 1) * 35 - 16), ((i + 1) * 35 - 43 + 70))
            window.drawLine(((j + 1) * 35 - 18), ((i + 1) * 35 - 41 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 - 43 + 70))
            window.drawLine(((j + 1) * 35 - 17), ((i + 1) * 35 - 41 + 70), ((j + 1) * 35 - 15), ((i + 1) * 35 - 43 + 70))
          }
          else if (direction == (1, 0)) {
            window.setColor(Color.white)
            window.drawLine(((j + 1) * 35 - 27), ((i + 1) * 35 - 15 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 - 8 + 70))
            window.drawLine(((j + 1) * 35 - 15), ((i + 1) * 35 - 15 + 70), ((j + 1) * 35 - 8), ((i + 1) * 35 - 8 + 70))
            window.drawLine(((j + 1) * 35 - 27), ((i + 1) * 35 - 8 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 - 15 + 70))
            window.drawLine(((j + 1) * 35 - 15), ((i + 1) * 35 - 8 + 70), ((j + 1) * 35 - 8), ((i + 1) * 35 - 15 + 70))
            window.setColor(Color.RED)
            window.drawFillRect((j + 1) * 35 - 19, ((i + 1) * 35 + 70), 3, 6)
            window.drawLine(((j + 1) * 35 - 19), ((i + 1) * 35 + 6 + 70), ((j + 1) * 35 - 21), ((i + 1) * 35 + 8 + 70))
            window.drawLine(((j + 1) * 35 - 18), ((i + 1) * 35 + 6 + 70), ((j + 1) * 35 - 16), ((i + 1) * 35 + 8 + 70))
            window.drawLine(((j + 1) * 35 - 18), ((i + 1) * 35 + 6 + 70), ((j + 1) * 35 - 20), ((i + 1) * 35 + 8 + 70))
            window.drawLine(((j + 1) * 35 - 17), ((i + 1) * 35 + 6 + 70), ((j + 1) * 35 - 15), ((i + 1) * 35 + 8 + 70))
          }
        }
      }
    }
  }

  //dessine la pomme sur la fenetre fungraphics
  def setApple() ={
    for(i <- grid.indices) {
      for (j <- grid(i).indices) {
        if(grid(i)(j) == -1){
          window.setColor(Color.RED)
          window.drawFillRect(((j + 1) * 35 - 28), ((i + 1) * 35 - 28 + 70), 21, 21)
          window.setColor(new Color(61, 43, 31))
          window.drawFillRect(((j + 1) * 35 - 19), ((i + 1) * 35 - 35 + 70), 3, 7)
        }
      }
    }
  }

  //Affiche la grille dans la console et affiche le tout (grille, serpent, pomme,..) sur la fenêtre FunGraphics
  def printGrid(): Unit = {
    for(i <- grid){
      println(i.mkString(" "))
    }
    println()
    setBackground()
    setSnake()
    setApple()
    setScore()
  }

  //Déplace le serpent dans la direction actuelle
  def moveSnake(): Unit = {
    try{
      direction match{
        case (-1, 0) =>
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) > 0) grid(i)(j) += 1
            }
          }
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) == 2) {
                if(grid(i-1)(j) == -1){
                  grid(i-1)(j) = 1
                  snakeLength += 1
                  score += 1
                  appleEatenSound.play()
                  placeApple()
                }
                else if(grid(i-1)(j) > 0){
                  gameOver = true
                  gameStarted = false
                }
                else{
                  grid(i-1)(j) = 1
                }
              }
            }
          }
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) > snakeLength) grid(i)(j) = 0
            }
          }
        case (1, 0) =>
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) > 0) grid(i)(j) += 1
            }
          }
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) == 2) {
                if(grid(i+1)(j) == -1){
                  grid(i+1)(j) = 1
                  snakeLength += 1
                  score += 1
                  appleEatenSound.play()
                  placeApple()
                }
                else if(grid(i+1)(j) > 0){
                  gameOver = true
                  gameStarted = false
                }
                else{
                  grid(i+1)(j) = 1
                }
              }
            }
          }
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) > snakeLength) grid(i)(j) = 0
            }
          }
        case (0, 1) =>
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) > 0) grid(i)(j) += 1
            }
          }
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) == 2) {
                if(grid(i)(j+1) == -1){
                  grid(i)(j+1) = 1
                  snakeLength += 1
                  score += 1
                  appleEatenSound.play()
                  placeApple()
                }
                else if(grid(i)(j+1) > 0){
                  gameOver = true
                  gameStarted = false
                }
                else{
                  grid(i)(j+1) = 1
                }
              }
            }
          }
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) > snakeLength) grid(i)(j) = 0
            }
          }
        case (0, -1) =>
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) > 0) grid(i)(j) += 1
            }
          }
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) == 2) {
                if(grid(i)(j-1) == -1){
                  grid(i)(j-1) = 1
                  snakeLength += 1
                  score += 1
                  appleEatenSound.play()
                  placeApple()
                }
                else if(grid(i)(j-1) > 0){
                  gameOver = true
                  gameStarted = false
                }
                else{
                  grid(i)(j-1) = 1
                }
              }
            }
          }
          for(i <- grid.indices){
            for(j <- grid(i).indices){
              if(grid(i)(j) > snakeLength) grid(i)(j) = 0
            }
          }
      }
    }
    catch{
      case e: ArrayIndexOutOfBoundsException =>
        println("GAME OVER")
        gameOver = true
        gameStarted = false
    }
  }

  //Place la pomme à un endroit aléatoire qui n'est pas occupé par le serpent
  def placeApple(): Unit ={
    var isOccupied = true
    while(isOccupied){
      val randomHeight = (math.random() * gridHeight).toInt
      val randomWidth = (math.random() * gridWidth).toInt
      if(grid(randomHeight)(randomWidth) == 0){
        grid(randomHeight)(randomWidth) = -1
        isOccupied = false
      }
      else isOccupied = true
    }
  }

  //Change la direction du serpent
  def changeDirection(newDirection: (Int, Int)): Unit = {
    if (newDirection != (-direction._1, -direction._2)) {
      direction = newDirection
    }
  }

  //Timer pour déplacer le serpent toutes les 115ms
  val timer = new Timer(115, (_: ActionEvent) => {
    window.syncGameLogic(60)
    if(gameStarted == true){
      moveSnake()
      printGrid()
    }
    else if(gameOver == true){
      setSnakeException()
      restart()
    }
  })

  //fonction qui capte les touches (flèches et WASD) pour changer la direction du serpent
  window.setKeyManager(new KeyAdapter() {
    override def keyPressed(e: KeyEvent): Unit = {
      e.getKeyCode match {
        case KeyEvent.VK_LEFT => changeDirection((0, -1))
        case KeyEvent.VK_RIGHT => changeDirection((0, 1))
        case KeyEvent.VK_DOWN => changeDirection((1, 0))
        case KeyEvent.VK_UP => changeDirection((-1, 0))
        case KeyEvent.VK_A => changeDirection((0, -1))
        case KeyEvent.VK_D => changeDirection((0, 1))
        case KeyEvent.VK_S => changeDirection((1, 0))
        case KeyEvent.VK_W => changeDirection((-1, 0))
        //case _ =>
      }
    }
  }
  )

  //fonction qui crée le du bouton (avec certaines dimensions) et le place à un certain endroit
  window.addMouseListener(new MouseAdapter {
    override def mousePressed(e: MouseEvent): Unit = {
      val mouseX = e.getX
      val mouseY = e.getY
      if (mouseX >= buttonx && mouseX <= buttonx + buttonw &&
        mouseY >= buttony && mouseY <= buttony + buttonh) {
        btnprsd = true
        startGame()
      }
    }
  })

  //cette fonction crée un rectangle, avec le string indiqué, au niveau du bouton créé
  def startbtn(s: String, textX: Int): Unit = {
    window.setColor(new Color(0, 70, 150))
    window.drawFillRect(buttonx, buttony, buttonw, buttonh)

    //Dessine le mot qu'on veut ajouter sur le bouton
    window.setColor(Color.WHITE)
    val customFont = new java.awt.Font("Lithograph", java.awt.Font.BOLD, 36) // Larger font size
    val textY = buttony + (buttonh / 2) + 13
    window.drawString(textX, textY, s"$s", customFont, new Color(181, 206, 161), halign, valign)
  }

  // Commence le jeu
  def startGame(): Unit ={
    endSound.stop()
    menuSound.stop()
    gameStarted = true
    hereWeGo.play()
    gameSound.play()
    printGrid()
    placeApple()
  }

  //la fonction restart est appelée quand on perd et affiche la page de fin et donne l'option de rejouer
  def restart() ={
    hereWeGo.stop()
    gameSound.stop()
    appleEatenSound.stop()
    endSound.play()
    window.drawTransformedPicture(349, 300, 0.0, 1.0, "/GTA_Wasted_Font_Style.png")

    if(score > highScore) highScore = score
    score = 0
    direction =(0, 1)
    snakeLength = 2
    gameStarted = false
    gameOver = false
    resetsnake()
    startbtn("REPLAY", buttonx + (buttonw / 2) - 72)
  }

  // Programme principal
  def main(args: Array[String]): Unit = {
    setMenu()
    println("Bienvenue dans le Snake Game ! Utilisez les flèches pour diriger le serpent.")
    startbtn("PLAY", buttonx + (buttonw / 2) - 48)
    timer.start()
  }
}
