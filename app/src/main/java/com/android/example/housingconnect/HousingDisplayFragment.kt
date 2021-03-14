package com.android.example.housingconnect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_housing_display.*


class HousingDisplayFragment : Fragment() {

    val args : HousingDisplayFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_housing_display, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentPost = args.PostShow
        // TODO: PHASE 3.3 - Use Glide to display the image stored in the Post
        Glide.with(this)
                .load("https://RentAppServer.hritupitu.repl.co/" + currentPost.image)
                .into(houseImage)

        // TODO: PHASE 3.3 - Dynamically update information showing in the xml using the Post object data fields
        location.text = currentPost.location
        numOfBeds.text = currentPost.bed.toString()
        numOfBaths.text = currentPost.bath.toString()
        val checkbox : ImageView = view.findViewById(R.id.covidbox)
//        covidTested.text = if(currentPost.covidTested=="True") "Yes" else "No"
        if (currentPost.covidTested=="False"|| currentPost.covidTested=="false"){
            checkbox.setImageResource(R.drawable.ic_x)
        }
        price.text = "$"+currentPost.price.toString()
        datePosted.text=currentPost.date
        description.text = currentPost.desc
        movein.text = currentPost.moveIn
        type.text = currentPost.type.toString().capitalize()

        // TODO: PHASE 3.3 - Create an onclick listener on a button to send an email.
        //  this stage will require sending an implicit intent to an email application.
        //  recall that you can find common intents on the android documentation
        email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // only email apps should handle this
                putExtra(Intent.EXTRA_EMAIL, arrayOf(currentPost.email))
            }
            startActivity(intent)
        }

    }
}