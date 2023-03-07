package com.example.shultetable.ui.game

import android.opengl.Visibility
import android.os.Bundle
import android.os.SystemClock
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shultetable.R
import com.example.shultetable.databinding.FragmentGameBinding
import com.example.shultetable.model.RecordModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding

    private val args: GameFragmentArgs by navArgs()
    private val viewModel: GameViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGameBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGameLevel(args.level)
        setupButtons()
        setupObservers()
        viewModel.startGame()
    }

    private fun saveResult() {
        binding.resultTime.setOnChronometerTickListener {
            val elapsedMillis = (SystemClock.elapsedRealtime() - binding.resultTime.base)
            viewModel.saveResultTime(RecordModel(args.level, elapsedMillis))
        }
    }

    private fun setupObservers() {
        viewModel.currentNumber.observe(viewLifecycleOwner) { currentNumber ->
           binding.currentNumber.text = currentNumber.toString()
        }
    }

    private fun setupGameLevel(level: String) {
        when (level) {
            "easy" -> {
                setupGameTable(EASY_COLUMN_COUNT, EASY_ROW_COUNT)
            }
            "hard" -> {
                setupGameTable(HARD_COLUMN_COUNT, HARD_ROW_COUNT)
            }
            "expert" -> {
                setupGameTable(EXPERT_COLUMN_COUNT, EXPERT_ROW_COUNT)
            }
        }
    }

    private fun setupButtons() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.endBtn.setOnClickListener {
            findNavController().navigate(GameFragmentDirections.actionGameFragmentToHomeFragment())
        }
    }

    private fun setupGameTable(gameColumns: Int, gameRows: Int) {
        binding.gameTable.apply {
            columnCount = gameColumns
            rowCount = gameRows
        }
        val numbers = fillNumbers(gameColumns * gameRows)

        for (i in 0 until gameRows) {
            for (j in 0 until gameColumns) {
                val randomNumber = numbers.random()
                val row = GridLayout.spec(i, 1f)
                val column = GridLayout.spec(j, 1f)
                val numberTv = createTextView(randomNumber)
                numberTv.setOnClickListener {
                    viewModel.checkNumber(randomNumber, gameColumns * gameRows)
                    if (randomNumber == gameColumns * gameRows) {
                        binding.resultTime.stop()
                        saveResult()
                        with(binding.victoryTv) {
                            binding.endBtn.isVisible = true
                            isVisible = true
                            text = binding.resultTime.text
                        }
                    }
                }
                binding.gameTable.addView(numberTv, GridLayout.LayoutParams(row, column))
                numbers.remove(randomNumber)
            }
        }
        binding.resultTime.start()
    }

    private fun createTextView(number: Int): TextView {
         return TextView(requireContext()).apply {
            text = number.toString()
            setBackgroundResource(R.drawable.rectangle_background)
            textSize = 24f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }

    private fun fillNumbers(number: Int): HashSet<Int> {
        val result = HashSet<Int>()
        for (symbol in 1..number) {
            result.add(symbol)
        }
        return result
    }

}