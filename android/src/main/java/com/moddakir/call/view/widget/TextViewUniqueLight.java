package com.moddakir.call.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class TextViewUniqueLight extends AppCompatTextView {
    public TextViewUniqueLight(Context context) {
        super(context);
        init();
    }

    public TextViewUniqueLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewUniqueLight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
       // setTypeface(font);
//        setTextSize(TypedValue.COMPLEX_UNIT_DIP, Constants.DEFAULT_TEXT_SIZE);

    }
}
