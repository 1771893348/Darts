package com.dart.wgw

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.dart.wgw.adapter.FragmentAdapter
import com.dart.wgw.module.dartsround.DartsRoundGame
import com.dart.wgw.databinding.ActivityMainBinding
import com.dart.wgw.util.BUManager
import android.widget.Adapter as Adapter1

class MainActivity : AppCompatActivity() {

//    lateinit var sample_text:Button
    lateinit var mBinding:ActivityMainBinding

    var fragmentAdapter:FragmentAdapter?=null
    var fragments:ArrayList<Fragment>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        fragments = ArrayList()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Example of a call to a native method
//        sample_text.text = stringFromJNI()
//        sample_text = findViewById(R.id.sample_text)
        fragments!!.add(BUManager.getFragment("com.dart.wgw.module.fragment.DartsNoTargetFragment"))
        fragmentAdapter=FragmentAdapter(supportFragmentManager,fragments!!)
        mBinding.mainViewpager.adapter = fragmentAdapter
        mBinding.sampleText.setOnClickListener {
            var intent = Intent(this,DartsRoundGame::class.java)
            startActivity(intent)
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    external fun stringFromJNI(): String

//    companion object {
//
//        // Used to load the 'native-lib' library on application startup.
//        init {
//            System.loadLibrary("native-lib")
//        }
//    }
}
class MyAdapter : FragmentPagerAdapter {

    var fragments: MutableList<Fragment> = ArrayList()

    constructor(fm: FragmentManager) : super(fm) {

    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int = fragments.size
}