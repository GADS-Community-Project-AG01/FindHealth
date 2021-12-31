package com.gadsag01.findhealth

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment

class OnboardingScreen : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment

        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.first_onboarding_screen))
        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.second_onboarding_screen))
        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.third_onboarding_screen))



    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        finish()
    }
}
