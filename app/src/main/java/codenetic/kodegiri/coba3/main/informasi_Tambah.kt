package codenetic.kodegiri.coba3.main

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import codenetic.kodegiri.coba3.R
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant.now
import java.time.LocalDateTime.now
import java.util.*

class informasi_Tambah : AppCompatActivity() {
    private lateinit var btnInput: Button
    private lateinit var btnInputPDF: Button
    private lateinit var edttittle: EditText
    private lateinit var edtisi: EditText
    private lateinit var reference: DatabaseReference
    private lateinit var mref: DatabaseReference
    private lateinit var storage : StorageReference
    private lateinit var uri : Uri
    private var PDF: Int = 0
    private var info = "Information"
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi__tambah)
        edttittle = findViewById(R.id.edit_Tittle)
        edtisi = findViewById(R.id.editisi)
        btnInput = findViewById(R.id.input)

        btnInput.setOnClickListener(View.OnClickListener {


            //menyimpan ke firebase database
            reference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Information")
                .child(edttittle.text.toString())

            reference.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("Title").setValue(edttittle.text.toString())
                    dataSnapshot.ref.child("Isi").setValue(edtisi.text.toString())
                    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    val date = Date()
                    val strDate = dateFormat.format(date)
                    dataSnapshot.ref.child("datetime").setValue(strDate)

                }

                override fun onCancelled(dataSnapshot: DatabaseError) {

                }
            })

            Toast.makeText(this, "Data telah di Simpan ke Database", Toast.LENGTH_SHORT).show()
            // berpindah activity

        })
    }
}