package de.htwg.se.ninja.model

case class Player(name: String, state: StateOfPlayer.stateOfPlayer) {
  def changeState(newState: StateOfPlayer.stateOfPlayer): Player = this.copy(state = newState)
}
