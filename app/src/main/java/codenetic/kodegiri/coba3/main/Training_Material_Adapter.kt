package codenetic.kodegiri.coba3.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.R

class Training_Material_Adapter (val listsales: ArrayList<Training_Material_isi>) : RecyclerView.Adapter<Training_Material_Adapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_training_material, viewGroup, false)
        return ListViewHolder(view)
    }


    override fun getItemCount(): Int {
        return listsales.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvSeri: TextView = itemView.findViewById(R.id.seri)
        var tvDate: TextView = itemView.findViewById(R.id.date)
        var url_pdf: TextView = itemView.findViewById(R.id.view)
    }
    fun deleteItem(pos:Int){
        listsales.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cata = listsales[position]

        holder.tvName.text = cata.Title
        holder.tvSeri.text = cata.Series
        holder.tvDate.text = cata.datetime
        holder.url_pdf.text = cata.url_pdf
        holder.itemView.setOnClickListener(View.OnClickListener {
            val context : Context = holder.itemView.context
            val Training_PDF_ViewerIntent = Intent(context, Dicoba::class.java)
            Training_PDF_ViewerIntent.putExtra("url_pdf", cata.url_pdf)
            context.startActivity(Training_PDF_ViewerIntent)
        })
    }
}