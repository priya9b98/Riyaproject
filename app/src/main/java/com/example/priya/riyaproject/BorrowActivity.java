package com.example.priya.riyaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BorrowActivity extends AppCompatActivity {
    TextView booname,pri,Total,caution,borrowd,returnd;
    Button confirm;
    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        AWSMobileClient.getInstance().initialize(this).execute();
        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();


        Bundle b=getIntent().getBundleExtra("bund");
        String name=b.getString("na");
        final Double bookid=b.getDouble("bid");
        final Integer integer=(int)Math.round(bookid);

        final Double userid=b.getDouble("userid");
        final String borrowdate=b.getString("dateborrow");
        final String returndate=b.getString("returndate");

        String price=b.getString("price");
        borrowd=findViewById(R.id.bordat);
        returnd=findViewById(R.id.retdat);
        borrowd.setText(borrowdate);
        returnd.setText(returndate);
        booname=findViewById(R.id.name11);
        pri=findViewById(R.id.pri1);
        Total=findViewById(R.id.totalprice);
        caution=findViewById(R.id.cautionprice);
        pri.setText(price);
        booname.setText(name);

        confirm=findViewById(R.id.confirm);
     //   Total.setText(total);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Borrowl item=new Borrowl();
                item.setDateClaimToRet(returndate);
                item.setDateOfBorrow(borrowdate);
                item.setSupplierID("0");
                item.setCustID(Double.toString(userid));
                item.setBookID(Integer.toString(integer));
                String h=Double.toString(userid).concat(Integer.toString(integer));
                item.setBorrowID(h);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dynamoDBMapper.save(item);
                    }
                }).start();

              //  Toast.makeText(getApplicationContext(),"booking done",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
