package de.htwg.se.ninja.model

import scala.util.Random

case class Field(matrix: Array[Array[Cell]]) {

    def matrix(tupel: (Int, Int)): Cell = matrix(tupel._1)(tupel._2)

    def getPosition(n1: Ninja): (Int, Int) = {
        for(r <- matrix.indices)
            for(c <- matrix.indices)
                if (matrix(r)(c).optNinja.getOrElse("kein Ninja") == Ninja(n1.weapon, n1.player))
                    return (r, c)

        throw new NoSuchElementException()
    }

    def isNinjaOfPlayerAtPosition(player: Player, row: Int, col: Int): Boolean = {
        if (inBounds(row, col) && matrix(row)(col).exists() && matrix(row, col).getNinja().player.name == player.name) {
            return true
        }
        false
    }

    def move(n1: Ninja, direction: Direction.direction): Field = {
        if(n1.weapon == Weapon.flag) throw new IllegalStateException()

        val pos: (Int, Int) = getPosition(n1)
        val newpos: (Int, Int) = add(pos, Direction.getDirectionIndex(direction))
        matrix(newpos).optNinja match {
            case None =>  this.-(n1, getPosition(n1)).+(n1, newpos)
            case Some(n2) => this.-(n1, getPosition(n1)).+(fight(n1, n2), newpos)
        }
    }

    def -(ninja: Ninja, pos: (Int,Int)): Field = copy(matrix.updated(pos._1, matrix(pos._1).updated(pos._2, Cell(None))))

    def +(ninja: Ninja, pos: (Int,Int)): Field = copy(matrix.updated(pos._1, matrix(pos._1).updated(pos._2, Cell(Some(ninja)))))

    def fight(n1: Ninja, n2: Ninja): Ninja = {
        if(n1.weapon == n2.weapon) {
            val r: Random = new Random()
            val n: Int = r.nextInt(3)
            val newN: Ninja = n1.copy(weapon = Weapon.createWeapon(n))
            if(weaponWeight(newN.weapon, n2.weapon)) return newN else return n2
        }
        if(weaponWeight(n1.weapon, n2.weapon)) n1 else n2
    }

    def weaponWeight(w1: Weapon.weapon, w2: Weapon.weapon) : Boolean = {
        w1 match {
            case Weapon.`rock` =>
                if (w2 == Weapon.paper) false else true
            case Weapon.scissors =>
                if (w2 == Weapon.rock) false else true
            case Weapon.paper =>
                if (w2 == Weapon.scissors) false else true
        }
    }

    def exists(row: Int, col: Int, direction: Direction.direction): Boolean = {
        val add1: (Int, Int) = add((row, col), Direction.getDirectionIndex(direction))
        if (!inBounds(add1) || (matrix(add1).exists() && matrix(add1).getNinja().player == matrix(row, col).getNinja().player)) false else true
    }

    def add(base: (Int, Int), amount: (Int, Int)): (Int, Int) = (base._1 + amount._1, base._2 + amount._2)

    def inBounds(tuple: (Int, Int)): Boolean = tuple._1 < matrix.length && tuple._1 >= 0 && tuple._2 < matrix.length && tuple._2 >= 0
}