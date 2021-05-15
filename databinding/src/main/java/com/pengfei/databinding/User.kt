package com.pengfei.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

//1、继承BaseObservable的binding数据
class ObUser : BaseObservable() {
    var age = 30
    var name = ""
        set(value) {
            notifyPropertyChanged(BR.name)
            field = value + "<fu>"
        }
        @Bindable
        get() {
            return "$field ObUser:Name"
        }

    var desc = "这是field的User,菲尔"
        set(value) {
            field = value + "<fu>"
            notifyPropertyChanged(BR.desc)
        }
        @Bindable
        get() {
            return "$field ObUser:Desc"
        }
    var str = ""
        set(value) {
            field=value
            notifyPropertyChanged(BR.str)
        }
        @Bindable
        get() = "age=${age},name=${name},desc=${field}"
}

//个别属性的binding
class FoUser {
    var age = ObservableInt(20)
    var name = ObservableField("FoUser:Name")
    var desc = ObservableField("FoUser:Desc")
    var str = "age=${age.get()},name=${name.get()},desc=${desc.get()}"
}

class User{
    var age = 60
    var name = "李四"
    var desc = "李四今年60大寿"
}