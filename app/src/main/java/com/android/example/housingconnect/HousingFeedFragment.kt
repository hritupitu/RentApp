package com.android.example.housingconnect

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_housing_feed.*
import retrofit2.Call
import retrofit2.Response

class HousingFeedFragment : Fragment() {

    val mAuth = FirebaseAuth.getInstance()
    lateinit var rv : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_housing_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: PHASE 3.1 - Connect adapter and layoutManager to the RecyclerView defined in xml

        val housingListAdapter = HousingListAdapter()
        housing_feed_recycler_view.adapter = housingListAdapter
        housing_feed_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        val user = mAuth.currentUser

        // TODO: PHASE 3.1 - Add onClickListener to Post Button and navigate to signin page or
        //  the start of the form (FormLocationFragment)
        postButton.setOnClickListener{
            var action = HousingFeedFragmentDirections.actionHousingFeedFragmentToFormLocationFragment()
            if(user==null){
                action = HousingFeedFragmentDirections.actionHousingFeedFragmentToSignInFragment()
            }
            findNavController().navigate(action)
        }


        // TODO: PHASE 4 - Get an instance of the singleton housingService defined in the MainActivity
        val housingService = (requireActivity() as MainActivity).housingService


        // TODO: PHASE 4 - using the housingService to fetch all Housing Listing from the server
        //  make sure to update the recycler views adapter

        housingService.getPosts().enqueue(object: retrofit2.Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: retrofit2.Response<List<Post>>) {
                Log.d("ListFragment","Success in onResponse")
                housingListAdapter.setData(response.body()!!)
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("ListFragment","Success in onFailure")
                Log.d("ListFragment",t.toString())
            }
        })


    }
}
