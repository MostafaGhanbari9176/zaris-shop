package ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile.fragmentAddressList

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.ui.App

class DialogAddress(ctx: Context = App.ctx) : Dialog(ctx) {
    private val v = LayoutInflater.from(ctx).inflate(R.layout.dialog_address, null, false)

    init {
        val p = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            (ctx as Activity).windowManager.defaultDisplay.getRealSize(p)
        } else
            (ctx as Activity).windowManager.defaultDisplay.getSize(p)
        setContentView(v)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        v.layoutParams.apply {
            width = p.x
            height = RelativeLayout.LayoutParams.WRAP_CONTENT
        }
    }

}