package com.example.shultetable.ui.game

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

    private fun setupObservers() {
        viewModel.currentNumber.observe(viewLifecycleOwner) { currentNumber ->
            binding.currentNumber.text = currentNumber.toString()
        }
    }

    private fun setupGameLevel(level: String) {
        when (level) {
            "easy" -> setupGameTable(EASY_COLUMN_COUNT, EASY_ROW_COUNT)
            "hard" -> setupGameTable(HARD_COLUMN_COUNT, HARD_ROW_COUNT)
            "expert" -> setupGameTable(EXPERT_COLUMN_COUNT, EXPERT_ROW_COUNT)
        }
    }

    private fun setupButtons() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.endBtn.setOnClickListener {
            findNavController().navigate(GameFragmentDirections.gameToHome())
        }
    }

    private fun setupGameTable(gameColumns: Int, gameRows: Int) {
        binding.gameTable.apply {
            columnCount = gameColumns
            rowCount = gameRows
        }
        val allNumbers = fillNumbers(gameColumns * gameRows)

        for (i in 0 until gameRows) {
            for (j in 0 until gameColumns) {
                val randomNumber = allNumbers.random()
                val numberTv = createTextView(randomNumber)
                binding.gameTable.addView(
                    numberTv,
                    GridLayout.LayoutParams(GridLayout.spec(i, 1f), GridLayout.spec(j, 1f))
                )
                allNumbers.remove(randomNumber)
                numberTv.setOnClickListener {
                    val currentNumber = binding.currentNumber.text.toString().toInt()
                    if (randomNumber == gameColumns * gameRows && randomNumber == currentNumber) {
                        binding.resultTime.stop()
                        viewModel.saveResultTime(
                            RecordModel(
                                args.level,
                                SystemClock.elapsedRealtime() - binding.resultTime.base
                            )
                        )
                        showEndGame()
                    }
                    viewModel.checkNumber(randomNumber, gameColumns * gameRows)
                }
            }
        }
        binding.resultTime.start()
    }

    private fun showEndGame() {
        binding.endBtn.isVisible = true
        binding.victoryTv.isVisible = true
        binding.victoryTv.text = binding.resultTime.text
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

    private fun fillNumbers(number: Int): ArrayList<Int> {
        val result = ArrayList<Int>()
        for (index in 1..number) {
            result.add(index)
        }
        return result
    }

}