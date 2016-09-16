package stripe.mystripe.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by pc-135 on 2016/9/15.
 */
public class ToastDialog extends BaseDialog {

    private String message, s_ok;
    private TextView tv_dialog_message;
    private Button bt_dialog_ok;
    private OnOKListener onOKListener;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOk(String s_ok) {
        this.s_ok = s_ok;
    }

    public void setOnOKListener(OnOKListener onOKListener){
        this.onOKListener = onOKListener;
    }

    protected ToastDialog(Context context) {
        super(context);
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void processClick(View view) {

    }
}
