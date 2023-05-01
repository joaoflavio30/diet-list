package com.joaoflaviofreitas.dietplan.feature.dailygoal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.feature.dailygoal.databinding.FragmentDailyGoalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyGoalFragment : Fragment(), DailyGoalContract.DailyGoalFragment {
    private var _binding: FragmentDailyGoalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DailyGoalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        checkIfUserMakeDailyGoal()
        _binding = FragmentDailyGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListener()
    }

    override fun checkFields(): Boolean {
        val protein = binding.proteinText.text.toString()
        val carb = binding.carbText.text.toString()
        val fat = binding.fatText.text.toString()
        val calories = binding.caloriesText.text.toString()
        val water = binding.waterText.text.toString()
        var valid = false

        when {
            protein.isEmpty() || !protein.isDigitsOnly() -> {
                binding.proteinText.setError("Enter Protein!", null)
                binding.proteinText.requestFocus()
            }
            carb.isEmpty() || !carb.isDigitsOnly() -> {
                binding.carbText.setError("Enter Carb!", null)
                binding.carbText.requestFocus()
            }
            fat.isEmpty() || !fat.isDigitsOnly() -> {
                binding.fatText.setError("Enter Fat!", null)
                binding.fatText.requestFocus()
            }
            calories.isEmpty() || !calories.isDigitsOnly() -> {
                binding.caloriesText.setError("Enter Calories!", null)
                binding.caloriesText.requestFocus()
            }
            water.isEmpty() || !water.isDigitsOnly() -> {
                binding.waterText.setError("Enter Water!", null)
                binding.waterText.requestFocus()
            }
            else -> {
                valid = true
            }
        }
        return valid
    }

    override fun setOnClickListener() {
        binding.submitBtn.setOnClickListener {
            if (checkFields()) {
                submitDailyDiet()
                navigateToHomeFragment()
            } else {
                showToastLengthLong("Put the fields correctly")
            }
        }
    }

    override fun submitDailyDiet() {
        val nutrients = DailyGoal(
            binding.caloriesText.text.toString().toDouble(),
            binding.proteinText.text.toString().toDouble(),
            binding.carbText.text.toString().toDouble(),
            binding.fatText.text.toString().toDouble(),
            binding.waterText.text.toString().toInt(),
        )
        viewModel.submitDailyDiet(nutrients)

        // submit too AchievedGoal in Database
        viewModel.submitAchievedGoal(AchievedGoal())

        putTrueForPreferencesOfIfUserMakeDailyGoal()
    }

    override fun showToastLengthLong(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    override fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_dailyGoalFragment_to_homeFragment)
    }

    override fun checkIfUserMakeDailyGoal() {
        val preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isDailyGoalSet = preferences.getBoolean("isDailyGoalSet", false)

        if (isDailyGoalSet) {
            // Navegar para a tela principal do aplicativo
            navigateToHomeFragment()
        }
    }

    override fun putTrueForPreferencesOfIfUserMakeDailyGoal() {
        val editor = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit()
        editor.putBoolean("isDailyGoalSet", true)
        editor.apply()
    }
}
