package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.R;
import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    public ProductAdapter(Context ctx, int layoutItem, ArrayList<Product> arrayList) {
        this.ctx = ctx;
        this.layoutItem = layoutItem;
        this.arrayList = arrayList;
    }

     Context ctx;
     int layoutItem;
     ArrayList<Product> arrayList;

    @Override
    public int getCount() {
        return arrayList.size() ;
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        TextView tvProductName,tvProductDescription,tvProductPrice;
        ImageView ivProduct;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutItem,null);
            holder.tvProductName = (TextView)row.findViewById(R.id.tvProductName);
            holder.tvProductDescription = (TextView)row.findViewById(R.id.tvProductDescription);
            holder.tvProductPrice = (TextView)row.findViewById(R.id.tvProductPrice);
            holder.ivProduct = (ImageView) row.findViewById(R.id.ivProduct);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
//        view = LayoutInflater.from(ctx).inflate(layoutItem,viewGroup,false);
//        TextView tvProductName = view.findViewById(R.id.tvProductName);
//        TextView tvProductDescription = view.findViewById(R.id.tvProductDescription);
//        TextView tvProductPrice = view.findViewById(R.id.tvProductPrice);
//        ImageView ivProduct = view.findViewById(R.id.ivProduct);

        Product product = arrayList.get(i);

        holder.tvProductName.setText(product.getName());
        holder.tvProductDescription.setText(product.getDescription());
        holder.tvProductPrice.setText(product.getPrice()+"");
        byte[] imageProduct = product.getImageProduct();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageProduct, 0 , imageProduct.length);
        holder.ivProduct.setImageBitmap(bitmap);

        return row;
    }
}
