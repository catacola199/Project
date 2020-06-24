package codenetic.kodegiri.coba3.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import codenetic.kodegiri.coba3.R;

public class Dicoba extends AppCompatActivity {
    private TextView text1;
    private PDFView pdfView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mref = database.getReference("url_pdf");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicoba);
        pdfView = findViewById(R.id.pdf_view);
        text1 = findViewById(R.id.Tittle_PDF);

        String url = getIntent().getStringExtra("url_pdf");
        new RetrivePdfStream().execute(url);


    }
    class RetrivePdfStream extends AsyncTask<String,Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL (strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e){
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream)
                    .enableSwipe(true)
                    .enableAnnotationRendering(true)
                    .scrollHandle(null)
                    .swipeHorizontal(false).load();
        }
    }
}