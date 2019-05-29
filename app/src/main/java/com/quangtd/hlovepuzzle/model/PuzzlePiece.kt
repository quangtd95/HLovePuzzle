package com.quangtd.hlovepuzzle.model

import android.graphics.Bitmap

data class PuzzlePiece(
    var img: String = "",
    var xCoord: Int = 0,
    var yCoord: Int = 0,
    var pieceWidth: Int = 0,
    var pieceHeight: Int = 0,
    var canMove: Boolean = true,
    var imageBitmap: Bitmap? = null
)