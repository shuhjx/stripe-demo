package stripe.mystripe.dialog;

import android.content.Context;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;

/**
 * Created by pc-135 on 2016/9/14.
 */
public abstract class BaseDialog extends AlertDialog implements View.OnClickListener{

    protected BaseDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        initViews(savedInstanceState);
    }

    public abstract void findViews();
    public abstract void initViews(Bundle savedInstanceState);
    public abstract void processClick(View view);

    /*
	 * 这个是继承得来的方法，用这个方法后，一个activity里面所有组件的按钮都只要去
	 * 实现抽象方法就行，组件上就会自动的挂上侦听，而不用每个组件都写一个点击事件
	 * 了，这样可以提高代码的复用性，还有代码的间接性。
	 */
    @Override
    public void onClick(View v) {
        processClick(v);

    }
}
