package com.example.sceneformintro;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class PointerDrawable extends Drawable {
    private final Paint paint = new Paint();

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}
