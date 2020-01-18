package de.htwg.se.ninja.model.component.component.component.component

object StateOfPlayer extends Enumeration {
  type stateOfPlayer = Value
  val go, pause = Value

  def stringToStateOfPlayer(str: String): StateOfPlayer.stateOfPlayer = {
    str match {
      case "go" => StateOfPlayer.go
      case "pause" => StateOfPlayer.pause
    }
  }
}
