package codenetic.kodegiri.coba3.main

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import codenetic.kodegiri.coba3.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Training_Material_Tambah : AppCompatActivity() {
    private lateinit var btnInput: Button
    private lateinit var btnInputPDF: Button
    private lateinit var edttittle: EditText
    private lateinit var edtseries: EditText
    private lateinit var reference: DatabaseReference
    private lateinit var storage : StorageReference
    private lateinit var uri : Uri
    private var PDF: Int = 0
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training__material__tambah)
        getUsernameLocal()
        btnInput = findViewById(R.id.input)
        edttittle = findViewById(R.id.editTittle)
        edtseries = findViewById(R.id.editSeries)
        btnInputPDF = findViewById(R.id.inputPdf)
        btnInputPDF.setOnClickListener( {
            findPhoto()
        })
        btnInput.setOnClickListener(View.OnClickListener {

            //menyimpan kepada lokal storage/smartphone


            //menyimpan ke firebase database
            reference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Training_Material")
                .child(edttittle.text.toString())
            storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child("PDF")
                .child(edttittle.text.toString())
            if(uri != null){
                val mStorageReference: StorageReference = storage.child( System.currentTimeMillis().toString() + "." + getFileExtension(uri))

                mStorageReference.putFile(uri)
                    .addOnFailureListener {
                        //failure upload
                    }
                    .addOnSuccessListener {


                        mStorageReference.downloadUrl.addOnSuccessListener { uri ->
                            //Toast.makeText(this, "URL : $uri", Toast.LENGTH_LONG).show()

                            reference.ref.child("url_pdf").setValue(uri.toString())
                        }
                    }.addOnCompleteListener {

                    }
            }
            reference.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("Title").setValue(edttittle.text.toString())
                    dataSnapshot.ref.child("Series").setValue("A-"+edtseries.text.toString())
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PDF && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            uri = data.data!!


        }
    }

    fun getFileExtension(uri: Uri): String? {
        val contentResolver: ContentResolver = contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }
    fun findPhoto(){
        val pictureIntent = Intent()
        pictureIntent.setType("application/pdf")
        pictureIntent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(pictureIntent, PDF)
    }
    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }
}