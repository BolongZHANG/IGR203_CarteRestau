package com.carteresto.igr230.carteresto.MenuPrincipale;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carteresto.igr230.carteresto.MenuPrincipale.PlatFragment.OnListFragmentInteractionListener;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.SimplyProductViewHolder> {

    private final List<Product> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public ProductRecyclerViewAdapter(List<Product> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public SimplyProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cardview, parent, false);
        return new SimplyProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimplyProductViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mPriceView.setText(mValues.get(position).getPrice() + " €");
        holder.mQuantityView.setText(String.valueOf(mValues.get(position).getQuantity()));
        //TODO save the image in the resource directory
        int resID = mContext.getResources().getIdentifier(mValues.get(position).getImage(), "drawable", mContext.getPackageName());
        holder.mImageView.setImageResource(resID);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);

                    //TODO
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class SimplyProductViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mPriceView;
        public final TextView mQuantityView;
        public final ImageView mImageView;
        public final FloatingActionButton mAddButton;
        public final FloatingActionButton msubtractButton;
        public Product mItem;

        public SimplyProductViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mPriceView = (TextView) view.findViewById(R.id.price);
            mQuantityView = (TextView) view.findViewById(R.id.quantity);
            mImageView = (ImageView) view.findViewById(R.id.productPhoto);
            mAddButton = (FloatingActionButton) view.findViewById(R.id.add);
            msubtractButton = (FloatingActionButton) view.findViewById(R.id.subtract);

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
}
