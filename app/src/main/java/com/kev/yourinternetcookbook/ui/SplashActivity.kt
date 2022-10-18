package com.kev.yourinternetcookbook.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kev.yourinternetcookbook.R


class SplashActivity : AppCompatActivity() {

	lateinit var firebaseAuth: FirebaseAuth
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		firebaseAuth = FirebaseAuth.getInstance()
		val user = FirebaseAuth.getInstance().currentUser

		if (user != null) {
			// User is signed in
		} else {
			// No user is signed in
		}

		// our window to full screen
		window.setFlags(
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN
		)

		setContentView(R.layout.activity_splash)
		Handler().postDelayed({
			// on below line we are
			// creating a new intent
			val i = Intent(
				this,
				MainActivity::class.java
			)
			// on below line we are
			// starting a new activity.
			startActivity(i)

			// on the below line we are finishing
			// our current activity.
			finish()
		}, 4000)
	}
}