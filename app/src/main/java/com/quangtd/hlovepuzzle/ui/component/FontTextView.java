package com.quangtd.hlovepuzzle.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.quangtd.hlovepuzzle.R;
import com.quangtd.hlovepuzzle.common.CommonConstants;
import com.quangtd.hlovepuzzle.util.FontUtils;


public class FontTextView extends AppCompatTextView {

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        mFontName = a.getString(R.styleable.FontTextView_font_name);
        if (mFontName == null) {
            mFontName = CommonConstants.DEFAULT_FONT;
        }
        Typeface typeface = FontUtils.Companion.getTypeface(mFontName, context);
        setTypeface(typeface);
        a.recycle();
    }

    public void setFont(String fontName) {
        mFontName = fontName;
        Typeface typeface = FontUtils.Companion.getTypeface(mFontName, getContext());
        setTypeface(typeface);
    }

    private String mFontName;


}

