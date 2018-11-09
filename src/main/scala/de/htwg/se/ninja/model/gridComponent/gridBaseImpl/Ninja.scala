package de.htwg.se.ninja.model.gridComponent.gridBaseImpl

import de.htwg.se.ninja.model.gridComponent.gridBaseImpl.Waffen.Waffen

class Ninja  {
  var team: Boolean =_
  var waffe: Waffen =_
}

object Ninja {
  def apply(team: Boolean, waffe: Waffen): Ninja = {
    var n = new Ninja
    n.team = team
    n.waffe = waffe
    n
  }
}
