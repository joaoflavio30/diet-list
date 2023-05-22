package com.joaoflaviofreitas.dietplan.ui.home

import android.app.AlertDialog
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.dietplan.ui.common.utils.formatCurrentVsTotal
import com.joaoflaviofreitas.dietplan.ui.common.utils.highlightAView
import com.joaoflaviofreitas.dietplan.ui.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContract.HomeFragment, SensorEventListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var storage: FirebaseStorage

    private var sensorManager: SensorManager? = null

    private var running = false

    private var totalSteps = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        checkIfIsNextDayForZeroAchievedGoal()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        bindData()
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun viewWaterMetrics() {
        lifecycleScope.launch(Dispatchers.Main) {
            val current = viewModel.getAchievedGoal(auth.currentUser!!.email!!).water.toDouble()
            val total = viewModel.getDailyDiet(auth.currentUser!!.email!!).water.toDouble()
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
        val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val currentDate = formatDate.format(Date())

        viewModel.resetAchievedGoal(auth.currentUser!!.email!!, currentDate)
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
            ) { dialogInterface, int ->
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.addAerobicAsDone(auth.currentUser!!.email!!)
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

    override fun viewAerobicMetrics() {
        lifecycleScope.launch(Dispatchers.Main) {
            val aerobic = viewModel.getAchievedGoal(auth.currentUser!!.email!!).aerobic
            if (aerobic) {
                binding.aerobicContent?.text = getString(R.string.done)
            } else {
                binding.aerobicContent?.text = getString(R.string.not_done)
            }
        }
    }

    override fun setOnClickListener() {
        binding.waterMetric.setOnClickListener {
            showWaterDialog()
        }
        binding.aerobicMetric.setOnClickListener {
            showAerobicDialog()
        }
    }

    override fun navigateToSearchFragment() {
        navigateToSearchFragment()
    }

    override fun bindData() {
        bindProfileImage()
        bindCurrentDate()
        lifecycleScope.launch(Dispatchers.Main) {
            val dailyDiet = viewModel.getDailyDiet(auth.currentUser!!.email!!)
            val goalAchieved = viewModel.getAchievedGoal(auth.currentUser!!.email!!)
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
            viewAerobicMetrics()
        }
    }

    override fun showWaterDialog() {
        val dialog = requireActivity().let {
            AlertDialog.Builder(it)
        }
        dialog.setMessage("did you drink 1 liter of water?")
        dialog.apply {
            setPositiveButton(
                "Yes",
            ) { dialogInterface, int ->
                lifecycleScope.launch(Dispatchers.Main) {
                    Log.d("antes", "${viewModel.getAchievedGoal(auth.currentUser!!.email!!).water}")
                    viewModel.incWater(auth.currentUser!!.email!!)
                    Log.d("depois", "${viewModel.getAchievedGoal(auth.currentUser!!.email!!).water}")
                    binding.currentWater.text = getString(R.string.water_numb).formatCurrentVsTotal(
                        viewModel.getAchievedGoal(auth.currentUser!!.email!!).water.toString(),
                        viewModel.getDailyDiet(auth.currentUser!!.email!!).water.toString(),
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

    override fun onResume() {
        super.onResume()
        running = true
        handleWithStepSensor()
    }

    private fun handleWithStepSensor() {
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor != null) {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
            showToastLengthLong("Sensor detected on this device")
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running) {
            totalSteps = event!!.values[0]
            binding.intoWalk.text = "${totalSteps.toInt()}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
