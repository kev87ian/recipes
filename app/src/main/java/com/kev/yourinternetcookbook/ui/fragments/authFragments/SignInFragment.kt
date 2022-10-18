package com.kev.yourinternetcookbook.ui.fragments.authFragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kev.yourinternetcookbook.R
import com.kev.yourinternetcookbook.databinding.FragmentSignInBinding

import com.kev.yourinternetcookbook.ui.viewmodels.AuthViewModel
import com.kev.yourinternetcookbook.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

	private var _binding: FragmentSignInBinding? = null
	private val binding get() = _binding!!
	private val viewModel: AuthViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSignInBinding.inflate(inflater, container, false)
		return binding.root

	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		navigateToSignUp()

		binding.signInButton.setOnClickListener {
			signInUsers()
		}

		navigateToResetPassword()

		setUpObserver()

		super.onViewCreated(view, savedInstanceState)
	}

	private fun navigateToResetPassword() {
		binding.forgotPassTxt.setOnClickListener { findNavController().navigate(R.id.action_signInFragment_to_resetPasswordFragment) }
	}


	private fun setUpObserver() {
		viewModel.loginLiveData.observe(viewLifecycleOwner) { resource ->
			when (resource) {
				is Resource.Error -> {
					Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
					binding.progressBarSignin.visibility = View.GONE

				}
				is Resource.Loading -> {
					binding.progressBarSignin.visibility = View.VISIBLE

				}

				is Resource.Success -> {
					binding.progressBarSignin.visibility = View.GONE
					Toast.makeText(
						requireContext(),
						"Welcome, ".plus(resource.data?.email),
						Toast.LENGTH_SHORT
					).show()
					findNavController().navigate(R.id.action_signInFragment_to_mealCategoriesFragment)
				}
			}


		}
	}


	private fun signInUsers() {
		val email = binding.userEmailEtv.text.toString()
		val password = binding.userPasswordEtv.text.toString()


		if (email.isEmpty() || password.isEmpty()) {
			Toast.makeText(
				requireContext(),
				"Ensure all fields are filled, and password has more than 6 characters",
				Toast.LENGTH_SHORT
			).show()
		} else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			Toast.makeText(
				requireContext(),
				"Ensure the email is correctly typed",
				Toast.LENGTH_SHORT
			).show()
		} else if (password.length <= 5) {
			Toast.makeText(
				requireContext(),
				"Password must contain at least 6 characters.",
				Toast.LENGTH_SHORT
			).show()
		} else {

			viewModel.loginUser(email, password)
		}

	}


	private fun navigateToSignUp() {
		binding.signUpTxt.setOnClickListener {
			findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
		}
	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}

}