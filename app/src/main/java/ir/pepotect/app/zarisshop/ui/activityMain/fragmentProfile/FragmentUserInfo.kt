package ir.pepotect.app.zarisshop.ui.activityMain.fragmentProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.dataModel.User
import ir.pepotect.app.zarisshop.presenter.UserPresenter
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import ir.pepotect.app.zarisshop.ui.uses.dialog.DialogProgress
import kotlinx.android.synthetic.main.fragment_user_info.*

class FragmentUserInfo : MyFragment() {

    lateinit var parent: FragmentProfile
    private lateinit var progress: DialogProgress

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutInflater.from(ctx).inflate(R.layout.fragment_user_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        progress = DialogProgress(ctx)
        btnUpdateUserInfo.setOnClickListener {
            validateDate()
        }
        getUserInfo()
    }

    private fun getUserInfo() {
        progress.show()
        UserPresenter(object : UserPresenter.Result {
            override fun userData(ok: Boolean, message: String, data: User?) {
                progress.cancel()
                if (ok) {
                    txtNameUserInfo.setText(data?.name)
                    txtFamilyUserInfo.setText(data?.family)
                    txtEmailUserInfo.setText(data?.email)
                } else
                    toast(message)
            }
        }, cancelTag).getUserInfo()
    }

    private fun validateDate() {
        val name = txtNameUserInfo.text.toString().trim()
        if (name.isEmpty()) {
            txtNameUserInfo.apply {
                error = "کامل کردن این فیلد اجباری است"
                requestFocus()
            }
            return
        }

        val family = txtFamilyUserInfo.text.toString().trim()
        if (family.isEmpty()) {
            txtFamilyUserInfo.apply {
                error = "کامل کردن این فیلد اجباری است"
                requestFocus()
            }
            return
        }
        updateInf(name, family)
    }

    private fun updateInf(name: String, family: String) {
        progress.show()
        UserPresenter(object : UserPresenter.Result {
            override fun result(ok: Boolean, message: String) {
                progress.cancel()
                if (!ok)
                    toast(message)
            }
        }, cancelTag).updateUserInfo(name, family, txtEmailUserInfo.text.toString().trim())
    }

}