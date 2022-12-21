package tw.edu.pu.myapp.presentation.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import tw.edu.pu.myapp.R
import tw.edu.pu.myapp.databinding.FragmentFavoritesBinding
import tw.edu.pu.myapp.library.DialogHelper
import tw.edu.pu.myapp.presentation.MainActivity
import tw.edu.pu.myapp.presentation.WeatherViewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var dialogHelper: DialogHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesBinding.bind(view)

        initView()
        initButton()
        initViewModel()
        setupRecyclerView()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.cityState.collect {
                when (it) {
                    is WeatherViewModel.FavoriteWeatherState.Loading -> {
                        dialogHelper.showLoading()
                    }
                    is WeatherViewModel.FavoriteWeatherState.Success -> {
                        dialogHelper.hideLoading()
                        favoritesAdapter.differ.submitList(it.data)
                        Log.e("TAG", "initViewModel: ${it.data.size}")
                    }
                    is WeatherViewModel.FavoriteWeatherState.Error -> {
                        dialogHelper.hideLoading()
                        Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            adapter = favoritesAdapter
        }
    }

    private fun initButton() {
        favoritesAdapter = FavoritesAdapter()
    }

    private fun initView() {
        viewModel = (activity as MainActivity).viewModel
        viewModel.loadFavoriteCitiesWeatherInfo()
        dialogHelper = DialogHelper(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}