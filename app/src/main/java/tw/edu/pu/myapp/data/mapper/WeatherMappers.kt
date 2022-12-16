package tw.edu.pu.myapp.data.mapper

import android.annotation.SuppressLint
import tw.edu.pu.myapp.data.remote.WeatherDataDto
import tw.edu.pu.myapp.data.remote.WeatherDto
import tw.edu.pu.myapp.domain.weather.*
import java.text.SimpleDateFormat
import java.util.*

fun WeatherDataDto.toWeatherDetail(): WeatherDetail {
    return WeatherDetail(
        datetime = dt_txt,
        temperature = toCelsius(main.temp),
        lowHigh = "${toCelsius(main.temp_min).toInt()}°C / ${toCelsius(main.temp_max).toInt()}°C",
        pressure = main.pressure,
        humidity = main.humidity,
        visibility = visibility,
        windSpeed = wind.speed,
        weatherId = weather[0].id,
    )
}

@SuppressLint("SimpleDateFormat")
fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val currentTime = Calendar.getInstance().timeInMillis
    val weatherDetailList = list.map {
        val weatherTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(it.dt_txt)!!.time
        if (weatherTime <= currentTime) it.toWeatherDetail() else null
    }

    val weatherDetailData = weatherDetailList.filterNotNull()
    val miniDataList: MutableList<WeatherMiniData> = mutableListOf()

    weatherDetailData.forEach {
        val miniData = WeatherMiniData(
            datetime = it.datetime,
            temperature = it.temperature.toInt(),
            weatherType = WeatherType.fromWeather(it.weatherId)
        )

        miniDataList.add(miniData)
    }

    val currentData = weatherDetailList[0]

    return WeatherInfo(
        currentWeatherData = WeatherData(
            datetime = currentData!!.datetime,
            temperature = currentData.temperature,
            lowHigh = currentData.lowHigh,
            pressure = currentData.pressure,
            humidity = currentData.humidity,
            visibility = currentData.visibility,
            windSpeed = currentData.windSpeed,
            city = city.name,
            country = city.country,
            weatherType = WeatherType.fromWeather(currentData.weatherId),
            weatherMiniData = miniDataList
        )
    )
}

fun toCelsius(kelvin: Double): Double {
    return kelvin - 273.15
}