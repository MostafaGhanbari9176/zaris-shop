package ir.pepotect.app.zarisshop.ui.authentication

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import androidx.transition.*
import android.view.ViewTreeObserver
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.uses.MyActivity
import kotlinx.android.synthetic.main.activity_auth.*

class ActivityAuth:MyActivity(R.id.ContentAuth) {
    private var CVDrawed = false
    private var imgDrawed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        App.ctx = this
        init()
    }

    private fun init(){
        startHeadAnimation()
        listenLayoutDrawed()
        changeView(FragmentGetPhone())
    }

    private fun listenLayoutDrawed() {
        setTransition()
        imgAuth.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                imgAuth.viewTreeObserver.removeOnGlobalLayoutListener(this)
                imgDrawed = true
                if(CVDrawed)
                    changeLayoutBounds()
            }
        })
        CVAuth.viewTreeObserver.addOnGlobalLayoutListener(object:ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                CVAuth.viewTreeObserver.removeOnGlobalLayoutListener(this)
                CVDrawed = true
                if(imgDrawed)
                    changeLayoutBounds()
            }
        })
    }

    private fun changeLayoutBounds() {
        val imgH = imgAuth.measuredHeight
        val cvH = CVAuth.height
        CVAuth.requestLayout()
        CVAuth.layoutParams.height = cvH + imgH/2
        CVAuth.y -= imgH/2
    }

    private fun setTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(RLAuth, TransitionSet().apply {
                addTransition(ChangeBounds())
                addTransition(Fade())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addTransition(ChangeClipBounds())
                }
            })
        }
    }

    private fun startHeadAnimation() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            (imgAuthHead.drawable as AnimatedVectorDrawable).start()
        } else
            (imgAuthHead.drawable as AnimatedVectorDrawableCompat).start()
    }

    override fun onResume() {
        super.onResume()
        App.ctx = this
    }

}