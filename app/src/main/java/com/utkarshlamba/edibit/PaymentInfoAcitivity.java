package com.utkarshlamba.edibit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import io.triangle.reader.PaymentCard;
import io.triangle.reader.ScanActivity;


public class PaymentInfoAcitivity extends Activity {

    public static int TRIANGLE_REQUEST_CODE = 1000;

    RecyclerView cardRecyclerView;
    CreditCardAdapter cardAdapter;
    ArrayList<String[]> cardData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info_acitivity);

        String test1[] = {"Visa Debit", "**** **** **** 1234", "Jeffrey Seto", "10/18"};
        String test2[] = {"Visa Credit", "**** **** **** 0420", "Billy Bob", "10/22"};

        cardData = new ArrayList<>();
        cardData.add(test1);
        cardData.add(test2);

        cardRecyclerView = (RecyclerView) findViewById(R.id.creditCardRecyclerView);
        cardAdapter = new CreditCardAdapter(cardData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        cardRecyclerView.setLayoutManager(layoutManager);
        cardRecyclerView.setAdapter(cardAdapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment_info_acitivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == TRIANGLE_REQUEST_CODE){
            if(resultCode == ScanActivity.RESULT_OK) {
                PaymentCard scannedCard = intent.getParcelableExtra(ScanActivity.INTENT_EXTRA_PAYMENT_CARD);
                if (scannedCard != null  && scannedCard.getExpiryDate() != null && scannedCard.getLastFourDigits() != null) {
                    String exp = scannedCard.getExpiryDate().getMonth() + "/" + scannedCard.getExpiryDate().getYear();
                    String number = "**** **** **** " + scannedCard.getLastFourDigits();
                    String type = scannedCard.getCardBrand();

                    String name = scannedCard.getCardholderName();
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
        }


    }
}
