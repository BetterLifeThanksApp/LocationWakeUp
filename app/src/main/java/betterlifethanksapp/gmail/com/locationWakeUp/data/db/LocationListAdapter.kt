package betterlifethanksapp.gmail.com.locationWakeUp.data.db

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import betterlifethanksapp.gmail.com.locationWakeUp.R

class LocationListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations = emptyList<Location>()

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val locationItemView: TextView = itemView.findViewById(R.id.tvLocationName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_location_item,parent,false)
        return LocationViewHolder(itemView)
    }

    override fun getItemCount() = locations.size

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val current = locations[position]
        holder.locationItemView.text = current.location
    }

    internal fun setLocations(locations: List<Location>){
        this.locations = locations
        notifyDataSetChanged()
    }


}