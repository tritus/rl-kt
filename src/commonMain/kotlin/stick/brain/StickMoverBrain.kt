package stick.brain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import stick.brain.model.BrainModel
import stick.lifecycle.RecyclableObject

class StickMoverBrain(val scope: CoroutineScope) : RecyclableObject { // TODO Implement RL algorythm

    private var isRecycling = false
    private val model: BrainModel = DRELUDModel()
    private val reactionTimeInMS = 300L

    fun control(stick: MovableStick) {
        scope.launch {
            while (!isRecycling) {
                val dx = model.bestActionFrom(stick.xOrigin, stick.angle)
                stick.moveByXCm(dx)
                delay(reactionTimeInMS)
            }
        }
    }

    override fun recycle() {
        isRecycling = true
    }
}