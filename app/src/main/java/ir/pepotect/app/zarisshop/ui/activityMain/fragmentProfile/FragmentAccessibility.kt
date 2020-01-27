package ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile.fragmentAddressList.FragmentAddressList
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import kotlinx.android.synthetic.main.fragment_accessibility.*

class FragmentAccessibility : MyFragment() {

    lateinit var parent:FragmentProfile

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(ctx).
        inflate(R.layout.fragment_accessibility, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init()
    {
        btnUserInfo.setOnClickListener {
            parent.changeView(FragmentUserInfo().
                apply { this.parent = this@FragmentAccessibility.parent })
        }
        `btnَAddressList`.setOnClickListener {
            parent.changeView(FragmentAddressList().
                apply { this.parent = this@FragmentAccessibility.parent })
        }
    }

}