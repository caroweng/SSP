package de.htwg.se.ninja.model.gridComponent.gridBaseImpl

import org.scalatest.{Matchers, WordSpec}

class SpielfeldSpec extends WordSpec with Matchers{
  "A Cell" when {
    "with a Ninja" should {
      val withNinja = Play() ((0)(0))
      "with Stein und Team true" in {
        withNinja should be(true, Waffen.Stein)
      }
    }
    "without a Ninja" should {
      val withoutNinja = Spielfeld(3)(0)
      "" in {
        withoutNinja should be(null, null)
      }

    }
  }
}
