package com.example.kotlinflowwithretrofit.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinflowwithretrofit.R
import com.example.kotlinflowwithretrofit.ui.main.model.data_class.User
import kotlinx.android.synthetic.main.item_data.view.*


class DataAdapter(private var dataList: List<User>) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}