//val window = PopupWindow(this)
//val view = layoutInflater.inflate(R.layout.popup_language_light, null)

//window.contentView = view
//val EnglishButton = view.findViewById<Button>(R.id.ButtonEngLight)
//EnglishButton.setOnClickListener{
//    window.dismiss()
//}
//window.showAsDropDown(spinner_theme_selection)


package com.designtesting.timelineappdev1

import android.R.attr.statusBarColor
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.system.exitProcess


class FirstTime : AppCompatActivity() {
    var userHasInteracted = false
    override fun onUserInteraction() {
        userHasInteracted = true
    }
    override fun onCreate(savedInstanceState: Bundle?) {



        //get preferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        when (sharedPreferences.getString("THEME", "LIGHT")) {
            "LIGHT" -> setDefaultNightMode(MODE_NIGHT_NO)
            "DARK" -> setDefaultNightMode(MODE_NIGHT_YES)
            "SYSTEM" -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        }


        fun isDarkThemeActive(activity: Activity): Boolean {
            return activity.resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        }
        when(isDarkThemeActive(this@FirstTime)){
            false -> setTheme(R.style.DarkStatusBarText)
            true -> setTheme(R.style.LightStatusBarText)
        }

        super.onCreate(savedInstanceState)

        setContentView(R.layout.first_startup_activity)

        val ThemeSpinner: Spinner = findViewById(R.id.spinner_theme_selection)
        when (sharedPreferences.getString("THEME", "LIGHT")) {
            "LIGHT" -> ThemeSpinner.setSelection(0, true)
            "DARK" -> ThemeSpinner.setSelection(1, true)
            "SYSTEM" -> ThemeSpinner.setSelection(2, true)
        }

        ThemeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (userHasInteracted) {
                    when (pos) {
                        0 -> editor.putString("THEME", "LIGHT")
                        1 -> editor.putString("THEME", "DARK")
                        2 -> editor.putString("THEME", "SYSTEM")
                    }
                    editor.apply()
                    Thread.sleep(350)
                    startActivity(
                        Intent(applicationContext, MainActivity::class.java)
                    )
                    exitProcess(0)
                }
            }
        }



        val LanguageSpinner: Spinner = findViewById(R.id.spinner_language_selection)
        when (sharedPreferences.getString("LANGUAGE", "ENGLISH")) {
            "ENGLISH" -> LanguageSpinner.setSelection(0, true)
            "JAPANESE" -> LanguageSpinner.setSelection(1, true)
        }
        LanguageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (userHasInteracted) {

                    when (pos) {
                        0 -> editor.putString("LANGUAGE", "ENGLISH")
                        1 -> editor.putString("LANGUAGE", "JAPANESE")
                    }
                    editor.apply()
                    Thread.sleep(400)
                    startActivity(
                        Intent(applicationContext, MainActivity::class.java)
                    )
                    exitProcess(0)
                }
            }
        }


        val GradeSpinner: Spinner = findViewById(R.id.spinner_grade_selection)
        when (sharedPreferences.getString("GRADE", "9")) {
            "9" -> GradeSpinner.setSelection(0)
            "8" -> GradeSpinner.setSelection(1)
            "7" -> GradeSpinner.setSelection(2)
            "OTHER" -> GradeSpinner.setSelection(3)

        }
        GradeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (userHasInteracted) {
                    when (pos) {
                        0 -> editor.putString("GRADE", "9")
                        1 -> editor.putString("GRADE", "8")
                        2 -> editor.putString("GRADE", "7")
                        3 -> editor.putString("GRADE", "OTHER")
                    }
                    editor.apply()
                }
            }
        }

        val ContinueButton: Button = findViewById(R.id.button_continue_first)
        ContinueButton.setOnClickListener {
            editor.putBoolean("FIRST_TIME", false)
            editor.apply()


            Thread.sleep(400)
            val myIntent = Intent(this@FirstTime, MainActivity::class.java)
            this@FirstTime.startActivity(myIntent)
            finish()
        }

    }
}
