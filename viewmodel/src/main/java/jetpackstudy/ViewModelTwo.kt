package jetpackstudy

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel


class ViewModelTwo(application: Application) : AndroidViewModel(application) {

    //初始化
    init {
        Log.i("VmTwo", "VmTwo 创建")
    }

    //内联函数
    internal fun getNum(): String {
        return "￥ VmTwo ￥ ${System.currentTimeMillis()}"
    }

    //Viewmodel销毁
    override fun onCleared() {
        super.onCleared()
        Log.e("VmTwo", "VmTwo 销毁 onClear")
    }
}