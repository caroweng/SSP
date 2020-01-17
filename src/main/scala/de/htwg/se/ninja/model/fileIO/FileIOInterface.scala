package de.htwg.se.ninja.model.fileIO

import de.htwg.se.ninja.controller.component.State
import de.htwg.se.ninja.model.DeskInterface
import play.api.libs.json.JsObject

trait FileIOInterface {
  def load: DeskInterface
  def save(desk: DeskInterface, state: State.Value): Unit
  def deskToJson(desk: DeskInterface, state: State.Value): JsObject
}
