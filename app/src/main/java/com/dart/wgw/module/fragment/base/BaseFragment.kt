package com.dart.wgw.module.fragment.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Author:Admin
 * Time:2019/5/30 16:20
 * 描述：
 */
abstract class BaseFragment:Fragment(){
    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}