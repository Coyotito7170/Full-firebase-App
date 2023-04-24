package com.example.midmorningfirebasedatabaseapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class UsersupdateActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtIdNumber: EditText
    lateinit var progressDialog: ProgressDialog
    lateinit var btnUpdate:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usersupdate)
        edtName = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtIdNumber = findViewById(R.id.mEdtIdNumber)
        btnUpdate = findViewById(R.id.mBtnUserUpdate)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Updating")
        progressDialog.setMessage("please wait..")
        // Receive data sent via the intent
        var receivedName = intent.getStringExtra("name")
        var receivedEmail = intent.getStringExtra("email")
        var receivedIdNumber = intent.getStringExtra("idNumber")
        var receivedId = intent.getStringExtra("id")
        // Display the received data on the EditTexts
        edtName.setText(receivedName)
        edtEmail.setText(receivedEmail)
        edtIdNumber.setText(receivedIdNumber)
        // Set an onClick listener to the button update
        btnUpdate.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            var id = receivedId!!
            // check if the user is submitting empty fields
            if (name.isEmpty()){
                edtName.setError("please fill this input")
                edtName.requestFocus()
            }else if (email.isEmpty()) {
                edtEmail.setError("please fill this input")
                edtEmail.requestFocus()
            }else if (idNumber.isEmpty()) {
                edtIdNumber.setError("please fill this input")
                edtIdNumber.requestFocus()
            }else{
                // proceed to save
                var user = User(name, email, idNumber, id)
                // create a reference to firebase
                var ref = FirebaseDatabase.getInstance().getReference().child("Users/"+id)
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener{
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this,"user Updated successfully!", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@UsersupdateActivity,UsersActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"user Updating failed!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}