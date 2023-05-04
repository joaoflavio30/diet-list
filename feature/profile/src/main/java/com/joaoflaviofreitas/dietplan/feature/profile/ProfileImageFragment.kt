package com.joaoflaviofreitas.dietplan.feature.profile

import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.canhub.cropper.*
import com.joaoflaviofreitas.dietplan.feature.profile.databinding.FragmentProfileImageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileImageFragment : Fragment(), ProfileImageContract.ProfileImageFragment {

    private var _binding: FragmentProfileImageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by activityViewModels()

    private val cropImageLauncher = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val imageCropped =
                Uri.parse(result.getUriFilePath(requireContext(), true))
            binding.profileImg.setImageURI(imageCropped)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    override fun cropImage(data: Uri?) {
    }

    override fun deleteUserImageView() {
    }

    override fun setOnClickListeners() {
        binding.edit.setOnClickListener {
            DialogEditProfileImage(requireContext(), this, cropImageLauncher).show()
        }
        binding.titleEditImage.setOnClickListener {
            navigateToProfileFragment()
        }
    }

    override fun navigateToProfileFragment() {
        findNavController().popBackStack()
    }
}
