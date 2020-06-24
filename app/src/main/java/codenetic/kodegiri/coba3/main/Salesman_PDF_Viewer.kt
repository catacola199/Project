package codenetic.kodegiri.coba3.main

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.get
import codenetic.kodegiri.coba3.R
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class Salesman_PDF_Viewer : AppCompatActivity() {
    private lateinit var pdfview : PDFView

    private lateinit var Tittle : TextView
    private lateinit var reference: DatabaseReference
    private lateinit var storage: StorageReference
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private lateinit var uri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salesman__p_d_f__viewer)
        getUsernameLocal()
        Tittle = findViewById(R.id.Tittle)

        val position = intent.getStringExtra("url_pdf")
        Tittle.text = position


    }
    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }

}