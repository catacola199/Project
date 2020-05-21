package codenetic.kodegiri.coba3.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R

class catalog : AppCompatActivity() {
    private lateinit var rvCata: RecyclerView
    private var list: ArrayList<catalog_isi> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        supportActionBar?.title = "Catalog Apps"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rvCata = findViewById(R.id.rv_catalog)
        rvCata.setHasFixedSize(true)
        list.addAll(Data.listData)
        showRecyclerList()

    }
    private fun showRecyclerList() {
        rvCata.layoutManager = LinearLayoutManager(this)
        val catalog_adapter = AdapterCatalog(list)
        rvCata.adapter = catalog_adapter

        catalog_adapter.setOnItemClickCallback (object : AdapterCatalog.OnItemClickCallback {
            override fun onItemClicked(data: catalog_isi) {
                showSelectedGame(data)
            }
        })
    }


    private fun showSelectedGame(ci : catalog_isi) {
        Toast.makeText(this, ci.name, Toast.LENGTH_SHORT).show()
    }
    override fun onBackPressed(){

    }

}
