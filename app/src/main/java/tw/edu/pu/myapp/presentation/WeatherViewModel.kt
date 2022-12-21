package tw.edu.pu.myapp.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tw.edu.pu.myapp.common.Constants
import tw.edu.pu.myapp.common.Resource
import tw.edu.pu.myapp.data.repository.WeatherRepository
import tw.edu.pu.myapp.domain.weather.WeatherData
import tw.edu.pu.myapp.library.ShareHelper

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository(),
    app: Application
): ViewModel() {

    private val shareHelper = ShareHelper(app)

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Empty)
    val state = _state.asStateFlow()

    fun loadWeatherInfo(lat: Double, lon: Double) {
        viewModelScope.launch {
            // repeat(99999) { i ->
                repository.getWeather(lat, lon).let {
                    when (it) {
                        is Resource.Loading -> {
                            _state.value = WeatherState.Loading
                        }
                        is Resource.Success -> {
                            _state.value = WeatherState.Success(it.data?.currentWeatherData!!)
                        }
                        is Resource.Error -> {
                            _state.value = WeatherState.Error(it.message!!)
                        }
                    }
                }

                // Log.e("TAG", "Repeating: $i")
                // delay(10000)
            // }
        }
    }

    fun getWeatherByCity(city: String) {
        viewModelScope.launch {
            try {
                repository.getWeatherByCity(city).let {
                    when (it) {
                        is Resource.Loading -> {
                            _state.value = WeatherState.Loading
                        }
                        is Resource.Success -> {
                            _state.value = WeatherState.Success(it.data?.currentWeatherData!!)
                        }
                        is Resource.Error -> {
                            _state.value = WeatherState.Error(it.message!!)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = WeatherState.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }

    private val _cityState = MutableStateFlow<FavoriteWeatherState>(FavoriteWeatherState.Empty)
    val cityState = _cityState.asStateFlow()

    fun loadFavoriteCitiesWeatherInfo() {
        viewModelScope.launch {
            val cityList = shareHelper.get<MutableList<String>>(Constants.KEY_CITY_LIST)
            val data = mutableListOf<WeatherData>()

            try {
                cityList?.forEach {
                    repository.getWeatherByCity(it).let { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                _cityState.value = FavoriteWeatherState.Loading
                            }

                            is Resource.Success -> {
                                data.add(resource.data?.currentWeatherData!!)
                            }

                            is Resource.Error -> {
                                _cityState.value = FavoriteWeatherState.Error(resource.message!!)
                            }
                        }
                    }
                }

                _cityState.value = FavoriteWeatherState.Success(data)

            } catch (e: Exception) {
                e.printStackTrace()
                _cityState.value = FavoriteWeatherState.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }

    sealed class FavoriteWeatherState {
        object Loading: FavoriteWeatherState()
        data class Success(val data: MutableList<WeatherData>): FavoriteWeatherState()
        data class Error(val message: String): FavoriteWeatherState()
        object Empty: FavoriteWeatherState()
    }

    sealed class WeatherState {
        data class Success(val data: WeatherData) : WeatherState()
        data class Error(val message: String) : WeatherState()
        object Loading: WeatherState()
        object Empty: WeatherState()
    }
}