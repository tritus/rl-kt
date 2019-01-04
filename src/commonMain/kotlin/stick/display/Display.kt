package stick.display

interface Display {
    val width: Int
    val height: Int

    fun draw(image: List<List<Int>>)
}