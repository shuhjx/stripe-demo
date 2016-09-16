package stripe.mystripe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ldoublem.loadingviewlib.LVBlock;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import stripe.mystripe.dialog.TokenDialog;
import stripe.mystripe.utils.ViewUtils;

public class MainActivity extends AppCompatActivity implements PaymentForm {

    private EditText cardNo, year, month, cvc;
    private Button saveBtn;
    private TextView tvToken;
    private LVBlock loading;
    private View container_loading;
    private Spinner currency;

    private TokenCallback tokenCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
    }

    private void findViews(){
        cardNo = ViewUtils.findViewById(this, R.id.card_no);
        year = ViewUtils.findViewById(this, R.id.year);
        month = ViewUtils.findViewById(this, R.id.month);
        cvc = ViewUtils.findViewById(this, R.id.cvc);
        saveBtn = ViewUtils.findViewById(this, R.id.save_btn);
        tvToken = ViewUtils.findViewById(this, R.id.token);
        loading = ViewUtils.findViewById(this, R.id.loading);
        container_loading = ViewUtils.findViewById(this, R.id.container_loading);
        currency = ViewUtils.findViewById(this, R.id.currency);
    }

    private void initViews(){
        if(Config.DEBUG){
            cardNo.setText("4242424242424242");
            year.setText("2016");
            month.setText("10");
            cvc.setText("123");
        }
        tokenCallback = new TokenCallback() {
            @Override
            public void onError(Exception error) {
                container_loading.setVisibility(View.GONE);
                loading.stopAnim();
                tvToken.setText("onError\n");
                tvToken.append(error.getMessage());
            }

            @Override
            public void onSuccess(Token token) {
                container_loading.setVisibility(View.GONE);
                loading.stopAnim();
                tvToken.setText("onSuccess\n");
                tvToken.append(token.toString());
                TokenDialog.showDialog(MainActivity.this, token);
            }
        };
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewUtils.hideKeyboard(MainActivity.this);
                Card card = new Card(getCardNumber(),
                                     getExpMonth(),
                                     getExpYear(),
                                     getCvc());
                card.setCurrency(getCurrency());

                if(card.validateCard()){
                    container_loading.setVisibility(View.VISIBLE);
                    loading.startAnim();
                    //生成支付token
                    new Stripe().createToken(card, Config.PUBLISHABLE_KEY, tokenCallback);
                }
            }
        });
        container_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }



    @Override
    public String getCardNumber() {
        return cardNo.getText().toString();
    }

    @Override
    public String getCvc() {
        return cvc.getText().toString();
    }

    @Override
    public Integer getExpMonth() {
        return Integer.parseInt(month.getText().toString());
    }

    @Override
    public Integer getExpYear() {
        return Integer.parseInt(year.getText().toString());
    }

    @Override
    public String getCurrency() {
        if (currency.getSelectedItemPosition() == 0) return null;
        String selected = (String) currency.getSelectedItem();
        if (selected.equals("Unspecified"))
            return null;
        else
            return selected.toLowerCase();
    }

}
