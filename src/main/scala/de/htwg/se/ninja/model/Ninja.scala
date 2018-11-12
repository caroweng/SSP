package de.htwg.se.ninja.model

import Weapons.Weapons
import de.htwg.se.ninja.model.Team.Team

class Ninja  {
  var team: Team =_
  var weapon: Weapons =_
}

object Ninja {
  def apply(team: Team, weapon: Weapons): Ninja = {
    var n = new Ninja
    n.team = team
    n.weapon = weapon
    n
  }
}