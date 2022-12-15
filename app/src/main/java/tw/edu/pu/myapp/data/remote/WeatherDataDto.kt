package tw.edu.pu.myapp.data.remote

import com.squareup.moshi.Json

data class WeatherDataDto(
    @field:Json(name = "main")
    val main: Main,
    @field:Json(name = "weather")
    val weather: List<Weather>,
    @field:Json(name = "wind")
    val wind: Wind,
    val visibility: Int,
    val dt_txt: String
)