package com.capstone.ecosortbin.ui.detail

import com.capstone.ecosortbin.R
import com.capstone.ecosortbin.Trash
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.ecosortbin.databinding.ActivityDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class DetailActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityDetailBinding
//    private lateinit var navController: NavController
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        navController = findNavController(R.id.nav_host_fragment_activity_main)
//        val navView: BottomNavigationView = binding.navView
//
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//
//        val trash = if (Build.VERSION.SDK_INT >= 33) {
//            intent.getParcelableExtra(MainActivity.EXTRA_TRASH, Trash::class.java)
//        } else {
//            @Suppress("DEPRECATION")
//            intent.getParcelableExtra(MainActivity.EXTRA_TRASH)
//        }
//
//        trash?.let {
//            binding.imgItemPhoto.setImageResource(it.image)
//            binding.tvItemName.text = it.dataType
//            binding.tvDescription.text = it.description
//        }
//    }
}
