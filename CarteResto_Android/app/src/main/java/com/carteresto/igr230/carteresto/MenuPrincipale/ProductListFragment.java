package com.carteresto.igr230.carteresto.MenuPrincipale;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.carteresto.igr230.carteresto.MenuDetail.MenuDetailActivity;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProductListFragment extends DialogFragment implements ProductRecyclerViewAdapter.QuantityModifyListener {
    static final String TAG = ProductListFragment.class.getSimpleName();
    private static final String ARG_TYPE = "type";
    private static final String COUNT = "count";
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private String mType = Product.VIN;
    private OnListFragmentInteractionListener mListener;
    private ProductListViewModel viewModel;
    @BindView(R.id.cb_filter0)
    RadioButton cbFilter0;
    @BindView(R.id.cb_filter1)
    RadioButton cbFilter1;
    @BindView(R.id.cb_filter2)
    RadioButton cbFilter2;
    @BindView(R.id.cb_filter3)
    RadioButton cbFilter3;
    @BindView(R.id.cb_filter4)
    RadioButton cbFilter4;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.filterGp)
    RadioGroup filterGp;

    private String mConstraint = "";
    final private ProductRecyclerViewAdapter mAdapter = new ProductRecyclerViewAdapter(this, this);
    final private List<Product> mProducts = new ArrayList<>();
    int mCount = 4;


    Unbinder unbinder;
    private int rgFlag;
    private boolean isRefresh;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductListFragment getInstance(@Product.Types String type, int count) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        args.putInt(COUNT, count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
            mCount = getArguments().getInt(COUNT);
        }
        this.viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ProductListViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plat_list, container, false);
        ButterKnife.bind(this, view);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);

        int count = mType.equals(Product.MENU) ? 1 : this.mCount;
        int orientation = mType.equals(Product.MENU) ? StaggeredGridLayoutManager.HORIZONTAL : StaggeredGridLayoutManager.VERTICAL;
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(count, orientation);
//            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        this.viewModel.getProductsListByType(mType).observe(this, products -> {
            Log.e(TAG, "onCreateView: change " + mType + " list:" + products.size());
            this.mProducts.clear();
            this.mProducts.addAll(products);

            update(products);
        });


        initFilter();


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isRefresh){
                    isRefresh = true;
                    Collections.sort(mProducts, new Comparator<Product>() {
                        @Override
                        public int compare(Product lhs, Product rhs) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return lhs.getQuantity() > rhs.getQuantity() ? -1 : 0;
                        }
                    });

                    update(mProducts);
                    isRefresh = false;
                    refreshLayout.setRefreshing(false);
                }


            }
        });
        return view;
    }


    void update(final List<Product> products) {
        Log.d(TAG, "update: product:" + products.size());

        synchronized (this) {


            if (mConstraint.length() == 0) {
                mAdapter.setData(products);
                return;
            }

            ArrayList<Product> filteredList = new ArrayList<>();

            for (Product product : mProducts) {
                Log.i(TAG, "update: product" + product.getIngredients());
                if (product.getIngredients().toLowerCase().contains(mConstraint)) {
                    filteredList.add(product);
                }
            }

            mAdapter.setData(filteredList);
            Log.d(TAG, "update: update data with filter:" + mConstraint.length() + " - " + mConstraint + " list size:" + filteredList.size());

        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onCreateView: " + viewModel.getPlatList());


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void add(String id) {
        Log.e(TAG, "add: add quantity for " + id);
        viewModel.addProductQuantity(id);
    }

    @Override
    public void minus(String id) {
        Log.e(TAG, "minus: minus quantity for " + id);
        viewModel.minusProductQuantity(id);
    }

    @Override
    public void showDialogue(String id) {
        if (mType.equals(Product.MENU)) {
            Intent intent = new Intent(getActivity(), MenuDetailActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        } else {
            ProductShowFragment fragment = ProductShowFragment.newInstance(id, true);
            fragment.show(Objects.requireNonNull(getFragmentManager()), "product-" + id);
        }

    }

    private void initFilter() {

        if (mType.equals(Product.VIN)) {
            cbFilter0.setText("All");
            cbFilter0.setTag("");
            cbFilter1.setText("Les rouges");
            cbFilter1.setTag("rouges");
            cbFilter2.setText("Les rosés");
            cbFilter2.setTag("rosés");
            cbFilter3.setText("Les blancs");
            cbFilter3.setTag("blancs");
            cbFilter4.setText("Les pétillants");
            cbFilter4.setTag("pétillants");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Product item);
    }


    @OnClick({R.id.cb_filter0, R.id.cb_filter1, R.id.cb_filter2, R.id.cb_filter3, R.id.cb_filter4})
    public void onRadioButtonClicked(View view) {
        RadioButton radioButton = (RadioButton) view;
        if (view.getTag() == null) {
            radioButton.setChecked(false);
            return;
        }

        rgFlag = view.getId();
        ((RadioButton) view).setChecked(true);
        mConstraint = radioButton.getTag().toString().toLowerCase().trim();
        this.update(mProducts);

    }

}
