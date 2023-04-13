package com.example.dietplan.searchfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dietplan.R
import com.example.dietplan.data.local.Meal
import com.example.dietplan.data.model.DailyGoal
import com.example.dietplan.databinding.FragmentSearchBinding
import com.example.dietplan.extensions.formatToTwoHouses
import com.example.dietplan.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBtn.setOnClickListener {
            searchData()
        }
        binding.btnAdd.setOnClickListener {
            insertMeal()
            addMealForDatabase()
            findNavController().navigate(R.id.homeFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.noMeal()
    }

    private fun addMealForDatabase() {
        viewModel.meal.observe(viewLifecycleOwner) {
            viewModel.insertMeals(it)
            viewModel.insertMealForTheList(it, args.mealType)
        }
    }

    private fun searchData() {
        val foodText = binding.editText
        val radioGroup = binding.radioGroup
        val quantityText = binding.quantityText
        if (!foodText.text.isNullOrEmpty() && radioGroup.checkedRadioButtonId != -1 && !quantityText.text.isNullOrEmpty()) {
            val radioButton = binding.root.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            lifecycleScope.launch {
                if (viewModel.mealIsPresentInDatabase(foodText.text.toString())) {
                    setupDataByDatabase()
                } else {
                    viewModel.getFoods(foodText.text.toString() + " " + quantityText.text + " " + radioButton.text, foodText.text.toString())
                    setupData()
                }
            }
        }
    }

    private fun setupDataByDatabase() {
        lifecycleScope.launch {
            viewModel.getFoodByFoodName(binding.editText.text.toString())
            setupData()
        }
    }

    private fun setupData() {
        viewModel.meal.observe(viewLifecycleOwner) {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            binding.proteinCount.text = df.format(it.protein).toString()
            binding.carbCount.text = df.format(it.carb).toString()
            binding.fatCount.text = df.format(it.fat).toString()
            binding.caloriesCount.text = df.format(it.calories).toString()
            quantoFaltaProteina(it)
        }
    }

    private fun insertMeal() {
        viewModel.meal.observe(viewLifecycleOwner) {
            viewModel.bindCurrentDaily(
                DailyGoal(
                    calories = it.calories.formatToTwoHouses(),
                    protein = it.protein.formatToTwoHouses(),
                    carb = it.carb.formatToTwoHouses(),
                    fat = it.fat.formatToTwoHouses(),
                ),
            )
        }
    }

    private fun quantoFaltaProteina(meal: Meal) {
        viewModel.currentDaily.observe(viewLifecycleOwner) { currentDaily ->
            viewModel.dailyGoal.observe(viewLifecycleOwner) { dailyGoal ->
                binding.progress1.max = dailyGoal.protein.toInt()
                binding.progress2.max = dailyGoal.carb.toInt()
                binding.progress3.max = dailyGoal.fat.toInt()
                binding.progress4.max = dailyGoal.calories.toInt()
                binding.progress1.setProgress(currentDaily.protein.toInt() + meal.protein.toInt(), true)
                binding.progress2.setProgress(currentDaily.carb.toInt() + meal.carb.toInt(), true)
                binding.progress3.setProgress(currentDaily.fat.toInt() + meal.fat.toInt(), true)
                binding.progress4.setProgress(currentDaily.calories.toInt() + meal.calories.toInt(), true)
            }
        }
    }
}
