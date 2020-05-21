package codenetic.kodegiri.coba3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView


class MainMenu : AppCompatActivity() {

    private lateinit var cardtm   : CardView
    private lateinit var cardlogout   : CardView
    private lateinit var cardelearning   : CardView
    private lateinit var cardcatalog   : CardView
    private lateinit var cardtransaction   : CardView
    private lateinit var cardsalesbul   : CardView
    private lateinit var cardinformation   : CardView
    private lateinit var cardquick   : CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        cardtm = findViewById(R.id.training_material)
        cardelearning = findViewById(R.id.elearning)
        cardcatalog = findViewById(R.id.catalog)
        cardlogout = findViewById(R.id.logout)
        cardtransaction = findViewById(R.id.transaction)
        cardsalesbul = findViewById(R.id.salesman)
        cardinformation = findViewById(R.id.information)
        cardquick = findViewById(R.id.quick_guide)
        cardquick.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.quick_guide::class.java)
            startActivity(intent)
        }
        cardsalesbul.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.salesman_bulletin::class.java)
            startActivity(intent)
        }
        cardinformation.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.information::class.java)
            startActivity(intent)
        }
        cardtransaction.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.transaction::class.java)
            startActivity(intent)
        }
        cardtm.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.training_material::class.java)
            startActivity(intent)
        }
        cardelearning.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.elearning::class.java)
            startActivity(intent)
        }
        cardcatalog.setOnClickListener{
            val intent = Intent (this, codenetic.kodegiri.coba3.main.catalog::class.java)
            startActivity(intent)
        }
        cardlogout.setOnClickListener {

            val intent_signout = Intent(this, MainActivity::class.java)
            startActivity(intent_signout)
            finish()
        }
    }
    override fun onBackPressed(){

    }

}
