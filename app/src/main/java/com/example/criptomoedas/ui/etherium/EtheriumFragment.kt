package com.example.criptomoedas.ui.etherium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.criptomoedas.R

class EtheriumFragment : Fragment() {

    private lateinit var etheriumViewModel: EtheriumViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        etheriumViewModel =
                ViewModelProviders.of(this).get(EtheriumViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_etherium, container, false)
        val textView: TextView = root.findViewById(R.id.text_ltc)
        etheriumViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
