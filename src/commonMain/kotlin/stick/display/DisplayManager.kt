package stick.display

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import stick.lifecycle.RecyclableObject

class DisplayManager(scope: CoroutineScope, private val display: Display, private val itemDrawer: ItemDrawer, private val refreshTimeInMs: Long) : RecyclableObject {

    private var isRecycling = false
    private val displayableItems = mutableListOf<DisplayableItem>()

    init {
        scope.launch {
            while (!isRecycling) {
                refresh()
                delay(refreshTimeInMs)
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

    override fun recycle() {
        isRecycling = true
    }
}