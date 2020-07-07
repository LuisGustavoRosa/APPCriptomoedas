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

    var btcAPI: MoedaAPI? = getObjectApi("btc")

    override fun onCreateView(


        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recycle: RecyclerView = root.findViewById(R.id.recycle_home)

        initRecyclerView(recycle)
        return root
    }

    override fun onStart() {
        super.onStart()
        updateAdapter()
        somar()
    }

    fun getObjectApi(moeda: String): MoedaAPI? {
        var ticker: MoedaAPI? = null
        val call: Call<MoedaAPI> = RetrofitConfig().getMoedaService().getMoeda(moeda)
        call.enqueue(object: Callback<MoedaAPI> {
            override fun onFailure(call: Call<MoedaAPI>, t: Throwable) {
                Log.e("onFailure error", t.message)
            }

            override fun onResponse(call: Call<MoedaAPI>, response: Response<MoedaAPI>) {
                ticker = response.body()?.ticker!!
            }
        })

        return ticker
    }

    private fun updateAdapter() {
        val methods = activity?.let { AtivosMethods(it) }
        listaAtivos.clear()
        if (methods != null) {
            listaAtivos = methods.get()
        }
        if(listaAtivos.isEmpty()) {
            recycle_home.visibility = View.GONE
            recycle_home.visibility = View.VISIBLE
            btc_msg.text = "Nenhum ativo adicionado para esta moeda, \n adicione em +"
        } else {
            recycle_home.visibility = View.VISIBLE
            home_msg.text = ""
        }
        recycle_home.adapter = AtivosAdapter(listaAtivos.reversed(), 1.1)
        recycle_home.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerView(recycle: RecyclerView) {
        val webView1: WebView? = null
        var webSettings: WebSettings? = null
        val content = ("<html>"
                + "  <head>"
                + "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>"
                + "    <script type=\"text/javascript\">"
                + "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
                + "      google.setOnLoadCallback(drawChart);"
                + "      function drawChart() {"
                + "        var data = google.visualization.arrayToDataTable(["
                + "          ['Year', 'Sales', 'Expenses'],"
                + "          ['2010',  1000,      400],"
                + "          ['2011',  1170,      460],"
                + "          ['2012',  660,       1120],"
                + "          ['2013',  1030,      540]"
                + "        ]);"
                + "        var options = {"
                + "          title: 'Truiton Performance',"
                + "          hAxis: {title: 'Year', titleTextStyle: {color: 'red'}}"
                + "        };"
                + "        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));"
                + "        chart.draw(data, options);"
                + "      }"
                + "    </script>"
                + "  </head>"
                + "  <body>"
                + "    <div id=\"chart_div\" style=\"width: " + (webView1?.width  ) + "px; height: " + (webView1?.height) + "px;\"></div>"
                + "	   <img style=\"padding: 0; margin: 0 0 0 330px; display: block;\" src=\"truiton.png\"/>"
                + "  </body>" + "</html>")




        if (webView1 != null) {
            webSettings = webView1.settings
        }
        if (webSettings != null) {
            webSettings.javaScriptEnabled = true
        }
        if (webView1 != null) {
            webView1.requestFocusFromTouch()
        }
        if (webView1 != null) {
            webView1.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "utf-8", null)
        }



        val adapter_ = AtivosAdapter(listaAtivos.reversed(),1.0)
        val layout = LinearLayoutManager(activity)
        recycle.setHasFixedSize(true)
        recycle.adapter = adapter_
        recycle.layoutManager = layout
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