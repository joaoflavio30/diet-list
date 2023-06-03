package com.joaoflaviofreitas.dietplan.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.component.food.domain.model.DailyGoal
import com.joaoflaviofreitas.dietplan.ui.common.utils.ext.formatCurrentVsTotal
import com.joaoflaviofreitas.dietplan.ui.common.utils.ext.highlightAView
import com.joaoflaviofreitas.dietplan.ui.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContract.HomeFragment {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var storage: FirebaseStorage

    private lateinit var dailyGoal: DailyGoal
    private lateinit var achievedGoal: AchievedGoal

    private val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val currentDate = formatDate.format(Date())

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        viewModel.loadAchievedGoal.observe(viewLifecycleOwner) { achieved ->
            achievedGoal = achieved
            viewModel.loadDailyGoal.observe(viewLifecycleOwner) { daily ->
                dailyGoal = daily
                bindData()
            }
        }
    }

    override fun viewWaterMetrics(achievedGoal: AchievedGoal, dailyGoal: DailyGoal) {
        val current = achievedGoal.water.toDouble()
        val total = dailyGoal.water.toDouble()
        Log.d("tag", "${current.div(total)}")

        if (current.div(total) >= 0.33 && current.div(total) < 0.66) {
            binding.waterView2.highlightAView()
        }
        if (current.div(total) >= 0.66 && current.div(total) < 1.0) {
            binding.waterView2.highlightAView()
            binding.waterView1.highlightAView()
        }
        if (current.div(total) >= 1.0) {
            binding.waterView2.highlightAView()
            binding.waterView1.highlightAView()
            binding.waterView3.highlightAView()
        }
    }

    override fun bindCurrentDate() {
        val calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance().format(calendar.time)
        binding.date.text = currentDate
    }

    override fun bindProfileImage() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val signature = ObjectKey(sharedPreferences.getLong("updated_image_in_cache", 0))
        val imageView = binding.profileImg
        val storageRef =
            storage.reference.child("profile_images/${auth.currentUser?.uid}/profile.jpg")
        Glide.with(requireContext()).load(storageRef).placeholder(R.drawable.ic_baseline_account_circle_24).signature(signature).into(imageView)
    }

    override fun checkIfIsNextDayForZeroAchievedGoal() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.resetAchievedGoal(currentDate, achievedGoal)
        }
    }

    override fun showToastLengthLong(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    override fun showAerobicDialog() {
        val dialog = requireActivity().let {
            AlertDialog.Builder(it)
        }
        dialog.setMessage("Did you make your aerobic of the day?")
        dialog.apply {
            setPositiveButton(
                "Yes",
            ) { _, _ ->
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.addAerobicAsDone()
                    binding.aerobicContent?.text = getString(R.string.done)
                }
            }
                .setNegativeButton(
                    "No",
                    null,
                )
        }
        if (binding.aerobicContent?.text == getString(R.string.not_done)) {
            dialog.create()
            dialog.show()
        }
    }

    override fun viewAerobicMetrics(achievedGoal: AchievedGoal) {
        lifecycleScope.launch(Dispatchers.Main) {
            val aerobic = achievedGoal.aerobic
            if (aerobic) {
                binding.aerobicContent?.text = getString(R.string.done)
            } else {
                binding.aerobicContent?.text = getString(R.string.not_done)
            }
        }
    }

    override fun setOnClickListener() {
        binding.waterMetric.setOnClickListener {
            showWaterDialog(achievedGoal, dailyGoal)
        }
        binding.aerobicMetric.setOnClickListener {
            showAerobicDialog()
        }
        binding.adjustDietBtn?.setOnClickListener {
            showAdjustDietDialog()
        }
    }

    override fun showAdjustDietDialog() {
        val dialog = requireActivity().let {
            AlertDialog.Builder(it)
        }
        dialog.setMessage("Did you re-make your diet plan?")
        dialog.apply {
            setPositiveButton(
                "Yes",
            ) { _, _ ->
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.resetDailyGoal()
                    navigateToDailyGoalFragment()
                }
            }
                .setNegativeButton(
                    "No",
                    null,
                )
        }
        dialog.create()
        dialog.show()
    }

    override fun navigateToDailyGoalFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_dailyGoalFragment)
    }

    override fun achievedGoalObserver() {
        viewModel.loadAchievedGoal.observe(viewLifecycleOwner) {
            achievedGoal = it
        }
    }

    override fun navigateToSearchFragment() {
        navigateToSearchFragment()
    }

    override fun bindData() {
        bindNutritionImageColor()
        bindProfileImage()
        if (achievedGoal.date != currentDate) {
            achievedGoal = AchievedGoal(userEmail = achievedGoal.userEmail, id = achievedGoal.id)
            checkIfIsNextDayForZeroAchievedGoal()
        }
        bindCurrentDate()
        binding.protein.text = getString(R.string.protein_numb).formatCurrentVsTotal(
            achievedGoal.protein.toString(),
            dailyGoal.protein.toString(),
            " g",
        )
        binding.carb.text = getString(R.string.protein_numb).formatCurrentVsTotal(
            achievedGoal.carb.toString(),
            dailyGoal.carb.toString(),
            " g",
        )
        binding.fat.text = getString(R.string.protein_numb).formatCurrentVsTotal(
            achievedGoal.fat.toString(),
            dailyGoal.fat.toString(),
            " g",
        )
        binding.currentWater.text = getString(R.string.protein_numb).formatCurrentVsTotal(
            achievedGoal.water.toString(),
            dailyGoal.water.toString(),
            " liters",
        )
        binding.intoCalories.text =
            getString(R.string.calories_count, achievedGoal.calories.toString())
        binding.countCalories.max = dailyGoal.calories.toInt()
        binding.countCalories.setProgress(achievedGoal.calories.toInt(), true)

        viewWaterMetrics(achievedGoal, dailyGoal)
        viewAerobicMetrics(achievedGoal)
    }

    override fun showWaterDialog(achievedGoal: AchievedGoal, dailyGoal: DailyGoal) {
        val dialog = requireActivity().let {
            AlertDialog.Builder(it)
        }
        dialog.setMessage("did you drink 1 liter of water?")
        dialog.apply {
            setPositiveButton(
                "Yes",
            ) { _, _ ->
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.incWater(achievedGoal)
                    binding.currentWater.text = getString(R.string.water_numb).formatCurrentVsTotal(
                        achievedGoal.water.toString(),
                        dailyGoal.water.toString(),
                        " liters",
                    )
                    viewWaterMetrics(achievedGoal, dailyGoal)
                }
            }
                .setNegativeButton(
                    "No",
                    null,
                )
        }
        dialog.create()
        dialog.show()
    }

    override fun bindNutritionImageColor() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            val newColor = ContextCompat.getColor(requireContext(), R.color.white)
            binding.imgProtein.setColorFilter(newColor, PorterDuff.Mode.SRC_IN)
            binding.imgFat.setColorFilter(newColor, PorterDuff.Mode.SRC_IN)
            binding.imgCarb.setColorFilter(newColor, PorterDuff.Mode.SRC_IN)
        }
    }
}
