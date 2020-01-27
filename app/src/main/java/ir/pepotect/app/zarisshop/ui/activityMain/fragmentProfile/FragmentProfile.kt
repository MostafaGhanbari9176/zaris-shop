package ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile

import android.app.Activity
import android.graphics.Point
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.*
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class FragmentProfile : MyFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(ctx).inflate(R.layout.fragment_profile, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        changeLayoutBounds()
        animateHead()
        changeView(FragmentAccessibility().
        apply { parent = this@FragmentProfile })
    }

    fun changeView(f: MyFragment) {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.ContentProfile, f).commit()
    }

    private fun animateHead() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            (imgProfileHead.drawable as AnimatedVectorDrawable).start()
        } else
            (imgProfileHead.drawable as AnimatedVectorDrawableCompat).start()
    }

    private fun changeLayoutBounds() {
        val p = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            (ctx as Activity).windowManager.defaultDisplay.getRealSize(p)
        } else
            (ctx as Activity).windowManager.defaultDisplay.getSize(p)
        RLProfile.layoutParams.height = (p.y * (6.5 / 16)).toInt()
    }

}