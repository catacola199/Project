package codenetic.kodegiri.coba3.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import codenetic.kodegiri.coba3.R
import com.bumptech.glide.Glide

class AdapterCatalog(val listcata: ArrayList<catalog_isi>) : RecyclerView.Adapter<AdapterCatalog.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_catalog, viewGroup, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cata = listcata[position]
        Glide.with(holder.itemView.context)
            .load(cata.photo)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgPhoto)
        holder.tvName.text = cata.name
        holder.tvDetail.text = cata.detail



        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listcata[holder.adapterPosition])

            val moveToDetailActivity = Intent(it.context, Detail::class.java)
            moveToDetailActivity.putExtra(Detail.EXTRA_NAME, cata.name)
            moveToDetailActivity.putExtra(Detail.EXTRA_DETAIL, cata.detail)
            moveToDetailActivity.putExtra(Detail.EXTRA_IMAGE, cata.photo)
            it.context.startActivity(moveToDetailActivity)
        }
    }

    override fun getItemCount(): Int {
        return listcata.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_item_detail)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: catalog_isi)
    }

}