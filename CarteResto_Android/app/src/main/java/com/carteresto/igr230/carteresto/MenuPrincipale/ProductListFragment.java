package com.carteresto.igr230.carteresto.MenuPrincipale;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.carteresto.igr230.carteresto.MenuDetail.MenuDetailActivity;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;

import java.util.Collections;
import java.util.Comparator;
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

    Unbinder unbinder;
    private String mType = Product.VIN;
    private OnListFragmentInteractionListener mListener;
    private ProductListViewModel viewModel;
    private ProductRecyclerViewAdapter mAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductListFragment getInstance(@Product.Types String type) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }
        this.viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ProductListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plat_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        Context context = view.getContext();
        this.recyclerView = view.findViewById(R.id.list);
        int count = mType.equals(Product.MENU) ? 1 : 4;
        int orientation = mType.equals(Product.MENU) ? StaggeredGridLayoutManager.HORIZONTAL : StaggeredGridLayoutManager.VERTICAL;
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(count, orientation);
//            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ProductRecyclerViewAdapter(this, this);

        recyclerView.setAdapter(mAdapter);

        this.viewModel.getProductsListByType(mType).observe(this, products -> {
            Log.e(TAG, "onCreateView: change plat list " + products.size());
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return lhs.getQuantity() > rhs.getQuantity() ? -1 : 0;
                }
            });

            mAdapter.setData(products);
        });


        initFilter();

        return view;
    }

    private void initFilter() {

        if(mType.equals(Product.VIN)){
            cbFilter1.setText("Les rouges");
            cbFilter2.setText("Les rosés");
            cbFilter3.setText("Les blancs");
            cbFilter4.setText("Les pétillants");
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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


    @OnClick({R.id.cb_filter1, R.id.cb_filter2, R.id.cb_filter3, R.id.cb_filter4})
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cb_filter1:
                if (checked)
                    mAdapter.getFilter().filter(((RadioButton) view).getText());
                    break;
            case R.id.cb_filter2:
                if (checked)
                    mAdapter.getFilter().filter(((RadioButton) view).getText());
                    break;
            case R.id.cb_filter3:
                if (checked)
                    mAdapter.getFilter().filter(((RadioButton) view).getText());
                    break;
            case R.id.cb_filter4:
                if (checked)
                    mAdapter.getFilter().filter(((RadioButton) view).getText());
                    break;
        }
    }
}
