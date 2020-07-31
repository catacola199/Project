package codenetic.kodegiri.coba3.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AdapterSales(val listsales: ArrayList<SalesBulletin_isi>) : RecyclerView.Adapter<AdapterSales.ListViewHolder>() {
    private var removedPosition: Int = 0
    private var removedItem: String = ""
    private lateinit var reference: DatabaseReference
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_salesman, viewGroup, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cata = listsales[position]

        holder.tvName.text = cata.Title
        holder.tvSeri.text = cata.Series
        holder.tvDate.text = cata.datetime
        holder.url_pdf.text = cata.url_pdf
        holder.itemView.setOnClickListener(View.OnClickListener {
            val context : Context = holder.itemView.context
            val Salesman_PDF_ViewerIntent = Intent(context, Dicoba::class.java)
            Salesman_PDF_ViewerIntent.putExtra("url_pdf", cata.url_pdf)
            context.startActivity(Salesman_PDF_ViewerIntent)
        })
    }

    override fun getItemCount(): Int {
        return listsales.size }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_nameS)
        var tvSeri: TextView = itemView.findViewById(R.id.seriS)
        var tvDate: TextView = itemView.findViewById(R.id.dateS)
        var url_pdf: TextView = itemView.findViewById(R.id.viewS) }

    fun removeItem(position: Int) {


    }
}

