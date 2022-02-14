package com.example.rockpaperscissors.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.databinding.ActivityMenuBinding
import com.example.rockpaperscissors.preference.UserPreference
import com.example.rockpaperscissors.ui.game.MainActivity
import com.google.android.material.snackbar.Snackbar

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewsMenu()
        setClickListener()
        setTitle()
        showSnackBar()
        isFirstAppOpen()
    }

    private fun bindViewsMenu() {
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    private fun setTitle() {
        binding.tvChooseToPlay.text = String.format(
            getString(R.string.text_title_menu).uppercase(),
            UserPreference(this).name
        )
    }

    private fun showSnackBar() {
        val namePlayer = String.format(getString(R.string.tv_player), UserPreference(this).name)
        Snackbar.make(binding.root, "Welcome $namePlayer", Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.text_snackbar_close)) {
                Snackbar.Callback.DISMISS_EVENT_ACTION
            }.setTextColor(resources.getColor(R.color.black))
            .setBackgroundTint(resources.getColor(R.color.white)).show()
    }

    private fun setClickListener() {
        binding.ivPlayerVsPlayer.setOnClickListener {
            MainActivity.startThisActivity(this, MainActivity.PLAY_MODE_VS_PLAYER)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        binding.ivPlayerVsCom.setOnClickListener {
            MainActivity.startThisActivity(this, MainActivity.PLAY_MODE_VS_COMPUTER)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
    private fun isFirstAppOpen() {
        UserPreference(this).isAppOpenedFirstTime = false
    }
}