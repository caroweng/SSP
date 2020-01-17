package de.htwg.se.rummy.model.fileIO.xml

import com.google.inject.Inject
import de.htwg.se.ninja.controller.component.State
import de.htwg.se.ninja.model.DeskInterface
import de.htwg.se.ninja.model.component.Desk
import de.htwg.se.ninja.model.component.component.component.{CellInterface, FieldInterface, PlayerInterface}
import de.htwg.se.ninja.model.fileIO.FileIOInterface
import play.api.libs.json.{JsObject, Json}

import scala.collection.SortedSet
import scala.xml.PrettyPrinter

class FileIO @Inject() extends FileIOInterface {

    override def load: DeskInterface = {
//        val file = scala.xml.XML.loadFile("/target/desk.xml")
//        val amountOfPlayersAttr = (file \\ "desk" \ "@amountOfPlayers")
//        val amountOfPlayers = amountOfPlayersAttr.text.toInt
        var player1 = null
        var player2 = null
        var field = null
//        var bagOfTiles = Set[TileInterface]()
//        var ssets = Set[SortedSet[TileInterface]]()
//        for (playerNodes <- file \\ "desk" \\ "players") {
//            for (player <- playerNodes \\ "player") {
//                val playerName: String = (player \ "@name").text.toString
//                val playerNumber: Int = (player \ "@number").text.toInt
//                val playerState: String = (player \ "@state").text.toString
//                var board: BoardInterface = Board(SortedSet[TileInterface]())
//                for (tile <- player \\ "board" \\ "tile") {
//                    board = board + Tile.stringToTile((tile \ "@identifier").text.toString)
//                }
//                players = players.+(Player(playerName, playerNumber, board, State.stringToState(playerState)))
//            }
//
//
//            for (tileNodes <- file \\ "desk" \\ "bagOfTiles") {
//                for (tile <- tileNodes \\ "tile") {
//                    bagOfTiles = bagOfTiles + Tile.stringToTile((tile \ "@identifier").text.toString.trim)
//                }
//            }
//
//            for (ssetsNodes <- file \\ "desk" \\ "sets") {
//                for (set <- ssetsNodes \\ "sortedSet") {
//                    var sorted = SortedSet[TileInterface]()
//                    for (tile <- set \\ "tile") {
//                        sorted = sorted + Tile.stringToTile((tile \ "@identifier").text.toString.trim)
//                    }
//                    ssets = ssets + sorted
//                }
//            }
//        }
        new Desk(field, player1, player2)
    }

    def save(grid: DeskInterface, state: State.state): Unit = saveString(grid, state)

    private def saveString(desk: DeskInterface, state: State.state): Unit = {
        import java.io._
        val pw = new PrintWriter(new File("/target/desk.xml"))
        val prettyPrinter = new PrettyPrinter(120, 4)
        val xml = prettyPrinter.format(deskToXml(desk, state))
        pw.write(xml)
        pw.close()
    }


    private def deskToXml(desk: DeskInterface, state: State.state) = {
        <desk>
            <player1>
                {playerToXml(desk.player1)}
            </player1>
            <player2>
                {playerToXml(desk.player2)}
            </player2>
            <state>
                {stateToXml(state)}
            </state>
        </desk>
    }

//            <field>
//                {for {
//                cell <- desk.field.matrix
//            } yield tiletoXml(cell)}
//            </field>

    private def playerToXml(player: PlayerInterface) = {
        <player name={player.name.toString}
                state={player.state.toString}
                id={player.id}>
        </player>
    }

    private def stateToXml(state: State.state) = {
        <state>
            {}
        </state>
    }

    def cellToXml(field: FieldInterface, row: Int, col: Int) = {
        <cell row={ row.toString } col={ col.toString } ninja={} >
            { field.matrix(row)(col).exists() }
        </cell>
    }
//
//    private def boardToXml(set: SortedSet[TileInterface]) =
//        <board>
//            {set.map(s => tiletoXml(s))}
//        </board>
//
//    private def tiletoXml(tile: TileInterface) =
//        <tile identifier={tile.identifier.toString}></tile>

    override def deskToJson(desk: DeskInterface, state: State.state): JsObject = { //remove this ???
        Json.obj()
    }
}
