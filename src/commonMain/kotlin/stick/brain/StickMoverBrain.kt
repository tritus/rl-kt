package stick.brain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import stick.brain.model.BrainModel
import stick.lifecycle.RecyclableObject

class StickMoverBrain : RecyclableObject { // TODO Implement RL algorythm

    private var isRecycling = false
    private val model: BrainModel = DRELUDModel()
    private val reactionTimeInMS = 300L

    fun control(stick: MovableStick, scope: CoroutineScope) {
        scope.launch {
            while (!isRecycling) {
                val bestAction = model.bestActionFrom(stick.xOrigin, stick.angle)
                dxFrom(bestAction).let { stick.moveByXCm(it) }
                delay(reactionTimeInMS)
            }
        }
    }

    private fun dxFrom(bestAction: List<Float>): Float {
        return bestAction.foldIndexed(0f to 0f) { index, actionPair, actionValue ->
            if (actionValue > actionPair.second) {
                (index - 1).toFloat() * 15f to actionValue
            } else {
                actionPair
            }
        }.let { it.first }
    }

    override fun recycle() {
        isRecycling = true
    }
}