package com.dart.wgw.module.fragment.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.dart.wgw.databinding.ActivityMainBinding

/**
 * Author:Admin
 * Time:2019/5/30 16:20
 * 描述：
 */
abstract class BaseLazyFragment: Fragment(){
    var isVisible:Boolean?=false
     var mBinding:ViewDataBinding?=null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        var view =inflater.inflate(layout(),null)
        mBinding= DataBindingUtil.inflate(inflater,layout(),container,true)
        var view = mBinding!!.getRoot()
        initView(view)
        initListener()
        return view

    }
    abstract fun initView(view:View)
    abstract fun initListener()
    abstract fun layout():Int

    override fun onPause() {
        super.onPause()
    }
    override fun setUserVisibleHint(isVisibleToUser : Boolean ){
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint){
            isVisible = true
            onvisible()
        }else{
            isVisible = false
            onvisible()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
    fun onvisible(){
        lazyLoad()
    }
    abstract fun lazyLoad()
}