package ir.pepotect.app.zarisshop.ui.uses

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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