package tw.edu.pu.myapp.domain.repository

import tw.edu.pu.myapp.common.Resource
import tw.edu.pu.myapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): Resource<WeatherInfo>
}