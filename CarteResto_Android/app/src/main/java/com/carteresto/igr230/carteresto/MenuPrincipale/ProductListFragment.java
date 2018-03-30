package com.carteresto.igr230.carteresto.MenuPrincipale;


import android.support.v4.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;

import java.util.List;
import java.util.Objects;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProductListFragment extends DialogFragment implements ProductRecyclerViewAdapter.QuantityModifyListener {
    static final String TAG = ProductListFragment.class.getSimpleName();
    private static final String ARG_TYPE = "type";
    private String mType = Product.VIN;
    private OnListFragmentInteractionListener mListener;
    private StaggeredGridLayoutManager mLayoutManager;
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
    public static ProductListFragment getInstance( @Product.Types String type) {
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
            Context context = view.getContext();
            RecyclerView recyclerView =  view.findViewById(R.id.list);
            TextView textView = view.findViewById(R.id.textView);
            mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
//            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new ProductRecyclerViewAdapter(this, this);

        recyclerView.setAdapter(mAdapter);

        viewModel.getCMD().observe(this, new Observer<Command>() {
            @Override
            public void onChanged(@Nullable Command command) {
                textView.setText(command.toString());
            }
        });

        this.viewModel.getProductsListByType(mType).observe(this, products -> {
            Log.e(TAG, "onCreateView: change plat list " + products.size());
            mAdapter.setData(products);
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onCreateView: " + viewModel.getPlatList() );


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
        ProductShowFragment fragment = ProductShowFragment.newInstance(id);
        fragment.show(Objects.requireNonNull(getFragmentManager()), "product-" + id);
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
}
