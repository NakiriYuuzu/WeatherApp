package tw.edu.pu.myapp.domain.weather

data class WeatherMiniData(
    val datetime: String,
    val temperature: Double,
    val weatherType: WeatherType
)