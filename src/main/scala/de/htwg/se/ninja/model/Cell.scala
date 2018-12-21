package de.htwg.se.ninja.model

case class Cell(optNinja: Option[Ninja]) {
//  override def toString: String ={
//    var str: String = ""
//    if(this.optNinja.isEmpty) {
//      str = "   "
//      str
//    } else {
//      val ninja = this.getNinja()
//      if(ninja.player == ) str = "1"  else str = "2"
//      ninja.weapon match {
//        case Weapon.flag => str.concat(" f")
//        case Weapon.stone => str.concat(" r")
//        case Weapon.paper => str.concat(" p")
//        case Weapon.scissors => str.concat(" s")
//      }
//    }
//  }

  def exists(): Boolean = optNinja.isDefined
  def getNinja(): Ninja = optNinja.get
}