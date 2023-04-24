package com.example.midmorningfirebasedatabaseapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class CustomAdapter(var context: Context, var data:ArrayList<User>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var mTxtName:TextView
        var mTxtEmail:TextView
        var mTxtIdNumber:TextView
        var btnDelete:Button
        var btnUpdate:Button
        init {
            this.mTxtName = row?.findViewById(R.id.mTvName) as TextView
            this.mTxtEmail = row?.findViewById(R.id.mTvEmail) as TextView
            this.mTxtIdNumber = row?.findViewById(R.id.mTvIdNumber) as TextView
            this.btnUpdate = row?.findViewById(R.id.mBtnUpdate) as Button
            this.btnDelete = row?.findViewById(R.id.mBtnDelete) as Button
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.user_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:User = getItem(position) as User
        viewHolder.mTxtName.text = item.name
        viewHolder.mTxtEmail.text = item.email
        viewHolder.mTxtIdNumber.text = item.idNumber
        viewHolder.btnDelete.setOnClickListener {
            var ref = FirebaseDatabase.getInstance().getReference().child("User/"+item.id)
            ref.removeValue().addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(context,"User deleted successfully",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Deleating user failed",Toast.LENGTH_LONG).show()
                }
            }


        }
        viewHolder.btnUpdate.setOnClickListener {
            var intent = Intent(context,UsersActivity::class.java)
            intent.putExtra("name",item.name)
            intent.putExtra("email",item.email)
            intent.putExtra("idNumber",item.idNumber)
            intent.putExtra("id",item.id)
            context.startActivity(intent)
        }
        return view as View
    }



    override fun getItem(position: Int): Any {
        return  data.get(position)

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}