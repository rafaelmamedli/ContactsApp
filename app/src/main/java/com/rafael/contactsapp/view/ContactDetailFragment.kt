package com.rafael.contactsapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rafael.contactsapp.R
import com.rafael.contactsapp.data.util.Tags.TAG
import com.rafael.contactsapp.data.util.UiState
import com.rafael.contactsapp.data.util.goTo
import com.rafael.contactsapp.data.util.toast
import com.rafael.contactsapp.databinding.FragmentContactDetailBinding
import com.rafael.contactsapp.viewmodel.ContactDetailViewModel
import com.rafael.contactsapp.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailFragment : Fragment() {

    private val viewModel: ContactDetailViewModel by viewModels()
    private lateinit var binding: FragmentContactDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(layoutInflater)
        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            observer()
            val value1=binding.editTextName.text.toString()
            val value2=binding.editTextNumber.text.toString()
            viewModel.addContact(value1,value2)
            findNavController().navigate(R.id.action_contactDetailFragment_to_contactsFragment)

        }

    }

    private fun observer(){
        viewModel.addContacts.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Success ->{
                    Log.e(TAG, state.data.toString())

                }
                is UiState.Loading ->{
                    Log.e(TAG, "state.data.toString()")

                }
                is UiState.Failure ->{
                    Log.e(TAG, "Error")

                }
            }

        }
    }





}