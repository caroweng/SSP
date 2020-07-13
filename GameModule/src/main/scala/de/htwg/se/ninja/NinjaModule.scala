package de.htwg.se.ninja

import com.google.inject.AbstractModule
import de.htwg.se.ninja.controller.ControllerInterface
import de.htwg.se.ninja.controller.component.Controller
import de.htwg.se.ninja.model.DeskInterface
import de.htwg.se.ninja.model.component.Desk
import de.htwg.se.ninja.model.component.component.component.{CellInterface, FieldInterface, NinjaInterface}
import de.htwg.se.ninja.model.component.component.component.component.{Cell, Field, Ninja}
import de.htwg.se.ninja.model.fileIO.FileIOInterface
import net.codingwell.scalaguice.ScalaModule

class NinjaModule extends AbstractModule with ScalaModule {
    var player1 = Player("Spieler1", StateOfPlayer.go, 1)
    var player2 = Player("Spieler2", StateOfPlayer.pause, 2)
    var field = Field(Array.ofDim[Cell](6,6))
    val desk = Desk(field, player1, player2)

    def configure() = {
        bind[FieldInterface].toInstance(field)
        bind[DeskInterface].toInstance(Desk(field, player1, player2))
        bind[ControllerInterface].toInstance(new Controller(desk))

        bind[FileIOInterface].to[model.fileIO.json.FileIO]
//        bind[FileIOInterface].to[model.fileIO.xml.FileIO]

    }

}