package de.htwg.se.ninja.view

import de.htwg.se.ninja.controller.{Controller, State}

import scala.swing.{Action, Button, Dimension, Frame, GridPanel, Label, Menu, MenuBar, MenuItem, ScrollPane, TextField}
import scala.swing.event.{ButtonClicked, MouseClicked}
import scala.swing.Swing.LineBorder


class Gui(controller: Controller) extends Frame with UIInterface {
    controller.add(this)

    size = new Dimension(100, 100)
    title = "Rummy"
    menuBar = new MenuBar {
        contents += new Menu("Menu") {
            contents += new MenuItem(Action("Quit game") {
                System.exit(0)
            })
        }
    }

//    contents = new GridPanel(4, 1) {
//        hGap = 3
//        vGap = 3
//        contents += gridPanel
//    }
    visible = true

    var cells = Array.ofDim[CellPanel](6, 6)

    def gridPanel(): Unit = {
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
                        listenTo(controller)
                        reactions += {
                            case e: CellChanged => {
                                text = if (controller.available(row, column).contains(value)) value.toString else " "
                                repaint
                            }
                            case MouseClicked(src, pt, mod, clicks, pops) => {
                                controller.set(row, column, value)
                                text = if (controller.available(row, column).contains(value)) value.toString else " "
                                repaint
                            }
                        }
                    }
                }
            }
        }

        contents = gridpanel
    }

    def cellText(row: Int, col: Int): String = {
        val cell = controller.desk.field.getCellAtPosition(row, col)
        if (cell.exists()) {
            cell.getNinja().weapon.toString
        } else {
            ""
        }
    }


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

        contents = new GridPanel(3, 1) {
                    contents += tf
                    contents += okButton
                }

    }

    override def update: Unit = {
        controller.state match {
//            case State.INSERTING_NAME_1 => gridPanel()
            case State.INSERTING_NAME_1 => enterName()
            case State.INSERTING_NAME_2 => enterName()
            case State.SET_FLAG_1 => gridPanel()
        }
    }
}
