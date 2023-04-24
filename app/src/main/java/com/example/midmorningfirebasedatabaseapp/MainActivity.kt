package com.example.midmorningfirebasedatabaseapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var edtName:EditText
    lateinit var edtEmail:EditText
    lateinit var edtIdNumber:EditText
    lateinit var btnSave:Button
    lateinit var btnView:Button
    lateinit var progressDialog:ProgressDialog
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtIdNumber = findViewById(R.id.mEdtIdNumber)
        btnSave = findViewById(R.id.mBtnSave)
        btnView = findViewById(R.id.btnView)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("saving")
        progressDialog.setMessage("please wait..")
        btnSave.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            var id = System.currentTimeMillis().toString()
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
                        Toast.makeText(this,"user saved successfully!",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"user saving failed!",Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        btnView.setOnClickListener {
            var intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)
        }
    }
}