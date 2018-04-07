package com.carteresto.igr230.carteresto.Panier;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleProductAdapter extends RecyclerView.Adapter<SimpleProductAdapter.ProductViewHolder> {
    private static final String TAG = SimpleProductAdapter.class.getSimpleName();

    @NonNull
    private final List<SimpleProduct> mData = new ArrayList<>();

    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final CommandItemModifyListener mListener;
    private boolean complet;

    public SimpleProductAdapter(@NonNull CommandItemModifyListener listener) {
        this.mListener = listener;
        this.complet = true;
    }


    public SimpleProductAdapter(@NonNull CommandItemModifyListener listener, boolean complet) {
        this.mListener = listener;
        this.complet = complet;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.command_product_item, parent, false);

        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        SimpleProduct product = mData.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice() + " €");
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));

        String imageRef = "gs://carterestoandroid.appspot.com/product/" + product.getId() + "/"
                + "small.jpg";
        Log.d(TAG, "onBindViewHolder: load image:" + imageRef);

        Glide.with(holder.itemView.getContext() /* context */)
                .using(new FirebaseImageLoader())
                .load(storage.getReferenceFromUrl(imageRef))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(holder.productImg);
        if(complet){
            holder.btnAdd.setOnClickListener(v -> mListener.add(product.getId()));
            holder.btnMinus.setOnClickListener(v -> mListener.minus(product.getId()));
            holder.btnNote.setOnClickListener(v->mListener.editNote(product));
        }else{
            holder.btnAdd.setVisibility(View.INVISIBLE);
            holder.btnMinus.setVisibility(View.INVISIBLE);
            holder.productPrice.setVisibility(View.INVISIBLE);
            holder.btnNote.setVisibility(View.INVISIBLE);
        }


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<SimpleProduct> data) {
        Log.d(TAG, "setData: update the list data:" + data);

        SimpleProductAdapter.SimpleProductDiffCallback productDiffCallback = new SimpleProductAdapter.SimpleProductDiffCallback(this.mData, data);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(productDiffCallback);
        mData.clear();
        mData.addAll(data);
        diffResult.dispatchUpdatesTo(this);
    }




    class SimpleProductDiffCallback extends DiffUtil.Callback {

        private final List<SimpleProduct> oldProducts;
        private final List<SimpleProduct> newProducts;

        SimpleProductDiffCallback(List<SimpleProduct> oldProducts, List<SimpleProduct> newProducts) {
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


    class ProductViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.product_img)
        AppCompatImageView productImg;
        @BindView(R.id.product_name)
        AppCompatTextView productName;
        @BindView(R.id.product_price)
        AppCompatTextView productPrice;
        @BindView(R.id.btn_minus)
        FloatingActionButton btnMinus;
        @BindView(R.id.product_quantity)
        AppCompatTextView productQuantity;
        @BindView(R.id.btn_add)
        FloatingActionButton btnAdd;
        @BindView(R.id.btn_note)
        ImageButton btnNote;

        ProductViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
