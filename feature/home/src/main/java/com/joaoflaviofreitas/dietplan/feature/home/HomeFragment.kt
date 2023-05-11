package com.joaoflaviofreitas.dietplan.feature.home

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.dietplan.component.food.domain.model.AchievedGoal
import com.joaoflaviofreitas.dietplan.feature.common.utils.formatCurrentVsTotal
import com.joaoflaviofreitas.dietplan.feature.common.utils.highlightAView
import com.joaoflaviofreitas.dietplan.feature.home.databinding.FragmentHomeBinding
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
        bindData()
    }

    override fun viewWaterMetrics() {
        lifecycleScope.launch(Dispatchers.Main) {
            val current = viewModel.getAchievedGoal().water.toDouble()
            val total = viewModel.getDailyDiet().water.toDouble()
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
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val lastDate = sharedPreferences.getString("last_date_reset", null)

        val currentDate = formatDate.format(Date())

        Log.d("teste","${lastDate} ------- $currentDate")
        if (currentDate != lastDate) {
            viewModel.resetAchievedGoal()
            sharedPreferences.edit().putString("last_date_reset", currentDate).apply()
        }
    }

    override fun setOnClickListener() {
        binding.waterMetric.setOnClickListener {
            showDialog()
        }
    }

    override fun navigateToSearchFragment() {
        navigateToSearchFragment()
    }

    override fun bindData() {
        checkIfIsNextDayForZeroAchievedGoal()
        bindProfileImage()
        bindCurrentDate()
        lifecycleScope.launch(Dispatchers.Main) {
            val dailyDiet = viewModel.getDailyDiet()
            val goalAchieved = viewModel.getAchievedGoal()
            Log.d("tag", "$dailyDiet")
            binding.protein.text = getString(R.string.protein_numb).formatCurrentVsTotal(
                goalAchieved.protein.toString(),
                dailyDiet.protein.toString(),
                " g",
            )
            binding.carb.text = getString(R.string.protein_numb).formatCurrentVsTotal(
                goalAchieved.carb.toString(),
                dailyDiet.carb.toString(),
                " g",
            )
            binding.fat.text = getString(R.string.protein_numb).formatCurrentVsTotal(
                goalAchieved.fat.toString(),
                dailyDiet.fat.toString(),
                " g",
            )
            binding.currentWater.text = getString(R.string.protein_numb).formatCurrentVsTotal(
                goalAchieved.water.toString(),
                dailyDiet.water.toString(),
                " liters",
            )
            binding.intoCalories.text =
                getString(R.string.calories_count, goalAchieved.calories.toString())
            binding.countCalories.setMax(dailyDiet.calories.toInt())
            binding.countCalories.setProgress(goalAchieved.calories.toInt(), true)

            viewWaterMetrics()
        }
    }

    override fun showDialog() {
        val dialog = requireActivity().let {
            AlertDialog.Builder(it)
        }
        dialog.setMessage("did you drink 1 liter of water?")
        dialog.apply {
            setPositiveButton(
                "Yes",
            ) { dialogInterface, int ->
                lifecycleScope.launch(Dispatchers.Main) {
                    Log.d("antes", "${viewModel.getAchievedGoal().water}")
                    viewModel.incWater()
                    Log.d("depois", "${viewModel.getAchievedGoal().water}")
                    binding.currentWater.text = getString(R.string.water_numb).formatCurrentVsTotal(
                        viewModel.getAchievedGoal().water.toString(),
                        viewModel.getDailyDiet().water.toString(),
                        " liters",
                    )
                    viewWaterMetrics()
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
}
