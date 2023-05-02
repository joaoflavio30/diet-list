package com.joaoflaviofreitas.dietplan.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.joaoflaviofreitas.dietplan.feature.profile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), ProfileContract.ProfileFragment {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
//    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let { nonNullUri ->
//            firebaseImageUploader.uploadImageToFirebaseStorage(
//                nonNullUri,
//                successCallback = { uri ->
//                    Glide.with(this).load(uri).into(binding.imgProfile)
//                },
//                errorCallback = { exception ->
//                    Log.e("TAG", "Error loading image from Firebase Storage", exception)
//                },
//            )
// //            viewModel.bindUriProfileImg(uri.toString())
//        }
//    }

//    @Inject
//    lateinit var firebaseImageUploader: FirebaseImageUploader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOnClickListener()
    }

    private fun showPictureDialog() {
        val options = arrayOf("Take a Picture", "Choose from Gallery")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select an Option")
            .setItems(options) { dialog, which ->
                handleDialogOptionSelected(which)
            }
            .show()
    }

    private fun handleDialogOptionSelected(option: Int) {
        when (option) {
            0 -> {
                // Lógica para tirar uma foto
            }
            1 -> {
//                pickImage.launch("image/*")
            }
        }
    }

    override fun setOnClickListener() {
        binding.imgProfile.setOnClickListener {
            showPictureDialog()
        }
    }
}
