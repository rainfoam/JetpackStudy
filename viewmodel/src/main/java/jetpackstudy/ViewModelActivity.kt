package com.pengfei.jetpackstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.pengfei.viewmodel.R
import jetpackstudy.ViewModelFactory
import jetpackstudy.ViewModelOne
import jetpackstudy.ViewModelTwo
import kotlinx.android.synthetic.main.activity_view_model.*

class ViewModelActivity : AppCompatActivity() {
    //懒加载一个ViewModelOne,viewModelStore方法只有onCreat方法调用后才会被初始化
    val viewmodelOne by ViewModelLazy<ViewModelOne>(ViewModelOne::class,
        { viewModelStore },
        { defaultViewModelProviderFactory })

    val viewmodelOne2 by viewModels<ViewModelOne> { defaultViewModelProviderFactory }

    //android view model 使用自定义Factory创建
    val viewmodelTwo by viewModels<ViewModelTwo> { ViewModelFactory(application) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)

        //创建Viewmodel
        val viewmodelOne3 = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(ViewModelOne::class.java)

        //库提供的其他Factory
        //ViewModelProvider.NewInstanceFactory()
        //ViewModelProvider.AndroidViewModelFactory(application)
        // ViewModelProvider.Factory

        //输出结果
        tv_one.text =viewmodelOne.getNum()
        tv_two.text =viewmodelOne2.getNum()
        tv_three.text =viewmodelTwo.getNum()
        tv_four.text =viewmodelOne3.getNum()

        //返回MainActivity
        findViewById<Button>(R.id.bt_main).setOnClickListener {
            finish()
        }


    }
}