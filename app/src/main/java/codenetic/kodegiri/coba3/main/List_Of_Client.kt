package codenetic.kodegiri.coba3.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import codenetic.kodegiri.coba3.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class List_Of_Client : AppCompatActivity()  {

    private lateinit var FAB : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list__of__client)
        FAB = findViewById(R.id.button_note)

        FAB.setOnClickListener{view ->
            val intent = Intent (this, Add_Client::class.java)
            startActivity(intent)
        }

    }


}