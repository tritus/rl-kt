package stick.display

import stick.geometry.Size
import kotlin.test.Test
import kotlin.test.assertTrue

class DisplayManagerTest {
    @Test
    fun testDisplay1Image() {
        val display = display5x5Stub()
        val itemToDisplay = DisplayItemStub()
        val displayManager = DisplayManager(display, StickDrawer())
        displayManager.display(itemToDisplay)
        assertTrue { display.screen[3][2] == 1 && display.screen[4][4] == 1 }
        displayManager.recycle()
    }

    @Test
    fun testDisplay2Images() {
        val display = display5x5Stub()
        val itemToDisplay = DisplayItemStub()
        val displayManager = DisplayManager(display, StickDrawer())
        displayManager.display(itemToDisplay)
        itemToDisplay.drawSinglePixelAt(3, 3)
        assertTrue { display.screen[3][2] == 0 && display.screen[4][4] == 0 && display.screen[3][3] == 1 }
        displayManager.recycle()
    }

    private fun display5x5Stub(): DisplayStub {
        return DisplayStub(Size(5, 5))
    }

    class DisplayItemStub : DisplayableItem {
        private var pixels = listOf(
                3 to 2,
                4 to 4
        )

        fun drawSinglePixelAt(row: Int, column: Int) {
            pixels = listOf(row to column)
        }
    }

    class DisplayStub(size: Size<Int>) : Display {
        val screen = (1 until size.width)
                .map {
                    (1 until size.height)
                            .map { 0 }
                            .toMutableList()
                }
                .toMutableList()
    }
}