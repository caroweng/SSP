package de.htwg.se.ninja.model

import scala.util.Random

case class Field(matrix: Array[Array[Cell]]) {

    def getCellAtPosition(tupel: (Int, Int)): Cell = matrix(tupel._1)(tupel._2)

    def setNewField(): Field = {
        setEmpty()
        setInitialNinjaRows()
    }

    def setEmpty(): Field = {
        val newMatrix: Array[Array[Cell]] = Array.ofDim[Cell](matrix.length, matrix.length)
        for {i <- newMatrix.indices
             j <- newMatrix.indices} {
            newMatrix(i)(j) = Cell(None)
        }
        this.copy(matrix = newMatrix)
    }

    def setInitialNinjaRows(): Field = {
        val newMatrix: Array[Array[Cell]] = Array.ofDim[Cell](this.matrix.length, this.matrix.length)
            for {row <- 0 until this.getAmountOfNinjaRows()
                 col <- this.matrix.indices} {
                val r: Random = new Random()
                val n: Int = r.nextInt(3)
                newMatrix(row)(col) = Cell(Some(Ninja(Weapon.createWeapon(n), 1)))
            }
            for {row <- this.matrix.length - this.getAmountOfNinjaRows() until this.matrix.length
                 col <- this.matrix.indices} {
                val r: Random = new Random()
                val n: Int = r.nextInt(3)
                newMatrix(row)(col) = Cell(Some(Ninja(Weapon.createWeapon(n), 2)))
            }
        this.copy(matrix = newMatrix)
    }

    def setFlag(player: Int, row : Int , col : Int): Field = {
        val newMatrix: Array[Array[Cell]] = Array.ofDim[Cell](this.matrix.length, this.matrix.length)
        val ninja: Ninja = this.matrix(row)(col).getNinja()

        newMatrix(row)(col) = Cell(Some(Ninja(Weapon.flag, ninja.playerId)))
        copy(matrix = newMatrix)
    }

    def getAmountOfNinjaRows(): Int = if (this.matrix.length / 3 < 2) 1 else 2

    def getPosition(n1: Ninja): (Int, Int) = {
        for(r <- matrix.indices)
            for(c <- matrix.indices)
                if (matrix(r)(c).optNinja.getOrElse("kein Ninja") == Ninja(n1.weapon, n1.playerId))
                    return (r, c)

        throw new NoSuchElementException()
    }

    def isNinjaOfPlayerAtPosition(player: Player, row: Int, col: Int): Boolean = {
        if (inBounds(row, col) && matrix(row)(col).exists() && getCellAtPosition(row, col).getNinja().playerId == player.id) {
            return true
        }
        false
    }

    def checkWalk(n1: Ninja, direction: Direction.direction): Field = {
        if(n1.weapon == Weapon.flag) throw new IllegalStateException()

        val pos: (Int, Int) = getPosition(n1)
        val newpos: (Int, Int) = add(pos, Direction.getDirectionIndex(direction))
        walkNinja(n1, newpos)
    }

    def walkNinja(ninja: Ninja, newpos: (Int, Int)): Field = {
        getCellAtPosition(newpos).optNinja match {
            case None =>  this.-(ninja, getPosition(ninja)).+(ninja, newpos)
            case Some(n2) => this.-(ninja, getPosition(ninja)).+(fight(ninja, n2), newpos)
        }
    }

    def -(ninja: Ninja, pos: (Int,Int)): Field = copy(matrix.updated(pos._1, matrix(pos._1).updated(pos._2, Cell(None))))

    def +(ninja: Ninja, pos: (Int,Int)): Field = copy(matrix.updated(pos._1, matrix(pos._1).updated(pos._2, Cell(Some(ninja)))))

    def fight(n1: Ninja, n2: Ninja): Ninja = {
        if(n1.weapon == n2.weapon) { //???? wenn Waffen gleich Spieler neue wählen lassen
            val r: Random = new Random()
            val n: Int = r.nextInt(3)
            val newN: Ninja = n1.copy(weapon = Weapon.createWeapon(n))
            if(weaponWeight(newN.weapon, n2.weapon)) return newN else return n2
        }
        if(weaponWeight(n1.weapon, n2.weapon)) n1 else n2
    }

    def weaponWeight(w1: Weapon.weapon, w2: Weapon.weapon) : Boolean = {
        w1 match {
            case Weapon.rock =>
                if (w2 == Weapon.paper) false else true
            case Weapon.scissors =>
                if (w2 == Weapon.rock) false else true
            case Weapon.paper =>
                if (w2 == Weapon.scissors) false else true
        }
    }

    def cellExists(row: Int, col: Int, direction: Direction.direction): Boolean = {
        val add1: (Int, Int) = add((row, col), Direction.getDirectionIndex(direction))
        if (!inBounds(add1) || (getCellAtPosition(add1).exists() && getCellAtPosition(add1).getNinja().playerId == getCellAtPosition(row, col).getNinja().playerId)) false else true
    }

    def add(base: (Int, Int), amount: (Int, Int)): (Int, Int) = (base._1 + amount._1, base._2 + amount._2)

    def inBounds(tuple: (Int, Int)): Boolean = tuple._1 < matrix.length && tuple._1 >= 0 && tuple._2 < matrix.length && tuple._2 >= 0
}