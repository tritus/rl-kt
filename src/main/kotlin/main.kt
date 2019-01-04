package learning

import brain.StickMoverBrain
import environment.MovingStick
import display.DisplayManager
import geometry.Size

fun main() {
    MovingStick()
      .also { StickMoverBrain().control(it) }
      .also { DisplayManager(Size(800, 600)).display(it) }
}