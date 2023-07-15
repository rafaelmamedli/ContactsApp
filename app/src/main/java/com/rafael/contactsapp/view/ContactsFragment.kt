package com.rafael.contactsapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafael.contactsapp.R
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.util.Tags.TAG
import com.rafael.contactsapp.data.util.UiState
import com.rafael.contactsapp.databinding.FragmentContactsBinding
import com.rafael.contactsapp.view.adapter.ContactsAdapter
import com.rafael.contactsapp.view.adapter.SwipeDeleteCallBack
import com.rafael.contactsapp.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private val viewModel: ContactsViewModel by viewModels()
    private lateinit var adapter: ContactsAdapter
    private lateinit var binding: FragmentContactsBinding
    private val contactList = mutableListOf<Contacts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactList?.let {
            adapter = ContactsAdapter(it)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.button.setOnClickListener {
            findNavController().navigate(
                R.id.action_contactsFragment_to_contactDetailFragment,
                Bundle().apply {
                    putString("type", "create")
                }
            )
        }

        viewModel.fetchContacts()
        adapter.setItemClickListener {
            findNavController().navigate(
                R.id.action_contactsFragment_to_contactDetailFragment,Bundle().apply {
                    putString("type","view")
                    putParcelable("contact", it)

                }
            )
        }
        observer()
        setupSwipeToDelete()
    }


    private fun observer() {
        viewModel.getContacts.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    Log.d(TAG,"Loading")
                }

                is UiState.Success -> {
                    contactList.clear()
                    state.data?.let { contactList.addAll(it) }
                    adapter.notifyDataSetChanged()
                }

                is UiState.Failure -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupSwipeToDelete() {
        val swipeToDeleteBack = object : SwipeDeleteCallBack(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.list[position]
                contactList.remove(item)
                adapter.notifyItemRemoved(position)
                viewModel.deleteContact(item.contact_id)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteBack)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}




