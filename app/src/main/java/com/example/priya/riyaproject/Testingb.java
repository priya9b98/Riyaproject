package com.example.priya.riyaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapperConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Testingb extends AppCompatActivity {
    DynamoDBMapper dynamoDBMapper;
    Button setrate;
    EditText giverate;
    String datr;
    Borrow bor=new Borrow();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testingb);
        Intent i=getIntent();
        Bundle b=i.getBundleExtra("bundl");
        String returnclaim=b.getString("returnclaim");
        String dateofb=b.getString("dateofborr");
        Double boreid=b.getDouble("borid");
        Double bookid=b.getDouble("bookid");
        Double custid=b.getDouble("custid");
        String supplierid=b.getString("supid");
        giverate=findViewById(R.id.ratingiven);
        setrate=findViewById(R.id.upsaterate);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
                dynamoDBMapper = DynamoDBMapper.builder()
                        .dynamoDBClient(dynamoDBClient)
                        .awsConfiguration(
                                AWSMobileClient.getInstance().getConfiguration())
                        .build();
            }
        }).execute();

        bor.setDateOfBorrow(dateofb);
        bor.setBorrowId(boreid);
        bor.setCustID(custid);
        bor.setDateClaimToRet(returnclaim);
        bor.setBookID(bookid);
        bor.setSupplierID(supplierid);

        setrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bor.setRating(giverate.getText().toString());
                datr=getCurrentDate(v);
                //Toast.makeText(Testingb.this,datr,Toast.LENGTH_SHORT).show();
                bor.setActualRetDate(datr);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dynamoDBMapper.save(bor);
                    }
                }).start();

            }
        });


    }

    public String getCurrentDate(View view) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy ");
        String strDate =  mdformat.format(calendar.getTime());
        return  strDate;
    }}

