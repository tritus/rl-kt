package display

import kotlin.test.Test
import display.DisplayManager
import geometry.Size

class DisplayManagerTest {
    @Test
    fun simpleTest() {
        val display = DisplayManager(Size(4, 4))
        print("hourra")
    }
}