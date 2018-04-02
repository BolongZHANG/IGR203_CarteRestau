package com.carteresto.igr230.carteresto.MenuPrincipale;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.util.DiffUtil;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;


import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.SimplyProductViewHolder>{

    private static final String TAG = ProductRecyclerViewAdapter.class.getSimpleName();
    @NonNull
    private List<Product> mData = new ArrayList<>();
    private Context mContext;
    private Fragment mFragment;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private QuantityModifyListener mListener;

    public interface QuantityModifyListener{
        void add(String id);
        void minus(String id);
        void showDialogue(String id);
    }

    public ProductRecyclerViewAdapter(Fragment fragment,  QuantityModifyListener listener) {
        this.mFragment = fragment;
        this.mListener =  listener;
      }

    @Override
    public SimplyProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cardview, parent, false);
        return new SimplyProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimplyProductViewHolder holder, final int position) {
        final Product product = mData.get(position);
        holder.mItem = mData.get(position);
        holder.mNameView.setText(product.getName());
        holder.mPriceView.setText(product.getPrice() + " €");
        holder.mQuantityView.setText(String.valueOf(product.getQuantity()));
        //TODO save the image in the resource directory
        String imageRef =  "gs://carterestoandroid.appspot.com/product/" + product.getId() + "/small.jpg";
        Log.d(TAG, "onBindViewHolder: load image:" + imageRef);

        Glide.with(mContext /* context */)
                .using(new FirebaseImageLoader())
                .load(storage.getReferenceFromUrl(imageRef))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImageView);

        holder.mAddButton.setOnClickListener(v -> mListener.add(product.getId()));
        holder.mMinusButton.setOnClickListener(v->mListener.minus(product.getId()));
        holder.mImageView.setOnClickListener(v -> mListener.showDialogue(product.getId()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setData(List<Product> data) {
        Log.d(TAG, "setData: update the list data");
        if(this.mData != null){
            ProductDiffCallback productDiffCallback = new ProductDiffCallback(this.mData, data);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(productDiffCallback);
            mData.clear();
            mData.addAll(data);
            diffResult.dispatchUpdatesTo(this);
            this.mData = data;
        }
    }


    public class SimplyProductViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNameView;
        final TextView mPriceView;
        final TextView mQuantityView;
        final ImageView mImageView;
        final FloatingActionButton mAddButton;
        final FloatingActionButton mMinusButton;
        Product mItem;

        SimplyProductViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.name);
            mPriceView = view.findViewById(R.id.price);
            mQuantityView = view.findViewById(R.id.quantity);
            mImageView = view.findViewById(R.id.productPhoto);
            mAddButton = view.findViewById(R.id.add);
            mMinusButton = view.findViewById(R.id.subtract);

        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", mNameView=" + mNameView.getText() +
                    ", mPriceView=" + mPriceView.getText() +
                    ", mQuantityView=" + mQuantityView.getText() +
                    ", mItem=" + mItem +
                    '}';
        }
    }


    class ProductDiffCallback extends DiffUtil.Callback {

        private final List<Product> oldProducts, newProducts;

        ProductDiffCallback(List<Product> oldProducts, List<Product> newProducts) {
            this.oldProducts = oldProducts;
            this.newProducts = newProducts;
        }

        @Override
        public int getOldListSize() {
            return oldProducts.size();
        }

        @Override
        public int getNewListSize() {
            return newProducts.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldProducts.get(oldItemPosition).getId().equals(newProducts.get(newItemPosition).getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldProducts.get(oldItemPosition).equals(newProducts.get(newItemPosition));
        }
    }


}
