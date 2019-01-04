package learning

import stick.brain.StickMoverBrain
import stick.environment.MovingStick
import stick.display.DisplayManager
import stick.geometry.Size

fun main() {
    MovingStick()
      .also { StickMoverBrain().control(it) }
      .also { DisplayManager(Size(800, 600)).display(it) }
}