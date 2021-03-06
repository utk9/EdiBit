package com.utkarshlamba.edibit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;

import java.util.ArrayList;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import io.triangle.reader.PaymentCard;
import io.triangle.reader.ScanActivity;

public class PayActivity extends AppCompatActivity {

    public static int TRIANGLE_REQUEST_CODE = 1000;
    public static int CARD_IO_REQUEST_CODE = 1001;

    public static String EXTRA_COST = "Cost";
    public static String EXTRA_FOOD = "Food";

    TextView cost,food;

    ArrayList<String[]> card;
    RecyclerView recyclerView;
    CreditCardAdapter cardAdapter;

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

    private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);
                    // The following are only used in PayPalFuturePaymentActivity.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.payRecyclerView);
        card = new ArrayList();
        cardAdapter = new CreditCardAdapter(card);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardAdapter);

        cost = (TextView) findViewById(R.id.costText);
        food = (TextView) findViewById(R.id.foodText);

        Bundle bundle = getIntent().getExtras();
        String foodString =  bundle.get(EXTRA_FOOD).toString();
        String costString = bundle.get(EXTRA_COST).toString();

        cost.setText("Total Cost: $" + costString);
        food.setText("Dish: " + foodString);

        Intent paypalService = new Intent(this, PayPalService.class);
        paypalService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);


    }

    public void nfcClicked(View v){
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter != null && !nfcAdapter.isEnabled()){

            // Alert the user that NFC is off
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("NFC Sensor Turned Off");
            alertDialog.setMessage("In order to use this application, the NFC sensor must be turned on. Do you wish to turn it on?");
            alertDialog.setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    // Send the user to the settings page and hope they turn it on
                    if (android.os.Build.VERSION.SDK_INT >= 16)
                    {
                        startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
                    }
                    else
                    {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                }
            });
            alertDialog.setNegativeButton("Do Nothing", null);
            alertDialog.show();
        }

        if (nfcAdapter != null && nfcAdapter.isEnabled())
        {
            // If no cards have been scanned so far, then automatically kick off a scan
            Intent scanIntent = new Intent(this,io.triangle.reader.ScanActivity.class);
            scanIntent.putExtra(ScanActivity.INTENT_EXTRA_RETRY_ON_ERROR, true);
            startActivityForResult(scanIntent,TRIANGLE_REQUEST_CODE);
        }
    }

    public void cameraClicked(View v){
        try{
            Intent cameraIntent = new Intent(this, CardIOActivity.class);
            cameraIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY,true);
            cameraIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true);
            startActivityForResult(cameraIntent, CARD_IO_REQUEST_CODE);
        } catch (Exception e){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Error occurred when accessing camera.  Check if device camera is working");
            alertDialog.setNegativeButton("Ok", null);
            alertDialog.show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == TRIANGLE_REQUEST_CODE){
            if(resultCode == ScanActivity.RESULT_OK) {
                PaymentCard scannedCard = intent.getParcelableExtra(ScanActivity.INTENT_EXTRA_PAYMENT_CARD);
                if (scannedCard != null  && scannedCard.getExpiryDate() != null && scannedCard.getLastFourDigits() != null) {
                    String exp = scannedCard.getExpiryDate().getMonth() + "/" + scannedCard.getExpiryDate().getYear();
                    String number = "**** **** **** " + scannedCard.getLastFourDigits();
                    String type = scannedCard.getCardBrand();
                    String name = scannedCard.getCardholderName();
                    cardAdapter.removeCard(0);
                    cardAdapter.addCard(type, number, name, exp);
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setMessage("Card brand or type not supported.  Please try another method");
                    alertDialog.setNegativeButton("Ok", null);
                    alertDialog.show();
                }
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Card scan aborted");
                alertDialog.setNegativeButton("Ok", null);
                alertDialog.show();
            }
        } else if(requestCode == CARD_IO_REQUEST_CODE){
            if(intent != null && intent.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)){
                CreditCard scannedCard = intent.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                if(scannedCard.cardholderName != null && scannedCard.cardNumber != null && scannedCard.isExpiryValid()){
                    String exp = scannedCard.expiryMonth + "/" + scannedCard.expiryYear;
                    String number = "**** **** **** " + scannedCard.cardNumber.substring(scannedCard.cardNumber.length() - 4);
                    String name = scannedCard.cardholderName;

                    String type;
                    int typeID[] = new int[4];
                    for(int i = 0; i < 4; i++){
                        typeID[i] = Integer.parseInt("" + scannedCard.cardNumber.charAt(i));
                    }
                    if (typeID[0] == 4) {
                        type = "Visa";
                    } else if(typeID[0] == 3 && typeID[1] == 7){
                        type = "Amex";
                    } else if(typeID[0] == 5 && typeID[1] <= 5 && typeID[1] >= 1){
                        type = "Mastercard";
                    } else if(typeID[0] == 3 && typeID[1] == 0 && typeID[2] <= 5 && typeID[2] >= 0){
                        type = "Diner's Club";
                    } else if(typeID[0] == 3 && typeID[1] == 6 || typeID[1] == 8){
                        type = "Diner's Club";
                    } else {
                        type = "NA";
                    }
                    cardAdapter.removeCard(0);
                    cardAdapter.addCard(type, number, name, exp);
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setMessage("Invalid card information.  Please try again");
                    alertDialog.setNegativeButton("Ok", null);
                    alertDialog.show();
                }

            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Card scan aborted");
                alertDialog.setNegativeButton("Ok", null);
                alertDialog.show();
            }
        }


    }
}
