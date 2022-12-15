package tw.edu.pu.myapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tw.edu.pu.myapp.common.Resource
import tw.edu.pu.myapp.data.remote.WeatherDto
import tw.edu.pu.myapp.data.repository.WeatherRepository
import tw.edu.pu.myapp.domain.weather.WeatherData
import tw.edu.pu.myapp.domain.weather.WeatherInfo

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
): ViewModel() {

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Empty)
    val state = _state.asStateFlow()

    fun loadWeatherInfo(lat: Double, lon: Double) {
        viewModelScope.launch {
            repeat(99999) { i ->
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

                Log.e("TAG", "Repeating: $i")
                delay(10000)
            }
        }
    }

    sealed class WeatherState {
        data class Success(val data: WeatherData) : WeatherState()
        data class Error(val message: String) : WeatherState()
        object Loading: WeatherState()
        object Empty: WeatherState()
    }
}