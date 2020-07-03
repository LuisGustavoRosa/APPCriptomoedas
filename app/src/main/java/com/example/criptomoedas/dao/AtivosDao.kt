package com.example.criptomoedas.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.criptomoedas.db.*

class AtivosDao (context : Context) {
    var db_ = DbHelper(context)

    fun insert(ativos: Ativos): String {
        val db_ = db_.writableDatabase
        val values = ContentValues()
        values.put(MOEDA, ativos.moeda)
        values.put(QUANTIDADE, ativos.quantidade)
        values.put(VALOR, ativos.valor)

        val resp = db_.insert(TABLE_ATIVO, null, values)
        val msg = if (resp != -1L) {

            "Inserido com sucesso"

        } else {
            "Erro ao inserir"
        }
        db_.close()
        return msg
    }

    //fun update

    fun remove(ativos: Ativos): Int {
        val db = db_.writableDatabase
        return db.delete(TABLE_ATIVO, "ID =?", arrayOf(ativos.id.toString()))
    }

    fun get(): ArrayList<Ativos> {
        val db = db_.writableDatabase
        val sql = "SELECT *from ${TABLE_ATIVO}"
        val cursor = db.rawQuery(sql, null)
        val lista = ArrayList<Ativos>()
        while (cursor.moveToNext()) {
            val ativo = ativoFromCursor(cursor)
            lista.add(ativo)
        }
        cursor.close()
        db.close()
        return  lista
    }

    private fun ativoFromCursor(cursor: Cursor): Ativos {
        val id = cursor.getInt(cursor.getColumnIndex(ID))
        val moeda = cursor.getString(cursor.getColumnIndex(MOEDA))
        val quantidade = cursor.getDouble(cursor.getColumnIndex(QUANTIDADE))
        val valor = cursor.getDouble(cursor.getColumnIndex(VALOR))
        val data = cursor.getString(cursor.getColumnIndex(DATA))
        return Ativos (id, moeda, quantidade, valor, data)

    }
    fun getByProduto(ativos: Ativos): ArrayList<Ativos> {
        val db = db_.writableDatabase
        val sql = "SELECT * from ${TABLE_ATIVO} WHERE ${MOEDA} LIKE '%moeda%'"
        val cursor = db.rawQuery(sql ,null)
        val lista = ArrayList<Ativos>()

        while(cursor.moveToNext()) {
            val moeda__ = ativoFromCursor(cursor)
            lista.add(moeda__)
        }

        cursor.close()
        db.close()
        return lista
    }
}