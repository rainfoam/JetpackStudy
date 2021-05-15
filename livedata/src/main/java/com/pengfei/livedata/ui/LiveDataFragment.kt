package com.pengfei.livedata.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.pengfei.livedata.R
import kotlinx.android.synthetic.main.fragment_livedata.*


class LiveDataFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_livedata,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as LiveDataActivity).apply {
            //liveData值修改,通知fragment刷新
            liveData.observe(viewLifecycleOwner, Observer {
                tv_live_apple.text = "liveData值修改$it"
            })

            //map转换数据
            val liveMapApple = liveData.map {
                Log.d("AppleFragment", "LiveData在AppleFragment中 map $it")
                "it mapped it ${it.takeLast(4)}"
            }
            //在inActive状态下，是不会感知数据的，但是一旦resume，就会得到最新的数据
            liveMapApple.observe(viewLifecycleOwner, Observer {
                val s = "mapped it: $it"
                tv_mapped_live_apple.setText("liveData值map变换:$s")
                Log.w("AppleFragment", "LiveData在AppleFragment中 map后的数据 $it")
            })

            //mediator
            mediatorLive.observe(viewLifecycleOwner, Observer {
                //如果在inactive状态下，one two都变化了，它resume后只接受 代码顺序 最新添加的 source 的最后的值
                val s = "mediator it $it"
                tv_media_live_apple.setText("liveData值mediator:$s")
                Log.w("AppleFragment", "AppleFragment中 mediatorLive ---> $it")
            })

            //switch map 结合mediator，通过条件，控制选择数据源,这里模拟的是，it的数字奇偶，控制最终输出
            val swLive = mediatorLive.switchMap {
                if (it.second.takeLast(1).toInt() % 2 == 0) liveOne else liveTwo
            }

            //UI可以看出，不论是one，还是 two，改变的话，只有满足条件，才会生效。
            swLive.observe(viewLifecycleOwner, Observer {
                tv_switch_live_apple.text = "liveData值swith:$it"
                Log.w("AppleFragment", "AppleFragment中 switchMap ---> $it")
            })
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}