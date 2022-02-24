package com.example.triagem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.triagem.adapters.RegisterItemAdapter
import com.example.triagem.util.Constants
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {
    private val adapter by lazy { RegisterItemAdapter() }
    private lateinit var databaseUsers: CollectionReference
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        fillRecyclerview()

        btn_register.setOnClickListener{
//            findDatabase()
//            saveData()

            Toast.makeText(context, "Usuário adicionado com sucesso!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_registerFragment_to_registerDetailsFragment)
        }
    }

    private fun fillRecyclerview(){
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.Register.FIRST_NAME))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.Register.LAST_NAME))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.Register.PHONE))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.Register.ADRESS))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.Register.EMAIL))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.Register.RG))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.Register.CPF))
    }

    private fun findDatabase() {
        val db: FirebaseFirestore  = FirebaseFirestore.getInstance()
        databaseUsers = db.collection(Constants.Firebase.DB_NAME_USERS)
    }

    private fun saveData() {

        val users = hashMapOf<String, String>()
        for(info in adapter.getItems()) {
            info.itemDescription?.let { users.put(info.itemLabel, it) }
        }

        databaseUsers.add(users)
            .addOnSuccessListener { documentReference ->
                Log.d("LOG", "Document added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("LOG", "Error adding document", e)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }
}