package tw.edu.pu.myapp.data.repository

import tw.edu.pu.myapp.common.Constants
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
                    appid = Constants.API_KEY
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    suspend fun getWeatherByCity(city: String): Resource<WeatherInfo> {
        Resource.Loading(data = true)
        return try {
            Resource.Success(
                data = WeatherApi.instance.getWeatherByCity(
                    city = city,
                    appid = Constants.API_KEY
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}