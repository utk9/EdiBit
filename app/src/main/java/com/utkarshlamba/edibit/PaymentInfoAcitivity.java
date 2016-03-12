package com.utkarshlamba.edibit;

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


public class PaymentInfoAcitivity extends ActionBarActivity {

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
        boolean askingToEnableNfc = false;

        if (nfcAdapter != null && !nfcAdapter.isEnabled())
        {
            askingToEnableNfc = true;

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
            
        }
    }

    public void cameraClicked(View v){

    }
}
