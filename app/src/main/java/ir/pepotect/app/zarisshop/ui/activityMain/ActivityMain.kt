package ir.pepotect.app.zarisshop.ui.activityMain

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.activityMain.fragmentCategory.FragmentCategory
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
                R.id.bnvProfile -> changeView(FragmentProfile())
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

}