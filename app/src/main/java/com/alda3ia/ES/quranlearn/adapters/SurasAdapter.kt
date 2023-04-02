package com.alda3ia.ES.quranlearn.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.alda3ia.ES.quranlearn.R
import com.alda3ia.ES.quranlearn.databinding.SuraButtonItemBinding
import com.alda3ia.ES.quranlearn.model.OnListItemClickListener
import com.alda3ia.ES.quranlearn.model.Sura

class SurasAdapter(val listener: OnListItemClickListener,val list:List<Sura>) :RecyclerView.Adapter<SurasAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
         val binding=SuraButtonItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.sura_button_item,parent,false))
        return Holder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.button.apply{
            text = if(list[position].name!="Al-Istiadha"){
                icon=AppCompatResources.getDrawable(context,R.drawable.quran__1_)
                "Surah ${list[position].name} - ${list[position].number}"

            }
            else{
                icon=AppCompatResources.getDrawable(context,R.drawable.star)
                "Ayat ${list[position].name}"}
        }

        holder.binding.button.setOnClickListener {
            listener.onClick(sura = list[position])
        }
    }

    class Holder(val binding: SuraButtonItemBinding):RecyclerView.ViewHolder(binding.root)



}