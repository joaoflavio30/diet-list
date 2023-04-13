package com.example.dietplan.ui.dailygoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dietplan.R
import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.databinding.FragmentDailyGoalBinding
import com.example.dietplan.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyGoalFragment : Fragment() {
    private var _binding: FragmentDailyGoalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.submitBtn.setOnClickListener { inputsVerifications() }
    }

    private fun inputsVerifications() {
        if (binding.proteinText.text.isNullOrEmpty() || binding.carbText.text.isNullOrEmpty() || binding.fatText.text.isNullOrEmpty() || binding.caloriesText.text.isNullOrEmpty()
        ) {
            Toast.makeText(requireContext(), "Please put the fields correctly", Toast.LENGTH_SHORT).show()
        } else {
            val dailyGoal = DailyGoal(
                binding.caloriesText.text.toString().toDouble(),
                binding.proteinText.text.toString().toDouble(),
                binding.carbText.text.toString().toDouble(),
                binding.fatText.text.toString().toDouble(),
                binding.waterText.text.toString().toInt()
            )
            viewModel.bindDailyGoal(dailyGoal)
            findNavController().navigate(R.id.homeFragment)
        }
    }
}
