package com.joaoflaviofreitas.dietplan.feature.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogEditProfileImage(context: Context, fragment: Fragment, cropImageLauncher: ActivityResultLauncher<CropImageContractOptions>, viewModel: ProfileViewModel, imageView: ImageView) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_layout)

        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            setGravity(Gravity.BOTTOM)
        }

        // Configurando os botões do dialog
        val btnDelete = findViewById<Button>(R.id.btn_delete_photo)
        val btnTakePhoto = findViewById<Button>(R.id.btn_take_photo)
        val btnChoosePhoto = findViewById<Button>(R.id.btn_choose_photo)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)

        btnDelete.setOnClickListener {
            if (imageView.drawable != null) {
                viewModel.deleteProfileImageInFirebaseStorage()
            }
            viewModel.deleteProfileImageLiveData()
            imageView.setImageResource(0)
            dismiss()
        }

        btnTakePhoto.setOnClickListener {
            // Ação do botão Take Photo
            dismiss()
        }

        btnChoosePhoto.setOnClickListener {
            setupCropImage(cropImageLauncher)

            dismiss()
        }

        btnCancel.setOnClickListener {
            // Ação do botão Cancel
            dismiss()
        }
    }
    private fun setupCropImage(cropImageLauncher: ActivityResultLauncher<CropImageContractOptions>) {
        val cropImageOptions = CropImageOptions()
        cropImageOptions.cropShape = CropImageView.CropShape.OVAL
        cropImageOptions.fixAspectRatio = true
        cropImageOptions.showCropLabel = true
        cropImageOptions.autoZoomEnabled = false
        cropImageOptions.minCropWindowHeight = 100
        cropImageOptions.minCropWindowWidth = 100
        cropImageOptions.allowRotation = true
        cropImageOptions.allowFlipping = true
        val cropImageContractOptions = CropImageContractOptions(null, cropImageOptions)
        cropImageLauncher.launch(cropImageContractOptions)
    }
}
