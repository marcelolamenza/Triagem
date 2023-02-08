package com.example.triagem.diseases

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.triagem.R
import com.example.triagem.util.Diseases

class DiseaseListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_disease_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.red_description).text = adjustList(Diseases.redDiseases)
        view.findViewById<TextView>(R.id.orange_description).text = adjustList(Diseases.orangeDiseases)
        view.findViewById<TextView>(R.id.yellow_description).text = adjustList(Diseases.yellowDiseases)
        view.findViewById<TextView>(R.id.green_description).text = adjustList(Diseases.greenDiseases)
        view.findViewById<TextView>(R.id.blue_description).text = adjustList(Diseases.blueDiseases)
    }

    private fun adjustList(list: List<String>): String {
        var resultString = ""
        list.forEach {
            resultString += "\n- $it"
        }

        //Removing first \n
        resultString = resultString.substring(1)
        return resultString
    }
}