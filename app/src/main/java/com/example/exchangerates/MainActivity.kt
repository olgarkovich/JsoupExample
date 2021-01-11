package com.example.exchangerates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var doc: Document
    private lateinit var secondThread: Thread
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        runnable = Runnable { getWeb() }
        secondThread = Thread(runnable)
        secondThread.start()
    }

    private fun getWeb() {

        try {
            doc = Jsoup.connect("https://myfin.by/currency/minsk").get()

            val tables: Elements = doc.getElementsByTag("tbody")
            val table: Element = tables[0]
            Log.d("MyLog", "table : ${table.childrenSize()}")
            val tableElement = table.child(0)
            Log.d("MyLog", "table : ${tableElement.childrenSize()}")



        }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }
}