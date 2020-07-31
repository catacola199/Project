package codenetic.kodegiri.coba3.main

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import codenetic.kodegiri.coba3.R
import codenetic.kodegiri.coba3.register
import com.allyants.notifyme.NotifyMe
import kotlinx.android.synthetic.main.activity_add__client.*
import kotlinx.android.synthetic.main.item_row_quick_guide.*
import java.util.*

class Add_Client : AppCompatActivity() , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var edt_date : EditText
    private lateinit var edt_client : EditText
    private lateinit var edt_phone : EditText
    private lateinit var edt_address : EditText
    private lateinit var edt_email : EditText
    private lateinit var Pickdate : Button
    private lateinit var now : Calendar
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var SaveDay = 0
    var SaveMonth = 0
    var SaveYear = 0
    var SaveHour = 0
    var SaveMinute = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__client)

        edt_date = findViewById(R.id.addTextEmailClient)
        edt_client = findViewById(R.id.addTextNameClient)
        edt_phone    = findViewById(R.id.addPhoneClient)
        edt_address  = findViewById(R.id.addTextAlamat)
        edt_email   = findViewById(R.id.addTextEmailClient)
        Pickdate = findViewById(R.id.SetDatePick)

        pickDate()

    }
    private fun getDateTimeCalendar(){
       val cal = Calendar.getInstance()

       day = cal.get(Calendar.DAY_OF_MONTH)
       month = cal.get(Calendar.MONTH)
       year = cal.get(Calendar.YEAR)
       hour = cal.get(Calendar.HOUR)
       minute = cal.get(Calendar.MINUTE)


    }

    private fun pickDate(){
        Pickdate.setOnClickListener(){
            getDateTimeCalendar()
            DatePickerDialog(this,this,year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        SaveDay = dayOfMonth
        SaveMonth = month
        SaveYear = year

        getDateTimeCalendar()
        TimePickerDialog(this,this, hour,minute,true).show()


        now.set(Calendar.YEAR,year)
        now.set(Calendar.MONTH,month)
        now.set(Calendar.DAY_OF_MONTH,dayOfMonth)

    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        SaveHour = hourOfDay
        SaveMinute = minute
        addTextDate.text =  "$SaveDay-$SaveMonth-$SaveYear, $SaveHour:$SaveMonth"

        now.set(Calendar.HOUR_OF_DAY,hourOfDay)
        now.set(Calendar.MINUTE,minute)
        val intent = Intent (this, register::class.java)


        val notifyme = NotifyMe.Builder(applicationContext)
            .title(edt_client.text.toString())
            .content(edt_client.text.toString())
            .color(255,0,0,255)
            .led_color(255,255,255,255)
            .time(now)
            .addAction(intent,"")


    }
}