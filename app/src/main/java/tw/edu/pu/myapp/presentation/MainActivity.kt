package tw.edu.pu.myapp.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import tw.edu.pu.myapp.R
import tw.edu.pu.myapp.databinding.ActivityMainBinding
import tw.edu.pu.myapp.library.DialogHelper
import tw.edu.pu.myapp.library.RequestHelper
import tw.edu.pu.myapp.library.fadeOut

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    private lateinit var navHostFragment: FragmentContainerView
    lateinit var viewModel: WeatherViewModel

    private lateinit var requestHelper: RequestHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreen()

        navHostFragment = findViewById(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        requestHelper = RequestHelper(this)
        requestHelper.requestLocation()
        if (!requestHelper.checkGPS()) DialogHelper(this)
            .showDialog(getString(R.string.no_GPS_title), getString(R.string.no_GPS_message), false)

        viewModel = WeatherViewModel()
        viewModel.loadWeatherInfo(24.2163967,120.5854669)
    }

    private fun splashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.splashScreen.fadeOut(1000L, 0L)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.splashScreen.visibility = View.GONE
            }, 1000L)
        }, 1000L)
    }
}