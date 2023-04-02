package com.alda3ia.ES.quranlearn.ui


import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.alda3ia.ES.quranlearn.R
import com.alda3ia.ES.quranlearn.database.Dao
import com.alda3ia.ES.quranlearn.database.MyDatabase
import com.alda3ia.ES.quranlearn.databinding.FragmentHomeBinding
import com.alda3ia.ES.quranlearn.model.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val binder: (View) -> FragmentHomeBinding
        get() = FragmentHomeBinding::bind
    private lateinit var  db:Dao



    override fun setup() {

        db=MyDatabase.getInstance(requireContext().applicationContext).dao()

        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        setHasOptionsMenu(true)



    }

    override fun addCallbacks() {

        binding.noteButton.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_homeFragment_to_notesFragment)
        }

        binding.keysButton.apply {
            isSelected=false
            setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToVideoFragment(
                    db.getItem("keys"),
                    "Pronounciation key"
                )
                requireView().findNavController().navigate(action)
            }
        }

        binding.level1Button.setOnClickListener {
               goToSurahFragment(1)
        }

        binding.level2Button.setOnClickListener {
            goToSurahFragment(2)
        }


    }

    private fun goToSurahFragment(i: Int) {
       val action=HomeFragmentDirections.actionHomeFragmentToSurasFragment(i)
       requireView().findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
          R.id.about->requireView().findNavController().navigate(R.id.action_homeFragment_to_aboutUsFragment)
        }
        return true

    }





}