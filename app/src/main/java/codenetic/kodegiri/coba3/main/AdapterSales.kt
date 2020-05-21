package codenetic.kodegiri.coba3.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import codenetic.kodegiri.coba3.R
import com.bumptech.glide.Glide

class AdapterSales(val listsales: ArrayList<SalesBulletin_isi>) : RecyclerView.Adapter<AdapterSales.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_salesman, viewGroup, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cata = listsales[position]
        Glide.with(holder.itemView.context)
            .load(cata.photo)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgPhoto)
        holder.tvName.text = cata.name
        holder.tvSeri.text = cata.series
        holder.tvDate.text = cata.date
        holder.tvViews.text = cata.viiew


    }

    override fun getItemCount(): Int {
        return listsales.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvSeri: TextView = itemView.findViewById(R.id.seri)
        var tvDate: TextView = itemView.findViewById(R.id.date)
        var tvViews: TextView = itemView.findViewById(R.id.view)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

}