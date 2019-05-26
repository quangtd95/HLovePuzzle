package com.quangtd.hlovepuzzle.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.quangtd.hlovepuzzle.R
import com.quangtd.hlovepuzzle.databinding.ActivityPuzzleBinding
import com.quangtd.hlovepuzzle.db.entity.ContentEntity
import com.quangtd.hlovepuzzle.viewmodel.PuzzleViewModel

class PuzzleActivity : BaseActivity() {

    private lateinit var mBinding: ActivityPuzzleBinding
    private lateinit var mContent: ContentEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_puzzle)

        val viewModel = ViewModelProviders.of(this).get(PuzzleViewModel::class.java)
        val liveData = viewModel.listContent

        mBinding.imageRequestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }
        }
        mBinding.placeholder = "https://static.thenounproject.com/png/1134418-200.png"
        liveData.observe(this, Observer<List<ContentEntity>> { contentEntity ->
            mContent = contentEntity[0]
            mBinding.content = mContent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mBinding.tvContent.text = Html.fromHtml(
                    mContent.content,
                    Html.FROM_HTML_MODE_COMPACT
                )
            } else {
                mBinding.tvContent.text = Html.fromHtml(
                    mContent.content
                )
            }
            mBinding.tvContent.movementMethod = ScrollingMovementMethod()
        })
        mBinding.tvContent.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(mContent.link)))
        }
    }
}
