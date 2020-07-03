package codenetic.kodegiri.coba3.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class information : AppCompatActivity() {
    private lateinit var rvInformation: RecyclerView
    private var list: ArrayList<information_isi> = arrayListOf()
    private lateinit var reference: DatabaseReference
    private lateinit var adapter_information : information_adapter
    private lateinit var FAB : FloatingActionButton
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private var role = "Admin"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        supportActionBar?.title = "Information"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        FAB = findViewById(R.id.button_note)
        getUsernameLocal()
        FAB.setOnClickListener{view ->
            val intent = Intent (this, codenetic.kodegiri.coba3.main.informasi_Tambah::class.java)
            startActivity(intent)
        }
        rvInformation = findViewById(R.id.rv_information)
        rvInformation.setHasFixedSize(true)
        rvInformation.layoutManager = LinearLayoutManager(this)

        reference = FirebaseDatabase.getInstance().getReference("Information")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (datasnapshot1 : DataSnapshot in dataSnapshot.children) {
                    var p : information_isi = datasnapshot1.getValue(information_isi::class.java) as information_isi
                    list.add(p)
                }
                adapter_information = information_adapter(list)
                rvInformation.adapter = adapter_information

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
}
