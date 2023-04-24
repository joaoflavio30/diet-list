package com.example.dietplan.searchfood

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dietplan.R
import com.example.dietplan.data.model.RequestFood
import com.example.dietplan.databinding.FragmentSearchBinding
import com.example.dietplan.searchfood.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchContract.SearchFragment {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListener()

//        super.onViewCreated(view, savedInstanceState)
//        binding.searchBtn.setOnClickListener {
//            searchData()
//        }
//        binding.btnAdd.setOnClickListener {
//            insertMeal()
//            addMealForDatabase()
//            findNavController().navigate(R.id.homeFragment)
//        }
    }

    override fun setOnClickListener() {
        binding.searchBtn.setOnClickListener {
            if (checkFields()) {
                val buttonSelectedId = binding.radioGroup.checkedRadioButtonId
                val radioBtnSelected = binding.radioGroup.findViewById<RadioButton>(buttonSelectedId).text.toString()
                val request = RequestFood(binding.editText.text.toString(), binding.quantityText.text.toString().toDouble(), radioBtnSelected)
                Log.d("Tag", "$request")
                viewModel.getMeal(request)
                bindData()
                progressBarObserver()
            }
        }

        binding.btnAdd.setOnClickListener {
            viewModel.searchMeal.observe(viewLifecycleOwner) { searchMeal ->
                viewModel.goalAchieved.observe(viewLifecycleOwner) { goalAchieved ->
                    viewModel.incrementNutrientsToDailyDiet(searchMeal, goalAchieved)
                }
            }
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
            editText.isEmpty() -> {
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
            !quantity.isDigitsOnly() -> {
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
        viewModel.dailyDiet.observe(viewLifecycleOwner) { dailyDiet ->
            viewModel.searchMeal.observe(viewLifecycleOwner) { searchMeal ->
                viewModel.goalAchieved.observe(viewLifecycleOwner) { goalAchieved ->
                    Log.d("teste", "${dailyDiet.calories.div(searchMeal.calories)}")
                    binding.progress1.max = dailyDiet.protein.toInt()
                    binding.progress2.max = dailyDiet.carb.toInt()
                    binding.progress3.max = dailyDiet.fat.toInt()
                    binding.progress4.max = dailyDiet.calories.toInt()

                    binding.progress1.progress = searchMeal.protein.toInt().plus(goalAchieved.protein.toInt())
                    binding.progress2.progress = searchMeal.carb.toInt().div(dailyDiet.carb.toInt()).plus(goalAchieved.carb.toInt())
                    binding.progress3.progress = searchMeal.fat.toInt().div(dailyDiet.fat.toInt()).plus(goalAchieved.fat.toInt())
                    binding.progress4.progress = searchMeal.calories.toInt().div(dailyDiet.calories.toInt()).plus(goalAchieved.calories.toInt())
                }
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
            } else showToastLengthLong("Food not found")
        }
    }

    override fun bindData() {
        progressBarObserver()
        setNutrientsNumber()
    }

    override fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
    }
}
