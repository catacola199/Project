package codenetic.kodegiri.coba3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import codenetic.kodegiri.coba3.main.SharedPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main_menu.*


class MainMenu : AppCompatActivity() {

    private lateinit var cardtm   : CardView
    private lateinit var cardlogout   : CardView
    private lateinit var cardelearning   : CardView
    private lateinit var cardcatalog   : CardView
    private lateinit var cardtransaction   : CardView
    private lateinit var cardsalesbul   : CardView
    private lateinit var cardinformation   : CardView
    private lateinit var cardregis   : CardView
    private lateinit var cardquick   : CardView
    private lateinit var btn_profile : ImageButton
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private var role = "Admin"
    private lateinit var Auth : FirebaseAuth
    private lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        btn_profile = findViewById(R.id.img_profile)
        cardtm = findViewById(R.id.training_material)

        cardelearning = findViewById(R.id.elearning)
        cardcatalog = findViewById(R.id.catalog)
        cardlogout = findViewById(R.id.logout)
        cardtransaction = findViewById(R.id.transaction)
        cardsalesbul = findViewById(R.id.salesman)
        cardregis = findViewById(R.id.register)
        cardinformation = findViewById(R.id.information)
        cardquick = findViewById(R.id.quick_guide)
        Auth = FirebaseAuth.getInstance()
        getUsernameLocal()



        cardregis.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.register::class.java)
            startActivity(intent)
        }
        cardquick.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.quick_guide::class.java)
            startActivity(intent)
        }
        cardsalesbul.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.salesman_bulletin::class.java)
            startActivity(intent)
        }
        cardinformation.setOnClickListener{

        }
        cardtransaction.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.transaction::class.java)
            startActivity(intent)
        }
        cardtm.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.training_material::class.java)
            startActivity(intent)
        }
        cardelearning.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.Elearning_Act::class.java)
            startActivity(intent)
        }
        cardcatalog.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.catalog::class.java)
            startActivity(intent)
        }
        cardlogout.setOnClickListener {

        }
        btn_profile.setOnClickListener{
            val intent = Intent(this, codenetic.kodegiri.coba3.main.Profile::class.java)
            startActivity(intent)
            finish()
        }
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
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rolefromfirebase: String = dataSnapshot.child("Role").value.toString()
                if(role == rolefromfirebase){
                    cardregis.visibility = View.VISIBLE

                } else {
                    cardregis.visibility = View.GONE
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}
