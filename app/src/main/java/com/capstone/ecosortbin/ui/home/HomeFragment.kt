package com.capstone.ecosortbin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.ecosortbin.databinding.FragmentHomeBinding
import com.capstone.ecosortbin.ui.AnorganicActivity
import com.capstone.ecosortbin.ui.OrganicActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOrganic.setOnClickListener {
            val intent = Intent(activity, OrganicActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAnorganic.setOnClickListener {
            val intent = Intent(activity, AnorganicActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
