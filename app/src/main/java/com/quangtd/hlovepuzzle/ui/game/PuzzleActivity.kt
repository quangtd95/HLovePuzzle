package com.quangtd.hlovepuzzle.ui.game

import android.graphics.Color.*
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.quangtd.hlovepuzzle.R
import com.quangtd.hlovepuzzle.binding.BindingAdapters
import com.quangtd.hlovepuzzle.databinding.ActivityPuzzleBinding
import com.quangtd.hlovepuzzle.db.entity.ContentEntity
import com.quangtd.hlovepuzzle.model.DragData
import com.quangtd.hlovepuzzle.model.PuzzlePiece
import com.quangtd.hlovepuzzle.ui.BaseActivity
import com.quangtd.hlovepuzzle.viewmodel.PuzzleViewModel
import kotlinx.android.synthetic.main.item_puzzle.view.*

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
        })

        val adapter = PuzzlePieceAdapter()
        var list = ArrayList<PuzzlePiece>()
        for (i in 1..5) {
            list.add(PuzzlePiece("https://static.thenounproject.com/png/1134418-200.png"))
        }
        adapter.setList(list)
        mBinding.rvPieceList.adapter = adapter
        mBinding.tvContent.setOnClickListener {
            //            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(mContent.link)))
        }
        mBinding.frGame.setOnDragListener(this)
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        when (event!!.action) {
            DragEvent.ACTION_DRAG_ENTERED -> {
                mBinding.frGame.setBackgroundColor(GREEN)
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                mBinding.frGame.setBackgroundColor(RED)
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                mBinding.frGame.setBackgroundColor(WHITE)
            }
            DragEvent.ACTION_DROP -> {
                var dropX = event.x
                var dropY = event.y
//                var state = (DragData) event!!
                var state: DragData = event.localState as DragData

                var shape = LayoutInflater.from(this).inflate(
                    R.layout.item_puzzle, mBinding.frGame, false
                ) as CardView
                var imageView = shape.imvPiece as ImageView
                BindingAdapters.bindImage(imageView, state.item.img, null)
                shape.x = dropX - state.width / 2
                shape.y = dropY - state.height / 2
                shape.layoutParams.width = state.width
                shape.layoutParams.height = state.height
                mBinding.frGame.addView(shape)
            }
        }
        return true
    }
}
