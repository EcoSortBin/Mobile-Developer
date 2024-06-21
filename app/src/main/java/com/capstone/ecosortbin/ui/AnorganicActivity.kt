package com.capstone.ecosortbin.ui

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.ecosortbin.R
import com.capstone.ecosortbin.databinding.ActivityAnorganicBinding

class AnorganicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnorganicBinding
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAnorganicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the description from the string-array resource
        val descriptions = resources.getStringArray(R.array.Description_text)
        val description = descriptions[1]

        // Set the description text to the TextView
        binding.descriptionText.text = Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT)
    }
}