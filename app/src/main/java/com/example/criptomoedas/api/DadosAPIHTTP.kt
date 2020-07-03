package com.example.criptomoedas.api

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object DadosAPIHTTP {
    fun hasConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }

    fun loadAPI(): DadosAPI {
        val URL = "https://www.mercadobitcoin.net/api/BTC/ticker/"

        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        val req = Request.Builder()
            .url(URL)
            .build()
        val res =  client.newCall(req).execute()

        val jsonString =res.body?.string()
        val jsonObject = JSONObject(jsonString)
        val json = jsonObject.getJSONObject("ticker")
        return getJsonObject((json))

        val dados = DadosAPI(
            high = json.optDouble("high"),
            low = json.optDouble("low"),
            vol = json.optDouble("vol"),
            price = json.optDouble("last"),
            buy = json.optDouble("buy"),
            sell = json.optDouble("sell"),
            date = json.optDouble("date")
            )
        }

    fun getJsonObject(json: JSONObject): DadosAPI {
        //val date_ = formatarData(json.optDouble("date").toString())
        val dados= DadosAPI(
            high = json.optDouble("high"),
            low = json.optDouble("low"),
            vol = json.optDouble("vol"),
            price = json.optDouble("last"),
            buy = json.optDouble("buy"),
            sell = json.optDouble("sell"),
            date = json.optDouble("date")
        )

        return dados
    }
    fun formatarData(data: String): String {
        val diaString =data
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var date = LocalDate.parse(diaString)
        var formattedDate = date.format(formatter)
        return formattedDate
    }

}