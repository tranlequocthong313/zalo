package vn.edu.ou.zalo.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class NonInterceptRecyclerView extends RecyclerView {

    public NonInterceptRecyclerView(Context context) {
        super(context);
    }

    public NonInterceptRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonInterceptRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Prevent parent (ViewPager) from intercepting touch events when scrolling horizontally
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            getParent().requestDisallowInterceptTouchEvent(false);

            // Call performClick when ACTION_UP to handle clicks correctly
            performClick();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        // Ensure accessibility events are handled correctly
        return super.performClick();
    }
}
