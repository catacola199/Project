package codenetic.kodegiri.coba3.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class quick_guide : AppCompatActivity() {
    private lateinit var rvQuick: RecyclerView
    private var list: ArrayList<Quick_guide_isi> = arrayListOf()
    private lateinit var reference: DatabaseReference
    private lateinit var adapterquick_guide : Quick_guide_Adapter
    private lateinit var FAB : FloatingActionButton
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private var role = "Admin"
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_guide)
        supportActionBar?.title = "Quick Guide"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        FAB = findViewById(R.id.button_note)

        getUsernameLocal()
        FAB.setOnClickListener{view ->
            val intent = Intent (this, codenetic.kodegiri.coba3.main.Quick_guide_Tambah::class.java)
            startActivity(intent)
        }
        rvQuick = findViewById(R.id.rv_quick)
        rvQuick.setHasFixedSize(true)
        rvQuick.layoutManager = LinearLayoutManager(this)

        reference = FirebaseDatabase.getInstance().getReference("Quick_Guide")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (datasnapshot1 : DataSnapshot in dataSnapshot.children) {
                    var p : Quick_guide_isi = datasnapshot1.getValue(Quick_guide_isi::class.java) as Quick_guide_isi
                    list.add(p)
                }
                adapterquick_guide = Quick_guide_Adapter(list)
                rvQuick.adapter = adapterquick_guide

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    override fun onBackPressed(){

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
}
