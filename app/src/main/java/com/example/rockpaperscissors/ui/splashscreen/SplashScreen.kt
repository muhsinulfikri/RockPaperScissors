package com.example.rockpaperscissors.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.preference.UserPreference
import com.example.rockpaperscissors.ui.intro.IntroActivity
import com.example.rockpaperscissors.ui.menu.MenuActivity

class SplashScreen : AppCompatActivity() {

    private val timer: CountDownTimer by lazy {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if (UserPreference(this@SplashScreen).isAppOpenedFirstTime) {
                    val intent = Intent(this@SplashScreen, IntroActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val intent = Intent(this@SplashScreen, MenuActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setSplashTimer()
    }

    private fun setSplashTimer() {
        timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }


}