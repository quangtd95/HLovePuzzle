package com.quangtd.hlovepuzzle.ui.game

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.quangtd.hlovepuzzle.R
import com.quangtd.hlovepuzzle.binding.BindingAdapters
import com.quangtd.hlovepuzzle.databinding.ActivityPuzzleBinding
import com.quangtd.hlovepuzzle.db.entity.ContentEntity
import com.quangtd.hlovepuzzle.model.DragData
import com.quangtd.hlovepuzzle.ui.BaseActivity
import com.quangtd.hlovepuzzle.util.PieceGenerator
import com.quangtd.hlovepuzzle.viewmodel.PuzzleViewModel

class PuzzleActivity : BaseActivity(), View.OnDragListener {


    private lateinit var mBinding: ActivityPuzzleBinding
    private lateinit var mContent: ContentEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_puzzle)

        val viewModel = ViewModelProviders.of(this).get(PuzzleViewModel::class.java)
        val liveData = viewModel.listContent

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
            val adapter = PuzzlePieceAdapter()
            mBinding.imageRequestListener = object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Handler().postDelayed({
                        val list = PieceGenerator.splitImage(mBinding.imvGame, (resource as BitmapDrawable).bitmap)
                        adapter.setList(list)
                        mBinding.rvPieceList.adapter = adapter
                    }, 0)
                    return false
                }

            }
        })


        mBinding.tvContent.setOnClickListener {
            //            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(mContent.link)))
        }
        mBinding.frGame.setOnDragListener(this)
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        when (event!!.action) {
            DragEvent.ACTION_DRAG_ENTERED -> {
//                mBinding.frGame.setBackgroundColor(GREEN)
            }
            DragEvent.ACTION_DRAG_EXITED -> {
//                mBinding.frGame.setBackgroundColor(RED)
            }
            DragEvent.ACTION_DRAG_ENDED -> {
//                mBinding.frGame.setBackgroundColor(WHITE)
            }
            DragEvent.ACTION_DROP -> {
                var dropX = event.x
                var dropY = event.y
//                var state = (DragData) event!!
                var state: DragData = event.localState as DragData

                var shape = LayoutInflater.from(this).inflate(
                    R.layout.item_puzzle, mBinding.frGame, false
                ) as CardView
                var imageView = ImageView(this, null)//shape.imvPiece as ImageView
                BindingAdapters.bindImage(imageView, state.item.imageBitmap, null)
                imageView.x = dropX - state.width / 2
                imageView.y = dropY - state.height / 2
                imageView.layoutParams = RelativeLayout.LayoutParams(state.item.pieceWidth, state.item.pieceHeight)
//                imageView.layoutParams.width = state.item.pieceWidth
//                imageView.layoutParams.height = state.item.pieceHeight
                mBinding.frGame.addView(imageView)
            }
        }
        return true
    }
}
