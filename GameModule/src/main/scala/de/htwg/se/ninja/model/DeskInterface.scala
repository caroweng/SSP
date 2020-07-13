package de.htwg.se.ninja.model

import de.htwg.se.ninja.model.component.component.component.component.Direction.direction
import de.htwg.se.ninja.model.component.component.component.FieldInterface

trait DeskInterface {

    val field: FieldInterface
    val player1: PlayerInterface
    val player2: PlayerInterface

    def setNewGame(): DeskInterface
    def win(row: Int, col: Int, d: direction): Boolean
    def changeTurns(): DeskInterface
    def copyWithNewPlayer(playerId: Int, newPlayer: PlayerInterface): DeskInterface
    def copyWithNewField(field: FieldInterface): DeskInterface
    def copyDesk(): DeskInterface
}
