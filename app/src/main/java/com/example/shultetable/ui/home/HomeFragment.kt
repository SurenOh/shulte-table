package com.example.shultetable.ui.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.shultetable.MainActivity
import com.example.shultetable.R
import com.example.shultetable.databinding.FragmentHomeBinding
import com.example.shultetable.model.RecordModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        setupObservers()
        viewModel.getAllRecords()
        onBackPressed()
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
            exit()
        }
        binding.recordsBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.goToRecords())
        }
    }

    private fun setupObservers() {
        viewModel.records.observe(viewLifecycleOwner) { records ->
            unlockLevels(records)
        }
    }

    private fun unlockLevels(records: List<RecordModel>) {
        for (element in records) {
            when (element.level) {
                "easy" -> {
                    binding.hardBtn.isClickable = true
                    binding.hardBtn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
                    binding.hardLevelLock.isVisible = false
                }
                "hard" -> {
                    binding.expertBtn.isClickable = true
                    binding.expertBtn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
                    binding.expertLevelLock.isVisible = false
                }
            }
        }
    }

    private fun onBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                return
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    private fun exit() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Exit")
        builder.setMessage("Do you want leave the game?")
        builder.setNegativeButton("No") { dialog, i ->

        }
        builder.setPositiveButton("Yes") { dialog, i ->
            (requireActivity() as? MainActivity)?.finish()
        }
        builder.show()
    }
}