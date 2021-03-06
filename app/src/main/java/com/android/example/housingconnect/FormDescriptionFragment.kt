package com.android.example.housingconnect

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_form_description.*
import java.time.LocalDate
import java.time.LocalDateTime

class FormDescriptionFragment : Fragment() {
    val args : FormDescriptionFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_description, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var post = args.postVal
        // TODO: PHASE 3.2 - when a user clicks 'continue' navigate the user to the FormImageFragment
        //  and send the data the user has filled in so far. the recommended way is to send a Post object
        continueBtn.setOnClickListener {

            post.desc = description.text.toString()
            post.covidTested = "True"
            post.date = LocalDate.now().toString()
            if(covidTestedCheck.isChecked ) post.covidTested = "True"
            else post.covidTested ="False"
            post.email=""
            val action = FormDescriptionFragmentDirections.actionFormDescriptionFragmentToFormImageFragment(post)
            findNavController().navigate(action)
        }
    }
}