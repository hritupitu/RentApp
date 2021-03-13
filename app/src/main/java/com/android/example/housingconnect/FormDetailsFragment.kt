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
    val args : FormDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var post = args.postVal
        // TODO: PHASE 3.2 - when a user clicks 'continue' navigate the user to the FormDescriptionFragment
        //  and send the data the user has filled in so far. the recommended way is to send a Post object
        continueBtn.setOnClickListener {

            post.type = housingType.text.toString()
            post.bed = bedrooms.text.toString().toInt()
            post.bath = bathrooms.text.toString().toInt()
            post.price = rent.text.toString().toInt()
            post.moveIn = movein.text.toString()
            val action = FormDetailsFragmentDirections.actionFormDetailsFragmentToFormDescriptionFragment(post)
            findNavController().navigate(action)
        }
    }
}