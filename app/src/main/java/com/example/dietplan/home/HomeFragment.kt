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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dietplan.*
import com.example.dietplan.databinding.FragmentHomeBinding
import com.example.dietplan.extensions.formatCurrentVsTotal
import com.example.dietplan.home.adapter.RoutineFoodAdapter
import com.example.dietplan.viewmodel.SearchViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    @Inject
    lateinit var myDialog: MyDialog

    @Inject
    lateinit var firebaseImageUploader: FirebaseImageUploader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Glide.with(this).load("profile_images/${FirebaseAuth.getInstance().currentUser!!.uid}").preload()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            myDialog.show()
        }
        onClickMenuItem()
        bindDailyGoal()
        binding.waterMetric.setOnClickListener { incWater() }
        loadImage()
        bindFoodRoutine()
    }

    private fun incWater() {
        val dialog = requireActivity().let {
            AlertDialog.Builder(it)
        }
        dialog.setMessage("did you drink 1 liter of water?")
        dialog.apply {
            setPositiveButton(
                "Yes",
            ) { dialogInterface, int ->
                val current = (viewModel.currentDaily.value?.water)?.plus(1).toString()
                val total = viewModel.dailyGoal.value?.water.toString()
                binding.currentWater.text = getString(R.string.water_numb, current, total)
                viewModel.incWater()
                viewWaterMetrics(current.toDouble(), total.toDouble())
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

    private fun bindDailyGoal() {
        viewModel.dailyGoal.observe(viewLifecycleOwner) { dailyGoal ->
            viewModel.currentDaily.observe(viewLifecycleOwner) { currentDaily ->
                binding.protein.text = getString(R.string.protein_numb).formatCurrentVsTotal(
                    currentDaily.protein.toString(),
                    dailyGoal.protein.toString(),
                    " g",
                )
                binding.carb.text = getString(R.string.protein_numb).formatCurrentVsTotal(
                    currentDaily.carb.toString(),
                    dailyGoal.protein.toString(),
                    " g",
                )
                binding.fat.text = getString(R.string.protein_numb).formatCurrentVsTotal(
                    currentDaily.fat.toString(),
                    dailyGoal.fat.toString(),
                    " g",
                )
                binding.currentWater.text = getString(R.string.water_numb).formatCurrentVsTotal(
                    currentDaily.water.toString(),
                    dailyGoal.water.toString(),
                    " liters",
                )
                binding.intoCalories.text =
                    getString(R.string.calories_count, currentDaily.calories.toString())
                binding.countCalories.setMax(dailyGoal.calories.toInt())
                binding.countCalories.setProgress(currentDaily.calories.toInt(), true)
                viewWaterMetrics(currentDaily.water.toDouble(), dailyGoal.water.toDouble())
            }
        }
    }

    private fun viewWaterMetrics(current: Double, total: Double) {
        if (current.div(total) >= 0.33 && current.div(total) < 0.66) {
            binding.waterView2.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red,
                ),
            )
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

    private fun loadImage() {
        Log.d("TAG", "Image URI: ${viewModel.uriProfileImage.value}")
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

    private fun bindFoodRoutine() {
        viewModel.breakfast.observe(viewLifecycleOwner) {
            val adapter = RoutineFoodAdapter()
            binding.rvBreakfast.layoutManager = LinearLayoutManager(requireContext())
            binding.rvBreakfast.adapter = adapter
            adapter.submitList(it)
        }
        viewModel.lunch.observe(viewLifecycleOwner) {
            val adapter = RoutineFoodAdapter()
            binding.rvLunch.adapter = adapter
            adapter.submitList(it)
        }
        viewModel.snack.observe(viewLifecycleOwner) {
            val adapter = RoutineFoodAdapter()
            binding.rvSnack.adapter = adapter
            adapter.submitList(it)
        }
        viewModel.dinner.observe(viewLifecycleOwner) {
            val adapter = RoutineFoodAdapter()
            binding.rvDinner.adapter = adapter
            adapter.submitList(it)
        }
    }
}
