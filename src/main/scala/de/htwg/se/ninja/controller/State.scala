package de.htwg.se.ninja.controller

object State extends Enumeration {

  type state = Value
  val DIRECTION_DOES_NOT_EXIST, TURN, SET_FLAG1, SET_FLAG2, No_NINJA_OR_NOT_VALID, WON = Value
}
