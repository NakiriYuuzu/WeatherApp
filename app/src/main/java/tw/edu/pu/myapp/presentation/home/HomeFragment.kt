package tw.edu.pu.myapp.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import tw.edu.pu.myapp.R
import tw.edu.pu.myapp.databinding.FragmentHomeBinding
import tw.edu.pu.myapp.library.DialogHelper
import tw.edu.pu.myapp.presentation.MainActivity
import tw.edu.pu.myapp.presentation.WeatherViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding ?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel

    private lateinit var dialogHelper: DialogHelper

    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)


        initView()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = homeAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is WeatherViewModel.WeatherState.Loading -> {
                        dialogHelper.showLoading()
                    }

                    is WeatherViewModel.WeatherState.Success -> {
                        dialogHelper.hideLoading()
                        binding.titleCity.text = it.data.city
                        binding.titleCountry.text = it.data.country
                        binding.titleTemp.text = "${it.data.temperature.toInt()}°C"
                        binding.titleState.text = "  ${it.data.weatherType.weatherDesc}"
                        binding.tvLowHigh.text = it.data.lowHigh
                        binding.tvFeelLike.text = "${it.data.temperature.toInt() - 2}°C"
                        binding.tvHumid.text = "${it.data.humidity}%"
                        binding.tvWind.text = "${it.data.windSpeed} km/h"
                        binding.tvPressure.text = "${it.data.pressure} hPa"
                        binding.tvVisibility.text = "${it.data.visibility} km"
                        binding.imageView.setImageResource(it.data.weatherType.iconRes)
                        homeAdapter.differ.submitList(it.data.weatherMiniData)
                    }

                    is WeatherViewModel.WeatherState.Error -> {
                        dialogHelper.hideLoading()
                        dialogHelper.showDialog("Error", it.message, false)
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun initView() {
        dialogHelper = DialogHelper(requireActivity())
        homeAdapter = HomeAdapter()

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel = (activity as MainActivity).viewModel
            initViewModel()
        }, 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}