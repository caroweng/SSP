package de.htwg.se.ninja.model

case class Player(name: String, state: StateOfPlayer.stateOfPlayer, id: Int) {
  def changeState(newState: StateOfPlayer.stateOfPlayer): Player = this.copy(state = newState)
}
