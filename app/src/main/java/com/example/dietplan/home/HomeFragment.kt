package com.example.dietplan.home

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dietplan.* // ktlint-disable no-wildcard-imports
import com.example.dietplan.databinding.FragmentHomeBinding
import com.example.dietplan.extensions.formatCurrentVsTotal
import com.example.dietplan.extensions.highlightAView
import com.example.dietplan.searchfood.viewmodel.SearchViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContract.HomeFragment {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    @Inject
    lateinit var myDialog: MyDialog

    @Inject
    lateinit var firebaseImageUploader: FirebaseImageUploader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            Glide.with(this@HomeFragment).load("profile_images/${FirebaseAuth.getInstance().currentUser!!.uid}").preload()
            loadImage()
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
        bindData()
    }

    override fun onClickMenuItem() {
        binding.bottomMenu.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_2 -> {
                    findNavController().navigate(R.id.searchFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.page_4 -> {
                    findNavController().navigate(R.id.dailyGoalFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.page_5 -> {
                    findNavController().navigate(R.id.profileFragment)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
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

    override fun loadImage() {
        firebaseImageUploader.loadImageFromFirebaseStorage(
            "profile_images/${FirebaseAuth.getInstance().currentUser!!.uid}",
            successCallback = { uri: Uri ->
                Glide.with(this).load(uri).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.profileImg)
            },
            errorCallback = { exception ->
                Log.e("TAG", "Error loading image from Firebase Storage", exception)
            },
        )
    }

    override fun setOnClickListener() {
        binding.fab.setOnClickListener {
            myDialog.show()
        }
        binding.waterMetric.setOnClickListener {
            showDialog()
        }
        onClickMenuItem()
    }

    override fun navigateToSearchFragment() {
        navigateToSearchFragment()
    }

    override fun bindData() {
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
