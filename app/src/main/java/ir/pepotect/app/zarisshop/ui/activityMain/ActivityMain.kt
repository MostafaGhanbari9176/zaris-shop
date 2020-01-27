package ir.pepotect.app.zarisshop.ui.activityMain

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.orhanobut.hawk.Hawk
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.localData.Pref
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.activityMain.fragmentCategory.FragmentCategory
import ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile.FragmentProfile
import ir.pepotect.app.zarisshop.ui.authentication.ActivityAuth
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {

    private var exit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        App.ctx = this
        changeView(FragmentHome())
    }

    private fun init() {
        BNVMAin.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnvHome -> changeView(FragmentHome())
                R.id.bnvCategory -> changeView(FragmentCategory())
                R.id.bnvCart -> changeView(FragmentCart())
                R.id.bnvProfile -> {
                    // if (Hawk.get(Pref.logIn, false))
                    changeView(FragmentProfile())
                    //  else
                    //     startActivityForResult(Intent(this, ActivityAuth::class.java), 1)
                }
            }
            true
        }
    }

    private fun setTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(RLMain, TransitionSet().apply {
                addTransition(ChangeBounds())
                addTransition(Fade())
            })
        }
    }

    private fun changeView(f: Fragment) {
        setTransition()
        tlbMain.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.ContentMain, f).commit()
        txtAppLabel.text = when (f) {
            is FragmentProfile -> {
                tlbMain.visibility = View.GONE
                ""
            }
            is FragmentCart -> "سبد خرید"
            is FragmentCategory -> "دسته بندی ها"
            else -> "خانه"
        }
    }

    override fun onResume() {
        super.onResume()
        App.ctx = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            BNVMAin.selectedItemId = R.id.bnvProfile
        }
    }

    override fun onBackPressed() {
        if (exit) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(
                this, "برای خروج از برنامه یک بار دیگر کلیک کنید.",
                Toast.LENGTH_SHORT
            ).show()
            exit = true
            Handler().postDelayed({ exit = false }, 3 * 1000.toLong())
        }

    }

}