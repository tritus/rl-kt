package stick.math

class Matrix(vararg linesInput: List<Float>) {
    private val lines: List<List<Float>> = linesInput.toList()

    fun times(vector: List<Float>): List<Float> {
        return lines.map { line -> line.zip(vector).fold(0f) { value, pair -> value + pair.first * pair.second } }
    }
}