package com.example.priya.riyaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;

public class RegisterActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    EditText name,password,phone,email,address;
    Button submit;
    Double lastcustid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // AWSMobileClient enables AWS user credentials to access your table
        AWSMobileClient.getInstance().initialize(this).execute();
        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();

        lastcustid=null;
        System.out.println("executed properly");
        name=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.pass1);
        phone=(EditText)findViewById(R.id.phone1);
        submit=(Button)findViewById(R.id.submit);
        email=(EditText)findViewById(R.id.email);
        address=(EditText)findViewById(R.id.address);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createItem();
            }
        });

    }
    public void createItem() {

        checklastid(dynamoDBMapper);
        while(lastcustid==null){}
        final CustomerTableDo Item = new CustomerTableDo();
        // for(int i=0;i<500;i++) {
        String n = name.getText().toString();
        String e = email.getText().toString();

        String p = password.getText().toString();
        String a = address.getText().toString();
        double num = (Double.parseDouble(phone.getText().toString()));


        Item.setCustName(n);
        Item.setCustEmail(e);
        Item.setCustNumber(num);
        Item.setCustPass(p);
        Item.setUserId(lastcustid);
        Item.setCustAddress(a);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(Item);
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                // Item saved
            }
        }).start();
        //}

    }

    private void checklastid(final DynamoDBMapper dynamoDBMapper) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Lastid lastid=new Lastid();
                lastid=dynamoDBMapper.load(Lastid.class,"Customer_table");
                lastcustid=lastid.getId()+1;
                lastid.setId(lastcustid);
                dynamoDBMapper.save(lastid);
            }
        }).start();
    }
}

