package stick.display

import stick.geometry.Point
import kotlin.test.Test
import kotlin.test.assertEquals

class StickDrawerTest {
    @Test
    fun testDraw() {
        val display = TestDisplay()
        StickDrawer().draw(Stick(), display)
        val expectedMatrix = listOf(
                listOf(0, 0, 0, 0, 0),
                listOf(0, 0, 0, 0, 0),
                listOf(1, 0, 0, 0, 0),
                listOf(0, 1, 0, 0, 0)
        )
        expectedMatrix.forEachIndexed { lineIndex, line -> line.forEachIndexed { columnIndex, pixel -> assertEquals(pixel, display.screenMatrix[lineIndex][columnIndex], "expected : $expectedMatrix, got : ${display.screenMatrix}") } }
    }

    class TestDisplay : Display {
        override val width: Int = 5
        override val height: Int = 4

        val screenMatrix = (0 until height).map { (0 until width).map { 0 }.toMutableList() }

        override fun draw(image: List<List<Int>>) {
            image.forEachIndexed { columnIndex, column -> column.forEachIndexed { lineIndex, pixel -> screenMatrix[lineIndex][columnIndex] = pixel } }
        }
    }

    class Stick : DisplayableItem {
        override val originCm = Point(6f, 3f)
        override val radiusCm = 3f
        override val angleRad = 2f / 3f * 3.14f
    }
}