package com.dart.wgw.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dart.wgw.R
import com.dart.wgw.bean.NoTargetItem

class MainRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list:ArrayList<NoTargetItem> ?= null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView){
        var item_name:TextView = itemView.findViewById<TextView>(R.id.item_name)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.layout_notagetitem,parent,false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    public fun setMenuList(list:ArrayList<NoTargetItem>){
        this.list = list
    }
    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder:ViewHolder = holder as ViewHolder
        var noTargetItem:NoTargetItem = list!![position]
        viewHolder.item_name.text = noTargetItem.name
    }

}