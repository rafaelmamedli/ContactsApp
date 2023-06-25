package com.rafael.contactsapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.util.Tags.TAG
import com.rafael.contactsapp.data.util.UiState
import com.rafael.contactsapp.data.util.gone
import com.rafael.contactsapp.data.util.show
import com.rafael.contactsapp.data.util.toast
import com.rafael.contactsapp.databinding.FragmentContactDetailBinding
import com.rafael.contactsapp.viewmodel.ContactDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailFragment : Fragment() {

    private val viewModel: ContactDetailViewModel by viewModels()
    private lateinit var binding: FragmentContactDetailBinding
    var contactValue : Contacts? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(layoutInflater)
        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observer()
        updateUi()
        addContact()


    }

    private fun updateUi(){
        val type =  arguments?.getString("type",null)
        type?.let {
            if(it == "view"){

                    binding.apply {
                        button.gone()
                        progressBar.gone()
                        editTextName.isEnabled = false
                        editTextNumber.isEnabled = false
                        contactValue = arguments?.getParcelable<Contacts>("contact")
                        editTextNumber.setText(contactValue?.contact_number)
                        editTextName.setText(contactValue?.contact_name)
                    }


            }
        }
    }

    private fun observer(){
        viewModel.addContacts.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Loading ->{
                    binding.progressBar.show()
                    binding.button.text = ""
                }
                is UiState.Success ->{
                    toast(state.data)
                    binding.progressBar.gone()
                    binding.button.text = "Create"
                }
                is UiState.Failure ->{
                    Log.e(TAG, "Error")
                }
            }

        }
    }

    private fun addContact(){
        binding.button.setOnClickListener {
            val name=binding.editTextName.text.toString()
            val number=binding.editTextNumber.text.toString()
            if (number.isNotEmpty() && name.isNotEmpty()) {
                viewModel.addContact(name,number)
            }
        }
    }





}