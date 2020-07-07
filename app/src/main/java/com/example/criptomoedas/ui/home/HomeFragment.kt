package com.example.criptomoedas.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criptomoedas.AtivosAdapter
import com.example.criptomoedas.R
import com.example.criptomoedas.api.MoedaAPI
import com.example.criptomoedas.api.RetrofitConfig
import com.example.criptomoedas.methods.Ativos
import com.example.criptomoedas.methods.AtivosMethods
import kotlinx.android.synthetic.main.fragment_bitcoin.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
class HomeFragment : Fragment() {

    private var listaAtivos = mutableListOf<Ativos>()
    private var total: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onStart() {
        super.onStart()
        getListaDB()
        somar()
    }

//    private fun updateAdapter() {
//        val methods = activity?.let { AtivosMethods(it) }
//        listaAtivos.clear()
//        if (methods != null) {
//            listaAtivos = methods.get()
//        }
//        if(listaAtivos.isEmpty()) {
//            recycle_home.visibility = View.GONE
//            recycle_home.visibility = View.VISIBLE
//            btc_msg.text = "Nenhum ativo adicionado, \n adicione dentro da moeda correspondente"
//        } else {
//            recycle_home.visibility = View.VISIBLE
//            home_msg.text = ""
//            recycle_home.adapter = AtivosAdapter(listaAtivos.reversed(), 1.1)
//            recycle_home.adapter?.notifyDataSetChanged()
//        }
//    }

//    private fun initRecyclerView(recycle: RecyclerView) {
//        val adapter_ = AtivosAdapter(listaAtivos.reversed(),1.0)
//        val layout = LinearLayoutManager(activity)
//        recycle.setHasFixedSize(true)
//        recycle.adapter = adapter_
//        recycle.layoutManager = layout
//    }

    fun getListaDB() {
        val methods = activity?.let { AtivosMethods(it) }
        listaAtivos.clear()
        if (methods != null) {
            listaAtivos = methods.get()
        }
    }

    fun somar() {
        total = 0.0
        for(position in 0 .. listaAtivos.size - 1) {
            val ativo = listaAtivos[position]
            total += ativo.valor
        }
        val valor = DecimalFormat("#,##0.00").format(total)
        total_home.text = "R$ ${valor}"
    }
}