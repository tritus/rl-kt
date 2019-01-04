package stick.display

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DisplayManager(private val display: Display, val itemDrawer: ItemDrawer) {

    private var isRecycling = false
    private val displayableItems = mutableListOf<DisplayableItem>()

    init {
        GlobalScope.launch {
            while (!isRecycling) {
                refresh()
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