package stick.display

import kotlinx.coroutines.delay
import stick.geometry.Point
import stick.geometry.Size
import stick.runInScope
import kotlin.test.Test
import kotlin.test.assertEquals

class DisplayManagerTest {
    @Test
    fun testDisplay1Image() {
        runInScope {
            val display = display5x5Stub()
            val itemToDisplay = DisplayItemStub()
            val displayManager = DisplayManager(this, display, StickDrawer())
            displayManager.display(itemToDisplay)
            delay(100)
            assertEquals(1, display.screen[4][1], "screen : ${display.screen}")
            assertEquals(0, display.screen[4][4], "screen : ${display.screen}")
            displayManager.recycle()
        }
    }

    @Test
    fun testDisplay2Images() {
        runInScope {
            val display = display5x5Stub()
            val itemToDisplay = DisplayItemStub()
            val displayManager = DisplayManager(this, display, StickDrawer())
            displayManager.display(itemToDisplay)
            itemToDisplay.originCm = Point(12.1f, 12.1f)
            delay(100)
            assertEquals(0, display.screen[3][2], "screen : ${display.screen}")
            assertEquals(0, display.screen[4][4], "screen : ${display.screen}")
            assertEquals(1, display.screen[2][1], "screen : ${display.screen}")
            displayManager.recycle()
        }
    }

    private fun display5x5Stub(): DisplayStub {
        return DisplayStub(Size(5, 5))
    }

    class DisplayItemStub : DisplayableItem {
        override var originCm: Point<Float> = Point(24.1f, 12.1f)
        override val radiusCm: Float = 0f
        override val angleRad: Float = 0f
    }

    class DisplayStub(size: Size<Int>) : Display {
        val screen = (0 until size.width)
                .map {
                    (0 until size.height)
                            .map { 0 }
                            .toMutableList()
                }
        override val width: Int = screen.size

        override val height: Int = screen.first().size

        override fun draw(image: List<List<Int>>) {
            image.forEachIndexed { columnIndex, column -> column.forEachIndexed { lineIndex, pixelValue -> screen[columnIndex][lineIndex] = pixelValue } }
        }
    }
}