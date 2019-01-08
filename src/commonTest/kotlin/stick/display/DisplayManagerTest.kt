package stick.display

import stick.geometry.Point
import stick.geometry.Size
import stick.waitFor
import kotlin.test.Test
import kotlin.test.assertTrue

class DisplayManagerTest {
    @Test
    fun testDisplay1Image() {
        val display = display5x5Stub()
        val itemToDisplay = DisplayItemStub()
        val displayManager = DisplayManager(display, StickDrawer())
        displayManager.display(itemToDisplay)
        assertTrue { display.screen[3][2] == 1 && display.screen[4][4] == 0 }
        displayManager.recycle()
    }

    @Test
    fun testDisplay2Images() {
        val display = display5x5Stub()
        val itemToDisplay = DisplayItemStub()
        val displayManager = DisplayManager(display, StickDrawer())
        displayManager.display(itemToDisplay)
        itemToDisplay.originCm = Point(12.1f, 12.1f)
        waitFor(1000)
        assertTrue { display.screen[3][2] == 0 && display.screen[4][4] == 0 && display.screen[3][3] == 1 }
        displayManager.recycle()
    }

    private fun display5x5Stub(): DisplayStub {
        return DisplayStub(Size(5, 5))
    }

    class DisplayItemStub : DisplayableItem {
        override var originCm: Point<Float> = Point(12.1f, 8.1f)
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
            image.forEachIndexed { lineIndex, line -> line.forEachIndexed { columnIndex, pixelValue -> screen[columnIndex][lineIndex] = pixelValue } }
        }
    }
}