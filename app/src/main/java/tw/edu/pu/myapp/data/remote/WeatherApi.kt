package tw.edu.pu.myapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/forecast?")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
    ): WeatherDto

    @GET("data/2.5/forecast?")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") appid: String,
    ): WeatherDto

    companion object {
        val instance: WeatherApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }
}