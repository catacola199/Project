package codenetic.kodegiri.coba3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.*

class register : AppCompatActivity() {
    private lateinit var btnBack: LinearLayout
    private lateinit var btnContinue: Button
    private lateinit var edtnama: EditText
    private lateinit var edtnumbermobile: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtUsername: EditText

    //Firebase RealtimeDatabase
    private lateinit var reference: DatabaseReference // penyimpanan data secara lokal storage

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        btnContinue = findViewById(R.id.signup)

        //casting EditText
        edtEmail = findViewById(R.id.editTextEmail)
        edtnumbermobile = findViewById(R.id.editTextMobile)
        edtPassword = findViewById(R.id.editTextPassword)
        edtnama = findViewById(R.id.editTextName)
        edtUsername = findViewById(R.id.editUserName)
        //kembali ke halaman sign in

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

            reference.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("Username").setValue(edtUsername.text.toString())
                    dataSnapshot.ref.child("Email").setValue(edtEmail.text.toString())
                    dataSnapshot.ref.child("Name").setValue(edtnama.text.toString())
                    dataSnapshot.ref.child("password").setValue(edtPassword.text.toString())
                    dataSnapshot.ref.child("Number Mobile").setValue(edtnumbermobile.text.toString())
                    dataSnapshot.ref.child("user_balance").setValue(25)
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {

                }
            })

            Toast.makeText(this, "Data telah di Simpan ke Database", Toast.LENGTH_SHORT).show()
            // berpindah activity
            val registerActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(registerActivityIntent)
        })
    }
}
