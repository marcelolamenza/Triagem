package com.example.triagem.diseases

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.triagem.R


/**
 * A simple [Fragment] subclass.
 * Use the [DiseaseListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiseaseListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_disease_list, container, false)
    }

}