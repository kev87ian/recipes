package com.kev.yourinternetcookbook.ui.fragments.authFragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kev.yourinternetcookbook.R
import com.kev.yourinternetcookbook.databinding.FragmentSignUpBinding
import com.kev.yourinternetcookbook.ui.viewmodels.AuthViewModel
import com.kev.yourinternetcookbook.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
	private var _binding: FragmentSignUpBinding? = null
	val binding get() = _binding!!
	val viewModel : AuthViewModel by viewModels()


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		navigateToSignIn()
		setupObserver()
		binding.signUpButton.setOnClickListener {
			signupUser()
		}
		super.onViewCreated(view, savedInstanceState)
	}

	private fun setupObserver() {
		viewModel.signUpLiveData.observe(viewLifecycleOwner){resource->
			when(resource){
				is Resource.Loading ->{
					binding.progressBarSignup.visibility = View.VISIBLE
				}

				is Resource.Error ->{
					binding.progressBarSignup.visibility = View.GONE
					Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()

				}
				is Resource.Success->{
					findNavController().navigate(R.id.action_signUpFragment_to_mealCategoriesFragment)
					Toast.makeText(requireContext(), "Account successfully created", Toast.LENGTH_SHORT).show()
				}


			}

		}
	}

	private fun signupUser() {
		val userEmail = binding.userEmailEtv.text.toString().trim()
		val password = binding.userPasswordEtv.text.toString()
		val confirmPassword = binding.confirmPasswordEtv.text.toString()



		if (userEmail.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
			Toast.makeText(requireContext(), "Ensure all fields are field", Toast.LENGTH_SHORT).show()
		}

		else if(password != confirmPassword){
			Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
		}

		else if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
			Toast.makeText(requireContext(), "Ensure the email is correctly typed", Toast.LENGTH_SHORT).show()
		}

		else{
			viewModel.signupUser(userEmail, password)
		}
	}


	private fun navigateToSignIn() {
		binding.signInTxt.setOnClickListener{
			findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
		}
	}




	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSignUpBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}