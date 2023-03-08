package com.example.shultetable.ui.web

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shultetable.databinding.FragmentWebBinding

class WebFragment : Fragment() {
    private lateinit var binding: FragmentWebBinding

    private val args: WebFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWebBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        setupWebView()
    }

    private fun setupButtons() {
        binding.backToGame.setOnClickListener {
            findNavController().navigate(WebFragmentDirections.webToHome())
        }
    }

    private fun setupWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            settings.setSupportZoom(true)
            loadUrl(args.link)

            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (canGoBack()) {
                        goBack()
                    }
                }
            }
            activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
        }


    }

}