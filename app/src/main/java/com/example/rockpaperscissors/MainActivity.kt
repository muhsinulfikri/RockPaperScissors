package com.example.rockpaperscissors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.rockpaperscissors.databinding.ActivityMainBinding
import com.example.rockpaperscissors.enum.Computer
import com.example.rockpaperscissors.enum.PlayerChoice
import com.example.rockpaperscissors.enum.User

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var binding: ActivityMainBinding
    private lateinit var computer: Computer
    private lateinit var user: User
    private var isGameFinished: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
        computer = Computer()
        user = User()
        playGame()
    }

    private fun bindViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    private fun playGame(){
        setInitialHero()
        setHeroAssets()
        setInitialState()
        setClickListeners()
    }

    private fun setInitialHero() {
        user.heroes = arrayListOf(
            binding.ivChoiceLeft0,
            binding.ivChoiceLeft1,
            binding.ivChoiceLeft2
        )
        computer.heroes = arrayListOf(
            binding.ivChoiceRight0,
            binding.ivChoiceRight1,
            binding.ivChoiceRight2
        )
    }

    private fun setHeroAssets() {
        user.heroes[PlayerChoice.ROCK.index].setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_rock
            )
        )
        user.heroes[PlayerChoice.PAPER.index].setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_paper
            )
        )
        user.heroes[PlayerChoice.SCISSOR.index].setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_scissors
            )
        )

        computer.heroes[PlayerChoice.ROCK.index].setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_rock
            )
        )
        computer.heroes[PlayerChoice.PAPER.index].setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_paper
            )
        )
        computer.heroes[PlayerChoice.SCISSOR.index].setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_scissors
            )
        )
    }

    private fun setInitialState() {
        (user.heroes + computer.heroes).forEach { it.background = null }
        binding.tvVs.text = getString(R.string.text_vs)
        isGameFinished = false
    }

    private fun setClickListeners() {
        user.heroes.forEachIndexed { index, hero ->
            hero.setOnClickListener {
                if (!isGameFinished) {
                    Log.i(TAG, "setClickListeners : ${PlayerChoice.getValueFromIndex(index)}")
                    user.choice = index
                    it.background = ContextCompat.getDrawable(this, R.drawable.img_select)
                    computer.choice = (0..2).random()
                    computer.heroes[computer.choice].background =
                        ContextCompat.getDrawable(this, R.drawable.img_select)
                    decideWinner()
                    isGameFinished = true
                }
            }
        }
        binding.ivRefresh.setOnClickListener {
            binding.tvStatusGame.text = ""
            setInitialState()
        }
    }

    private fun decideWinner() {
        when {
            (user.choice + 1) % 3 == computer.choice -> {
                Log.i(TAG, "decideWinner: Player 2 Win")
                binding.tvStatusGame.text = getString(R.string.text_com_win)
            }
            user.choice == computer.choice -> {
                Log.i(TAG, "decideWinner: Draw")
                binding.tvStatusGame.text = getString(R.string.text_draw)
            }
            else -> {
                Log.i(TAG, "decideWinner: Player 1 Win")
                binding.tvStatusGame.text = getString(R.string.text_player_win)
            }
        }
    }
}