package codenetic.kodegiri.coba3.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import codenetic.kodegiri.coba3.R;

import static java.lang.String.valueOf;

public class transaction extends AppCompatActivity {
    Button createButton;
    private EditText name_client;
    private EditText price1;
    private EditText email;
    private EditText project;
    private EditText item;
    float p;
    int pageWidth = 1600;
    Bitmap bmp, scaledbmp;
    Date dateObj;
    DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getSupportActionBar().setTitle("Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        item = findViewById(R.id.item_name);
        project = findViewById(R.id.project_name);
        email = findViewById(R.id.email_client);
        name_client = findViewById(R.id.client_name);
        price1 = findViewById(R.id.price);
        bmp = BitmapFactory.decodeResource(getResources(),R.mipmap.logo2);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 200,200, false);
        createButton = findViewById(R.id.selesai);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();

    }
    public void onBackPressed(){

    }
    private void createPDF() {
        createButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dateObj = new Date();
                                                String no=price1.getText().toString();       //this will get a string
                                                int no2=Integer.parseInt(no);              //this will get a no from the string
                                                float finalValue1=Float.parseFloat(valueOf(no2));
                                                if (name_client.getText().toString().length() == 0 ||
                                                        no.length() == 0) {
                                                    Toast.makeText(transaction.this, "Some fields empty", Toast.LENGTH_LONG).show();
                                                }else {
                                                    PdfDocument myPdfDocument = new PdfDocument();
                                                    Paint myPaint = new Paint();
                                                    Paint titlePaint = new Paint();

                                                    PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1600,2500,1).create();
                                                    PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                                                    Canvas canvas = myPage1.getCanvas();

                                                    canvas.drawBitmap(scaledbmp,100,180, myPaint);

                                                    titlePaint.setTextAlign(Paint.Align.RIGHT);
                                                    titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                    titlePaint.setTextSize(40);
                                                    canvas.drawText("PURCHASE ORDER",pageWidth-200,230, titlePaint);

                                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                                    myPaint.setTextSize(20);
                                                    myPaint.setColor(Color.BLACK);
                                                    canvas.drawText("PT.Kode Evolusi Bangsa ", 100, 450,myPaint);
                                                    canvas.drawText("Head Office :",100,490,myPaint);
                                                    canvas.drawText("Jl. Kenangan 1268, Jombor Kidul",100,530,myPaint);
                                                    canvas.drawText("Sinduadi, Mlati, Sleman",100,570,myPaint);
                                                    canvas.drawText("Yogyakarta 55284",100,610,myPaint);
                                                    canvas.drawText("Indonesia",100,650,myPaint);
                                                    canvas.drawText("Tel : +62274-623-971",100,690,myPaint);

                                                    myPaint.setStyle(Paint.Style.STROKE);
                                                    myPaint.setStrokeWidth(2);
                                                    canvas.drawRect(100,720,pageWidth-700,770,myPaint);
                                                    canvas.drawRect(100,770,pageWidth-700,820,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    myPaint.setTextSize(20);
                                                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                    canvas.drawText("Project Name ",495, 750,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    canvas.drawText(project.getText().toString(),495, 800,myPaint); //isi dari Project Name

                                                    myPaint.setStyle(Paint.Style.STROKE);
                                                    myPaint.setStrokeWidth(2);
                                                    canvas.drawRect(1200,720,pageWidth-200,770,myPaint);
                                                    canvas.drawRect(1200,770,pageWidth-200,820,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    myPaint.setTextSize(20);
                                                    canvas.drawText("Date Issued",1300, 750,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    dateFormat = new SimpleDateFormat("dd/MM/yy");
                                                    canvas.drawText(dateFormat.format(dateObj),1300, 800,myPaint);// isi dari tanggal

                                                    myPaint.setStyle(Paint.Style.STROKE);
                                                    myPaint.setStrokeWidth(2);
                                                    canvas.drawRect(100,850,pageWidth-700,900,myPaint);
                                                    canvas.drawRect(100,900,pageWidth-700,950,myPaint);

                                                    canvas.drawLine(450,850,450,900,myPaint);
                                                    canvas.drawLine(450,900,450,950,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    myPaint.setTextSize(20);
                                                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                    canvas.drawText("Nama Client ",275, 875,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    canvas.drawText(name_client.getText().toString(),275, 925,myPaint); //isi dari Project Name

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    myPaint.setTextSize(20);
                                                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                    canvas.drawText("Email",670, 875,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    canvas.drawText(email.getText().toString(),670, 925,myPaint); //isi dari Project Name

                                                    myPaint.setStyle(Paint.Style.STROKE);
                                                    myPaint.setStrokeWidth(2);
                                                    canvas.drawRect(100,980,pageWidth-700,1030,myPaint);
                                                    canvas.drawRect(100,1030,pageWidth-700,1080,myPaint);

                                                    canvas.drawLine(450,980,450,1030,myPaint);
                                                    canvas.drawLine(450,1030,450,1080,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    myPaint.setTextSize(20);
                                                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                    canvas.drawText("Nama Sales",275, 1005,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    canvas.drawText(" Bagus Fachrurozi ",275, 1055,myPaint); //isi dari Project Name

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    myPaint.setTextSize(20);
                                                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                                                    canvas.drawText("Email",670, 1005,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.CENTER);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    canvas.drawText(" bagus@gmail.com ",670, 1055,myPaint); //isi dari Project Name

                                                    myPaint.setStyle(Paint.Style.STROKE);
                                                    myPaint.setStrokeWidth(2);
                                                    canvas.drawRect(100,1130,pageWidth-400,1180,myPaint);

                                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                                    myPaint.setStyle(Paint.Style.FILL);
                                                    canvas.drawText("No ",130, 1155,myPaint);
                                                    canvas.drawText("Item Description ",200, 1155,myPaint);
                                                    canvas.drawText("Price ",700, 1155,myPaint);


                                                    canvas.drawLine(180,1130,180,1180,myPaint);
                                                    canvas.drawLine(680,1130,680,1180,myPaint);


                                                    canvas.drawText("1",100, 1210, myPaint);
                                                    canvas.drawText(item.getText().toString(),200,1210,myPaint);
                                                    canvas.drawText(valueOf(finalValue1), 700,1210,myPaint);

                                                    String value1= price1.getText().toString();
                                                    int finalValue=Integer.parseInt(value1);
                                                    float tax = finalValue * 10 / 100;
                                                    canvas.drawLine(680,1640,pageWidth-400,1340,myPaint);
                                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                                    canvas.drawText("Tax (10%)",700,1390,myPaint);
                                                    canvas.drawText(":",900,1390,myPaint);
                                                    myPaint.setTextAlign(Paint.Align.RIGHT);
                                                    canvas.drawText(valueOf(tax),pageWidth-420,1390,myPaint);

                                                    myPaint.setColor(Color.rgb(247,147,30));
                                                    canvas.drawRect(680,1740,pageWidth-400,1540,myPaint);
                                                    float total1 = finalValue + tax;
                                                    myPaint.setColor(Color.BLACK);
                                                    myPaint.setTextSize(25);
                                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                                    canvas.drawText("Total",700,1510,myPaint);
                                                    myPaint.setTextAlign(Paint.Align.RIGHT);
                                                    canvas.drawText(valueOf(total1),pageWidth-440,1510,myPaint);




                                                    myPdfDocument.finishPage(myPage1);
                                                    Toast.makeText(transaction.this,"Berhasil",Toast.LENGTH_LONG).show();

                                                    File file = new File(Environment.getExternalStorageDirectory(),"/hello99.pdf");

                                                    try {
                                                        myPdfDocument.writeTo(new FileOutputStream(file));
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                    myPdfDocument.close();
                                                }

                                            }
                                        }


        );
    }

}
