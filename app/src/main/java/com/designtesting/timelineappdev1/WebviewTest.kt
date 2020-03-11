package com.designtesting.timelineappdev1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.webview.*
import java.util.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.appcompat.*
import androidx.fragment.app.FragmentActivity


class WebviewTest : AppCompatActivity() {


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setTheme(R.style.DarkStatusBarText)
        val sharedPreferences: SharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)

        when (sharedPreferences.getString("THEME", "LIGHT")) {
            "LIGHT" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "DARK" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "SYSTEM" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        fun isDarkThemeActive(activity: Activity): Boolean {
            return activity.resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        }
        when(isDarkThemeActive(this@WebviewTest)){
            false -> setTheme(R.style.DarkStatusBarText)
            true -> setTheme(R.style.LightStatusBarText)
        }
        super.onCreate(savedInstanceState)




        setContentView(R.layout.webview)
        val webViewthingy = findViewById<WebView>(R.id.webView)
        webView!!.settings.javaScriptEnabled = true
        webView.settings.saveFormData = false
        webView.settings.savePassword = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webViewthingy.importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if ("managebac" in url.toString().toLowerCase(locale = Locale.ENGLISH)) {
                    view?.loadUrl(url)
                } else {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(url)
                    startActivity(openURL)

                }
                return true
            }
        }


        webViewthingy.loadUrl("https://oyis.managebac.com/login")


    }

    override fun onBackPressed() {
        super.onBackPressed()

        val myIntent = Intent(this@WebviewTest, FirstTime::class.java)
        this@WebviewTest.startActivity(myIntent)
    }
}

