package com.capstone.ecosortbin.ui.detail

import com.capstone.ecosortbin.Trash
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.capstone.ecosortbin.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val trash: Trash? = intent.getParcelableExtra("trash")
        trash?.let {
            binding.imgItemPhoto.setImageResource(it.image)
            binding.tvItemName.text = it.dataType
            binding.tvDescription.text = it.description

            Log.d("DetailActivity", "DataType: ${it.dataType}, Description: ${it.description}")
        } ?: run {
            Log.e("DetailActivity", "Trash data tidak ditemukan")
        }
    }
}
