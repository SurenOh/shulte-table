package com.example.shultetable.ui.home

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shultetable.R
import com.example.shultetable.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {
        binding.easyBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.goToGame("easy"))
        }
        binding.hardBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.goToGame("hard"))
        }
        binding.expertBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.goToGame("expert"))
        }
        binding.exitBtn.setOnClickListener {

        }
        binding.recordsBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.goToRecords())
        }
    }

}