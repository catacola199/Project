package codenetic.kodegiri.coba3

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
import android.widget.*
import codenetic.kodegiri.coba3.main.Profile
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class register : AppCompatActivity() {
    private lateinit var btnBack: LinearLayout
    private lateinit var btnContinue: Button
    private lateinit var edtnama: EditText
    private lateinit var edtnumbermobile: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtUsername: EditText
    private lateinit var edtGender: EditText
    private lateinit var edtFrom: EditText
    private lateinit var storage: StorageReference
    private lateinit var btn_add_new_photo : Button
    private lateinit var photo_location: Uri
    private lateinit var photo_profile : ImageView
    private var PHOTO_MAX: Int = 1

    //Firebase RealtimeDatabase
    private lateinit var reference: DatabaseReference // penyimpanan data secara lokal storage

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        btnContinue = findViewById(R.id.signup)

        //casting EditText
        btn_add_new_photo = findViewById(R.id.btn_add_new_photo)
        edtFrom = findViewById(R.id.editTextFrom)
        photo_profile = findViewById(R.id.photo_edit_profile)
        edtGender = findViewById(R.id.editTextGender)
        edtEmail = findViewById(R.id.editTextEmail)
        edtnumbermobile = findViewById(R.id.editTextMobile)
        edtPassword = findViewById(R.id.editTextPassword)
        edtnama = findViewById(R.id.editTextName)
        edtUsername = findViewById(R.id.editUserName)

        btn_add_new_photo.setOnClickListener{
            findPhoto()
        }
        btnContinue.setOnClickListener(View.OnClickListener {

            //menyimpan kepada lokal storage/smartphone
            val sharedPreferences: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(username_key, edtUsername.text.toString()).apply()

            //menyimpan ke firebase database
            reference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child(edtUsername.text.toString())
            storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child("Photousers")
                .child(edtUsername.text.toString())
            if(photo_location != null){
                val mStorageReference: StorageReference = storage.child( System.currentTimeMillis().toString() + "." + getFileExtension(photo_location))

                mStorageReference.putFile(photo_location)
                    .addOnFailureListener {
                        //failure upload
                    }
                    .addOnSuccessListener {

                        mStorageReference.downloadUrl.addOnSuccessListener { uri ->
                            //Toast.makeText(this, "URL : $uri", Toast.LENGTH_LONG).show()
                            reference.ref.child("Username").setValue(edtUsername.text.toString())
                            reference.ref.child("password").setValue(edtPassword.text.toString())
                            reference.ref.child("Name").setValue(edtnama.text.toString())
                            reference.ref.child("Email").setValue(edtEmail.text.toString())
                            reference.ref.child("From").setValue(edtFrom.text.toString())
                            reference.ref.child("Gender").setValue(edtGender.text.toString())
                            reference.ref.child("Phone").setValue(edtnumbermobile.text.toString())
                            reference.ref.child("url_photo_profile").setValue(uri.toString())
                        }
                    }.addOnCompleteListener {

                    }
            }

            Toast.makeText(this, "Data telah di Simpan ke Database", Toast.LENGTH_SHORT).show()
            val gotoprofile= Intent(this, MainMenu::class.java)
            startActivity(gotoprofile)
            // berpindah activity
        })

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PHOTO_MAX && resultCode == Activity.RESULT_OK && data != null && data.data != null){

            photo_location = data.data!!
            Picasso.get()
                .load(photo_location)
                .centerCrop()
                .fit()
                .into(photo_profile)

        }
    }

    fun getFileExtension(uri: Uri): String? {
        val contentResolver: ContentResolver = contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    fun findPhoto(){
        val pictureIntent = Intent()
        pictureIntent.setType("image/*")
        pictureIntent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(pictureIntent, PHOTO_MAX)
    }
}
