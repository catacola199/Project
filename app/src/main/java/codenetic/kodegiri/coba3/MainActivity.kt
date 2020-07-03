package codenetic.kodegiri.coba3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import codenetic.kodegiri.coba3.main.SharedPreference
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnlogin   : Button
    private lateinit var email      : EditText
    private lateinit var password   : EditText
    private lateinit var sharedPreference :SharedPreference
    private lateinit var reference: DatabaseReference
    private var USERNAME_KEY = "username_key"
    private var username_key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnlogin    = findViewById(R.id.btn_login)
        email       = findViewById(R.id.editTextEmail)
        password    = findViewById(R.id.editTextPassword)
        
        btnlogin.setOnClickListener({
            // ubah state menjadi loading
            btnlogin.isEnabled = false
            btnlogin.setText("Loading ...")

            val dUsername: String = email.text.toString()
            val dPassword: String = password.text.toString()

            if (dUsername.isEmpty()) {
                btnlogin.isEnabled = true
                btnlogin.setText("SIGN IN")
                Toast.makeText(this@MainActivity, "username kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (dPassword.isEmpty()) {
                    btnlogin.isEnabled = true
                    btnlogin.setText("SIGN IN")
                    Toast.makeText(this@MainActivity, "Password kosong", Toast.LENGTH_SHORT).show()
                } else {
                    reference = FirebaseDatabase.getInstance()
                        .reference
                        .child("Users")
                        .child(dUsername)

                    reference.addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(dataSnapshot.exists() && dUsername != ""){

//                        Toast.makeText(this@SignInActivity, "Username Ditemukan", Toast.LENGTH_SHORT).show()
                                //Ambil data dari firebase (password)
                                val passwordFromFirebase: String = dataSnapshot.child("password").value.toString()

                                //validasi password firebase
                                if(dPassword == passwordFromFirebase){

                                    //sharedPReference Username to local
                                    //menyimpan kepada lokal storage/smartphone

                                    val sharedPreferences: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
                                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                    editor.putString(username_key, dUsername).apply()

                                    //berpindah activity
                                    val gotoHomeIntent = Intent(this@MainActivity, MainMenu::class.java)
                                    startActivity(gotoHomeIntent)
                                } else {
                                    btnlogin.isEnabled = true
                                    btnlogin.setText("SIGN IN")
                                    Toast.makeText(this@MainActivity, "Password Salah", Toast.LENGTH_SHORT).show()
                                }

                            } else {
                                btnlogin.isEnabled = true
                                btnlogin.setText("SIGN IN")
                                Toast.makeText(this@MainActivity, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })

                }
            }
        })
    }



}
