package codenetic.kodegiri.coba3.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R

class training_material : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_material)
        supportActionBar?.title = "Catalog Apps"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onBackPressed(){

    }
}
