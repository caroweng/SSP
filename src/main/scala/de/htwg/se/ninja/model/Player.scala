package de.htwg.se.ninja.model

case class Player (name: String, state: Turn.turn) {
  def changeTurn(player2: Player): Unit = {
    this.copy(state = player2.state)
    player2.copy(state = this.state)
  }
}
