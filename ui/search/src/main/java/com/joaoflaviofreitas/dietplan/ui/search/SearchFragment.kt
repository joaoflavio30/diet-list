package com.joaoflaviofreitas.dietplan.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.Meal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.RequestFood
import com.joaoflaviofreitas.dietplan.ui.search.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchContract.SearchFragment {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var auth: FirebaseAuth

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var achievedGoal: AchievedGoal
    private lateinit var dailyGoal: DailyGoal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getAchievedGoal()
            viewModel.getDailyDiet()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadAchievedGoal.observe(viewLifecycleOwner) { achieved ->
            achievedGoal = achieved
            viewModel.loadDailyGoal.observe(viewLifecycleOwner) { daily ->
                dailyGoal = daily
                setOnClickListener()
            }
        }
    }

    override fun setOnClickListener() {
        binding.searchBtn.setOnClickListener {
            if (checkFields()) {
                val buttonSelectedId = binding.radioGroup.checkedRadioButtonId
                val radioBtnSelected = binding.radioGroup.findViewById<RadioButton>(buttonSelectedId).text.toString()
                val request = RequestFood(
                    binding.editText.text.toString(),
                    binding.quantityText.text.toString().toDouble(),
                    radioBtnSelected,
                )
                viewModel.getMeal(request)
                bindData()
                progressBarObserver()
            }
        }

        binding.btnAdd.setOnClickListener {
            saveAchievedGoal()
            navigateToHomeFragment()
        }
    }

    override fun checkFields(): Boolean {
        val editText = binding.editText.text.toString()
        val buttonSelectedId = binding.radioGroup.checkedRadioButtonId
        val radioBtnSelected = binding.radioGroup.findViewById<RadioButton>(buttonSelectedId).text.toString()
        val quantity = binding.quantityText.text.toString()
        var valid = false

        when {
            editText.isEmpty() || editText.isDigitsOnly() -> {
                binding.editText.setError("Enter Food!", null)
                binding.editText.requestFocus()
            }
            radioBtnSelected.isEmpty() -> {
                showToastLengthLong("Please choose some measure")
            }
            quantity.isEmpty() -> {
                binding.quantityText.setError("Passwords must match.", null)
                binding.quantityText.requestFocus()
            }
            quantity.toDoubleOrNull() == null -> {
                binding.quantityText.setError("Please put a number for measure", null)
            }
            else -> {
                valid = true
            }
        }
        return valid
    }

    override fun showToastLengthLong(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    override fun progressBarObserver() {
        lifecycleScope.launch {
            viewModel.searchMeal.observe(viewLifecycleOwner) { searchMeal ->
                binding.progress1.max = dailyGoal.protein.toInt()
                binding.progress2.max = dailyGoal.carb.toInt()
                binding.progress3.max = dailyGoal.fat.toInt()
                binding.progress4.max = dailyGoal.calories.toInt()

                binding.progress1.setProgress(searchMeal.protein.toInt().plus(achievedGoal.protein.toInt()), true)
                binding.progress2.setProgress(searchMeal.carb.toInt().plus(achievedGoal.carb.toInt()), true)
                binding.progress3.setProgress(searchMeal.fat.toInt().plus(achievedGoal.fat.toInt()), true)
                binding.progress4.setProgress(searchMeal.calories.toInt().plus(achievedGoal.calories.toInt()), true)
            }
        }
    }

    override fun observeDailyDiet() {
        viewModel.dailyDiet.observe(viewLifecycleOwner) {}
    }

    override fun setNutrientsNumber() {
        viewModel.searchMeal.observe(viewLifecycleOwner) { currentMeal ->
            if (currentMeal.calories != 0.0) {
                binding.proteinCount.text = currentMeal.protein.toString()
                binding.carbCount.text = currentMeal.carb.toString()
                binding.fatCount.text = currentMeal.fat.toString()
                binding.caloriesCount.text = currentMeal.calories.toString()
            } else {
                showToastLengthLong("Food not found")
            }
        }
    }

    override fun bindData() {
        progressBarObserver()
        setNutrientsNumber()
    }

    override fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
    }

    override fun saveAchievedGoal() {
        viewModel.searchMeal.observe(viewLifecycleOwner) { meal ->
            val mealForIncrement = Meal(
                calories = meal.calories,
                protein = meal.protein,
                carb = meal.carb,
                fat = meal.fat,
            )
            viewModel.updateAchievedGoal(mealForIncrement, achievedGoal)
        }
    }

    override fun achievedGoalObserver() {
        viewModel.goalAchieved.observe(viewLifecycleOwner) {
            achievedGoal = it
        }
    }
}
