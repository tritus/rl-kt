package stick.brain.policy

interface BrainPolicy {
    fun chooseActionFrom(actionsRatings: List<Float>): Int
}
