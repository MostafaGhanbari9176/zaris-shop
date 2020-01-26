package ir.pepotect.app.zarisshop.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import ir.pepotect.app.zarisshop.ui.App
import java.lang.Exception


class SmsReceiver : BroadcastReceiver() {

    interface SmsReceiverListener {
        fun verifyPass(pass: String)
    }

    private val smsReceiver = App.smsReceiver

    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras

        if (extras != null) {
            val smsextras = extras.get("pdus") as Array<Any>?

            for (i in smsextras!!.indices) {
                val smsmsg = SmsMessage.createFromPdu(smsextras[i] as ByteArray)
                val body = smsmsg.getMessageBody().toString()
                if (body.contains("کنسو", true)) {
                    try {
                        var pass = ""
                        for (j in body.indices) {
                            if (body[j].isDigit())
                                pass += body[j]
                        }
                        smsReceiver?.verifyPass(pass)
                    } catch (e: Exception) {
                    }
                }
            }

        }

    }
}