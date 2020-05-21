package codenetic.kodegiri.coba3.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R

class elearning : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elearning)
        supportActionBar?.title = "Catalog Apps"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onBackPressed(){

    }
}
