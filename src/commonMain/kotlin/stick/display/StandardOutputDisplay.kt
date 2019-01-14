package stick.display

import stick.geometry.Size

class StandardOutputDisplay(size: Size<Int>) : Display {
    override val width: Int = size.width
    override val height: Int = size.height

    override fun draw(image: List<List<Int>>) {
        clearScreen()
        drawFrame(image)
    }

    private fun drawFrame(image: List<List<Int>>) {
        val charImage = (0 until height).fold("") { outputImageChar, lineIndex ->
            val charLine = (0 until width).fold("") { outputLineChar, columnIndex ->
                val pixelValue = image[columnIndex][lineIndex]
                val pixelChar = if (pixelValue == 0) " " else "o"
                "$outputLineChar$pixelChar"
            }
            "$outputImageChar\n$charLine"
        }
        print(charImage)
    }

    private fun clearScreen() {
        println("\u001Bc")
    }
}