package ir.pepotect.app.zarisshop.ui.activityMain

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.localData.Pref
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.activityMain.fragmentCategory.FragmentCategory
import ir.pepotect.app.zarisshop.ui.authentication.ActivityAuth
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {

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
                    if (Hawk.get(Pref.logIn, false))
                        changeView(FragmentProfile())
                    else
                        startActivityForResult(Intent(this, ActivityAuth::class.java), 1)
                }
            }
            true
        }
    }

    private fun changeView(f: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.ContentMain, f).commit()
    }

    override fun onResume() {
        super.onResume()
        App.ctx = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK)
        {
            BNVMAin.selectedItemId = R.id.bnvProfile
        }
    }

}