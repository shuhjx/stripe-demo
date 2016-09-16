package stripe.mystripe.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by pc-135 on 2016/9/14.
 */
public class ViewUtils {

    public static <V extends View> V findViewById(Activity activity, @IdRes int id){
        if(activity == null)
            throw new IllegalStateException("activity must not null!");

        return (V) activity.findViewById(id);
    }

    public static <V extends View> V findViewById(View view, @IdRes int id){
        if(view == null)
            throw new IllegalStateException("view must not null!");

        return (V) view.findViewById(id);
    }

    //隐藏软键盘
    public static void hideKeyboard(Activity activity) {
        try {
            ((InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {}
    }
}
