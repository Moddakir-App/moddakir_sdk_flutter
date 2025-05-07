package com.moddakir.call.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.moddakir.call.Constants;

public class TextViewLateefRegOT extends AppCompatTextView {
    public TextViewLateefRegOT(Context context) {
        super(context);
        init();
    }

    public TextViewLateefRegOT(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewLateefRegOT(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    public void init() {
//        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font/LateefRegOT.ttf");
//        setTypeface(font);
//    }

    public void init() {
//        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font/Calibri_Regular.ttf");
//        setTypeface(font);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, Constants.DEFAULT_TEXT_SIZE);

    }

}
