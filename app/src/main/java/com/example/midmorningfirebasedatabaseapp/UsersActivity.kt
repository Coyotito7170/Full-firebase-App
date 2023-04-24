package com.example.midmorningfirebasedatabaseapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersActivity : AppCompatActivity() {
    lateinit var listUsers:ListView
    lateinit var users:ArrayList<User>
    lateinit var adapter: CustomAdapter
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        listUsers = findViewById(R.id.mListUsers)
        users = ArrayList()
        adapter = CustomAdapter(this,users)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Please wait")
        // Create reference to the database to load data
        var ref = FirebaseDatabase.getInstance().getReference().child("Users")
        // Show the progress as you load data
        progressDialog.show()
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                users.clear()
                for (data in snapshot.children){
                    var user = data.getValue(User::class.java)
                    users.add(user!!)
                }
                adapter.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UsersActivity,"DB is inaccessible",Toast.LENGTH_LONG).show()
            }
        })
        listUsers.adapter = adapter
    }
}