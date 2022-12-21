package tw.edu.pu.myapp.presentation.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tw.edu.pu.myapp.databinding.LayoutWeatherCardBinding
import tw.edu.pu.myapp.domain.weather.WeatherData

class SearchAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(
        val binding: LayoutWeatherCardBinding,
        val onItemClickListener: OnItemClickListener
        ) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            if (oldItem.datetime == newItem.datetime) return true
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutWeatherCardBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false),
            onItemClickListener = onItemClickListener
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binding.apply {
            val data = differ.currentList[position]

            if (!data.isExpand) expandedMenu.visibility = View.GONE
            titleCity.text = data.city
            titleCountry.text = data.country
            titleState.text = data.weatherType.weatherDesc
            titleTemp.text = "${data.temperature.toInt()}°C"
            imageView.setImageResource(data.weatherType.iconRes)

            titleCard.setOnClickListener {
                onItemClickListener.onItemClick(data)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener {
        fun onItemClick(data: WeatherData)
    }
}