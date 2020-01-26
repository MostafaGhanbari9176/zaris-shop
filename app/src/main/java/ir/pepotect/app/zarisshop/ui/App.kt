package ir.pepotect.app.zarisshop.ui

import android.app.Application
import android.content.Context
import com.orhanobut.hawk.Hawk
import ir.pepotect.app.zarisshop.reciever.SmsReceiver

class App:Application() {

    companion object{
        const val baseUrl: String = "https://www.store.zaris-dev.ir//wp-json/wc/v3/"
        var smsReceiver: SmsReceiver.SmsReceiverListener? = null
        lateinit var ctx:Context
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        ctx = this
    }

}