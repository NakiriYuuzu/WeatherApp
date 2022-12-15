package tw.edu.pu.myapp.domain.weather

data class WeatherDetail(
    val datetime: String,
    val temperature: Double,
    val lowHigh: String,
    val pressure: Int,
    val windSpeed: Double,
    val humidity: Int,
    val visibility: Int,
    val weatherId: Int,
)