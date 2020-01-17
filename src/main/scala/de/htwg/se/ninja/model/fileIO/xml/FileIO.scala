package de.htwg.se.ninja.model.fileIO.xml

import com.google.inject.Inject
import play.api.libs.json.{JsObject, Json}

import scala.collection.SortedSet
import scala.xml.PrettyPrinter

class FileIO
//@Inject()
//    extends FileIOInterface
{

//  override def load: DeskInterface = {
//    val file = scala.xml.XML.loadFile("/target/desk.xml")
//    val t = Tile(-1, Color.RED, -1)
//    val amountOfPlayersAttr = (file \\ "desk" \ "@amountOfPlayers")
//    val amountOfPlayers = amountOfPlayersAttr.text.toInt
//    var players = Set[PlayerInterface]()
//    var bagOfTiles = Set[TileInterface]()
//    var ssets = Set[SortedSet[TileInterface]]()
//    for (playerNodes <- file \\ "desk" \\ "players") {
//      for (player <- playerNodes \\ "player") {
//        val playerName: String = (player \ "@name").text.toString
//        val playerNumber: Int = (player \ "@number").text.toInt
//        val playerState: String = (player \ "@state").text.toString
//        var board: BoardInterface = Board(SortedSet[TileInterface]())
//        for (tile <- player \\ "board" \\ "tile") {
//          board = board + Tile.stringToTile((tile \ "@identifier").text.toString)
//        }
//        players = players.+(Player(playerName, playerNumber, board, State.stringToState(playerState)))
//      }
//
//
//      for (tileNodes <- file \\ "desk" \\ "bagOfTiles") {
//        for (tile <- tileNodes \\ "tile") {
//          bagOfTiles = bagOfTiles + Tile.stringToTile((tile \ "@identifier").text.toString.trim)
//        }
//      }
//
//      for (ssetsNodes <- file \\ "desk" \\ "sets") {
//        for (set <- ssetsNodes \\ "sortedSet") {
//          var sorted = SortedSet[TileInterface]()
//          for (tile <- set \\ "tile") {
//            sorted = sorted + Tile.stringToTile((tile \ "@identifier").text.toString.trim)
//          }
//          ssets = ssets + sorted
//        }
//      }
//    }
//    deskBaseImpl.Desk(players, bagOfTiles, ssets)
//  }
//
//  def save(grid: DeskInterface, state: ControllerState.Value): Unit = saveString(grid)
//
//
//  private def saveString(desk: DeskInterface): Unit = {
//    import java.io._
//    val pw = new PrintWriter(new File("/target/desk.xml"))
//    val prettyPrinter = new PrettyPrinter(120, 4)
//    val xml = prettyPrinter.format(deskToXml(desk))
//    pw.write(xml)
//    pw.close()
//  }
//
//
//  private def deskToXml(desk: DeskInterface) = {
//    <desk amountOfPlayers={desk.amountOfPlayers.toString}>
//      <players>
//        {for {
//        player <- desk.players
//      } yield playerToXml(player)}
//      </players>
//      <bagOfTiles>
//        {for {
//        tile <- desk.bagOfTiles
//      } yield tiletoXml(tile)}
//      </bagOfTiles>
//      <sets>
//        {for {
//        sset <- desk.board
//      } yield setToXml(sset)}
//      </sets>
//    </desk>
//  }
//
//
//  private def playerToXml(player: PlayerInterface) = {
//    <player name={player.name.toString}
//            number={player.number.toString}
//            state={player.state.toString}>
//      {boardToXml(player.tiles)}
//    </player>
//  }
//
//  private def setToXml(sset: SortedSet[TileInterface]) = {
//    <sortedSet>
//      {sset.map(s => tiletoXml(s))}
//    </sortedSet>
//  }
//
//  private def boardToXml(set: SortedSet[TileInterface]) =
//    <board>
//      {set.map(s => tiletoXml(s))}
//    </board>
//
//  private def tiletoXml(tile: TileInterface) =
//    <tile identifier={tile.identifier.toString}></tile>
//
//  override def deskToJson(desk: DeskInterface, state: ControllerState.Value): JsObject = { //remove this ???
//    Json.obj()
//  }
}
