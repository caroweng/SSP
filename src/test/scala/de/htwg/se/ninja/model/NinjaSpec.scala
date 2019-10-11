package de.htwg.se.ninja.model

import org.scalatest.{Matchers, WordSpec}

class NinjaSpec extends WordSpec with Matchers{
  "A Ninja" when {
    "be constructed" should {
      val player = Player("helen", StateOfPlayer.pause)
      val ninja = Ninja(Weapon.scissors, player)
      "have a name and a weapon" in {
        ninja.player should be(player)
        ninja.weapon should be(Weapon.scissors)
      }
      "change its weapon" in {
        val n2 = ninja.changeWeapon(Weapon.paper)
        n2.weapon should be(Weapon.paper)
      }
    }
  }
}