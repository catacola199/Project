package codenetic.kodegiri.coba3.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R

class quick_guide : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_guide)
        supportActionBar?.title = "Catalog Apps"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onBackPressed(){

    }
}
