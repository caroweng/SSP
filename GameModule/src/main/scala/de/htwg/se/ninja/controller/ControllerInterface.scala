package de.htwg.se.ninja.controller

import de.htwg.se.ninja.controller.component.State
import de.htwg.se.ninja.model.DeskInterface
import de.htwg.se.ninja.model.component.component.component.FieldInterface
import de.htwg.se.ninja.model.component.component.component.component.Direction

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
    var desk: DeskInterface
    var state: State.state

    def newDesk(player1: PlayerInterface, player2: PlayerInterface, field: FieldInterface): DeskInterface
    def newGame(): DeskInterface
    def currentPlayer: PlayerInterface
    def setName(name: String): State.state
    def setFlag(row: Int, col: Int): State.state
    def wonOrTurn(input: String): State.state
    def walk(row: Int, col: Int, d: Direction.direction): State.state
    def changeTurns(): State.state
    def loadFile: State.state
    def storeFile: State.state
    def switchState(newState: State.state): State.state
    def undo: State.state
    def redo: State.state
}
