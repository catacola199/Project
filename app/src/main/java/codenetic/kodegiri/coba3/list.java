package codenetic.kodegiri.coba3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.allyants.notifyme.NotifyMe;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;

import codenetic.kodegiri.coba3.main.TimePickerFragment;

public class list extends AppCompatActivity implements android.app.DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Button tekan;
    private Calendar now = Calendar.getInstance();
    private NotificationManager mNotifManager;
    private EditText etTitle,etContent,etPhone,etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        etTitle = findViewById(R.id.editTextClientName);
        etContent = findViewById(R.id.editTextCompanyName);
        etPhone = findViewById(R.id.editTextPhoneclient);
        etEmail = findViewById(R.id.editTextEmailClient);

        Button btnNotify = findViewById(R.id.notify);

        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"datePicker");
            }
        });
        mNotifManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    public void onBackPressed(){

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, month);
        now.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(now.getTime());
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"datePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        now.set(Calendar.HOUR_OF_DAY, hourOfDay);
        now.set(Calendar.MINUTE, minute);
        String[] TO = {"someone@gmail.com"};
        String[] CC = {"xyz@gmail.com"};

        String number = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String content = etContent.getText().toString();
        String title = etTitle.getText().toString();

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+"+62"+number));
        Intent WhatsApp = new Intent(Intent.ACTION_VIEW);
        WhatsApp.setData(Uri.parse("http://api.whatsapp.com/send/?phone="+"+62"+number));
        Intent Gmail = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + "subject text"+ "&body=" + "body text " + "&to=" + email);
        Gmail.setData(data);

        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title(etTitle.getText().toString())
                .content(etContent.getText().toString())
                .color(255,0,0,255)
                .led_color(255,255,255,255)
                .time(now)
                .addAction(WhatsApp,"WhatsApp",false)
                .key("test")
                .addAction(intent,"Phone", false)
                .addAction(Gmail ,"Gmail",false)
                .large_icon(R.mipmap.ic_launcher_round)
                .rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")
                .build();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}