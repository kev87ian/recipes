package com.kev.yourinternetcookbook.ui.fragments.authFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kev.yourinternetcookbook.R
import com.kev.yourinternetcookbook.databinding.FragmentSplashScreenBinding

import com.kev.yourinternetcookbook.ui.MainActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen), CoroutineScope {
	private var _binding : FragmentSplashScreenBinding? = null
	private val binding get() = _binding!!

	private lateinit var firebaseAuth: FirebaseAuth
	override val coroutineContext: CoroutineContext
		get() = Dispatchers.Main + Job()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		launch {
			delay(2000)
			withContext(Dispatchers.Main){
				moveToNext()
			}
		}

		super.onViewCreated(view, savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {



		firebaseAuth = FirebaseAuth.getInstance()
		_binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroy() {

		super.onDestroy()
		_binding = null
	}
	fun moveToNext() {
		val user = FirebaseAuth.getInstance().currentUser
		if (user!=null){
			findNavController().navigate(R.id.action_splashScreenFragment_to_mealCategoriesFragment)
			Toast.makeText(requireContext(), "${user.metadata}", Toast.LENGTH_SHORT).show()
		}
		else{
			findNavController().navigate(R.id.action_splashScreenFragment_to_signInFragment)
		}


	}

}