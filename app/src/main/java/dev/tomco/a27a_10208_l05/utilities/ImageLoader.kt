package dev.tomco.a27a_10208_l05.utilities

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import dev.tomco.a27a_10208_l05.R

class ImageLoader private constructor() {

    companion object {
        @Volatile
        private var instance: ImageLoader? = null

        fun init(): ImageLoader {
            return instance ?: synchronized(this) {
                instance ?: ImageLoader().also { instance = it }
            }
        }

        fun getInstance(): ImageLoader {
            return instance ?: throw IllegalStateException(
                "ImageLoader must be initialized by calling init() before use."
            )
        }
    }

    fun loadImage(
        source:String,
        imageView: ImageView,
        placeHolder:Int = R.drawable.unavailable_photo
    ){
        Glide
            .with(imageView)
            .load(source)
            .centerCrop()
            .placeholder(placeHolder)
            .into(imageView)
    }
    fun loadImage(
        source:Int,
        imageView: ImageView,
        placeHolder:Int = R.drawable.unavailable_photo
    ){
        Glide
            .with(imageView)
            .load(source)
            .centerCrop()
            .placeholder(placeHolder)
            .into(imageView)
    }
}

