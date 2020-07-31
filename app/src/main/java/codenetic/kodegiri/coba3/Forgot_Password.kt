package codenetic.kodegiri.coba3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class Forgot_Password : AppCompatActivity() {
    private lateinit var Email : EditText
    private lateinit var button1 : Button
    private lateinit var Auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot__password)
        supportActionBar?.title = "Forgot Password"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Email = findViewById(R.id.edt_email_pass)
        button1 = findViewById(R.id.Button_Forgot)
        Auth = FirebaseAuth.getInstance()
        button1.setOnClickListener({
            ForgotPassword(Email)
        })
    }
    override fun onBackPressed(){

    }
    private fun ForgotPassword(email : EditText){
        if (email.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }

        Auth.sendPasswordResetEmail(email.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Email sent.", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Email Tidak Terdaftar.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}