package stripe.mystripe.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.compat.AsyncTask;
import com.stripe.android.model.Token;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import stripe.mystripe.Config;
import stripe.mystripe.utils.BarcodeUtils;

/**
 * Created by pc-135 on 2016/9/14.
 */
public class TokenDialog extends BaseDialog {

    private Token token;
    private ImageView imageView;
    private static final int SUCCESS = 0x5655233;

    protected TokenDialog(Context context) {
        super(context);
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public static TokenDialog showDialog(Context context, Token token) {
        TokenDialog dialog = new TokenDialog(context);
        dialog.setToken(token);
        dialog.show();
        return dialog;
    }

    @Override
    public void findViews() {
        Bitmap bitmap = BarcodeUtils.getBarcode(token);
        imageView = new ImageView(getContext());
        imageView.setImageBitmap(bitmap);
        setContentView(imageView);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        imageView.setOnClickListener(this);
    }

    @Override
    public void processClick(View view) {
        requestToken();
    }

    //支付操作
    private void requestToken() {
        if (token == null) return;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... v) {
                try {
                    RequestOptions requestOptions = RequestOptions.builder().setApiKey(Config.SECRET_KEY).build();

                    Map<String, Object> params = new HashMap<>();
                    params.put("description", "Charge for shuh@alpha-it-system.com");
                    params.put("amount", "99");
                    params.put("currency", "USD");
                    params.put("source", token.getId());

                    Charge charge = Charge.create(params, requestOptions);
                    if("succeeded".equalsIgnoreCase(charge.getStatus())){
                        handler.sendEmptyMessage(SUCCESS);
                    }
                } catch (AuthenticationException e) {
                    Log.e("Debug", "===AuthenticationException===");
                    e.printStackTrace();
                } catch (InvalidRequestException e) {
                    Log.e("Debug", "===InvalidRequestException===");
                    e.printStackTrace();
                } catch (APIConnectionException e) {
                    Log.e("Debug", "===APIConnectionException===");
                    e.printStackTrace();
                } catch (CardException e) {
                    Log.e("Debug", "===CardException===");
                    e.printStackTrace();
                } catch (APIException e) {
                    Log.e("Debug", "===APIException===");
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    Toast.makeText(getContext(), "支付成功！", Toast.LENGTH_LONG).show();
                    dismiss();
                    break;
            }

        }
    };
}
