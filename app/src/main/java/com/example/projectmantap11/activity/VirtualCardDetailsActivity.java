package com.example.projectmantap11.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cooltechworks.creditcarddesign.CreditCardView;
import com.example.projectmantap11.R;

public class VirtualCardDetailsActivity extends AppCompatActivity {

    CreditCardView creditCardView;
    private boolean cardFacing = true;

    private TextView vCardName, vCardUserID, vCardCorpName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_card_details);
        getSupportActionBar().hide();

        //get value for card
        SharedPreferences sharedPref = this.getSharedPreferences("CORONA_PROJECT_MANTAP", Context.MODE_PRIVATE);
        String name = sharedPref.getString("yourname", "empty");
        String corp = sharedPref.getString("branchname", "empty");
        String userID = sharedPref.getString("userid", "empty");

        creditCardView = findViewById(R.id.card_5);
        creditCardView.setCardHolderName(name);

        vCardCorpName = findViewById(R.id.vcardcorpname);
        vCardCorpName.setText(corp);

        vCardName = findViewById(R.id.vcardnamestring);
        vCardName.setText(name);

        vCardUserID = findViewById(R.id.vcardemployeeidstring);
        vCardUserID.setText(userID);

        cardFacing = true;
        creditCardView.showFront();
        creditCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardFacing) {
                    creditCardView.showBack();
                    cardFacing = false;
                } else {
                    creditCardView.showFront();
                    cardFacing = true;
                }
            }
        });


    }
}