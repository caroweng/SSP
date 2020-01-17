package de.htwg.se.ninja.model.fileIO.json

import com.google.inject.Inject
import de.htwg.se.ninja.controller.component.State
import de.htwg.se.ninja.model.DeskInterface
import de.htwg.se.ninja.model.component.Desk
import de.htwg.se.ninja.model.component.component.component.component.{Cell, Field, Ninja, Player, StateOfPlayer, Weapon}
import de.htwg.se.ninja.model.component.component.component.{CellInterface, FieldInterface, NinjaInterface, PlayerInterface}
import de.htwg.se.ninja.model.fileIO.FileIOInterface
import play.api.libs.json._

import scala.io.Source

class FileIO @Inject() extends FileIOInterface {

    override def load: DeskInterface = {
        val json: JsValue = Json.parse(Source.fromFile("target/desk.json").getLines.mkString)
        var desk: DeskInterface = null

        val name_1 = ((json \ "desk" \ "player1") \ "name").as[String]
        val state_1_string = ((json \ "desk" \ "player1") \ "state").as[String]
        val state_1 = stringToStateOfPlayer(state_1_string)
        val id_1 = ((json \ "desk" \ "player1") \ "id").as[Int]
        val player1: PlayerInterface = Player(name_1, state_1, id_1)

        val name_2 = ((json \ "desk" \ "player2") \ "name").as[String]
        val state_2_string: String = ((json \ "desk" \ "player2") \ "state").as[String]
        val state_2 = stringToStateOfPlayer(state_2_string)
        val id_2 = ((json \ "desk" \ "player2") \ "id").as[Int]
        val player2: PlayerInterface = Player(name_2, state_2, id_2)

        var field: FieldInterface = Field(Array.ofDim[Cell](6,6))

        for {i <- field.matrix.indices
             j <- field.matrix.indices} {
            field.matrix(i)(j) = Cell(None)
        }


        for (index <- 0 until 6 * 6) {
            val row = (json \\ "row")(index).as[Int]
            val col = (json \\ "col")(index).as[Int]

            val cell = (json \\ "cell")(index)

            val ninja_i = (cell \ "ninja")
            println(ninja_i.get)
            if((ninja_i \ "weapon").isDefined) {
                val weapon_string = (ninja_i \ "weapon").as[String]
                val weapon = stringToWeapon(weapon_string)
                val ninjaId = (ninja_i \ "ninjaId").as[Int]
                val playerId = (ninja_i \ "playerId").as[Int]
                val ninja: NinjaInterface = Ninja(weapon, playerId, ninjaId)
                field = field.+(ninja, (row, col))
            }
        }

        desk = Desk(field, player1, player2)
        desk
    }

    def stringToStateOfPlayer(string: String): StateOfPlayer.stateOfPlayer = {
        if(string == "go")
            StateOfPlayer.go
        else
            StateOfPlayer.pause
    }

    def stringToWeapon(string: String): Weapon.weapon = {
        if(string == "f")
            Weapon.flag
        else if (string == "p")
            Weapon.paper
        else if (string == "s")
            Weapon.scissors
        else
            Weapon.rock
    }

    override def save(grid: DeskInterface, state: State.Value): Unit = {
        import java.io._
        val pw = new PrintWriter(new File("target/desk.json"))
        pw.write(Json.prettyPrint(deskToJson(grid, state)))
        pw.close()
    }

    implicit val ninjaWrites = new Writes[NinjaInterface] {
        def writes(ninja: NinjaInterface): JsObject = Json.obj(
            "weapon" -> ninja.weapon.toString,
            "playerId" -> ninja.playerId,
            "ninjaId" -> ninja.ninjaId
        )
    }

    implicit val cellWrites = new Writes[CellInterface] {
        def writes(cell: CellInterface): JsObject = {

            if(cell.exists()) {
                val ninja: NinjaInterface = cell.getNinja()
                Json.obj(
                    "ninja" -> Json.toJson(ninja)
                )
            } else {
                Json.obj("ninja" -> "")
            }
        }
    }

    implicit val playerWrites = new Writes[PlayerInterface] {
        def writes(player: PlayerInterface) = Json.obj(
            "name" -> player.name,
            "state" -> player.state,
            "id" -> player.id,
        )
    }

    def deskToJson(desk: DeskInterface, state: State.Value) = {
        Json.obj(
            "desk" -> Json.obj(
                "player1" -> Json.toJson(desk.player1),
                "player2" -> Json.toJson(desk.player2),
                "field" -> Json.toJson(
                    for {
                        row <- desk.field.matrix.indices;
                        col <- desk.field.matrix.indices
                    } yield {
                        Json.obj(
                            "row" -> row,
                            "col" -> col,
                            "cell" -> Json.toJson(desk.field.matrix(row)(col))
                        )
                    }
                )
            ),
            "state" -> Json.toJson(state)
        )
    }
}
