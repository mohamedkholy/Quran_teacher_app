package com.alda3ia.ES.quranlearn.ui


import android.content.Intent
import android.view.View
import android.widget.MediaController
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alda3ia.ES.quranlearn.R
import com.alda3ia.ES.quranlearn.database.Dao
import com.alda3ia.ES.quranlearn.database.MyDatabase
import com.alda3ia.ES.quranlearn.databinding.FragmentVideoBinding
import com.alda3ia.ES.quranlearn.model.BaseFragment


class ListenSuraFragment : BaseFragment<FragmentVideoBinding>(R.layout.fragment_video) {
    override val binder: (View) -> FragmentVideoBinding
        get() = FragmentVideoBinding::bind
    val args:ListenSuraFragmentArgs by navArgs()
    private lateinit var  db: Dao
    lateinit var mediaController:MediaController

    override fun setup() {
        binding.downloadButton.visibility=View.GONE
        binding.view.visibility=View.GONE
        binding.translationText.visibility=View.GONE
        db= MyDatabase.getInstance(requireContext().applicationContext).dao()
        mediaController=MediaController(requireContext()).apply {setAnchorView(binding.videoPlayer)}
        binding.title.text="Surah ${args.suraName}"
        setArabicText(db.getAllAyas(args.suraName.filterNot { it=='-' }))

    }

    private fun setArabicText(allAyas: List<String>) {
        val size= if(args.suraName=="an-Nas") 6 else allAyas.size
        for (i in 0 until size ){
            binding.arabicText.append("${allAyas[i]} (${i+1}) ")

        }
    }

    override fun addCallbacks() {

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
            mediaController.hide()
        }
        binding.playButton.setOnClickListener {
            binding.videoPlayer.setMediaController(mediaController)
            binding.videoPlayer.setVideoURI(args.suraPath.toUri())
            binding.videoPlayer.start()
            mediaController.show(500)
            it.visibility=View.GONE
        }

        binding.whatsupButton.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=+20 1110371247"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = url.toUri()
            startActivity(i)
        }

        binding.messengerButton.setOnClickListener {
            val url="https://m.me/bassem2442"
            startActivity(Intent(Intent.ACTION_VIEW,url.toUri()))
        }

    }



}