package de.htwg.se.ninja.view

import de.htwg.se.ninja.controller.{Controller, State, UpdateEvent}
import de.htwg.se.ninja.model.Cell

import scala.swing.{Action, BorderPanel, Button, Component, Dimension, FlowPanel, Frame, GridPanel, Label, Menu, MenuBar, MenuItem, ScrollPane, TextField}
import scala.swing.event.{ButtonClicked, MouseClicked}
import scala.swing.Swing.LineBorder


class Gui(controller: Controller) extends Frame with UIInterface {
    listenTo(controller)

    size = new Dimension(100, 100)
    title = "Ninja"
    var selectedCellCoordinates: (Int, Int) = null

    reactions += {
        case e: UpdateEvent => {
            update
        }
    }
//
//    contents = new BorderPanel {
//        add(gridPanel(), BorderPanel.Position.Center)
////        add(statusline, BorderPanel.Position.South)
//    }


    menuBar = new MenuBar {
        contents += new Menu("Menu") {
            contents += new MenuItem(Action("Quit game") {
                System.exit(0)
            })
        }
        contents += new Button {
            text = "left"
            reactions += {
                case ButtonClicked(_) => {
                    move("left")
                }
            }
        }
        contents += new Button {
            text = "right"
            reactions += {
                case ButtonClicked(_) => {
                    move("right")
                }
            }
        }
        contents += new Button {
            text = "up"
            reactions += {
                case ButtonClicked(_) => {
                    move("up")
                }
            }
        }
        contents += new Button {
            text = "down"
            reactions += {
                case ButtonClicked(_) => {
                    move("down")
                }
            }
        }
        contents += new Button {
            text = "next Player"
            reactions += {
                case ButtonClicked(_) => {
                    if(controller.state == State.WALKED) {
                        controller.changeTurns()
                        repaint
                    }
                }
            }
        }
        contents += new Button {
            text = "undo"
            reactions += {
                case ButtonClicked(_) => {
                    if(controller.state == State.WALKED) {
                        controller.undo
                        repaint
                    }
                }
            }
        }
        contents += new Button {
            text = "redo"
            reactions += {
                case ButtonClicked(_) => {
                    if(controller.state == State.TURN) {
                        controller.redo
                        repaint
                    }
                }
            }
        }
    }

    def move(direction: String): Unit = {
        if(selectedCellCoordinates == null) {
            return
        }
        val x = selectedCellCoordinates._1
        val y = selectedCellCoordinates._2
        controller.wonOrTurn("w " + x + " " + y + " " + direction)
        update
    }

    visible = true

    var cells = Array.ofDim[CellPanel](6, 6)

    def gridPanel(): Component = {
        if(controller.state == State.INSERTING_NAME_1 || controller.state == State.INSERTING_NAME_2) {
            return new Label()
        }
         val gridpanel = new GridPanel(6, 6) {
            border = LineBorder(java.awt.Color.PINK, 2)
            background = java.awt.Color.PINK
            for {
                outerRow <- 0 until 6
                outerColumn <- 0 until 6
            } {
                contents += new GridPanel(2, 4) {
                    border = LineBorder(java.awt.Color.PINK, 2)
                    val x = outerRow
                    val y = outerColumn
                    contents += new Label{
                        text = cellText(x, y)
                        listenTo(mouse.clicks)
                        reactions += {
                            case e: UpdateEvent => {
                                update
                                repaint
                            }
                            case MouseClicked(src, pt, mod, clicks, pops) => {
                                onCellClick(x, y)
                            }
                        }
                    }
                }
            }
        }
//        contents = gridpanel
        gridpanel
    }

    def onCellClick(x: Int, y: Int): Unit = {
        if(controller.state == State.SET_FLAG_1 || controller.state == State.SET_FLAG_2) {
            controller.setFlag(x, y)
        } else if (controller.state == State.TURN) {
            selectedCellCoordinates = (x, y)
        }
        repaint
    }

    def won(): Unit = {
        contents = new Label("Finished");
    }

    def cellText(row: Int, col: Int): String = {
        val cell = controller.desk.field.getCellAtPosition(row, col)
        if (cell.exists()) {
            if(cell.getNinja().playerId == controller.currentPlayer.id)
                cell.getNinja().weapon.toString
            else
                "xx"
        } else {
            ""
        }
    }

