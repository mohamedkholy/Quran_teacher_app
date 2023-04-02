package com.alda3ia.ES.quranlearn.model

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB:ViewBinding>(id:Int) : Fragment(id) {

    abstract  val binder:(View)->VB
    protected lateinit var binding: VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=binder(view)

        setup()
        addCallbacks()
    }

    abstract fun setup()

    abstract fun addCallbacks()

}