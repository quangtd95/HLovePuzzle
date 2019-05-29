/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.quangtd.hlovepuzzle.binding

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener

/**
 * Binding adapters that work with a fragment instance.
 */


object BindingAdapters {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, imageRequestListener: RequestListener<Drawable?>?) {
        Glide.with(imageView.context).load(url).listener(imageRequestListener).into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["imageBitmap", "imageRequestListener"], requireAll = false)
    fun bindImage(imageView: ImageView, imageBitmap: Bitmap?, imageRequestListener: RequestListener<Drawable?>?) {
        Glide.with(imageView.context).load(imageBitmap).listener(imageRequestListener).into(imageView)
    }
}

