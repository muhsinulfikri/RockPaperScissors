package com.example.rockpaperscissors.ui.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.databinding.FragmentLandingPageBinding
import com.example.rockpaperscissors.enum.LandingPage
import com.example.rockpaperscissors.preference.UserPreference
import com.example.rockpaperscissors.ui.menu.MenuActivity

class LandingPageFragment(private val landingPage: LandingPage) : Fragment() {

    private lateinit var binding: FragmentLandingPageBinding
    private val userPreference: UserPreference? by lazy {
        context?.let { UserPreference(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLandingPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        when (landingPage) {
            LandingPage.ONE -> {
                binding.ivLandingPageOne.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_landing_page_one
                    )
                )
                binding.tvLandingPageTitle.text = getString(R.string.text_play_with_friend)
                binding.tvDescGame.text = getString(R.string.text_desc_play_with_friend).uppercase()
            }
            LandingPage.TWO -> {
                binding.ivLandingPageOne.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_landing_page_two
                    )
                )
                binding.tvLandingPageTitle.text = getString(R.string.play_with_com)
                binding.tvDescGame.text = getString(R.string.text_desc_play_with_computer).uppercase()
            }
            LandingPage.THREE -> {
                binding.ivLandingPageOne.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_landing_page_three
                    )
                )
                binding.tvLandingPageTitle.text = getString(R.string.enter_name)
                binding.tilInputName.isVisible = true
                binding.etPlayerName.setText(userPreference?.name)
            }
        }
    }

    private fun isFilledName(): Boolean {
        val name = binding.etPlayerName.text.toString().trim()
        var isFormValid = true
        if (name.isEmpty()) {
            isFormValid = false
            Toast.makeText(context, getString(R.string.text_toast_input_name), Toast.LENGTH_SHORT).show()
        }
        return isFormValid
    }

    fun navigateToMenu() {
        if (isFilledName()) {
            userPreference?.name = binding.etPlayerName.text.toString().trim()
            val intent = Intent(context, MenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }


}