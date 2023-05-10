package com.joaoflaviofreitas.dietplan.feature.profile

import android.annotation.SuppressLint
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

@SuppressLint("UseCompatLoadingForDrawables")
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
            viewModel.deleteProfileImageInFirebaseStorage()
            CoroutineScope(Dispatchers.Main).launch {
                Glide.get(context).clearMemory()
                CoroutineScope(Dispatchers.IO).launch {
                    Glide.get(context).clearDiskCache()
                }
            }
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val value = System.currentTimeMillis()
            sharedPreferences.edit().putLong("updated_image_in_cache", value).apply()
            imageView.setImageResource(R.drawable.ic_baseline_account_circle_24)
            dismiss()
        }

        btnTakePhoto.setOnClickListener {
            // Ação do botão Take Photo
            dismiss()
        }

        btnChoosePhoto.setOnClickListener {
            setupCropImage(cropImageLauncher)
            CoroutineScope(Dispatchers.Main).launch {
                Glide.get(context).clearMemory()
                CoroutineScope(Dispatchers.IO).launch {
                    Glide.get(context).clearDiskCache()
                }
            }
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
