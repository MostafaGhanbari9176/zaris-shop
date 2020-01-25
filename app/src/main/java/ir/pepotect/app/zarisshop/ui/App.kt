package ir.pepotect.app.zarisshop.ui

import android.app.Application
import android.content.Context
import com.orhanobut.hawk.Hawk

class App:Application() {

    companion object{
        const val baseUrl: String = "https://www.store.zaris-dev.ir//wp-json/wc/v3/"
        lateinit var ctx:Context
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this)
        ctx = this
    }

}