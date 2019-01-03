import brain.StickMoverBrain
import environment.MovingStick
import display.DisplayManager
import geometry.Size

fun main(args: Array<String>) {
    MovingStick()
      .also { StickMoverBrain().control(it) }
      .also { DisplayManager(Size(800, 600)).display(it) }
}