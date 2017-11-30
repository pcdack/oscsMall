package win.pcdack.ec.fast_dev.type;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public class TextType {
    private String text;
    private int textSize;
    private int color;
    private Typeface typeface;

    private TextType(Builder builder) {
        text = builder.text;
        textSize = builder.textSize;
        color = builder.color;
        typeface=builder.typeface;
    }

    public String getText() {
        return text;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public int getTextSize() {
        if (textSize==0)
            textSize=60;
        return textSize;
    }

    public int getColor() {
        if (color==0)
            color= Color.GRAY;
        return color;
    }

    public static final class Builder {
        private String text;
        private int textSize;
        private int color;
        private Typeface typeface;

        public Builder() {
        }

        public Builder text(@NonNull String val) {
            text = val;
            return this;
        }
        public Builder typeface(@NonNull Typeface val) {
            typeface = val;
            return this;
        }

        public Builder textSize(int val) {
            textSize = val;
            return this;
        }

        public Builder color(@ColorInt int val) {
            color = val;
            return this;
        }

        public TextType build() {
            return new TextType(this);
        }
    }
}
