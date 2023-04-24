package com.example.dietplan.home

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dietplan.*
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
        Log.d("tag", "${viewModel.dailyDiet.value}")
    }

    fun onClickMenuItem() {
        binding.bottomMenu.setOnItemSelectedListener { menuitem ->
            when (menuitem.itemId) {
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

    private fun viewWaterMetrics() {
        viewModel.goalAchieved.observe(viewLifecycleOwner) { goalAchieved ->
            viewModel.dailyDiet.observe(viewLifecycleOwner) { dailyDiet ->

                val current = goalAchieved.water.toDouble()
                val total = dailyDiet.water.toDouble()

                if (current.div(total) >= 0.33 && current.div(total) < 0.66) {
                    binding.waterView2.highlightAView()
                }
                if (current.div(total) >= 0.66 && current.div(total) < 1.0) {
                    binding.waterView2.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red,
                        ),
                    )
                    binding.waterView1.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red,
                        ),
                    )
                }
                if (current.div(total) == 1.0) {
                    binding.waterView2.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red,
                        ),
                    )
                    binding.waterView1.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red,
                        ),
                    )
                    binding.waterView3.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red,
                        ),
                    )
                }
            }
        }
    }

    private fun loadImage() {
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
    }

    override fun navigateToSearchFragment() {
        navigateToSearchFragment()
    }

    override fun bindData() {
        viewModel.dailyDiet.observe(viewLifecycleOwner) { dailyDiet ->
            Log.d("tag", "$dailyDiet")
            viewModel.goalAchieved.observe(viewLifecycleOwner) { goalAchieved ->
                binding.protein.text = getString(R.string.protein_numb).formatCurrentVsTotal(
                    goalAchieved.protein.toString(),
                    dailyDiet.protein.toString(),
                    " g",
                )
                binding.carb.text = getString(R.string.protein_numb).formatCurrentVsTotal(
                    goalAchieved.carb.toString(),
                    dailyDiet.protein.toString(),
                    " g",
                )
                binding.fat.text = getString(R.string.protein_numb).formatCurrentVsTotal(
                    goalAchieved.fat.toString(),
                    dailyDiet.fat.toString(),
                    " g",
                )
                binding.currentWater.text = getString(R.string.water_numb).formatCurrentVsTotal(
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
                val current = (viewModel.goalAchieved.value?.water)?.plus(1).toString()
                val total = viewModel.dailyDiet.value?.water.toString()
                binding.currentWater.text = getString(R.string.water_numb, current, total)
                viewModel.incWater()
                viewWaterMetrics()
                Log.d("Tag", current + "----" + total)
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
