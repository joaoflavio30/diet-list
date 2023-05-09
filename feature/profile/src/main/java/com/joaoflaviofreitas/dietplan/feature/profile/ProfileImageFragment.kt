package com.joaoflaviofreitas.dietplan.feature.profile

import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.canhub.cropper.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.dietplan.feature.profile.databinding.FragmentProfileImageBinding
import dagger.hilt.android.AndroidEntryPoint
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
            val imageCropped = Uri.parse(result.getUriFilePath(requireContext(), false))
            binding.profileImg.setImageURI(imageCropped)
            viewModel.saveProfileImageInFirebaseStorage(result.uriContent)
            viewModel.saveProfileImageLiveData(result.uriContent.toString())
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

    override fun cropImage(data: Uri?) {
    }

    override fun deleteUserImageView() {
        binding.profileImg.setImageResource(0)
    }

    override fun setOnClickListeners() {
        binding.edit.setOnClickListener {
            DialogEditProfileImage(
                requireActivity(),
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
        val imageView = binding.profileImg
        val storageRef = storage.reference.child("profile_images/${auth.currentUser?.uid}/profile.jpg")
        viewModel.profileImage.observe(viewLifecycleOwner) { result ->
            Glide.with(requireActivity()).load(storageRef).into(imageView)
            if (result.isEmpty()) {
                Log.d("teste", result)
                Glide.with(requireContext()).clear(imageView)
            }
        }
    }
}
