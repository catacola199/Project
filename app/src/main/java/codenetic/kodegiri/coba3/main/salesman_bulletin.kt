package codenetic.kodegiri.coba3.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import androidx.recyclerview.widget.ItemTouchHelper

class salesman_bulletin : AppCompatActivity() {
    private lateinit var rvSalesB: RecyclerView
    private var list: ArrayList<SalesBulletin_isi> = arrayListOf()
    private lateinit var reference: DatabaseReference
    private lateinit var adaptersales : AdapterSales
    private lateinit var FAB : FloatingActionButton
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private var role = "Admin"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salesman_bulletin)
        supportActionBar?.title = "Salesman Bulletin"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        FAB = findViewById(R.id.button_note)
        getUsernameLocal()
        FAB.setOnClickListener{view ->
            val intent = Intent (this, codenetic.kodegiri.coba3.main.Salesman_Tambah::class.java)
            startActivity(intent)
        }
        rvSalesB = findViewById(R.id.rv_sales)
        rvSalesB.setHasFixedSize(true)
        rvSalesB.layoutManager = LinearLayoutManager(this)

        reference = FirebaseDatabase.getInstance().getReference("Salesman_Bulletin")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (datasnapshot1 : DataSnapshot in dataSnapshot.children) {
                    var p : SalesBulletin_isi = datasnapshot1.getValue(SalesBulletin_isi::class.java) as SalesBulletin_isi
                    list.add(p)
                }
                adaptersales = AdapterSales(list)
                rvSalesB.adapter = adaptersales
                var itemTouchHelper = ItemTouchHelper(SwipeDelete(adaptersales))
                itemTouchHelper.attachToRecyclerView(rvSalesB)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Users")
            .child(username_key_new)

        reference.addValueEventListener(object: ValueEventListener {
            @SuppressLint("RestrictedApi")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rolefromfirebase: String = dataSnapshot.child("Role").value.toString()
                if(role == rolefromfirebase){
                    FAB.visibility = View.VISIBLE

                } else {
                    FAB.visibility = View.GONE
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    override fun onBackPressed(){

    }
}
