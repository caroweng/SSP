package de.htwg.se.ninja.model

import de.htwg.se.ninja.model.component.Desk
import de.htwg.se.ninja.model.component.component.component.component.Direction.direction
import de.htwg.se.ninja.model.component.component.component.{FieldInterface, PlayerInterface}

trait DeskInterface {

    val field: FieldInterface
    val player1: PlayerInterface
    val player2: PlayerInterface

    def setNewGame(): Desk
    def win(row: Int, col: Int, d: direction): Boolean
    def changeTurns(): Desk
    def copyWithNewPlayer(playerId: Int, newPlayer: PlayerInterface): Desk
    def copyWithNewField(field: FieldInterface): Desk
    def copyDesk(): Desk
}
