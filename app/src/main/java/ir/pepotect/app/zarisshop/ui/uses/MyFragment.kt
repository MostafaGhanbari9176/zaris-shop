package ir.pepotect.app.zarisshop.ui.uses

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import ir.pepotect.app.zarisshop.model.ApiClient
import ir.pepotect.app.zarisshop.ui.App

abstract class MyFragment:Fragment() {

    var cancelTag = this::class.java.name

    var ctx:Context = App.ctx

    override fun onDestroy() {
        super.onDestroy()
        ApiClient().cancelRequest(cancelTag)
    }

    fun toast(message:String, duration:Int = Toast.LENGTH_SHORT)
    {
        Toast.makeText(ctx, message, duration).show()
    }

}