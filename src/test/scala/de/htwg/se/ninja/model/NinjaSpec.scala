package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class NinjaSpec extends WordSpec with Matchers{
  "A Ninja" should {
    "be constructed with Team and Weapon" in {
      val ninja = Ninja.apply(Team.T2, Weapon.scissors)
      ninja.team should be(Team.T2)
      ninja.weapon should be(Weapon.scissors)
      print("es geht")
    }
  }
}