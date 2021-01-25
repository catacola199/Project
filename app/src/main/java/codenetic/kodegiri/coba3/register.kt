package codenetic.kodegiri.coba3

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class register : AppCompatActivity() {
    private lateinit var btnBack: LinearLayout
    private lateinit var btnContinue: Button
    private lateinit var edt_nama: EditText
    private lateinit var edt_numbermobile: EditText
    private lateinit var edt_Password: EditText
    private lateinit var edt_Email: EditText
    private lateinit var edt_Gender: EditText
    private lateinit var edt_From: EditText
    private lateinit var edt_Role: EditText
    private lateinit var storage: StorageReference
    private lateinit var btn_add_new_photo : Button
    private lateinit var photo_location: Uri
    private lateinit var photo_profile : ImageView
    private var PHOTO_MAX: Int = 1
    private lateinit var auth: FirebaseAuth

    //Firebase RealtimeDatabase
    private lateinit var reference: DatabaseReference // penyimpanan data secara lokal storage

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        btnContinue = findViewById(R.id.signup)
        auth = FirebaseAuth.getInstance()
        //casting EditText
        btn_add_new_photo = findViewById(R.id.btn_add_new_photo)
        edt_From = findViewById(R.id.editTextFrom)
        edt_Role = findViewById(R.id.editTextRole)
        photo_profile = findViewById(R.id.photo_edit_profile)
        edt_Gender = findViewById(R.id.editTextGender)
        edt_Email = findViewById(R.id.editTextEmail)
        edt_numbermobile = findViewById(R.id.editTextMobile)
        edt_Password = findViewById(R.id.editTextPassword)
        edt_nama = findViewById(R.id.editTextName)

        btn_add_new_photo.setOnClickListener{
            findPhoto()
        }
        btnContinue.setOnClickListener(View.OnClickListener {

            //menyimpan kepada lokal storage/smartphone
            auth.createUserWithEmailAndPassword(edt_Email.text.toString(), edt_Password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = task.result!!.user
                        val id = auth.currentUser!!.uid
                        reference = FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("Users")
                            .child(id)
                        storage = FirebaseStorage
                            .getInstance()
                            .getReference()
                            .child("Photousers")
                            .child(id)
                        if(photo_location != null){
                            val mStorageReference: StorageReference = storage.child( System.currentTimeMillis().toString() + "." + getFileExtension(photo_location))

                            mStorageReference.putFile(photo_location)
                                .addOnFailureListener {
                                    //failure upload
                                }
                                .addOnSuccessListener {

                                    mStorageReference.downloadUrl.addOnSuccessListener { uri ->
                                        //Toast.makeText(this, "URL : $uri", Toast.LENGTH_LONG).show()
                                        reference.ref.child("Name").setValue(edt_nama.text.toString())
                                        reference.ref.child("Role").setValue(edt_Role.text.toString())
                                        reference.ref.child("Email").setValue(edt_Email.text.toString())
                                        reference.ref.child("From").setValue(edt_From.text.toString())
                                        reference.ref.child("Gender").setValue(edt_Gender.text.toString())
                                        reference.ref.child("Phone").setValue(edt_numbermobile.text.toString())
                                        reference.ref.child("url_photo_profile").setValue(uri.toString())
                                    }
                                }.addOnCompleteListener {

                                }
                        }
                        Toast.makeText(baseContext, "Register Success."+"id :"+ id,
                            Toast.LENGTH_SHORT).show()

                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(baseContext, "Register failed, Try Again. ",
                            Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
            //menyimpan ke firebase database


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

    private fun onAuthSuccess(user: FirebaseUser) {

    }
    override fun onBackPressed(){
        startActivity(Intent(this, MainMenu::class.java))
    }
}
