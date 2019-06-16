package com.example.priya.riyaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class MainActivity extends AppCompatActivity {

    Button login,signup;
    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




            //custtableauto o=new custtableauto(this);

            AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
                @Override
                public void onComplete(AWSStartupResult awsStartupResult) {
                    Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
                }
            }).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();


            login=findViewById(R.id.log);
            signup=findViewById(R.id.signup);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   Intent i=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);

                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
             //test(dynamoDBMapper);
                    Intent k=new Intent(MainActivity.this,RegisterActivity.class);
                    startActivity(k);

                }
            });

        }

   private void test(final DynamoDBMapper dynamoDBMapper) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Books books=new Books();
                books.setISBN(31616631.0);
                books=dynamoDBMapper.load(books);
                final Books finalBooks = books;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, finalBooks.getBookName(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
