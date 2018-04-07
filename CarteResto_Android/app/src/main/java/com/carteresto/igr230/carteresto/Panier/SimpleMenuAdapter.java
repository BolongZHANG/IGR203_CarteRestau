package com.carteresto.igr230.carteresto.Panier;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carteresto.igr230.carteresto.Model.SimpleMenu;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleMenuAdapter extends RecyclerView.Adapter<SimpleMenuAdapter.MenuViewHolder> {

    private static final String TAG = SimpleMenuAdapter.class.getSimpleName();
    private static final int MENU = 0;
    private static final int VIN = 1;
    private static final int OTHER = 3;

    @NonNull
    private List<SimpleMenu> mData = new ArrayList<>();

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private CommandItemModifyListener mListener;

    public SimpleMenuAdapter(CommandItemModifyListener listener) {
        this.mListener = listener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.command_menu_item, parent, false);
            return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        String type = mData.get(position).getType();
        SimpleMenu menu = mData.get(position);

        holder.menuName.setText(menu.getName());
        holder.menuPrice.setText(menu.getPrice() + " €");
        holder.menuQuantity.setText("x " + menu.getQuantity());

        String imageRef = "gs://carterestoandroid.appspot.com/product/" + menu.getId() + "/"
                    + menu.getId() + ".jpg";
        Log.d(TAG, "onBindViewHolder: load image:" + imageRef);

        Glide.with(holder.itemView.getContext() /* context */)
                    .using(new FirebaseImageLoader())
                    .load(storage.getReferenceFromUrl(imageRef))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .into(holder.menuImg);
        holder.setDishesList(menu.getDishesList());
        holder.btnEdit.setOnClickListener(v -> mListener.editMenu(menu.getId()));
        holder.btnNote.setOnClickListener(v -> mListener.editNote(menu));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<SimpleMenu> data) {
        Log.d(TAG, "setData: update the list data");

        SimpleMenuDiffCallback productDiffCallback = new SimpleMenuDiffCallback(this.mData, data);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(productDiffCallback);
        mData.clear();
        mData.addAll(data);
        diffResult.dispatchUpdatesTo(this);
    }





    class SimpleMenuDiffCallback extends DiffUtil.Callback {

        private final List<SimpleMenu> oldMenus;
        private final List<SimpleMenu> newMenus;

        SimpleMenuDiffCallback(List<SimpleMenu> oldProducts, List<SimpleMenu> newProducts) {
            this.oldMenus = oldProducts;
            this.newMenus = newProducts;
        }

        @Override
        public int getOldListSize() {
            return oldMenus.size();
        }

        @Override
        public int getNewListSize() {
            return newMenus.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldMenus.get(oldItemPosition).getId().equals(newMenus.get(newItemPosition).getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

            return oldMenus.get(oldItemPosition).equals(newMenus.get(newItemPosition));
        }
    }


    class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.menu_img)
        AppCompatImageView menuImg;
        @BindView(R.id.menu_name)
        AppCompatTextView menuName;
        @BindView(R.id.menu_price)
        AppCompatTextView menuPrice;
        @BindView(R.id.menu_quantity)
        AppCompatTextView menuQuantity;
        @BindView(R.id.btn_edit)
        AppCompatImageView btnEdit;
        @BindView(R.id.btn_note)
        AppCompatImageView btnNote;
        @BindView(R.id.expanding_icon)
        AppCompatImageView expandingIcon;
        @BindView(R.id.menu_detail_list)
//        TextView menuDetailList;
        RecyclerView menuDetailList;
        boolean isExpanding = true;
        SimpleProductAdapter adapter;

        MenuViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
             adapter = new SimpleProductAdapter( mListener, false);
             menuDetailList.setLayoutManager(new LinearLayoutManager(view.getContext()));
            menuDetailList.setAdapter(adapter);
            expandingIcon.setOnClickListener(this);
        }

        public void setDishesList(Map<String,SimpleProduct> dishesList) {
            List<SimpleProduct> list = new ArrayList<>(dishesList.values());
//            menuDetailList.setText("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            adapter.setData(list);
        }

        @Override
        public void onClick(View v) {
            isExpanding = !isExpanding;
            if(isExpanding){
                menuDetailList.setVisibility(View.VISIBLE);
                expandingIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                return;
            }
            menuDetailList.setVisibility(View.GONE);
            expandingIcon.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);
        }
    }
}
