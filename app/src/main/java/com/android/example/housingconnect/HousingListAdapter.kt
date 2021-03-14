package com.android.example.housingconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private var posts = emptyList<Post>()
class HousingListAdapter() : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.housing_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = posts[position]
        holder.apply {
            housingType.text= posts[position].type.toString().capitalize()
            // TODO: PHASE 3.1 - Re-define these values based on the the post object being displayed
            location.text = posts[position].location.toString()
            price.text = "$"+posts[position].price.toString()
            numOfBeds.text = posts[position].bed.toString()
            numOfBaths.text = posts[position].bath.toString()
            if (posts[position].covidTested=="False"|| posts[position].covidTested=="false"){
                checkbox.setImageResource(R.drawable.ic_x)
            }

//            covidTested.text = posts[position].covidTested

            // TODO: PHASE 3.1 Use Glide to show an image from the database
             Glide.with(holder.itemView)
                .load("https://RentAppServer.hritupitu.repl.co/" + item.image)
                .into(housingImage)
        }

        holder.housingItem.setOnClickListener{
            // TODO: PHASE 3.1 navigate to HousingDisplayFragment and send the Post obect
            val action = HousingFeedFragmentDirections.actionHousingFeedFragmentToHousingDisplayFragment(posts[position])
            findNavController(holder.itemView.findFragment()).navigate(action)
        }
    }

    override fun getItemCount() = posts.size

    fun setData(newPosts: List<Post>) {
        posts = newPosts
        this.notifyDataSetChanged()
    }
}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val housingItem: ConstraintLayout = view.findViewById(R.id.housingItem)
    val housingImage: ImageView = view.findViewById(R.id.housingImage1)
    val housingType: TextView = view.findViewById(R.id.housingType1)
    val location: TextView = view.findViewById(R.id.address1)
    val price: TextView = view.findViewById(R.id.price1)
    val numOfBeds: TextView = view.findViewById(R.id.numOfBeds1)
    val checkbox : ImageView = view.findViewById(R.id.checkboxcovid)
    val numOfBaths: TextView = view.findViewById(R.id.numOfBaths1)
    val covidTested: TextView = view.findViewById(R.id.covidTested1)
}