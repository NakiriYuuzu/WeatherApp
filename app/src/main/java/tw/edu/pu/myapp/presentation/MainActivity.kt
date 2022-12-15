package tw.edu.pu.myapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import tw.edu.pu.myapp.R
import tw.edu.pu.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    private lateinit var navHostFragment: FragmentContainerView
    lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = findViewById(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        viewModel = WeatherViewModel()
        viewModel.loadWeatherInfo(24.2163967,120.5854669)
    }
}