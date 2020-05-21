package codenetic.kodegiri.coba3.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import codenetic.kodegiri.coba3.R
import com.bumptech.glide.Glide

class Detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var imgPhoto: ImageView = findViewById(R.id.img)
        var tvDetail: TextView = findViewById(R.id.detail)

        val name = intent.getStringExtra(EXTRA_NAME)
        val detatil = intent.getStringExtra(EXTRA_DETAIL)
        val image = intent.getIntExtra(EXTRA_IMAGE, 0)

        Glide.with(this)
            .load(image)
            .into(imgPhoto);
        tvDetail.text = detatil

        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = name
        }
    }
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_IMAGE = "extra_image"

    }
}
