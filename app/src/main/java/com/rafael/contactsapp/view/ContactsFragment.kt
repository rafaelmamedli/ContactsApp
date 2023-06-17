package com.rafael.contactsapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafael.contactsapp.R
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.util.UiState
import com.rafael.contactsapp.databinding.FragmentContactsBinding
import com.rafael.contactsapp.view.adapter.ContactsAdapter
import com.rafael.contactsapp.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment() {


    private val viewModel: ContactsViewModel by viewModels()
    lateinit var adapter: ContactsAdapter
    lateinit var binding:FragmentContactsBinding
    var contactList= arrayListOf<Contacts>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            adapter = ContactsAdapter(contactList)
          //  viewModel.getContactResults()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter


        viewModel.getContacts.observe(viewLifecycleOwner) {state ->
            when(state) {
                is UiState.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()

                }
                is UiState.Success -> {
                  val contactList =  state.data.toMutableList()
                    adapter.list = contactList
                    adapter.notifyDataSetChanged()
                }
                is UiState.Failure -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()

                }
            }
        }



}}