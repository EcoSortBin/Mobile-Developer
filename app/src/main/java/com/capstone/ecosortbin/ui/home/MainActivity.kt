package com.capstone.ecosortbin.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.ecosortbin.R
import com.capstone.ecosortbin.Trash
import com.capstone.ecosortbin.databinding.ActivityMainBinding
import com.capstone.ecosortbin.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navView: BottomNavigationView = binding.navView

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.cardOrganic.setOnClickListener {
            openDetailActivity(createTrash("organic"))
        }

        binding.cardAnorganic.setOnClickListener {
            openDetailActivity(createTrash("anorganic"))
        }

        binding.buttonOrganic.setOnClickListener {
            openDetailActivity(createTrash("organic"))
        }

        binding.buttonAnorganic.setOnClickListener {
            openDetailActivity(createTrash("anorganic"))
        }
    }

    private fun openDetailActivity(trash: Trash) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_TRASH, trash)
        }
        startActivity(intent)
    }

    private fun createTrash(type: String): Trash {
        return when (type) {
            "organic" -> Trash(
                dataType = "Organic",
                description = "Organic waste includes food scraps, yard waste, and other biodegradable materials.",
                image = R.drawable.organik
            )

            "anorganic" -> Trash(
                dataType = "Anorganic",
                description = "Anorganic waste includes plastics, metals, glass, and other non-biodegradable materials.",
                image = R.drawable.anorganik
            )

            else -> throw IllegalArgumentException("Unknown trash type")
        }
    }

    companion object {
        val EXTRA_TRASH = "key_trash"
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
}
