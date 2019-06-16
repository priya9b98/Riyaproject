package com.example.priya.riyaproject;

import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDetails extends AppCompatActivity {

    List<Books> list;
    DynamoDBMapper dynamoDBMapper;
    ImageView imageView;
    Double borrowid;
    Button borrowbutton;
    EditText returnd,borrowd;
    TextView load,book,bname,dateb,dater,price,cmoney,totala,comey1,price1, totalprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        list=new ArrayList<>();
        borrowid=null;
        AWSMobileClient.getInstance().initialize(this).execute();
        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();

        imageView=findViewById(R.id.imageView99);
        borrowd=findViewById(R.id.bordat1);
        returnd=findViewById(R.id.retdat1);
        bname=findViewById(R.id.name112);
        list=null;
        book=findViewById(R.id.booo1);
        dateb=findViewById(R.id.dabo1);
        dater=findViewById(R.id.daret1);
        price=findViewById(R.id.pri12);
        cmoney=findViewById(R.id.caution1);
        totala=findViewById(R.id.total1);
        comey1=findViewById(R.id.cautionprice1);
        price1=findViewById(R.id.pri13);
        totalprice=findViewById(R.id.totalprice1);
        borrowbutton=findViewById(R.id.borbutton);

        final String url=getIntent().getStringExtra("imageurl");
        Picasso.with(getApplicationContext()).load(url).into(imageView);
        load=findViewById(R.id.textView5);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url1=method(url);
                Log.w("myApp",url1);
                load.setVisibility(v.INVISIBLE);

                fetch(dynamoDBMapper, url1);
                while(list==null){ }
                book.setVisibility(v.VISIBLE);
                bname.setText(list.get(0).getBookName());
                bname.setVisibility(v.VISIBLE);
                dateb.setVisibility(v.VISIBLE);
                borrowd.setVisibility(v.VISIBLE);
                returnd.setVisibility(v.VISIBLE);
                dater.setVisibility(v.VISIBLE);
                price.setVisibility(v.VISIBLE);
                price1.setText(list.get(0).getPrice());
                price1.setVisibility(v.VISIBLE);
                cmoney.setVisibility(v.VISIBLE);
                comey1.setVisibility(v.VISIBLE);
                Integer integer= Integer.valueOf(list.get(0).getPrice())+150;
                totalprice.setText(integer.toString());
                totalprice.setVisibility(v.VISIBLE);
                borrowbutton.setVisibility(v.VISIBLE);


            }
        });
        borrowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlastid(dynamoDBMapper,list.get(0));
                while(borrowid==null){}
                final RequestTableDO requestTableDO =new RequestTableDO();
                requestTableDO.setRequestId(borrowid);
                requestTableDO.setAccepted("pending");
                requestTableDO.setDateOfBorrow(borrowd.getText().toString());
                requestTableDO.setDateClaimToRet(returnd.getText().toString());
                requestTableDO.setCustEmail(getIntent().getStringExtra("email"));
                requestTableDO.setCustId(getIntent().getDoubleExtra("userid",0));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dynamoDBMapper.save(requestTableDO);
                    }
                }).start();
            }
        });
    }

    private void getlastid(final DynamoDBMapper dynamoDBMapper, Books books) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Lastid lastIdsDO=new Lastid();
                lastIdsDO=dynamoDBMapper.load(Lastid.class,"Book_Borrow");
                borrowid=lastIdsDO.getId()+1;
                lastIdsDO.setId(lastIdsDO.getId()+1);
                dynamoDBMapper.save(lastIdsDO);
            }
        }).start();
    }

    private void fetch(final DynamoDBMapper dynamoDBMapper, final String url) {
        Map<String, AttributeValue> es=new HashMap<>();
        es.put(":val", new AttributeValue().withS(url));
        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("ImageUrl = :val").withExpressionAttributeValues(es);
        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<Books> list2=dynamoDBMapper.scan(Books.class,scanExpression);
                list=list2;
                }
        }).start();
    }
    public String method(String str) {
       String x = Character.toString(str.charAt(0));
       for(int i=1;i<str.length();i++){
           if(str.charAt(i) == ' '){break;}
           else {
               x=x.concat(Character.toString(str.charAt(i)));
           }
       }
        return x;
    }
}
