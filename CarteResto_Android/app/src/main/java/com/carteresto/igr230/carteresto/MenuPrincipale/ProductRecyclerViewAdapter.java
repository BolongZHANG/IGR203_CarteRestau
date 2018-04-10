package com.carteresto.igr230.carteresto.MenuPrincipale;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ProductRecyclerViewAdapter.class.getSimpleName();
    private static final int MENU = 0;
    private static final int VIN = 1;
    private static final int OTHER = 3;
    @NonNull
    private List<Product> mData = new ArrayList<>();

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private QuantityModifyListener mListener;

    public ProductRecyclerViewAdapter(Fragment fragment, QuantityModifyListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MENU) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menu_item_cardview, parent, false);
            return new MenuViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cardview, parent, false);
        return new SimplyProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String type = mData.get(position).getType();
        final Product product = mData.get(position);

        if (type.equals(Product.MENU)) {
            MenuViewHolder menuHolder = (MenuViewHolder) holder;
            menuHolder.mNameView.setText(product.getName());
            menuHolder.mPriceView.setText(product.getPrice() + " €");
            String imageRef = "gs://carterestoandroid.appspot.com/product/" + product.getId() + "/"
                    + product.getId() + ".jpg";
            Log.d(TAG, "onBindViewHolder: load image:" + imageRef);

            Glide.with(holder.itemView.getContext() /* context */)
                    .using(new FirebaseImageLoader())
                    .load(storage.getReferenceFromUrl(imageRef))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(menuHolder.mImageView);
            menuHolder.mImageView.setOnClickListener(v -> mListener.showDialogue(product.getId()));
            return;
        }

        SimplyProductViewHolder mHolder = (SimplyProductViewHolder) holder;
        mHolder.mNameView.setText(product.getName());
        mHolder.mPriceView.setText(product.getPrice() + " €");
        mHolder.mQuantityView.setText(String.valueOf(product.getQuantity()));
        //TODO save the image in the resource directory
        String imageRef = "gs://carterestoandroid.appspot.com/product/" + product.getId() + "/small.jpg";
        Log.d(TAG, "onBindViewHolder: load image:" + imageRef);

        Glide.with(holder.itemView.getContext() /* context */)
                .using(new FirebaseImageLoader())
                .load(storage.getReferenceFromUrl(imageRef))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(mHolder.mImageView);

        mHolder.mAddButton.setOnClickListener(v -> mListener.add(product.getId()));
        mHolder.mMinusButton.setOnClickListener(v -> mListener.minus(product.getId()));
        mHolder.mImageView.setOnClickListener(v -> mListener.showDialogue(product.getId()));

    }

    @Override
    public int getItemViewType(int position) {
        String type = mData.get(position).getType();
        if (type.equals(Product.MENU)) return MENU;
        if (type.equals(Product.VIN)) return VIN;
        else return OTHER;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Product> data) {
        Log.d(TAG, "setData: update the list data");


        if (this.mData != null) {
            ProductDiffCallback productDiffCallback = new ProductDiffCallback(this.mData, data);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(productDiffCallback);
            mData.clear();
            mData.addAll(data);
            diffResult.dispatchUpdatesTo(this);
        }
    }


    public interface QuantityModifyListener {
        void add(String id);

        void minus(String id);

        void showDialogue(String id);
    }

    public class SimplyProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mNameView;
        @BindView(R.id.price)
        TextView mPriceView;
        @BindView(R.id.quantity)
        TextView mQuantityView;
        @BindView(R.id.productPhoto)
        ImageView mImageView;
        @BindView(R.id.add)
        FloatingActionButton mAddButton;
        @BindView(R.id.subtract)
        FloatingActionButton mMinusButton;

        SimplyProductViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    ", mNameView=" + mNameView.getText() +
                    ", mPriceView=" + mPriceView.getText() +
                    ", mQuantityView=" + mQuantityView.getText() +
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


    class MenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mNameView;
        @BindView(R.id.price)
        TextView mPriceView;
        @BindView(R.id.productPhoto)
        ImageView mImageView;

        MenuViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
