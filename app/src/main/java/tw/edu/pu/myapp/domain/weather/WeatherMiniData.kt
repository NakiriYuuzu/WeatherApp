package tw.edu.pu.myapp.domain.weather

data class WeatherMiniData(
    val datetime: String,
    val temperature: Int,
    val weatherType: WeatherType
)