package codenetic.kodegiri.coba3.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codenetic.kodegiri.coba3.MainMenu
import codenetic.kodegiri.coba3.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import androidx.recyclerview.widget.ItemTouchHelper

class salesman_bulletin : AppCompatActivity() {
    private lateinit var rvSalesB: RecyclerView
    private var list: ArrayList<SalesBulletin_isi> = arrayListOf()
    private lateinit var reference: DatabaseReference
    private lateinit var adaptersales : AdapterSales
    private lateinit var FAB : FloatingActionButton
    private lateinit var colorDrawableBackground: ColorDrawable
    private lateinit var deleteIcon: Drawable
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private var role = "Admin"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salesman_bulletin)
        colorDrawableBackground = ColorDrawable(Color.parseColor("#ff0000"))
        deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_delete_24)!!

        supportActionBar?.title = "Salesman Bulletin"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        FAB = findViewById(R.id.button_note)

        FAB.setOnClickListener{view ->
            val intent = Intent (this, codenetic.kodegiri.coba3.main.Salesman_Tambah::class.java)
            startActivity(intent)
        }
        rvSalesB = findViewById(R.id.rv_sales)
        rvSalesB.setHasFixedSize(true)
        rvSalesB.layoutManager = LinearLayoutManager(this)

        reference = FirebaseDatabase.getInstance().getReference("Salesman_Bulletin")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (datasnapshot1 : DataSnapshot in dataSnapshot.children) {
                    var p : SalesBulletin_isi = datasnapshot1.getValue(SalesBulletin_isi::class.java) as SalesBulletin_isi
                    list.add(p)
                }
                adaptersales = AdapterSales(list)
                rvSalesB.adapter = adaptersales

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                reference = FirebaseDatabase.getInstance().getReference("Quick_Guide")

                (adaptersales as AdapterSales).removeItem(viewHolder.adapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMarginVertical = (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX > 0) {
                    colorDrawableBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    deleteIcon.setBounds(itemView.left + iconMarginVertical, itemView.top + iconMarginVertical,
                        itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth, itemView.bottom - iconMarginVertical)
                } else {
                    colorDrawableBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    deleteIcon.setBounds(itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth, itemView.top + iconMarginVertical,
                        itemView.right - iconMarginVertical, itemView.bottom - iconMarginVertical)
                    deleteIcon.level = 0
                }

                colorDrawableBackground.draw(c)

                c.save()

                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)

                deleteIcon.draw(c)

                c.restore()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvSalesB)

    }

    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Users")
            .child(username_key_new)

        reference.addValueEventListener(object: ValueEventListener {
            @SuppressLint("RestrictedApi")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rolefromfirebase: String = dataSnapshot.child("Role").value.toString()
                if(role == rolefromfirebase){
                    FAB.visibility = View.VISIBLE

                } else {
                    FAB.visibility = View.GONE
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    override fun onBackPressed(){

    }
}
