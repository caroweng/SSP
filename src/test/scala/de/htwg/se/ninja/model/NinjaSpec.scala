package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class NinjaSpec extends WordSpec with Matchers{
  "A Ninja" when {
    "be constructed" should {
      val ninja = Ninja(Weapon.scissors,Player("helen", Turn.pause, false), 2)
      "have a name and a weapon" in {
        ninja.player.name should be("helen")
        ninja.weapon should be(Weapon.scissors)
        "change its weapon" in {
          ninja.changeWeapon(Weapon.paper)
          ninja.weapon should be(Weapon.paper)
        }
      }
    }
  }
}