package stick.brain

import kotlinx.coroutines.delay
import stick.brain.model.BrainModel
import stick.runInScope

class StickMoverBrain { // TODO Implement RL algorythm

    private val model: BrainModel = DRELUDModel()
    private val reactionTimeInMS = 300L

    fun control(stick: MovableStick) {
        runInScope {
            val dx = model.bestActionFrom(stick.xOrigin, stick.angle)
            stick.moveByXCm(dx)
            delay(reactionTimeInMS)
        }
    }
}