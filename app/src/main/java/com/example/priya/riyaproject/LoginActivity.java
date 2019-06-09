package com.example.priya.riyaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class LoginActivity extends AppCompatActivity {

    EditText name,password;
    Button login;
    DynamoDBMapper dynamoDBMapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
            name=findViewById(R.id.namelogin);
            password=findViewById(R.id.loginpass);
            login=findViewById(R.id.loginn);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CustomerTableDo customer=new CustomerTableDo();
                            String k=name.getText().toString();

                            customer=dynamoDBMapper.load(CustomerTableDo.class,k);
                            final CustomerTableDo finalCustomer = customer;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(finalCustomer!=null){
                                    if(finalCustomer.getCustPass().equals(password.getText().toString())){
                                        Intent intent=new Intent(LoginActivity.this,ProfileActivity.class);
                                        intent.putExtra("name",finalCustomer.getCustName());
                                        intent.putExtra("userid",finalCustomer.getUserId());
                                        intent.putExtra("email",finalCustomer.getCustEmail());
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"right",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"wrong password",Toast.LENGTH_SHORT).show();

                                    }}
                                    else{
                                        Toast.makeText(LoginActivity.this,"Check your Email",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();




                }
            });
        }

    }
