package com.pengfei.databinding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.pengfei.databinding.databinding.ActivityDatabindingBinding

class DataBindingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityDatabindingBinding>(this, R.layout.activity_databinding)
        //赋值
        binding.apply {

            name = "张三"  //@{name}  @{name??`Null of Name`}为null就显示Null of Name  @{name==null?`null`:`nonull`}三元运算符
            address = "北京" //@{address,default=`ShenZhen`}defult设置的值只在xml预览时有效,并不代表运行起来后有default值填充控件

            obName = ObservableField<String>("原始的obName")

            oUser = ObUser()
            fUser = FoUser()
            user = User()//普通user

            click = View.OnClickListener {
                oUser?.age = 22
                oUser?.name = "改名啦"

                fUser?.name?.set("你好呀")//Smart cast to 'FoUser' is impossible 因为对象可null

                address="菜鸟窝！！"
                user?.name = "普通user改name"
            }

            adapter = BdAdapter()
            info = ItemBean(0,"include 的item")

            imgRes = getDrawable(R.drawable.ic_launcher_foreground)
        }
    }
}

