package com.alda3ia.ES.quranlearn.ui

import android.content.Intent
import android.view.View
import androidx.core.net.toUri
import androidx.navigation.findNavController
import com.alda3ia.ES.quranlearn.R
import com.alda3ia.ES.quranlearn.databinding.FragmentAboutUsBinding
import com.alda3ia.ES.quranlearn.model.BaseFragment


class AboutUsFragment : BaseFragment<FragmentAboutUsBinding>(R.layout.fragment_about_us) {
    override val binder: (View) -> FragmentAboutUsBinding
        get() = FragmentAboutUsBinding::bind

    override fun setup() {

    }

    override fun addCallbacks() {

        binding.backButton.setOnClickListener {
            requireView().findNavController().popBackStack()
        }

        binding.youtubeButton.setOnClickListener {

            openLink( "https://www.youtube.com/@at-tanzilacademy4539")
        }


        binding.whatsupButton.setOnClickListener {
            openLink( "https://api.whatsapp.com/send?phone=+20 1110371247")
        }

        binding.messengerButton.setOnClickListener {
            openLink("https://m.me/bassem2442")
        }

    }

    private fun openLink(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    }

}