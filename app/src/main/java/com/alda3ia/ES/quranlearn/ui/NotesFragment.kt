package com.alda3ia.ES.quranlearn.ui


import android.view.View
import androidx.navigation.findNavController
import com.alda3ia.ES.quranlearn.R
import com.alda3ia.ES.quranlearn.databinding.FragmentNotesBinding
import com.alda3ia.ES.quranlearn.model.BaseFragment


class NotesFragment : BaseFragment<FragmentNotesBinding>(R.layout.fragment_notes) {
    override val binder: (View) -> FragmentNotesBinding
        get() = FragmentNotesBinding::bind

    override fun setup() {

    }

    override fun addCallbacks() {
        binding.backButton.setOnClickListener {
            requireView().findNavController().popBackStack()
        }
    }

}