package ir.pepotect.app.zarisshop.ui.authentication

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_get_phone.*
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import android.Manifest.permission
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest
import com.orhanobut.hawk.Hawk
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.ApiClient
import ir.pepotect.app.zarisshop.model.localData.Pref
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import ir.pepotect.app.zarisshop.ui.uses.dialog.DialogProgress
import kotlinx.android.synthetic.main.activity_auth.*


class FragmentGetPhone : MyFragment() {

    private val progress = DialogProgress()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_get_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        (ctx as ActivityAuth).txtLBLAuth.text = "حساب کاربری"
        txtGetPhone.setText(Hawk.get(Pref.phone, ""))
        btnNextGetPhone.setOnClickListener {
            checkData()
        }
    }

    private fun checkData() {
        val v = txtGetPhone.text.toString().trim()
        if (v.length == 11 && v[0] == '0' && v[1] == '9' && TextUtils.isDigitsOnly(v)) {
            checkPermission()
        } else
            txtGetPhone.apply {
                requestFocus()
                setError("لطفا شماره همراه خود را صحیح وارد کنید")
            }

    }

    private fun checkPermission() {
        Dexter.withActivity(App.ctx as ActivityAuth)
            .withPermissions(
                permission.READ_SMS,
                permission.RECEIVE_SMS
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    sendToServer()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .withErrorListener {
            }
            .onSameThread()
            .check()

    }

    private fun sendToServer() {
        val phone = txtGetPhone.text.toString().trim()
        (ctx as ActivityAuth).changeView(FragmentVerifyCode())

    }

    override fun onPause() {
        super.onPause()
        ApiClient().cancelRequest("account")
    }

}