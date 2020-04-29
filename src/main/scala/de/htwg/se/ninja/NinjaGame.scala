package de.htwg.se.ninja

import com.google.inject.Guice
import de.htwg.se.ninja.controller.ControllerInterface
import de.htwg.se.ninja.controller.component.{Controller, UpdateEvent}
import de.htwg.se.ninja.model.component.Desk
import de.htwg.se.ninja.model.component.component.component.component.{Cell, Field, Player, StateOfPlayer}
import de.htwg.se.ninja.view.{Gui, Tui}

import scala.io.StdIn.readLine

object NinjaGame {

  val injector = Guice.createInjector(new NinjaModule)
  val controller = injector.getInstance(classOf[ControllerInterface])


  controller.newGame()


  val tui = new Tui(controller)
  val gui = new Gui(controller)
  controller.publish(new UpdateEvent)

  def main(args: Array[String]): Unit= {
    val input: String = ""
    do {
      val input = readLine()
      tui.input(input)
    } while (input != "q")
  }

}
