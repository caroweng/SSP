package de.htwg.se.ninja.model

case class Player(name: String, state: Turn.turn) {
  def changeTurn(newState: Turn.turn): Player = copy(state = newState)
}
