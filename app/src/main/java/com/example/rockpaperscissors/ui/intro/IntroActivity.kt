package com.example.rockpaperscissors.ui.intro

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.enum.LandingPage
import com.github.appintro.AppIntro2

class IntroActivity : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        isWizardMode = true
        isSkipButtonEnabled = false

        setIndicatorColor(
            selectedIndicatorColor = ContextCompat.getColor(this, R.color.teal_200),
            unselectedIndicatorColor = ContextCompat.getColor(this, R.color.teal_700)
        )

        addSlide(LandingPageFragment(LandingPage.ONE))
        addSlide(LandingPageFragment(LandingPage.TWO))
        addSlide(LandingPageFragment(LandingPage.THREE))
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        if (currentFragment is LandingPageFragment) {
            currentFragment.navigateToMenu()
        }
    }
}