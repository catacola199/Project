package codenetic.kodegiri.coba3.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R

class salesman_bulletin : AppCompatActivity() {
    private lateinit var rvSalesB: RecyclerView
    private var list: ArrayList<SalesBulletin_isi> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salesman_bulletin)
        supportActionBar?.title = "Salesman Bulletin"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rvSalesB = findViewById(R.id.rv_sales)
        rvSalesB.setHasFixedSize(true)
        list.addAll(DataSales.listData)
        showRecyclerList()
    }
    private fun showRecyclerList() {
        rvSalesB.layoutManager = LinearLayoutManager(this)
        val catalog_adapter = AdapterSales(list)
        rvSalesB.adapter = catalog_adapter

    }


    override fun onBackPressed(){

    }
}
