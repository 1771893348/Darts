package com.dart.wgw.module.fragment

import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dart.wgw.R
import com.dart.wgw.adapter.MainRecyclerAdapter
import com.dart.wgw.bean.NoTargetItem
import com.dart.wgw.module.dartsround.DartsRoundGame
import com.dart.wgw.module.fragment.base.BaseLazyFragment



/**
 * Author:Admin
 * Time:2019/5/30 16:19
 * 描述：
 */
class DartsNoTargetFragment: BaseLazyFragment(){


    var btn_darts:Button?=null
    var ryview:RecyclerView?=null
    var list:ArrayList<NoTargetItem> ?=null
    var noTargetAdapter:MainRecyclerAdapter?=null
    override fun initView(view: View) {
        btn_darts = view.findViewById<Button>(R.id.btn_darts)
        ryview = view.findViewById(R.id.ryview)
        list = ArrayList()
        for (i in 0..5){
            var targetItem = NoTargetItem()
            targetItem.name = "wgw$i"
            list!!.add(targetItem)
        }
        noTargetAdapter = MainRecyclerAdapter()
        noTargetAdapter!!.list = list
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        ryview!!.layoutManager =layoutManager
        ryview!!.adapter=noTargetAdapter
    }
    override fun initListener() {
        btn_darts!!.setOnClickListener {
            var intent = Intent()
            intent.setClass(activity,DartsRoundGame::class.java)
            startActivity(intent)
        }
    }
    override fun layout(): Int {
        return R.layout.fragment_darts_no_target
    }

    override fun lazyLoad() {

    }

}