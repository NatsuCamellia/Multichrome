package net.natsucamellia.multichrome.model

import androidx.compose.ui.graphics.Color

class Palette(
    val model: String,
    rgbList: List<List<Int>>
) {
    var colors: List<Color>
        private set

    init {
        assert(rgbList.size == 5)
        colors = rgbList.map { rgb ->
            assert(rgb.size == 3)
            Color(red = rgb[0], green = rgb[1], blue = rgb[2])
        }
    }

    companion object {
        val Empty = Palette(
            model = "default",
            rgbList = listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))
        )
    }
}