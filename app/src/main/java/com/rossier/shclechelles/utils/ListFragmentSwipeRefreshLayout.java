package com.rossier.shclechelles.utils;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

public class ListFragmentSwipeRefreshLayout extends SwipeRefreshLayout {
	
	private ListFragment listView;
	public ListFragmentSwipeRefreshLayout(Context context,ListFragment listView) {
		super(context);
		this.listView = listView;
        
    }

    /**
     * As mentioned above, we need to override this method to properly signal when a
     * 'swipe-to-refresh' is possible.
     *
     * @return true if the {@link android.widget.ListView} is visible and can scroll up.
     */
    @Override
    public boolean canChildScrollUp() {
        if (listView.getListView().getVisibility() == View.VISIBLE) {
            return canListViewScrollUp(listView.getListView());
        } else {
            return false;
        }
    }
    private static boolean canListViewScrollUp(ListView listView) {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            // For ICS and above we can call canScrollVertically() to determine this
            return ViewCompat.canScrollVertically(listView, -1);
        } else {
            // Pre-ICS we need to manually check the first visible item and the child view's top
            // value
            return listView.getChildCount() > 0 &&
                    (listView.getFirstVisiblePosition() > 0
                            || listView.getChildAt(0).getTop() < listView.getPaddingTop());
        }
    }

}

