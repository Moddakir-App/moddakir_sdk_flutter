package com.moddakir.call.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class TextViewCalibriBold extends AppCompatTextView {
    public TextViewCalibriBold(Context context) {
        super(context);
        init();
    }

    public TextViewCalibriBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewCalibriBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
       // setTypeface(font);
//        setTextSize(TypedValue.COMPLEX_UNIT_DIP, Constants.DEFAULT_TEXT_SIZE);

    }
}
