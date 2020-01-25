package ir.pepotect.app.zarisshop.ui.uses

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.ui.App
import java.util.*

abstract class MyActivity(@IdRes private val content: Int = R.id.ContentCommon) : AppCompatActivity() {

    val backHistory = Stack<Fragment>()
    open fun changeView(f: Fragment) {
        if (backHistory.size > 0 && f::class == backHistory.peek()::class)
            return
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(content, addFragment(f)).commit()
    }

    private fun addFragment(f: Fragment): Fragment {
        val i = backHistory.search(f)
        return if (i != -1)
            backHistory[i]
        else {
            backHistory.add(f)
            f
        }

    }

    override fun onBackPressed() {
        if (backHistory.size <= 1) {
            super.onBackPressed()
            this.finish()
        } else {
            backHistory.pop()
            changeView(backHistory.pop())
        }
    }

    override fun onResume() {
        super.onResume()
        App.ctx = this
    }

}