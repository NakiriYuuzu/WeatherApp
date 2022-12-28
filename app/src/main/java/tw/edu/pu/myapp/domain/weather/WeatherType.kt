package tw.edu.pu.myapp.domain.weather

import androidx.annotation.DrawableRes
import tw.edu.pu.myapp.R

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int
) {
    object Sunny : WeatherType(
        weatherDesc = "ClearSky",
        iconRes = R.drawable.sunny
    )

    object Cloudy : WeatherType(
        weatherDesc = "Cloudy",
        iconRes = R.drawable.cloudy
    )

    object Rainy : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rain
    )

    object Snowy : WeatherType(
        weatherDesc = "Snowy",
        iconRes = R.drawable.snow
    )

    object Thunder : WeatherType(
        weatherDesc = "Thunder",
        iconRes = R.drawable.thunder
    )

    object Thunderstorm : WeatherType(
        weatherDesc = "Thunderstorm",
        iconRes = R.drawable.thunderstorm
    )

    companion object {
        fun fromWeather(code: Int): WeatherType {
            return when(code) {
                in 200..233 -> Thunderstorm
                in 300..302 -> Rainy
                in 500..549 -> Rainy
                in 600..623 -> Snowy
                800 -> Sunny
                in 801..804 -> Cloudy
                in 900..904 -> Thunder
                else -> Sunny
            }
        }
    }
}