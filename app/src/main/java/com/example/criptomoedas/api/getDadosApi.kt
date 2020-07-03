package com.example.criptomoedas.api

import android.os.AsyncTask
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_home.*

 class getDadosApi {
    fun teste(): DadosAPI {
         var asyncTask: getDadosApi.EstatisticasTask? = null
        lateinit var dados: DadosAPI
        EstatisticasTask()
        return dados




    }
    class EstatisticasTask : AsyncTask<Void, DadosAPI, DadosAPI>() {
        lateinit var dados: DadosAPI
        private var asyncTask: getDadosApi.EstatisticasTask? = null

        override fun onPreExecute() {
            super.onPreExecute()

        }

        override fun doInBackground(vararg params: Void?): DadosAPI {
            return DadosAPIHTTP.loadAPI()
        }

        override fun onPostExecute(resultado: DadosAPI?) {
            super.onPostExecute(resultado)
            atualizarEstatisticas(resultado)


        }

        private fun atualizarEstatisticas(resultado: DadosAPI?) {
            if (resultado != null) {
                dados = resultado
            }
        }

        private fun carregarEstatisticas() {
            if(asyncTask == null) {
                if(asyncTask?.status != AsyncTask.Status.RUNNING) {
                    asyncTask = EstatisticasTask()
                    asyncTask?.execute()
                }
            }
        }



    }

}