    def statusline() = new FlowPanel {
        if(controller.state == State.INSERTING_NAME_1 || controller.state == State.INSERTING_NAME_2) {

            contents += new Label("Highlight:")
            val tf = new TextField("Insert name player" + controller.currentPlayer.id, 10)
            val okButton = new Button {
                text = "Ok"
                reactions += {
                    case ButtonClicked(_) => {
                        println(tf.text)
                        controller.setName(tf.text)
                    }
                }
            }
            val state = new Label(stateToString())

            contents += new GridPanel(3, 1) {
                contents += tf
                contents += okButton
                contents += state
            }
        } else {
            contents += new Label(stateToString())
        }
    }
//        new Label(stateToString())


    def enterName() {
        val tf = new TextField("Insert name player" + controller.currentPlayer.id , 10)
        val okButton = new Button {
            text = "Ok"
            reactions += {
                case ButtonClicked(_) => {
                    println(tf.text)
                    controller.setName(tf.text)
                }
            }
        }
        val state = new Label(stateToString())

        contents = new GridPanel(3, 1) {
                    contents += tf
                    contents += okButton
                    contents += state
                }
    }

    def stateToString(): String = {
        controller.state match {
            case State.INSERTING_NAME_1 => "Player 1 insert your name! name <name>"
            case State.INSERTING_NAME_2 => "Player 2 insert your name! name <name>"
            case State.SET_FLAG_1 => controller.currentPlayer.name + " set your flag!"
            case State.SET_FLAG_1_FAILED => "Flag could not be set, try again!"
            case State.SET_FLAG_2 => controller.currentPlayer.name + " set your flag!"
            case State.SET_FLAG_2_FAILED => "Flag could not be set, try again!"
            case State.NAME_REGEX_INCORRECT_1 => "Eingabe war nicht korrekt"
            case State.NAME_REGEX_INCORRECT_2 => "Eingabe war nicht korrekt"
            case State.WALK_REGEX_INCORRECT => "Eingabe war nicht korrekt"
            case State.WALKED_INPUT_INCORRECT => "Eingabe war nicht korrekt, bitte <next Player> für nächsten Spieler oder <undo> um Zug rückgängig machen eingeben"
            case State.FLAG_REGEX_INCORRECT_1 => "Eingabe war nicht korrekt"
            case State.FLAG_REGEX_INCORRECT_2 => "Eingabe war nicht korrekt"
            case State.No_NINJA_OR_NOT_VALID => "Not valid, try again!"
            case State.DIRECTION_DOES_NOT_EXIST => "Direction not valid, try again"
            case State.TURN => controller.currentPlayer.name + " it's your turn!>"
            case State.WALKED => controller.currentPlayer.name + " press <next Player> for next player or <undo> for undo."
            case State.WON => controller.currentPlayer.name + " you win!"
        }
    }

    def getPanel(): Unit = {
        contents = new BorderPanel {
            add(gridPanel(), BorderPanel.Position.Center)
            add(statusline(), BorderPanel.Position.South)
        }
    }

    def update: Unit = {
        println(controller.state)
        controller.state match {
            case State.INSERTING_NAME_1 => getPanel()
//            case State.INSERTING_NAME_1 => enterName()
            case State.INSERTING_NAME_2 => getPanel()
            case State.SET_FLAG_1 => getPanel()

            case State.SET_FLAG_2 => getPanel()
            case State.SET_FLAG_1_FAILED =>
                controller.switchState(State.SET_FLAG_1)
                return
            case State.SET_FLAG_2_FAILED =>
                controller.switchState(State.SET_FLAG_2)
                return
            case State.NAME_REGEX_INCORRECT_1 =>
                controller.switchState(State.INSERTING_NAME_1)
                return
            case State.NAME_REGEX_INCORRECT_2 =>
                controller.switchState(State.INSERTING_NAME_2)
                return
            case State.WALK_REGEX_INCORRECT =>
                controller.switchState(State.TURN)
                return
            case State.FLAG_REGEX_INCORRECT_1 =>
                controller.switchState(State.SET_FLAG_1)
                return
            case State.FLAG_REGEX_INCORRECT_2 =>
                controller.switchState(State.SET_FLAG_2)
                return
            case State.WALKED_INPUT_INCORRECT =>
                controller.switchState(State.WALKED)
                return
            case State.DIRECTION_DOES_NOT_EXIST =>
                controller.switchState(State.TURN)
                return
            case State.TURN => getPanel()
            case State.WALKED => getPanel()
            case State.No_NINJA_OR_NOT_VALID =>
                controller.switchState(State.TURN)
                return
            case State.WON =>
                won()
        }
    }
}
