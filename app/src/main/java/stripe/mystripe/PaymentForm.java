package stripe.mystripe;

/**
 * Created by pc-135 on 2016/9/14.
 */
public interface PaymentForm {

    public String getCardNumber();
    public String getCvc();
    public Integer getExpMonth();
    public Integer getExpYear();
    public String getCurrency();
}
