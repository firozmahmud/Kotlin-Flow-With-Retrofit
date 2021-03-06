package com.example.kotlinflowwithretrofit.ui.user_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinflowwithretrofit.R
import com.example.kotlinflowwithretrofit.ui.user_list.model.data_class.User
import kotlinx.android.synthetic.main.item_data.view.*


class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var dataList: List<User> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvName.text = dataList[position].name
        holder.itemView.tvEmail.text = dataList[position].email
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateList(newList: List<User>) {
        dataList = newList
        notifyItemRangeChanged(0, itemCount)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}


