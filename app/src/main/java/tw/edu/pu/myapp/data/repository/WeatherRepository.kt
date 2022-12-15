package tw.edu.pu.myapp.data.repository

import tw.edu.pu.myapp.common.Resource
import tw.edu.pu.myapp.data.mapper.toWeatherInfo
import tw.edu.pu.myapp.data.remote.WeatherApi
import tw.edu.pu.myapp.domain.weather.WeatherInfo

class WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Resource<WeatherInfo> {
        Resource.Loading(data = true)
        return try {
            Resource.Success(
                data = WeatherApi.instance.getWeather(
                    lat = lat,
                    lon = lon,
                    appid = "e9bead3ce6662fb07b5f377564f90b93"
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}