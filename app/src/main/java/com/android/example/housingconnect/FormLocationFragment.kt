package com.android.example.housingconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_form_location.*

class FormLocationFragment : Fragment() {

    var post = Post(0,"","",0,0,0,"","","","","","")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: PHASE 3.2 - when a user clicks 'continue' navigate the user to the FormDetailsFragment
        //  and send the data the user has filled in so far. the recommended way is to send a Post object
        continueBtn.setOnClickListener {

            val location = location.text.toString()
            val city = city.text.toString()
            val state = state.text.toString()
            val address = "$location, $city, $state"
            post.location = address
            val action = FormLocationFragmentDirections.actionFormLocationFragmentToFormDetailsFragment(post)
            findNavController().navigate(action)
        }

    }
}