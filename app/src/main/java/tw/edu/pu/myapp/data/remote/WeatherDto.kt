package tw.edu.pu.myapp.data.remote

import com.squareup.moshi.Json

data class WeatherDto(
    val cod: String,
    val message: Int,
    val cnt: Int,
    @field:Json(name = "list")
    val list: List<WeatherDataDto>,
    @field:Json(name = "city")
    val city: CityDto
)