package tw.edu.pu.myapp.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tw.edu.pu.myapp.databinding.LayoutHourlyWigetBinding
import tw.edu.pu.myapp.domain.weather.WeatherMiniData

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(val binding: LayoutHourlyWigetBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<WeatherMiniData>() {
        override fun areItemsTheSame(oldItem: WeatherMiniData, newItem: WeatherMiniData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherMiniData, newItem: WeatherMiniData): Boolean {
            if (oldItem.datetime == newItem.datetime) return true
            if (oldItem.temperature == newItem.temperature) return true
            if (oldItem.weatherType == newItem.weatherType) return true
            return false
        }
    }

    val differ = androidx.recyclerview.widget.AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutHourlyWigetBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.apply {
            val time = differ.currentList[position].datetime.substring(11, 16)

            Icon.setImageResource(differ.currentList[position].weatherType.iconRes)
            tvTemp.text = "${differ.currentList[position].temperature}°C"
            tvTime.text = time
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}