package com.carteresto.igr230.carteresto.MenuPrincipale;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;
import com.carteresto.igr230.carteresto.source.ProductsRepository;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductShowFragment extends DialogFragment {
    static final String TAG = ProductsRepository.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.iv_product)
    ImageView ivProduct;
    //    @BindView(R.id.anim_toolbar)
//    Toolbar animToolbar;
//    @BindView(R.id.collapsing_toolbar)
//    CollapsingToolbarLayout collapsingToolbar;
//    @BindView(R.id.appbar)
//    AppBarLayout appbar;
    @BindView(R.id.description)
    TextView description;


    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.fab_minus)
    FloatingActionButton fabMinus;
    @BindView(R.id.fg_btmbar)
    FrameLayout fgBtmbar;
    @BindView(R.id.activity_main)
    CoordinatorLayout activityMain;
    // TODO: Rename and change types of parameters
    @NonNull
    private String mId;
    private OnFragmentInteractionListener mListener;
    private ProductViewModel viewModel;
    private FirebaseStorage storage;


    public ProductShowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id product id.
     * @return A new instance of fragment show the detail infomation about a product.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductShowFragment newInstance(String id) {
        ProductShowFragment fragment = new ProductShowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ARG_PARAM1);
        }
        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_show, container, false);
        unbinder = ButterKnife.bind(this, view);


        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();
        ViewGroup.LayoutParams param = fgBtmbar.getLayoutParams();
        param.width = d.getWidth();


        fgBtmbar.setLayoutParams(param);


        viewModel.getTestProduct(mId).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product product) {
                if (product != null) {
                    name.setText(product.getName());
                    description.setText(product.getDescription());
                    storage = FirebaseStorage.getInstance();
                    Glide.with(getContext() /* context */)
                            .using(new FirebaseImageLoader())
                            .load(storage.getReferenceFromUrl("gs://carterestoandroid.appspot.com/product/" + product.getId() + "/big.jpg"))
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .into(ivProduct);
                    return;
                }
                Log.e(TAG, "onChanged: can not get product info:cmd" + viewModel.getCmdNumber());
            }


        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

    }

}
