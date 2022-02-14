package com.example.rockpaperscissors.ui.game

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.databinding.ActivityMainBinding
import com.example.rockpaperscissors.enum.*
import com.example.rockpaperscissors.preference.UserPreference
import com.example.rockpaperscissors.ui.menu.MenuActivity

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var binding: ActivityMainBinding
    private lateinit var userTwo: User
    private lateinit var userOne: User
    private var isGameFinished: Boolean = false

    private var playMode: Int = PLAY_MODE_VS_COMPUTER
    private var playTurn: UserPosition = UserPosition.LEFT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
        userTwo = User()
        userOne = User()
        playGame()
    }

    private fun bindViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    private fun playGame() {
        getIntentExtras()
        setNamePlayer()
        setInitialState()
        setClickListeners()
    }

    private fun getIntentExtras() {
        playMode = intent.getIntExtra(ARG_EXTRA_PLAY_MODE, PLAY_MODE_VS_COMPUTER)
    }

    private fun setNamePlayer() {
        binding.tvPlayer.text =
            String.format(getString(R.string.tv_player).uppercase(), UserPreference(this).name)
        if (playMode == PLAY_MODE_VS_PLAYER){
            binding.tvCom.text = getString(R.string.text_player_opponent)
        } else {
            binding.tvCom.text = getString(R.string.tv_com)
        }
    }

    private fun showControllerUser(userPosition: UserPosition, isVisible: Boolean) {
        if (userPosition == UserPosition.LEFT) {
            binding.llPlayerLeft.isVisible = isVisible
        } else {
            binding.llPlayerRight.isVisible = isVisible
        }
    }

    private fun setHeroAssets(value: Int, userPosition: UserPosition) {
        val ivRock: ImageView
        val ivPaper: ImageView
        val ivScissors: ImageView
        when (userPosition) {
            UserPosition.LEFT -> {
                ivRock = binding.ivChoiceLeft0
                ivPaper = binding.ivChoiceLeft1
                ivScissors = binding.ivChoiceLeft2
            }
            UserPosition.RIGHT -> {
                ivRock = binding.ivChoiceRight0
                ivPaper = binding.ivChoiceRight1
                ivScissors = binding.ivChoiceRight2
            }
        }
        when (PlayerChoice.state(value)) {
            PlayerChoice.ROCK -> ivRock.background =
                ContextCompat.getDrawable(this, R.drawable.img_select)
            PlayerChoice.PAPER -> ivPaper.background =
                ContextCompat.getDrawable(this, R.drawable.img_select)
            PlayerChoice.SCISSOR -> ivScissors.background =
                ContextCompat.getDrawable(this, R.drawable.img_select)
        }
    }

    private fun setInitialState() {
        resetGame()
        if (playMode == PLAY_MODE_VS_PLAYER) {
            showControllerUser(UserPosition.LEFT, true)
            showControllerUser(UserPosition.RIGHT, true)
        }
        binding.tvVs.text = getString(R.string.text_vs)
        isGameFinished = false
    }

    private fun setClickListeners() {
        binding.ivChoiceLeft0.setOnClickListener {
            if (!isGameFinished) {
                userOne.choice = PlayerChoice.ROCK.index
                showToast(String.format(getString(R.string.text_toast_player_1_rock), UserPreference(this).name))
                setNextTurn()
            }
        }
        binding.ivChoiceLeft1.setOnClickListener {
            if (!isGameFinished) {
                userOne.choice = PlayerChoice.PAPER.index
                showToast(String.format(getString(R.string.text_toast_player_1_paper), UserPreference(this).name))
                setNextTurn()
            }
        }
        binding.ivChoiceLeft2.setOnClickListener {
            if (!isGameFinished) {
                userOne.choice = PlayerChoice.SCISSOR.index
                showToast(String.format(getString(R.string.text_toast_player_1_scissor), UserPreference(this).name))
                setNextTurn()
            }
        }
        binding.ivChoiceRight0.setOnClickListener {
            if (!isGameFinished && playTurn == UserPosition.RIGHT) {
                userTwo.choice = PlayerChoice.ROCK.index
                showToast(getString(R.string.text_toast_player_2_rock))
                decideWinner()
            }

        }
        binding.ivChoiceRight1.setOnClickListener {
            if (!isGameFinished && playTurn == UserPosition.RIGHT) {
                userTwo.choice = PlayerChoice.PAPER.index
                showToast(getString(R.string.text_toast_player_2_paper))
                decideWinner()
            }
        }
        binding.ivChoiceRight2.setOnClickListener {
            if (!isGameFinished && playTurn == UserPosition.RIGHT) {
                userTwo.choice = PlayerChoice.SCISSOR.index
                showToast(getString(R.string.text_toast_player_2_scissor))
                decideWinner()
            }
        }

        binding.ivRefresh.setOnClickListener {
            if (isGameFinished) {
                setInitialState()
            } else {
                if (playMode == PLAY_MODE_VS_PLAYER) {
                    if (playTurn == UserPosition.LEFT) {
                        playTurn = UserPosition.RIGHT
                        showControllerUser(UserPosition.LEFT, false)
                        showControllerUser(UserPosition.RIGHT, true)
                    } else {
                        playGame()
                    }
                } else {
                    playGame()
                }
            }
        }
        binding.ivExitGame.setOnClickListener {
            navigateToGameMenu()
        }
    }

    private fun setNextTurn(){
        if (playMode == PLAY_MODE_VS_PLAYER){
            setPlayerTurn(UserPosition.RIGHT)
        } else{
            decideWinner()
        }
    }

    private fun setPlayerTurn(userPosition: UserPosition) {
        playTurn = when (userPosition) {
            UserPosition.LEFT -> {
                showControllerUser(UserPosition.LEFT, true)
                showControllerUser(UserPosition.RIGHT, false)
                UserPosition.LEFT
            }
            UserPosition.RIGHT -> {
                showControllerUser(UserPosition.LEFT, false)
                showControllerUser(UserPosition.RIGHT, true)
                UserPosition.RIGHT
            }
        }
    }

    private fun decideWinner() {
        if (playMode == PLAY_MODE_VS_COMPUTER) {
            userTwo.choice = (0..2).random()
            when(userTwo.choice){
                0 -> showToast(getString(R.string.text_toast_com_rock))
                1 -> showToast(getString(R.string.text_toast_com_paper))
                2 -> showToast(getString(R.string.text_toast_com_scissor))
            }
        } else{
            showControllerUser(UserPosition.LEFT,true)
        }
        setHeroAssets(userOne.choice, UserPosition.LEFT)
        setHeroAssets(userTwo.choice, UserPosition.RIGHT)
        when {
            (userOne.choice + 1) % 3 == userTwo.choice -> {
                Log.d(TAG, "decideWinner: Player 2 Win")
                showDialogForState(String.format(getString(R.string.text_player_lose, UserPreference(this).name).uppercase()))
            }
            userOne.choice == userTwo.choice -> {
                Log.d(TAG, "decideWinner: Draw")
                showDialogForState(getString(R.string.text_draw))
            }
            else -> {
                Log.d(TAG, "decideWinner: Player 1 Win")
                showDialogForState(String.format(getString(R.string.text_player_win, UserPreference(this).name).uppercase()))
            }
        }
        isGameFinished = true
    }

    private fun resetGame(){
        binding.ivChoiceLeft0.background = null
        binding.ivChoiceLeft1.background = null
        binding.ivChoiceLeft2.background = null
        binding.ivChoiceRight0.background = null
        binding.ivChoiceRight1.background = null
        binding.ivChoiceRight2.background = null
        isGameFinished = false
    }

    private fun showDialogForState(title: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(getString(R.string.text_dialog_message))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.text_dialog_yes)) { _, _ ->
                playGame()
            }
            .setNegativeButton(getString(R.string.text_dialog_menu)) { _, _ ->
                navigateToGameMenu()
            }
            .show()
    }

    private fun showToast(textMessage: String){
        Toast.makeText(this, textMessage, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToGameMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    companion object {
        const val ARG_EXTRA_PLAY_MODE = "ARG_EXTRA_PLAY_MODE"
        const val PLAY_MODE_VS_COMPUTER = 0
        const val PLAY_MODE_VS_PLAYER = 1

        fun startThisActivity(context: Context?, playMode: Int) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(ARG_EXTRA_PLAY_MODE, playMode)
            context?.startActivity(intent)
        }
    }
}