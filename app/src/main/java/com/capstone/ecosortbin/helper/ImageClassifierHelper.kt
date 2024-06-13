package com.capstone.ecosortbin.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import com.capstone.ecosortbin.R
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

@Suppress("DEPRECATION")
class ImageClassifierHelper(
    private var threshold: Float = 0.1f,
    private var maxResult: Int = 3,
    private val modelName: String = "model_ecosortbin.tflite",
    private val context: Context,
    private val classifierListener: ClassifierListener?
) {
    interface ClassifierListener {
        fun onError(errorMessage: String)
        fun onResult(
            result: List<Float>?,
            interfaceTime: Long
        )
    }

    private var interpreter: Interpreter? = null

    init {
        setupInterpreter()
    }

    private fun setupInterpreter() {
        try {
            interpreter = Interpreter(loadModelFile(context, modelName))
        } catch (e: Exception) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, e.message.toString())
        }
    }

    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun classifyStaticImage(imageUri: Uri) {
        if (interpreter == null) {
            setupInterpreter()
        }
        val bitmap = getImageBitmap(imageUri)
        val tensorImage = preprocessImage(bitmap)
        val result = performInference(tensorImage)
        notifyResults(result)
    }

    private fun getImageBitmap(imageUri: Uri): Bitmap {
        return if (checkVersion()) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        }.copy(Bitmap.Config.ARGB_8888, true)
    }

    private fun preprocessImage(bitmap: Bitmap): TensorImage {
        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        val inputSize = 224 // Sesuaikan dengan ukuran input model Anda
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(inputSize, inputSize, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .build()
        return imageProcessor.process(tensorImage)
    }

    private fun checkVersion(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    }

    private fun performInference(tensorImage: TensorImage): List<Float>? {
        val inputBuffer = tensorImage.buffer
        val outputBuffer = Array(1) { FloatArray(maxResult) } // Sesuaikan dengan output model Anda
        var inferenceTime = SystemClock.uptimeMillis()
        interpreter?.run(inputBuffer, outputBuffer)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime
        classifierListener?.onResult(outputBuffer[0].toList(), inferenceTime)
        return outputBuffer[0].toList()
    }

    private fun notifyResults(result: List<Float>?) {
        classifierListener?.onResult(result, 0)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
