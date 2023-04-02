package com.alda3ia.ES.quranlearn.ui




import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.alda3ia.ES.quranlearn.R
import com.alda3ia.ES.quranlearn.adapters.AyatAdapter
import com.alda3ia.ES.quranlearn.databinding.FragmentsItemsBinding
import com.alda3ia.ES.quranlearn.model.BaseFragment
import com.alda3ia.ES.quranlearn.model.OnListItemClickListener
import com.alda3ia.ES.quranlearn.model.Sura
import com.alda3ia.ES.quranlearn.viewModels.SurasViewModel


class AyatFragment : BaseFragment<FragmentsItemsBinding>(R.layout.fragments_items),
    OnListItemClickListener {
    override val binder: (View) -> FragmentsItemsBinding
        get() = FragmentsItemsBinding::bind

    val args:AyatFragmentArgs by navArgs()
    lateinit var surahName:String
    var ayatCount:Int=0
    lateinit var surasViewModel: SurasViewModel

    override fun setup() {
        surasViewModel= ViewModelProvider(this).get(SurasViewModel::class.java)
        surahName=args.suraName
        ayatCount=args.AyatCount
        binding.title.text=surahName
        binding.recv.adapter=AyatAdapter(this,ayatCount+1)
    }

    override fun addCallbacks() {
        binding.backButton.setOnClickListener {
            requireView().findNavController().popBackStack()
        }
    }

    override fun onClick(aya: String?, sura: Sura?) {
        val action:NavDirections
        action = if(aya=="Aya 0"){
            val path = "android.resource://" + requireActivity().packageName + "/" +ResourceId(surahName)
            AyatFragmentDirections.actionAyatFragmentToListenSuraFragment2(path,surahName)
        }
        else{
            val videoItem=surasViewModel.getItem("${surahName}_${aya?.lowercase()}".filterNot { it.isWhitespace()||it.equals('-') })
            AyatFragmentDirections
                .actionAyatFragmentToVideoFragment(
                    videoItem,
                    "${surahName} $aya"
                )
        }
        requireView().findNavController().navigate(action)
    }

    private fun ResourceId(raw_name: String): Int {
        val resName="${surahName.filterNot { it=='-' }.lowercase()}"
        val resId = resources.getIdentifier(resName, "raw", requireActivity().packageName)
        return resId
    }

}