package stick.brain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import stick.brain.model.BrainModel
import stick.brain.policy.BrainPolicy
import stick.brain.policy.EpsilonGreedyPolicy
import stick.lifecycle.RecyclableObject

class StickMoverBrain : RecyclableObject { // TODO Implement RL algorythm

    private var isRecycling = false
    private val model: BrainModel = DRELUDModel()
    private val policy: BrainPolicy = EpsilonGreedyPolicy(0.01f)
    private val reactionTimeInMS = 300L

    fun control(stick: MovableStick, scope: CoroutineScope) {
        scope.launch {
            while (!isRecycling) {
                val actionsRating = model.bestActionFrom(stick.xOrigin, stick.angle)
                val actionToTake = policy.chooseActionFrom(actionsRating)
                dxFrom(actionToTake).let { stick.moveByXCm(it) }
                delay(reactionTimeInMS)
            }
        }
    }

    private fun dxFrom(actionIndex: Int): Float {
        return (actionIndex - 1).toFloat() * 15f
    }

    override fun recycle() {
        isRecycling = true
    }
}