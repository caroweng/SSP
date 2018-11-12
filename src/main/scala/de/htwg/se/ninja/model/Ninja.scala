package de.htwg.se.ninja.model

import Weapons.Weapons

class Ninja  {
  var team: Boolean =_
  var waffe: Weapons =_
}

object Ninja {
  def apply(team: Boolean, waffe: Weapons): Ninja = {
    var n = new Ninja
    n.team = team
    n.waffe = waffe
    n
  }
}