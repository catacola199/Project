package codenetic.kodegiri.coba3.main

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import codenetic.kodegiri.coba3.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class Edt_Profile : AppCompatActivity() {
    private lateinit var btn_edit_profile : Button
    private lateinit var btn_save : Button
    private lateinit var btn_back_home : Button
    //private lateinit var item_my_ticket : LinearLayout
    private lateinit var photo_profile : ImageView
    private lateinit var name : TextView
    private lateinit var from : TextView
    private lateinit var username : TextView
    private lateinit var phone : TextView
    private lateinit var gender : TextView
    private lateinit var email : TextView
    private lateinit var password : TextView
    private lateinit var btn_add_new_photo : Button
    private lateinit var storage: StorageReference
    private lateinit var reference : DatabaseReference
    private lateinit var reference2 : DatabaseReference
    private lateinit var photo_location: Uri
    private var PHOTO_MAX: Int = 1

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edt__profile)
        btn_add_new_photo = findViewById(R.id.btn_add_new_photo)
        btn_edit_profile = findViewById(R.id.btn_edt_profile)
        btn_back_home = findViewById(R.id.btn_back)
        btn_save = findViewById(R.id.btn_save)
        photo_profile = findViewById(R.id.myPict)
        name = findViewById(R.id.btn_edt_profile)
        phone = findViewById(R.id.view_Phone)
        username = findViewById(R.id.view_username)
        gender = findViewById(R.id.view_Gender)
        from = findViewById(R.id.view_from)
        email = findViewById(R.id.view_email)
        password = findViewById(R.id.view_password)

        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Users")
            .child(username_key_new)

        storage = FirebaseStorage
            .getInstance()
            .getReference()
            .child("Photousers")
            .child(username_key_new)

        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                name.setText(dataSnapshot.child("name").value.toString())
                phone.setText(dataSnapshot.child("phone").value.toString())
                username.setText(dataSnapshot.child("username").value.toString())
                password.setText(dataSnapshot.child("password").value.toString())
                gender.setText(dataSnapshot.child("password").value.toString())
                from.setText(dataSnapshot.child("from").value.toString())
                email.setText(dataSnapshot.child("email_address").value.toString())

                // mendapatkan alamat photo profil dari firebase
                val photoLink: String = dataSnapshot.child("url_photo_profile").value.toString()

                //load photo dari url/link
                Picasso.get()
                    .load(photoLink)
                    .centerCrop()
                    .fit()
                    .into(photo_profile)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        btn_save.setOnClickListener {

            btn_save.isEnabled = false
            btn_save.setText("Loading ...")

            reference.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("nama_lengkap").setValue(name.text.toString())
                    dataSnapshot.ref.child("bio").setValue(phone.text.toString())
                    dataSnapshot.ref.child("username").setValue(username.text.toString())
                    dataSnapshot.ref.child("password").setValue(gender.text.toString())
                    dataSnapshot.ref.child("email_address").setValue(email.text.toString())
                    dataSnapshot.ref.child("email_address").setValue(email.text.toString())
                    dataSnapshot.ref.child("email_address").setValue(email.text.toString())

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

            if(photo_location != null){
                val mStorageReference: StorageReference = storage.child( System.currentTimeMillis().toString() + "." + getFileExtension(photo_location))

                mStorageReference.putFile(photo_location)
                    .addOnFailureListener {
                        //failure upload
                    }
                    .addOnSuccessListener {

                        mStorageReference.downloadUrl.addOnSuccessListener { uri ->
                            //Toast.makeText(this, "URL : $uri", Toast.LENGTH_LONG).show()

                            reference.ref.child("url_photo_profile").setValue(uri.toString())
                        }
                    }.addOnCompleteListener {
                        val gotoprofile= Intent(this,Profile::class.java)
                        startActivity(gotoprofile)
                    }
            }
        }

        btn_add_new_photo.setOnClickListener{
            findPhoto()
        }

        btn_back_home.setOnClickListener {
            val gotoprofile= Intent(this,Profile::class.java)
            startActivity(gotoprofile)
        }
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

    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }
    fun findPhoto(){
        val pictureIntent = Intent()
        pictureIntent.setType("image/*")
        pictureIntent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(pictureIntent, PHOTO_MAX)
    }
}