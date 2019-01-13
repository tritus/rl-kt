package stick.math

interface ODESolver {
    fun nextValuesFrom(x0: Float, derivativeX0: Float, doubleDerivativeFormula: (Float, Float) -> Float, expectedT: Float): List<Float>
}
