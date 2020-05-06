package de.htwg.se.ninja.model.fileIO

import de.htwg.se.ninja.controller.component.State
import de.htwg.se.ninja.model.DeskInterface
import play.api.libs.json.JsObject

trait FileIOInterface {
  def load: DeskInterface
  def save(desk: DeskInterface, state: State.state): Unit
//  def deskToString(desk: DeskInterface, state: State.state): JsObject
}