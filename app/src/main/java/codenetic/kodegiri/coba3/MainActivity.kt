package codenetic.kodegiri.coba3

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import codenetic.kodegiri.coba3.main.SharedPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var btnlogin   : Button
    private lateinit var email      : EditText
    private lateinit var password   : EditText
    private lateinit var forgotPass : TextView

    private lateinit var alert : AlertDialog
    private lateinit var sharedPreference :SharedPreference
    private lateinit var reference: DatabaseReference
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private lateinit var Auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Auth = FirebaseAuth.getInstance()

        btnlogin    = findViewById(R.id.btn_login)
        email       = findViewById(R.id.editTextEmail)
        password    = findViewById(R.id.editTextPassword)
        forgotPass = findViewById(R.id.forgot_password)

        forgotPass.setOnClickListener({
            startActivity(Intent(this, Forgot_Password::class.java))
        })
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



                    Auth.signInWithEmailAndPassword(dUsername, dPassword)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                startActivity(Intent(this, MainMenu::class.java))
                                val user = Auth.currentUser
                                updateUI(user)
                                val id = Auth.currentUser!!.uid
                                val sharedPreferences: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                editor.putString(username_key, id).apply()
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(baseContext, "Login failed.",
                                    Toast.LENGTH_SHORT).show()
                                btnlogin.isEnabled = true
                                btnlogin.setText("SIGN IN")
                                updateUI(null)
                                // ...
                            }

                            // ...
                        }

                }
            }
        })
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = Auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser : FirebaseUser?){
        if (currentUser != null ){
            startActivity(Intent(this, MainMenu::class.java))
        }else{

        }
    }
}
