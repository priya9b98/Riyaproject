package com.example.priya.riyaproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class Listadapter extends BaseAdapter {

    public interface OnItemClickListener {
        void onItemClick(Books item);
    }

    Context mcontext;
    public List<Books> list;
    //private OnItemClickListener onItemClickListener;

    public Listadapter(Context mcontext,List<Books> l){
        this.mcontext=mcontext;
        this.list=l;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mcontext).inflate(R.layout.booklist,null);
        TextView book_name,author,rating,year;
        ImageView book_image;
        book_image=convertView.findViewById(R.id.bookpic);
        book_name=convertView.findViewById(R.id.bookname);
        rating=convertView.findViewById(R.id.bookr);
        author=convertView.findViewById(R.id.bookaut);
        year=convertView.findViewById(R.id.booka);
        year.setText(list.get(position).getPublicationYear());
        book_name.setText(list.get(position).getBookName());
        rating.setText(list.get(position).getRating());
        author.setText(list.get(position).getAuthor());
        RequestOptions p=new RequestOptions();
        p.placeholder(R.mipmap.ic_launcher);
        Glide.with(mcontext).applyDefaultRequestOptions(p).load(list.get(position).getImageUrl()).into(book_image);

        return convertView;
    }
}
