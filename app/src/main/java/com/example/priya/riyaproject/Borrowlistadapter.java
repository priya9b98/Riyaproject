package com.example.priya.riyaproject;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class Borrowlistadapter extends RecyclerView.Adapter<Borrowlistadapter.Viewholder> {
    Context mcontext;
    List<Borrow> list;
    String bname=null;
    String bauthor=null;
    String bimage=null;
    Bundle bundle=new Bundle();
    DynamoDBMapper dynamoDBMapper;


    public Borrowlistadapter(Context context,List<Borrow> list,DynamoDBMapper dynamoDBMapper){
        mcontext=context;
        this.list=list;
        this.dynamoDBMapper=dynamoDBMapper;
    }


    @NonNull
    @Override
    public Borrowlistadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.historylist,viewGroup,false);
        mcontext=viewGroup.getContext();
        return new Borrowlistadapter.Viewholder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull Borrowlistadapter.Viewholder viewholder, int i) {
        viewholder.setIsRecyclable(false);


        final Double name=list.get(i).getBookID();
        try{
        findname(dynamoDBMapper,name);}
        catch (Exception e) {
            e.printStackTrace();
        }
        final String rate=list.get(i).getRating();
        final String retDate=list.get(i).getActualRetDate();
        final String borrowdate=list.get(i).getDateOfBorrow();
        bundle.putString("rate",list.get(i).getRating());
        bundle.putString("ARD",list.get(i).getActualRetDate());
        bundle.putString("returnclaim",list.get(i).getDateClaimToRet());
        bundle.putString("dateofborr",list.get(i).getDateOfBorrow());
        bundle.putDouble("borid",list.get(i).getBorrowId());
        bundle.putDouble("bookid",list.get(i).getBookID());
        bundle.putDouble("custid",list.get(i).getCustID());
        bundle.putString("supid",list.get(i).getSupplierID());

        while (bname==null){}
        viewholder.setBook_name(name,i);
        viewholder.setdatb(borrowdate);
        viewholder.setRat(rate);
        viewholder.setdatr(retDate,bundle);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        View mview;
        Button rturn;
        ImageView imageView;
        TextView bookid,rat,datb,datr,author;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setdatr(String da,Bundle bun){
            datr = mview.findViewById(R.id.t5);
            if(da.equals("null")){
             rturn=mview.findViewById(R.id.buttonrten);
             datr.setText("not returned");
             rturn.setVisibility(itemView.VISIBLE);
             rturn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent=new Intent(v.getContext(),Testingb.class);
                     intent.putExtra("bundl",bundle);
                     mcontext.startActivity(intent);
                 }
             });
            }
            else {
//                rturn.setVisibility(itemView.INVISIBLE);

                datr.setText(da);}
        }
        public void setBook_name(Double name,int position) {
            bookid = mview.findViewById(R.id.t1);
            Integer a=(int) Math.round(name);
            String na=a.toString();
            bookid.setText(bname);
            author=mview.findViewById(R.id.t3);
            author.setText(bauthor);
            imageView=mview.findViewById(R.id.bookpic1);
            RequestOptions p=new RequestOptions();
            p.placeholder(R.mipmap.ic_launcher);
            Glide.with(mcontext).applyDefaultRequestOptions(p).load(bimage).into(imageView);

        }

       public void setdatb(String ph) {
            datb = mview.findViewById(R.id.t4);
            datb.setText(ph);
        }

        public void setRat(String rate) {
            rat = mview.findViewById(R.id.t2);
            rat.setText(rate);
        }


    }

    private void findname(final DynamoDBMapper dynamoDBMapper, final Double name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Books books=new Books();
                 books=dynamoDBMapper.load(Books.class,name);
                if(books!=null){                bname=books.getBookName();
                bauthor=books.getAuthor();
                bimage=books.getImageUrl();
            }}
        }).start();

    }
}