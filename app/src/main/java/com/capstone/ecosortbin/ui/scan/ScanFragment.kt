package com.capstone.ecosortbin.ui.scan

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.capstone.ecosortbin.R
import com.capstone.ecosortbin.databinding.FragmentScanBinding
import com.capstone.ecosortbin.ui.result.ResultActivity
import com.capstone.ecosortbin.ui.result.ResultActivity.Companion.TAG
import com.capstone.ecosortbin.utils.getImageUri
import com.yalantis.ucrop.UCrop
import java.io.File

class ScanFragment : Fragment() {
    private var currentImageUri: Uri? = null
    private var croppedImageUri: Uri? = null
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnScan.setOnClickListener {
            croppedImageUri?.let {
                analyzeImage(it)
                moveToResult()
            } ?: run {
                showToast(getString(R.string.empty_image_warning))
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
            startUCrop(currentImageUri!!)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess: Boolean ->
        if (isSuccess) {
            currentImageUri?.let {
                showImage()
                startUCrop(it)
            }
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val uCropLauncher = registerForActivityResult(UCropContract()) { uri: Uri? ->
        if (uri != null) {
            croppedImageUri = uri
            showCroppedImage(croppedImageUri!!)
        } else {
            showToast("Failed to crop image")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imageScan.setImageURI(it)
        }
    }

    private fun showCroppedImage(uri: Uri) {
        Log.d(TAG, "Displaying cropped image: $uri")
        binding.imageScan.setImageURI(uri)
    }

    private fun analyzeImage(croppedUri: Uri) {
        val intent = Intent(requireContext(), ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, croppedUri.toString())
    }

    private fun moveToResult() {
        Log.d(TAG, "Pindah Ke ResultActivity")
        croppedImageUri?.let {
            val intent = Intent(requireContext(), ResultActivity::class.java)
            intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, it.toString())
            startActivity(intent)
        }
    }

    private fun startUCrop(sourceUri: Uri) {
        val fileName = "cropped_image_${System.currentTimeMillis()}.jpg"
        val cacheDir = requireContext().cacheDir
        val destinationUri = Uri.fromFile(File(cacheDir, fileName))

        val uCrop = UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1000, 1000)
        uCropLauncher.launch(uCrop.getIntent(requireContext()))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireActivity())
        currentImageUri?.let {
            launcherIntentCamera.launch(it)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }


    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Contract for UCrop
class UCropContract : ActivityResultContract<Intent, Uri?>() {
    override fun createIntent(context: Context, input: Intent): Intent {
        return input
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == AppCompatActivity.RESULT_OK) {
            UCrop.getOutput(intent!!)
        } else {
            null
        }
    }
}