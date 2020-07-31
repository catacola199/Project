package codenetic.kodegiri.coba3.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import codenetic.kodegiri.coba3.R
import com.google.firebase.database.*

class informasi_Detail : AppCompatActivity() {
    private lateinit var item_tittle : TextView
    private lateinit var item_isi : TextView
    private lateinit var item_date : TextView
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi__detail)
        item_isi = findViewById(R.id.Item_Isi)
        item_date = findViewById(R.id.Item_time)
        item_tittle = findViewById(R.id.item_Tittle)
        val name = intent.getStringExtra("Tittle")

        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Information")
            .child(name)


        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                item_tittle.setText(dataSnapshot.child("Title").value.toString())
                item_isi.setText(dataSnapshot.child("Isi").value.toString())
                item_date.setText(dataSnapshot.child("datetime").value.toString())


            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}