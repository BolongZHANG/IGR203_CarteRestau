package com.carteresto.igr230.carteresto.MenuPrincipale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PlatFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private GridLayoutManager mLayoutManager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlatFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PlatFragment newInstance(int columnCount) {
        PlatFragment fragment = new PlatFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plat_list, container, false);

            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            mLayoutManager = new GridLayoutManager(context, mColumnCount);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(mLayoutManager);


            List<Product> itemList = new ArrayList<>();

            itemList.add(new Product("1","This is a test", 0, 23, this.getResources().getResourceEntryName(R.drawable.plat1)));
            itemList.add(new Product("2","This is a test", 0, 23, this.getResources().getResourceEntryName(R.drawable.plat2)));
            itemList.add(new Product("3","This is a test", 0, 23, this.getResources().getResourceEntryName(R.drawable.plat3)));
            itemList.add(new Product("1","This is a test", 0, 23, this.getResources().getResourceEntryName(R.drawable.plat4)));
            itemList.add(new Product("1","This is a test", 0, 23, this.getResources().getResourceEntryName(R.drawable.plat5)));

            recyclerView.setAdapter(new ProductRecyclerViewAdapter(itemList, mListener));

        return view;
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
