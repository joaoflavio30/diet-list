package com.joaoflaviofreitas.dietplan.feature.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.canhub.cropper.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.dietplan.feature.profile.databinding.FragmentProfileImageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileImageFragment : Fragment(), ProfileImageContract.ProfileImageFragment {

    private var _binding: FragmentProfileImageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var storage: FirebaseStorage

    private val viewModel: ProfileViewModel by activityViewModels()

    private val cropImageLauncher = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            CoroutineScope(Dispatchers.Main).launch {
                Glide.get(requireContext()).clearMemory()
                CoroutineScope(Dispatchers.IO).launch {
                    Glide.get(requireContext()).clearDiskCache()
                }
            }
            binding.profileImg.setImageURI(Uri.parse(result.getUriFilePath(requireContext(), false)))
            viewModel.saveProfileImageInFirebaseStorage(result.uriContent)
            bindSharedPreferencesForUpdateGlideCache()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        setOnClickListeners()
        bindImage()
    }

    override fun setOnClickListeners() {
        binding.edit.setOnClickListener {
            DialogEditProfileImage(
                requireContext(),
                this,
                cropImageLauncher,
                viewModel,
                binding.profileImg,
            ).show()
        }
        binding.titleEditImage.setOnClickListener {
            navigateToProfileFragment()
        }
    }

    override fun navigateToProfileFragment() {
        findNavController().popBackStack()
    }

    override fun bindImage() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val signature = ObjectKey(sharedPreferences.getLong("updated_image_in_cache", 0))
        val imageView = binding.profileImg
        val storageRef =
            storage.reference.child("profile_images/${auth.currentUser?.uid}/profile.jpg")
        Glide.with(requireContext()).load(storageRef).placeholder(R.drawable.ic_baseline_account_circle_24).signature(signature).into(imageView)
    }

    private fun bindSharedPreferencesForUpdateGlideCache() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val value = System.currentTimeMillis()
        editor.putLong("updated_image_in_cache", value)
        editor.apply()
    }
}
