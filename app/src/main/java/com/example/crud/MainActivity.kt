package com.example.crud

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.crud.adapter.UserAdapter
import com.example.crud.data.AppDatabase
import com.example.crud.data.entity.User
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private var list = mutableListOf<User>()
    private lateinit var adapter: UserAdapter
    private lateinit var database: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)


        database = AppDatabase.getInstance(applicationContext)
        adapter = UserAdapter(list)
        adapter.setDialog(object : UserAdapter.Dialog{

            override fun onClick(position: Int) {
                //membua dialog view
                val user = list[position]
                val fullName = "${user.firstName ?: ""} ${user.lastName ?: ""}".trim()

                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(fullName)
                dialog.setItems(R.array.items_option) { dialog, which ->
                    if (which==0) {
                        //coding ubah
                        val intent = Intent(this@MainActivity, EditorActivity2::class.java)
                        intent.putExtra("id", list[position].uid)
                        startActivity(intent)
                    } else if (which==1) {
                        //coding hapus
                        database.userDao().delete(list[position])
                        getData()
                        Toast.makeText(this@MainActivity, "El usuario ha sido eliminado", Toast.LENGTH_SHORT).show()
                    } else {
                        //coding batal
                        dialog.dismiss()
                    }


                }
                val dialogView = dialog.create()
                dialogView.show()
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))

        fab.setOnClickListener {
            startActivity(Intent(this, EditorActivity2::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData(){
        list.clear()
        list.addAll(database.userDao().getall())
        adapter.notifyDataSetChanged()
    }
}