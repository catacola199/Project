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
import android.widget.*
import codenetic.kodegiri.coba3.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class Edt_Profile : AppCompatActivity() {
    private lateinit var btn_edit_profile : Button
    private lateinit var btn_save : Button
    private lateinit var btn_back_home : ImageButton
    //private lateinit var item_my_ticket : LinearLayout
    private lateinit var photo_profile : CircleImageView

    private lateinit var name : TextInputEditText
    private lateinit var from : TextInputEditText
    private lateinit var phone : TextInputEditText
    private lateinit var gender : TextInputEditText
    private lateinit var email : TextInputEditText
    private lateinit var password : TextInputEditText
    private lateinit var username : TextInputEditText

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
        getUsernameLocal()
        btn_save = findViewById(R.id.btn_save)
        btn_edit_profile = findViewById(R.id.btn_add_new_photo)
        btn_back_home = findViewById(R.id.btn_back)
        photo_profile = findViewById(R.id.photo_edit_profile)
        name = findViewById(R.id.view_name2)
        phone = findViewById(R.id.view_Phone2)
        username = findViewById(R.id.view_username2)
        password = findViewById(R.id.view_password2)
        gender = findViewById(R.id.view_Gender2)
        from = findViewById(R.id.view_from2)
        email = findViewById(R.id.view_email2)
        btn_back_home.setOnClickListener{
            val gotoeditprofile = Intent(this, Profile::class.java)
            startActivity(gotoeditprofile)
        }
        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Users")
            .child(username_key_new)

        storage = FirebaseStorage
            .getInstance()
            .getReference()
            .child("Photousers")
            .child(username_key_new)
        btn_save.setOnClickListener {

            btn_save.isEnabled = false
            btn_save.setText("Loading ...")
            reference = FirebaseDatabase.getInstance()
                .reference
                .child("Users")
                .child(username_key_new)

            storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child("Photousers")
                .child(username_key_new)
            reference.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("Username").setValue(username.text.toString())
                    dataSnapshot.ref.child("password").setValue(password.text.toString())
                    dataSnapshot.ref.child("Name").setValue(name.text.toString())
                    dataSnapshot.ref.child("Email").setValue(email.text.toString())
                    dataSnapshot.ref.child("From").setValue(from.text.toString())
                    dataSnapshot.ref.child("Gender").setValue(gender.text.toString())
                    dataSnapshot.ref.child("Phone").setValue(phone.text.toString())

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



        reference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                name.setText(dataSnapshot.child("Name").value.toString())
                password.setText(dataSnapshot.child("password").value.toString())
                phone.setText(dataSnapshot.child("Phone").value.toString())
                username.setText(dataSnapshot.child("Username").value.toString())
                gender.setText(dataSnapshot.child("Gender").value.toString())
                from.setText(dataSnapshot.child("From").value.toString())
                email.setText(dataSnapshot.child("Email").value.toString())

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
        btn_edit_profile.setOnClickListener{
            findPhoto()
        }
    }
    fun findPhoto(){
        val pictureIntent = Intent()
        pictureIntent.setType("image/*")
        pictureIntent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(pictureIntent, PHOTO_MAX)
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
}