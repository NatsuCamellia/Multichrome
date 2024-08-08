package net.natsucamellia.multichrome.data

import androidx.compose.ui.graphics.toArgb
import net.natsucamellia.multichrome.network.ColormindApiService
import net.natsucamellia.multichrome.model.Palette
import net.natsucamellia.multichrome.network.PostBody

interface MultichromeRepository {
    suspend fun getRandomPalette(model: String): Palette
    suspend fun getPaletteWithSuggestion(model: String, palette: Palette, selectedIndex: List<Boolean>): Palette
    suspend fun getModelList(): List<String>
}

class NetworkMultichromeRepository(
    private val colormindApiService: ColormindApiService
) : MultichromeRepository {
    override suspend fun getRandomPalette(model: String): Palette {
        val postBody = PostBody(model = model)
        val response = colormindApiService.getRandomPalette(postBody)
        return Palette(
            model = model,
            rgbList = response.result
        )
    }

    override suspend fun getPaletteWithSuggestion(
        model: String,
        palette: Palette,
        selectedIndex: List<Boolean>
    ): Palette {
        val input: List<Any> = palette.colors.mapIndexed { index, it ->
            if (selectedIndex[index]) it.toArgb().toRgbList() else "N"
        }.toList()
        println(input)
        val postBody = PostBody(input = input, model = model)
        val response = colormindApiService.getRandomPalette(postBody)
        return Palette(
            model = model,
            rgbList = response.result
        )
    }

    override suspend fun getModelList(): List<String> {
        val response = colormindApiService.getModelList()
        return response.result
    }
}

fun Int.toRgbList(): List<Int> {
    var mask = 255
    val b = mask.and(this)
    mask *= 256
    val g = mask.and(this).div(256)
    mask *= 256
    val r = mask.and(this).div(256).div(256)
    return listOf(r, g, b)
}