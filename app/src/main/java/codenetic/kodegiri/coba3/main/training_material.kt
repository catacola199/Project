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

class training_material : AppCompatActivity() {
    private lateinit var rvTraining: RecyclerView
    private var list: ArrayList<Training_Material_isi> = arrayListOf()
    private lateinit var reference: DatabaseReference
    private lateinit var adaptertraining_material : Training_Material_Adapter
    private lateinit var FAB : FloatingActionButton
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private var role = "Admin"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_material)
        supportActionBar?.title = "Training Material"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        FAB = findViewById(R.id.button_note)
        getUsernameLocal()
        FAB.setOnClickListener{view ->
            val intent = Intent (this, codenetic.kodegiri.coba3.main.Training_Material_Tambah::class.java)
            startActivity(intent)
        }
        rvTraining = findViewById(R.id.rv_material)
        rvTraining.setHasFixedSize(true)
        rvTraining.layoutManager = LinearLayoutManager(this)

        reference = FirebaseDatabase.getInstance().getReference("Training_Material")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (datasnapshot1 : DataSnapshot in dataSnapshot.children) {
                    var p : Training_Material_isi = datasnapshot1.getValue(Training_Material_isi::class.java) as Training_Material_isi
                    list.add(p)
                }
                adaptertraining_material = Training_Material_Adapter(list)
                rvTraining.adapter = adaptertraining_material

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
