package de.htwg.se.ninja.view

import de.htwg.se.ninja.util.Observer

trait UIInterface extends Observer {

        def update: Unit


}
