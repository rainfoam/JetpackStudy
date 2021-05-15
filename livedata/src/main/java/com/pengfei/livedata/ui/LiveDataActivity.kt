package com.pengfei.livedata.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import com.pengfei.livedata.R
import kotlinx.android.synthetic.main.activity_livedata.*

open class LiveDataActivity : AppCompatActivity() {

    //声明变量LiveData
    val liveData = MutableLiveData<String>()

    //LiveData的Map转换
    val liveDataMap = liveData.map {
        Pair<Int, String>(it.hashCode(), it)
    }

    //
    val liveOne = MutableLiveData<String>()
    val liveTwo = MutableLiveData<String>()
    val mediatorLive = MediatorLiveData<Pair<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata)

        val appleFragment = LiveDataFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container_live, appleFragment)
            .commit()
        //// TODO 注意 hide 和 show不会改变fragment的生命周期状态 所以用attach detach
        //显示fragment
        btn_create_fg_live.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .attach(appleFragment)
                .commit()
            Log.d("LiveActivity", "onCreate: 显示fg ${appleFragment.isVisible}")
        }
        //隐藏fragment
        btn_destroy_fg_live.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .detach(appleFragment)
                .commit()
            Log.w("LiveActivity", "onCreate: 隐藏fg ${appleFragment.isVisible}")

        }
        //变更livedata的值
        btn_change_live.setOnClickListener {
            liveData.value = "当前liveData的值：${System.currentTimeMillis()}"
        }
        //变更LiveData One的值
        btn_change1_live.setOnClickListener {
            liveOne.value = "one:${System.currentTimeMillis().toString().takeLast(6)}"
        }
        //变更LiveData Two的值
        btn_change2_live.setOnClickListener {
            liveTwo.value = "two:${System.currentTimeMillis().toString().takeLast(6)}"
        }

        //观察值
        liveData.observe(this, Observer {
            tv_live_data_activity.text = "liveData:$it"
            Log.d("LiveActivity", "LiveData在LiveActivity中 $it")
        })
        //map转换后的数值
        liveDataMap.observe(this, Observer {
            tv_mapped_data_activity.text = "LiveData的Map后的值$it"
            Log.i("LiveActivity", "LiveData在LiveActivity中 map 后 $it")
        })


        //中介者
        mediatorLive.addSource(liveTwo) {
            Log.d("LiveActivity", "LiveActivity中 LiveTwo ---> $it")
            mediatorLive.value = "two >>>>>" to it
        }
        mediatorLive.addSource(liveOne) {
            Log.d("LiveActivity", "LiveActivity中 LiveOne ---> $it")
            mediatorLive.value = "one >>" to it
        }
    }
}