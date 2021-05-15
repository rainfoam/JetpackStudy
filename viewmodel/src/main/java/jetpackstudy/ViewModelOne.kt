package jetpackstudy

import android.util.Log
import androidx.lifecycle.ViewModel

class ViewModelOne : ViewModel() {

    //初始化
    init {
        Log.i("VmOne", "VmOne创建")
    }

    //内联函数
    internal fun getNum(): String {
        return "￥ vmOne ￥ ${System.currentTimeMillis()}"
    }

    //Viewmodel销毁
    override fun onCleared() {
        super.onCleared()
        Log.e("VmOne", "VmOne 销毁 onClear")
    }
}
