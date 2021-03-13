package com.android.example.housingconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_form_details.*

class FormDetailsFragment : Fragment() {
    private val args : FormDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: PHASE 3.2 - when a user clicks 'continue' navigate the user to the FormDescriptionFragment
        //  and send the data the user has filled in so far. the recommended way is to send a Post object
        continueBtn.setOnClickListener {
            val location = args.location
            val type = housingType.text.toString()
            val beds = bedrooms.text.toString().toInt()
            val baths = bathrooms.text.toString().toInt()
            val rent = rent.text.toString().toInt()
            val movein = movein.text.toString()
            val action = FormDetailsFragmentDirections.actionFormDetailsFragmentToFormDescriptionFragment(location,type,beds,baths,rent,movein)
            findNavController().navigate(action)
        }
    }
}