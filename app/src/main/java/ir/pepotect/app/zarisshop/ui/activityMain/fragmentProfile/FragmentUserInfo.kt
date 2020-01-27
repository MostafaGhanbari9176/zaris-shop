package ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.ui.uses.MyFragment

class FragmentUserInfo:MyFragment() {

    lateinit var parent: FragmentProfile

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(ctx).
        inflate(R.layout.fragment_user_info, container, false)

}