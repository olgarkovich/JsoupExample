package com.example.exchangerates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_converter, R.id.navigation_home, R.id.navigation_bank
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}


//    private fun init() {
//
//        runnable = Runnable { getWeb() }
//        secondThread = Thread(runnable)
//        secondThread.start()
//    }
//
//    private fun getWeb() {
//
//        try {
//            doc = Jsoup.connect("https://myfin.by/currency/minsk").get()
//
//            val tables: Elements = doc.getElementsByTag("tbody")
//            val table: Element = tables[0]
//            Log.d("MyLog", "table : ${doc.title()}")
//            val tableElement = table.child(0)
//            Log.d("MyLog", "table : ${tableElement.childrenSize()}")
//
//
//
//        }
//        catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
