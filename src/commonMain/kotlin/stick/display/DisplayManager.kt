package stick.display

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DisplayManager(scope: CoroutineScope, private val display: Display, val itemDrawer: ItemDrawer) {

    private var isRecycling = false
    private val displayableItems = mutableListOf<DisplayableItem>()

    init {
        scope.launch {
            while (!isRecycling) {
                refresh()
                delay(40)
            }
        }
    }

    fun display(displayableItem: DisplayableItem) {
        displayableItems.add(displayableItem)
    }

    private fun refresh() {
        displayableItems.forEach { displayItem(it) }
    }

    private fun displayItem(item: DisplayableItem) {
        itemDrawer.draw(item, display)
    }

    fun recycle() {
        isRecycling = true
    }
}