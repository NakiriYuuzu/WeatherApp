package tw.edu.pu.myapp.domain.weather

data class WeatherData(
    val datetime: String,
    val temperature: Double,
    val lowHigh: String,
    val pressure: Int,
    val windSpeed: Double,
    val humidity: Int,
    val visibility: Int,
    val city: String,
    val country: String,
    var isExpand: Boolean,
    val weatherType: WeatherType,
    val weatherMiniData: List<WeatherMiniData>
)