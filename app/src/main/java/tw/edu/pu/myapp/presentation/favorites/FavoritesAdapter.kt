package tw.edu.pu.myapp.presentation.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.edu.pu.myapp.databinding.LayoutWeatherCardBinding
import tw.edu.pu.myapp.domain.weather.WeatherData
import tw.edu.pu.myapp.presentation.home.HomeAdapter

class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    class FavoritesViewHolder(
        val binding: LayoutWeatherCardBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            if (oldItem.city == newItem.city) return true
            if (oldItem.isExpand == newItem.isExpand) return true
            if (oldItem.temperature == newItem.temperature) return true
            if (oldItem.weatherType == newItem.weatherType) return true
            if (oldItem.windSpeed == newItem.windSpeed) return true
            if (oldItem.humidity == newItem.humidity) return true
            if (oldItem.pressure == newItem.pressure) return true
            if (oldItem.visibility == newItem.visibility) return true
            if (oldItem.lowHigh == newItem.lowHigh) return true
            if (oldItem.weatherMiniData == newItem.weatherMiniData) return true
            if (oldItem.country == newItem.country) return true
            return false
        }
    }

    val differ = androidx.recyclerview.widget.AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            LayoutWeatherCardBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val homeAdapter = HomeAdapter()
        holder.binding.apply {
            val data = differ.currentList[position]
            titleCard
            titleTemp.text = "${data.temperature.toInt()}°C"
            titleCity.text = data.city
            titleCountry.text = data.country
            titleState.text = data.weatherType.weatherDesc
            imageView.setImageResource(data.weatherType.iconRes)
            tvLowHigh.text = data.lowHigh
            tvFeelLike.text = "${data.temperature.toInt() - 2}°C"
            tvHumid.text = "${data.humidity}%"
            tvWind.text = "${data.windSpeed} km/h"
            tvPressure.text = "${data.pressure} hPa"
            tvVisibility.text = "${data.visibility} km"

            if (data.isExpand) expandedMenu.visibility = View.VISIBLE
            else expandedMenu.visibility = View.GONE

            homeAdapter.differ.submitList(data.weatherMiniData)

            recyclerView.apply {
                val linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                layoutManager = linearLayoutManager
                hasFixedSize()
                adapter = homeAdapter
            }

            titleCard.setOnClickListener {
                // onItemClickListener.onItemClick(position)
                data.isExpand = !data.isExpand
                notifyItemChanged(holder.adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}