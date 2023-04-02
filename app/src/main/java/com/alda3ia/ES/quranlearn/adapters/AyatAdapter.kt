package com.alda3ia.ES.quranlearn.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.alda3ia.ES.quranlearn.R
import com.alda3ia.ES.quranlearn.databinding.AyatButtonItemBinding
import com.alda3ia.ES.quranlearn.model.OnListItemClickListener

class AyatAdapter(val listener: OnListItemClickListener, val AyatCount:Int) : RecyclerView.Adapter<AyatAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding=AyatButtonItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.ayat_button_item,parent,false))
        return Holder(binding)
    }

    override fun getItemCount() = AyatCount

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.binding.button.apply {
            if (position > 0){
                text = "Aya ${position}"
                icon=AppCompatResources.getDrawable(context,R.drawable.star)
            }
            else {
                icon=AppCompatResources.getDrawable(context,R.drawable.quran__1_)
                text = context.getString(R.string.Listen_full_sura)
            }

            setOnClickListener {
                listener.onClick(aya = "Aya ${position}")
            }
        }

    }
    class Holder(val binding: AyatButtonItemBinding):RecyclerView.ViewHolder(binding.root)





}