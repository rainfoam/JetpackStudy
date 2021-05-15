package jetpackstudy

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelOne::class.java)) {
            return ViewModelOne() as T
        }else if (modelClass.isAssignableFrom(ViewModelTwo::class.java)){
            return ViewModelTwo(application) as T
        }else{
            throw ClassNotFoundException("class $modelClass 没有注册到工厂类这个viewModel啊")
        }
    }
}