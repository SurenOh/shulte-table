package com.example.shultetable.ui.records

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.shultetable.databinding.FragmentRecordBinding

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding

    private val viewModel: RecordViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRecordBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupButtons()
        viewModel.getAllRecords()
    }

    private fun setupButtons() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObservers() {
        viewModel.records.observe(viewLifecycleOwner) { records ->
            records.forEach { model ->
                when (model.level) {
                    "easy" -> {
                        binding.easyResultTv.text = "${binding.easyResultTv.text}${getTimeFromMillis(model.millis)}"
                    }
                    "hard" -> {
                        binding.hardResultTv.text = "${binding.hardResultTv.text}${getTimeFromMillis(model.millis)}"
                    }
                    "expert" -> {
                        binding.expertResultTv.text = "${binding.expertResultTv.text}${getTimeFromMillis(model.millis)}"
                    }
                }
            }
        }
    }

    private fun getTimeFromMillis(millis: Long): String {
        var minutes = "00"
        var seconds = "00"
        if (millis >= 60_000) {
            minutes = (millis / 60_000).toString()
            seconds = (millis % 60_000 / 1000).toString()
        } else {
            seconds = (millis / 1000).toString()
        }
        return "$minutes:$seconds"
    }

}