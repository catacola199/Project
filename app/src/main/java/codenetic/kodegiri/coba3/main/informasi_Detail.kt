package codenetic.kodegiri.coba3.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import codenetic.kodegiri.coba3.R

class informasi_Detail : AppCompatActivity() {
    private lateinit var item_tittle : TextView
    private lateinit var item_isi : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi__detail)
        item_isi = findViewById(R.id.Item_Isi)
        item_tittle = findViewById(R.id.item_Tittle)
        val name = intent.getStringExtra(EXTRA_NAME)
        val detatil = intent.getStringExtra(EXTRA_DETAIL)
        item_tittle.text = detatil
        item_isi.text = name
    }
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DETAIL = "extra_detail"

    }
}