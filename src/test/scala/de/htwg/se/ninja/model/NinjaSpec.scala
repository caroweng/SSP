package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class NinjaSpec extends WordSpec with Matchers{
  "A Ninja" should {
    "be constructed with Player and Weapon" in {
      val ninja = Ninja(Weapon.scissors,Player("helen", Turn.pause), 2)
      ninja.player.name should be("helen")
      ninja.weapon should be(Weapon.scissors)
      ninja.changeWeapon(Weapon.paper)
      ninja.weapon should be(Weapon.paper)
    }
  }
}