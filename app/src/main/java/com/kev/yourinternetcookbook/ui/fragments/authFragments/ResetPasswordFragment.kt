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
import com.google.android.material.snackbar.Snackbar
import com.kev.yourinternetcookbook.R
import com.kev.yourinternetcookbook.databinding.FragmentResetPasswordBinding
import com.kev.yourinternetcookbook.ui.viewmodels.AuthViewModel
import com.kev.yourinternetcookbook.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {
	private var _binding: FragmentResetPasswordBinding? = null
	private val binding get() = _binding!!

	private val viewModel: AuthViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setUpObserver()
		binding.resetButton.setOnClickListener{
			resetPassword()
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
		return binding.root
	}

	private fun setUpObserver() {
		viewModel.resetPasswordLiveData.observe(viewLifecycleOwner) { state ->
			when (state) {
				is Resource.Loading -> {
					binding.progressBarResetPassword.visibility = View.VISIBLE
				}

				is Resource.Error -> {
					binding.progressBarResetPassword.visibility = View.GONE
					Toast.makeText(requireActivity(), state.message.toString(), Toast.LENGTH_SHORT).show()
				}

				is Resource.Success -> {
					Toast.makeText(requireContext(), "We've sent an email with further instructions.", Toast.LENGTH_SHORT).show()
					findNavController().navigate(R.id.action_resetPasswordFragment_to_signInFragment)
				}

				else -> {}
			}

		}
	}

	private fun resetPassword(){
		val email = binding.userEmailEtv.text.toString()
		if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
			Toast.makeText(requireActivity(), "Ensure the email is correctly typed", Toast.LENGTH_SHORT).show()
		}

		else{
			viewModel.resetPassword(email)
		}
	}
}