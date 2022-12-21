package tw.edu.pu.myapp.presentation.search

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tw.edu.pu.myapp.R
import tw.edu.pu.myapp.common.Constants
import tw.edu.pu.myapp.databinding.FragmentSearchBinding
import tw.edu.pu.myapp.domain.weather.WeatherData
import tw.edu.pu.myapp.library.DialogHelper
import tw.edu.pu.myapp.library.ShareHelper
import tw.edu.pu.myapp.library.ViewHelper
import tw.edu.pu.myapp.presentation.MainActivity
import tw.edu.pu.myapp.presentation.WeatherViewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel

    private lateinit var searchAdapter: SearchAdapter
    private lateinit var dialogHelper: DialogHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        initView()
        initViewModel()
        setupRecyclerView()

        var job: Job? = null
        binding.textInput.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.getWeatherByCity(editable.toString())
                        viewModel.loadFavoriteCitiesWeatherInfo()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = searchAdapter
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is WeatherViewModel.WeatherState.Loading -> {
                        dialogHelper.showLoading()
                    }
                    is WeatherViewModel.WeatherState.Success -> {
                        dialogHelper.hideLoading()
                        val list = listOf(it.data)
                        searchAdapter.differ.submitList(list)
                    }
                    is WeatherViewModel.WeatherState.Error -> {
                        dialogHelper.hideLoading()
                        dialogHelper.showDialog(getString(R.string.error_title), it.message, false)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun initView() {
        ViewHelper(requireActivity()).setupUI(binding.root)
        viewModel = (activity as MainActivity).viewModel
        dialogHelper = DialogHelper(requireActivity())

        searchAdapter = SearchAdapter(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(data: WeatherData) {
                val shareHelper = ShareHelper(requireContext())
                val oldCity = shareHelper.get<MutableList<String>>(Constants.KEY_CITY_LIST)
                Log.e("TAG", "oldCity: $oldCity")

                dialogHelper.showDialog(
                    getString(R.string.title_add_to_favorites),
                    getString(R.string.message_add_to_favorites),
                    true,
                    getString(R.string.positive_button),
                    getString(R.string.negative_button),
                    object : DialogHelper.OnDialogListener {
                        override fun onPositiveClick(dialog: DialogInterface?, which: Int) {
                            val newCity = mutableListOf<String>()

                            if (oldCity != null) {
                                if (oldCity.contains(data.city)) {
                                    Snackbar.make(
                                        binding.root,
                                        getString(R.string.error_toAdd),
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                } else {
                                    oldCity.add(data.city)
                                    shareHelper.put(oldCity, Constants.KEY_CITY_LIST)

                                    Snackbar.make(
                                        binding.root,
                                        getString(R.string.toast_add_to_favorites),
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                newCity.add(data.city)
                                shareHelper.put(newCity, Constants.KEY_CITY_LIST)

                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.toast_add_to_favorites),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }

                            dialog?.dismiss()
                        }

                        override fun onNegativeClick(dialog: DialogInterface?, which: Int) {
                            dialog?.dismiss()
                        }
                    }
                )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}