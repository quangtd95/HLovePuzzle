package com.quangtd.hlovepuzzle.ui.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.quangtd.hlovepuzzle.R;
import com.quangtd.hlovepuzzle.databinding.ItemPuzzleBinding;
import com.quangtd.hlovepuzzle.model.DragData;
import com.quangtd.hlovepuzzle.model.PuzzlePiece;

import java.util.List;

public class PuzzlePieceAdapter extends RecyclerView.Adapter<PuzzlePieceAdapter.PuzzlePieceViewHolder> {

    private ItemPuzzleBinding mBinding;

    private List<PuzzlePiece> mList;

    public PuzzlePieceAdapter() {
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PuzzlePieceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_puzzle, parent, false);
        PuzzlePieceViewHolder holder = new PuzzlePieceViewHolder(mBinding);
        final View shape = mBinding.imvPiece;
        mBinding.imvPiece.setOnLongClickListener(v -> {
            final PuzzlePiece item = mList.get(holder.getAdapterPosition());
            final DragData state = new DragData(item, shape.getWidth(), shape.getHeight());
            final View.DragShadowBuilder shadow = new View.DragShadowBuilder(shape);
            ViewCompat.startDragAndDrop(shape, null, shadow, state, 0);
            return true;
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PuzzlePieceViewHolder holder, int position) {
        holder.mBinding.setPiece(mList.get(position));
    }

    public void setList(List<PuzzlePiece> puzzlePieces) {
        if (mList == null) {
            mList = puzzlePieces;
            notifyItemRangeInserted(0, mList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mList.size();
                }

                @Override
                public int getNewListSize() {
                    return mList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mList.get(oldItemPosition).getImg() ==
                            mList.get(newItemPosition).getImg();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    PuzzlePiece newProduct = mList.get(newItemPosition);
                    PuzzlePiece oldProduct = mList.get(oldItemPosition);
                    return newProduct.getImg() == oldProduct.getImg();
                }
            });
            mList = puzzlePieces;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    static class PuzzlePieceViewHolder extends RecyclerView.ViewHolder {

        private ItemPuzzleBinding mBinding;

        public PuzzlePieceViewHolder(@NonNull ItemPuzzleBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }


}
