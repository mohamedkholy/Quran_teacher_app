package com.alda3ia.ES.quranlearn.ui

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.alda3ia.ES.quranlearn.R
import com.alda3ia.ES.quranlearn.adapters.SurasAdapter
import com.alda3ia.ES.quranlearn.database.Dao
import com.alda3ia.ES.quranlearn.databinding.FragmentsItemsBinding
import com.alda3ia.ES.quranlearn.model.BaseFragment
import com.alda3ia.ES.quranlearn.model.OnListItemClickListener
import com.alda3ia.ES.quranlearn.model.Sura
import com.alda3ia.ES.quranlearn.viewModels.SurasViewModel


class SurasFragment : BaseFragment<FragmentsItemsBinding>(R.layout.fragments_items),OnListItemClickListener {
    override val binder: (View) -> FragmentsItemsBinding
        get() = FragmentsItemsBinding::bind
    val args:SurasFragmentArgs by navArgs()
    private lateinit var  db: Dao
    lateinit var list:ArrayList<Sura>
    lateinit var surasViewModel:SurasViewModel


    override fun setup() {
        surasViewModel=ViewModelProvider(this).get(SurasViewModel::class.java)
    }

    override fun addCallbacks() {

        binding.backButton.setOnClickListener {
            requireView().findNavController().popBackStack()
        }

        surasViewModel.getSuraByLevel(args.level)
        surasViewModel.liveData.observe(this){
            if(args.level==1)
                binding.title.text=getText(R.string.level_1)
            else
                binding.title.text=getText(R.string.level_2)

            list=it
            binding.recv.adapter=SurasAdapter(this,list)
        }


    }

    override fun onClick(aya: String?, sura: Sura?) {
        if(sura?.ayaCount==0){
            val videoItem=surasViewModel.getItem("AlIstiadha")
            val action=SurasFragmentDirections.actionSurasFragmentToVideoFragment(videoItem,sura.name)
            requireView().findNavController().navigate(action)

            return
        }
        val action=SurasFragmentDirections.actionSurasFragmentToAyatFragment(suraName = sura!!.name, AyatCount = sura.ayaCount)
        requireView().findNavController().navigate(action)
    }

}