package codenetic.kodegiri.coba3.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.R

class information_adapter (val listinformation: ArrayList<information_isi>) : RecyclerView.Adapter<information_adapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_information, viewGroup, false)
        return ListViewHolder(view)
    }


    override fun getItemCount(): Int {
        return listinformation.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.item_Tittle)
        var tvIsi: TextView = itemView.findViewById(R.id.Item_Isi)
        var tvTime: TextView = itemView.findViewById(R.id.Item_time)

    }
    fun deleteItem(pos:Int){
        listinformation.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cata = listinformation[position]

        holder.tvName.text = cata.Title
        holder.tvIsi.text = cata.Isi
        holder.tvTime.text = cata.date
        holder.itemView.setOnClickListener(View.OnClickListener {
            val context : Context = holder.itemView.context
            val TittleIntent = Intent(context, informasi_Detail::class.java)
            TittleIntent.putExtra("Tittle", cata.Title)
            context.startActivity(TittleIntent)
        })
    }
}