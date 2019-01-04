package stick

import stick.brain.StickMoverBrain
import stick.display.DisplayImpl
import stick.environment.MovingStick
import stick.display.DisplayManager
import stick.geometry.Size

fun main() {
    MovingStick()
      .also { StickMoverBrain().control(it) }
      .also { DisplayManager(DisplayImpl(Size(800, 600))).display(it) }
}