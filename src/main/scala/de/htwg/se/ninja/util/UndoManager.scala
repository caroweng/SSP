package de.htwg.se.ninja.util

import de.htwg.se.ninja.controller.State

class UndoManager {
  private var undoStack: List[Command]= Nil
  private var redoStack: List[Command]= Nil
  def doStep(command: Command): State.state = {
    undoStack = command::undoStack
    command.doStep
  }

  def undoStep: State.state  = {
    undoStack match {
      case  Nil => null
      case head::stack => {
        val state = head.undoStep
        undoStack=stack
        redoStack= head::redoStack
        state
      }
    }
  }

  def redoStep: State.state = {
    redoStack match {
      case Nil => null
      case head::stack => {
        val state = head.redoStep
        redoStack=stack
        undoStack=head::undoStack
        state
      }
    }
  }
}