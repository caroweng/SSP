package de.htwg.se.ninja.util

import de.htwg.se.ninja.controller.component.State

trait Command {
    def doStep: State.state
    def undoStep: State.state
    def redoStep: State.state
}
