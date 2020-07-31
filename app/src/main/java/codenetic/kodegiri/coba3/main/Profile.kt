package codenetic.kodegiri.coba3.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.MainActivity
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class Profile : AppCompatActivity() {
    private lateinit var btn_edit_profile : Button
    private lateinit var btn_signout : Button
    private lateinit var btn_back_home : Button
    //private lateinit var item_my_ticket : LinearLayout
    private lateinit var photo_profile : CircleImageView
    private lateinit var name : TextView
    private lateinit var from : TextView
    private lateinit var phone : TextInputEditText
    private lateinit var role : TextInputEditText
    private lateinit var gender : TextInputEditText
    private lateinit var email : TextInputEditText
    private lateinit var btn_logout : Button
    private lateinit var storage: StorageReference
    private lateinit var reference : DatabaseReference
    private lateinit var reference2 : DatabaseReference
    private lateinit var sharedPreference :SharedPreference
    private lateinit var Auth : FirebaseAuth
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        getUsernameLocal()
        btn_edit_profile = findViewById(R.id.btn_edt_profile)
        btn_logout = findViewById(R.id.btn_logout1)
        //item_my_ticket = findViewById(R.id.item_my_ticket)
        btn_back_home = findViewById(R.id.btn_back_home)
        photo_profile = findViewById(R.id.myPict)
        name = findViewById(R.id.view_name1)
        role = findViewById(R.id.view_Role1)
        phone = findViewById(R.id.view_Phone1)
        gender = findViewById(R.id.view_Gender1)
        from = findViewById(R.id.view_from1)
        email = findViewById(R.id.view_email1)

        btn_logout.setOnClickListener({
            Auth.signOut()
            val intent_signout = Intent(this, MainActivity::class.java)
            startActivity(intent_signout)
            finish()
        })
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
                    name.setText(dataSnapshot.child("Name").value.toString())
                    phone.setText(dataSnapshot.child("Phone").value.toString())
                    role.setText(dataSnapshot.child("Role").value.toString())
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

    override fun onBackPressed(){

    }
}