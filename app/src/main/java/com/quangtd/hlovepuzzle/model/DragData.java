package com.quangtd.hlovepuzzle.model;

public class DragData {

    public final PuzzlePiece item;
    public final int width;
    public final int height;

    public DragData(PuzzlePiece item, int width, int height) {
        this.item = item;
        this.width = width;
        this.height = height;
    }

}