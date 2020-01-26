package ir.pepotect.app.zarisshop.ui.authentication

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.model.ApiClient
import ir.pepotect.app.zarisshop.reciever.SmsReceiver
import ir.pepotect.app.zarisshop.ui.App
import ir.pepotect.app.zarisshop.ui.uses.MyFragment
import ir.pepotect.app.zarisshop.ui.uses.dialog.DialogProgress
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_verify_code.*


class FragmentVerifyCode : MyFragment() {

    private lateinit var progress: DialogProgress
    private var requestTime = 60

    private val smsReceiver = object : SmsReceiver.SmsReceiverListener {
        override fun verifyPass(pass: String) {
            txtVerifyCode.setText(pass)
            checkCode(pass)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.smsReceiver = smsReceiver
        return inflater.inflate(R.layout.fragment_verify_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = DialogProgress()
        (ctx as ActivityAuth).txtLBLAuth.text = "تایید شماره همراه"
        btnVerifyCode.setOnClickListener {
            validateData()
        }
        btnRequestVerifyCode.setOnClickListener {
            if (requestTime == 0)
                requestVCode()
        }
        startTimer()
    }

    private fun startTimer() {
        val h = Handler()
        requestTime = 60
        btnRequestVerifyCode.text = "60 ثانیه تا درخواست مجدد کد تایید"
        Thread(Runnable {
            while (requestTime > 0) {
                h.post {
                    btnRequestVerifyCode.apply {
                        text = text.toString().replace("$requestTime", "${--requestTime}")
                        requestFocus()
                        if (requestTime == 0)
                            text = "ارسال مجدد کد تایید"
                    }
                }
                Thread.sleep(1000)
            }
        }).start()
    }

    private fun requestVCode() {
        startTimer()

    }

    private fun validateData() {
        val code = txtVerifyCode.text.toString().trim()
        if (code.isEmpty() || code.length != 6) {
            txtVerifyCode.apply {
                requestFocus()
                error =
                    if (code.isEmpty()) "لطفا مقداری وارد کنید" else "طول کد تایید 6 رقم می باشد"
            }
            return
        }
        checkCode(code)
    }

    private fun checkCode(code: String) {
        (ctx as ActivityAuth).apply {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        requestTime = 0
        ApiClient().cancelRequest("account")
    }

}