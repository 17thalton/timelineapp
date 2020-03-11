package com.designtesting.timelineappdev1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_menu.*
import java.util.*


//Most commented and unused code is from my experimentation with CardView, but that's not working yet

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        //Retrieves saved variables

        //Set app theme based on saved variable
        when (sharedPreferences.getString("THEME", "LIGHT")) {
            "LIGHT" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "DARK" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "SYSTEM" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        super.onCreate(savedInstanceState)

        //Set app language based on saved variable
        when (sharedPreferences.getString("LANGUAGE", "ENGLISH")) {
            "ENGLISH" -> {
                val locale = Locale("en")
                Locale.setDefault(locale)
                val config = Configuration()
                config.locale = locale
                baseContext.resources.updateConfiguration(
                    config,
                    baseContext.resources.displayMetrics
                )
            }
            "JAPANESE" -> {
                val locale = Locale("ja")
                Locale.setDefault(locale)
                val config = Configuration()
                config.locale = locale
                baseContext.resources.updateConfiguration(
                    config,
                    baseContext.resources.displayMetrics
                )
            }
        }

        //Launch FirstTime activity (program) if user has not started the app before
        if (!sharedPreferences.getBoolean("FIRST_TIME", true)) {
            val myIntent = Intent(this@MainActivity, WebviewTest::class.java)
            this@MainActivity.startActivity(myIntent)
            finish()
//            setContentView(R.layout.main_menu)
            //          setupRecyclerView()
        }
        //Otherwise, launch another activity (WebViewTest as placeholder for now)
        else {
            val myIntent = Intent(this@MainActivity, FirstTime::class.java)
            this@MainActivity.startActivity(myIntent)
            finish()

        }
    }
}
