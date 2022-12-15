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
                in 0..300 -> Thunder
                in 301..500 -> Thunderstorm
                in 501..600 -> Rainy
                in 601..700 -> Snowy
                in 701..799 -> Cloudy
                else -> Sunny
            }
        }
    }
}