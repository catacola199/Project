package codenetic.kodegiri.coba3.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class Profile : AppCompatActivity() {
    private lateinit var btn_edit_profile : Button
    private lateinit var btn_signout : Button
    private lateinit var btn_back_home : Button
    //private lateinit var item_my_ticket : LinearLayout
    private lateinit var photo_profile : ImageView
    private lateinit var name : TextView
    private lateinit var from : TextView
    private lateinit var username : TextView
    private lateinit var phone : TextView
    private lateinit var gender : TextView
    private lateinit var email : TextView
    private lateinit var storage: StorageReference
    private lateinit var reference : DatabaseReference
    private lateinit var reference2 : DatabaseReference

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        getUsernameLocal()

        btn_edit_profile = findViewById(R.id.btn_edt_profile)
        //item_my_ticket = findViewById(R.id.item_my_ticket)
        btn_back_home = findViewById(R.id.btn_back_home)
        photo_profile = findViewById(R.id.myPict)
        name = findViewById(R.id.btn_edt_profile)
        phone = findViewById(R.id.view_Phone)
        username = findViewById(R.id.view_username)
        gender = findViewById(R.id.view_Gender)
        from = findViewById(R.id.view_from)
        email = findViewById(R.id.view_email)



        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Users")
            .child(username_key_new)

        storage = FirebaseStorage
            .getInstance()
            .getReference()
            .child("Photousers")
            .child(username_key_new)

        reference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                name.setText(dataSnapshot.child("nama_lengkap").value.toString())
                phone.setText(dataSnapshot.child("bio").value.toString())
                username.setText(dataSnapshot.child("username").value.toString())
                gender.setText(dataSnapshot.child("password").value.toString())
                from.setText(dataSnapshot.child("email_address").value.toString())
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


        btn_edit_profile.setOnClickListener {
            val gotoeditprofile = Intent(this, Edt_Profile::class.java)
            startActivity(gotoeditprofile)
        }


        btn_back_home.setOnClickListener {
            val gotohome = Intent(this, MainMenu::class.java)
            startActivity(gotohome)
        }

    }
    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }
}