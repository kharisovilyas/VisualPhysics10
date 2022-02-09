package com.example.visualphysics10;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualphysics10.placeholder.PlaceholderContent;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment implements RecyclerViewAdapter.OnLessonListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    PlaceholderContent mLesson;
    ArrayList<PlaceholderContent> mValues = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     * @param
     */
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new RecyclerViewAdapter(PlaceholderContent.ITEMS, this));
        }
        return view;
    }

    @Override
    public void onLessonClick(int position) {

        TestFragment testFragment = new TestFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(new ItemFragment())
                .replace(R.id.container, testFragment)
                .addToBackStack(null)
                .commit();
    }

    private Fragment switchFragment(int position) {
        switch (position){
            case 1: return new TestFragment();
            default: Log.d("On Item Listener work?", "nou" );
                return null;
        }
    }

}