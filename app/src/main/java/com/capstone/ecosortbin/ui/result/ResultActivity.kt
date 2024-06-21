package com.capstone.ecosortbin.ui.result

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capstone.ecosortbin.databinding.ActivityResultBinding
import com.capstone.ecosortbin.helper.ImageClassifierHelper
import com.capstone.ecosortbin.ui.AdditionalInfo
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        if (imageUri != null) {
            val image = Uri.parse(imageUri)
            displayImage(image)

            val imageClassifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(errorMessage: String) {
                        Log.d(TAG, "Error: $errorMessage")
                    }

                    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                        results?.let { displayResults(it) }
                    }
                }
            )
            imageClassifierHelper.classifyStaticImage(image)
        } else {
            Log.e(TAG, "No image URI provided")
            finish()
        }
    }

    private fun displayResults(results: List<Classifications>) {
        val topResult = results[0]
        val category = topResult.categories[0]
        val additionalInfo = getAdditionalInfo(category.index)

        binding.resultText.text = "${category.label}: ${category.score.formatToString()}"

        binding.jenisTv.text = additionalInfo.jenis
        binding.deskripsiTv.text = additionalInfo.deskripsi
        binding.reuseTv.text = additionalInfo.manfaat
    }

    private fun getAdditionalInfo(index: Int): AdditionalInfo {
        return when (index) {
            0 -> AdditionalInfo(
                "Anorganik",
                "Sampah kaca adalah sampah dengan bahan bening dan keras yang dibuat dari lelehan pasir silika yang dipanaskan.",
                "Lampu Hias, Lukisan Kaca, Miniatur Kebun Kaca"
            )

            1 -> AdditionalInfo(
                "Organik",
                "Sampah karton adalah sampah dengan bahan kemasan yang terbuat dari kertas tebal yang sering digunakan untuk membuat kotak dan kemasan.",
                "Kerajinan Organizer, Kerajinan Topeng, Kerajinan Model Bangunan"
            )

            2 -> AdditionalInfo(
                "Organik",
                "Sampah kertas adalah sampah dengan bahan yang terbuat dari serat kayu yang diolah menjadi lembaran tipis.",
                "Kerajinan Origami, Kerajinan Decoupage, Kerajinan Kertas Lipat"
            )

            3 -> AdditionalInfo(
                "Anorganik",
                "Sampah logam adalah sampah dengan material yang biasanya keras tak tembus cahaya, berkilau dan memiliki konduktivitas listrik dan panas.",
                "Patung Logam, Aksesoris Logam, Pot Tanaman Logam"
            )

            4 -> AdditionalInfo(
                "Organik",
                "Sampah organik adalah sampah yang berasal dari tumbuhan dan hewan seperti sisa makanan, daun kering, kulit buah, dan sayuran yang sudah tidak terpakai.",
                "Kompos, Pakan Ternak, Pengkondisian Tanah"
            )

            5 -> AdditionalInfo(
                "Anorganik",
                "Sampah plastik adalah sampah dengan bahan sintetis yang dapat dibentuk menjadi berbagai bentuk dan digunakan dalam berbagai produk karena sifatnya yang ringan dan tahan lama.",
                "Kerajinan Tas Tangan, Kerajinan Hiasan Rumah, Kerajinan Mainan Anak"
            )

            else -> AdditionalInfo(
                "Informasi tidak tersedia",
                "Informasi tidak tersedia",
                "Informasi tidak tersedia"
            )
        }
    }


    private fun Float.formatToString(): String {
        return String.format("%.2f%%", this * 100)
    }

    private fun displayImage(uri: Uri) {
        Log.d(TAG, "Displaying image: $uri")
        binding.resultImageBackground.setImageURI(uri)
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val TAG = "Result_Activity"
    }
}
