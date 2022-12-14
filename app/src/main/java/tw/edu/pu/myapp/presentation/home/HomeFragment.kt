package tw.edu.pu.myapp.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import tw.edu.pu.myapp.R
import tw.edu.pu.myapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding ?= null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}