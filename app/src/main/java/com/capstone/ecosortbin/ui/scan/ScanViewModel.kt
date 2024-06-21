package com.capstone.ecosortbin.ui.scan

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.ecosortbin.R
import com.capstone.ecosortbin.ui.result.ResultActivity
import com.yalantis.ucrop.UCrop
import java.io.File

class ScanViewModel(application: Application) : AndroidViewModel(application) {

    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> = _currentImageUri

    private val _croppedImage = MutableLiveData<Uri?>()
    val croppedImage: LiveData<Uri?> = _croppedImage

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    fun setCurrentImageUri(uri: Uri?) {
        Log.d("ScanViewModel", "setCurrentImageUri: $uri")
        _currentImageUri.value = uri
    }

    fun showCroppedImage(uri: Uri?) {
        Log.d("ScanViewModel", "showCroppedImage: $uri")
        _croppedImage.value = uri
    }

    fun showToast(message: String) {
        Log.d("ScanViewModel", "showToast: $message")
        _toastMessage.value = message
    }

    fun startUCrop(sourceUri: Uri, fragment: Fragment) {
        val fileName = "cropped_image_${System.currentTimeMillis()}.jpg"
        val destinationUri = Uri.fromFile(File(fragment.requireContext().cacheDir, fileName))

        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1000, 1000)
            .start(fragment.requireActivity(), fragment)
    }


    fun analyzeImage(context: Context) {
        Log.d("ScanViewModel", "analyzeImage")
        val intent = Intent(context, ResultActivity::class.java)
        _croppedImage.value?.let { uri ->
            Log.d("ScanViewModel", "analyzeImage: uri=$uri")
            intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, uri.toString())
        } ?: run {
            Log.d("ScanViewModel", "analyzeImage: croppedImage is null")
            showToast(context.getString(R.string.image_classifier_failed))
        }
    }

    fun moveToResult(context: Context) {
        Log.d("ScanViewModel", "moveToResult")
        val intent = Intent(context, ResultActivity::class.java)
        _croppedImage.value?.let { uri ->
            Log.d("ScanViewModel", "moveToResult: uri=$uri")
            intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, uri.toString())
            context.startActivity(intent)
        } ?: run {
            Log.d("ScanViewModel", "moveToResult: croppedImage is null")
            showToast(context.getString(R.string.image_classifier_failed))
        }
    }
}
