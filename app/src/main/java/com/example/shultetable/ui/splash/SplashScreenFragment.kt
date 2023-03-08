package com.example.shultetable.ui.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shultetable.databinding.FragmentSplashScreenBinding
import com.example.shultetable.firebase.LINK_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadInfo()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.pairInfo.observe(viewLifecycleOwner) { pair ->
            checkFirstLaunch(pair)
        }
    }

    private fun checkFirstLaunch(pair: Pair<String, Boolean>) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val isFirstLaunch = sharedPref.getBoolean(FIRST_LAUNCH, true)
        var link = sharedPref.getString(LINK_KEY, pair.first) ?: ""
        if (link == "") {
            link = pair.first
        }
        if (isFirstLaunch) {
            with (sharedPref.edit()) {
                putBoolean(FIRST_LAUNCH, false)
                putString(LINK_KEY, pair.first)
                apply()
            }
            if (pair.second) {
                findNavController().navigate(SplashScreenFragmentDirections.goToWeb(link))
            } else {
                findNavController().navigate(SplashScreenFragmentDirections.splashToHome())
            }
        } else {
            findNavController().navigate(SplashScreenFragmentDirections.goToWeb(link))
        }
    }

}