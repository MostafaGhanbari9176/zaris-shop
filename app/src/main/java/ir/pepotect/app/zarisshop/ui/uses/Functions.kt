package ir.pepotect.app.zarisshop.ui.uses

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import ir.pepotect.app.zarisshop.R
import ir.pepotect.app.zarisshop.ui.App

fun loadImage(url: String, img: ImageView, ctx: Context = App.ctx) {
    Glide.with(ctx)
        .load(url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .error(R.drawable.logo)
        .into(img)
}

fun loadImageWithBounds(url: String, img: ImageView, ctx: Context = App.ctx) {

    val rOption = RequestOptions()
    rOption.diskCacheStrategy(DiskCacheStrategy.NONE)
    rOption.skipMemoryCache(true)

    Glide.with(ctx)
        .asBitmap()
        .load(url)
        .apply(rOption)
        .error(R.drawable.logo)
        .centerCrop()
        .placeholder(R.drawable.logo)
        .into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(b: Bitmap, transition: Transition<in Bitmap>?) {
                val div = b.height.toFloat() / b.width
                val h = div * img.width
                img.apply {
                    layoutParams.width = img.width
                    layoutParams.height = h.toInt()
                    setImageBitmap(b)
                }
            }
        })
}

fun changeValuePresenter(value: String): String {

    val dataPart = ArrayList<String>()

    var part = ""
    for (i in (value.length - 1) downTo 0) {
        part = value[i] + part
        if ((part.length) % 3 == 0) {
            dataPart.add(part.trimStart('0'))
            part = ""
        }
    }
    if (part.isNotEmpty())
        dataPart.add(part.trimStart('0'))

    var presentData = ""
    for (i in (dataPart.size - 1) downTo 0) {
        val p = when (i) {
            0 -> ""
            1 -> "هزار"
            2 -> "میلیون"
            3 -> "میلیارد"
            4 -> "تیلیارد"
            else -> "بیخیال"

        }
        if (dataPart[i].isEmpty())
            continue
        presentData += dataPart[i] + p + " و "
    }

    return presentData.trim('و', ' ')
}