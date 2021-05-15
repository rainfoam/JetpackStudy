package com.pengfei.jetpackstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.pengfei.databinding.DataBindingActivity
import com.pengfei.livedata.ui.LiveDataActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //跳转到LiveData
        findViewById<Button>(R.id.bt_livedata).setOnClickListener{
            startActivity(Intent(this,LiveDataActivity::class.java))
        }

        //跳转到Viewmodel
        findViewById<Button>(R.id.bt_viewmodel).setOnClickListener{
            startActivity(Intent(this,ViewModelActivity::class.java))
        }

        //跳转到DataBinding
        findViewById<Button>(R.id.bt_databinding).setOnClickListener{
            startActivity(Intent(this, DataBindingActivity::class.java))
        }



    }
}