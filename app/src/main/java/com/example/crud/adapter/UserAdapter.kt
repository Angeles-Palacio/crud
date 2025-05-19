package com.example.crud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.data.entity.User
import com.example.crud.R;

class UserAdapter(var list: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private  lateinit var dialog: Dialog

    fun setDialog(dialog: Dialog){
        this.dialog = dialog
    }

    interface Dialog {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val user = list[position]
        val fullName = "${user.firstName ?: ""} ${user.lastName ?: ""}".trim()

        holder.fullname.text = fullName
        holder.email.text = user.email ?: ""
        holder.phone.text = user.phone ?: ""
    }


    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fullname: TextView
        var email: TextView
        var phone: TextView

        init {
            fullname = view.findViewById(R.id.full_name)
            email = view.findViewById(R.id.email)
            phone = view.findViewById(R.id.phone)
            view.setOnClickListener{
                dialog.onClick(layoutPosition)
            }

        }

    }
}