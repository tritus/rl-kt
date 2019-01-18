package stick.brain.policy

class EpsilonGreedyPolicy(epsilon: Float) : BrainPolicy {

    private val maxRandomizedNumber = (1f / epsilon).toInt()

    override fun chooseActionFrom(actionsRatings: List<Float>): Int {
        return (0 until maxRandomizedNumber)
                .random()
                .takeIf { it == 0 }
                ?.let { randomChoice(actionsRatings) }
                ?: greedyChoice(actionsRatings)
    }

    private fun randomChoice(actionsRatings: List<Float>): Int {
        return (0 until actionsRatings.size).random()
    }

    private fun greedyChoice(actionsRatings: List<Float>): Int {
        return actionsRatings.foldIndexed(0) { index, bestActionIndex, actionValue ->
            if (actionValue > actionsRatings[bestActionIndex]) {
                index
            } else {
                bestActionIndex
            }
        }
    }
}
