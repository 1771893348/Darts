package com.dart.wgw

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.dart.wgw.R.id.sample_text
import com.dart.wgw.module.dartsround.DartsRoundGame
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sample_text:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
//        sample_text.text = stringFromJNI()
        sample_text = findViewById(R.id.sample_text)
        sample_text.setOnClickListener {
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
