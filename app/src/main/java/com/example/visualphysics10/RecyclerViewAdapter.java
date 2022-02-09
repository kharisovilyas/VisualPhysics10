package com.example.visualphysics10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualphysics10.databinding.FragmentItemBinding;
import com.example.visualphysics10.placeholder.PlaceholderContent;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderContent.PlaceHolderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public final List<PlaceholderContent.PlaceHolderItem> mValues;

    private final OnLessonListener onLessonListener;
    public RecyclerViewAdapter(List<PlaceholderContent.PlaceHolderItem> items, OnLessonListener onLessonListener) {
        mValues = items;
        this.onLessonListener = onLessonListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from
                                (parent.getContext()), parent, false), onLessonListener);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).title);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderContent.PlaceHolderItem mItem;
        public int position;
        OnLessonListener onLessonListener;
        public ViewHolder(FragmentItemBinding binding, OnLessonListener onLessonListener) {
            super(binding.getRoot());
            mIdView = binding.title;
            mContentView = binding.description;
            this.onLessonListener = onLessonListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            position = getLayoutPosition();
            onLessonListener.onLessonClick(getLayoutPosition());
                        /*AppCompatActivity activity = (AppCompatActivity) v.getContext();
            TestFragment myFragment = new TestFragment();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.action_bar_container, myFragment)
                    .addToBackStack(null).commit();*/
        }
    }
    public interface OnLessonListener{
        void onLessonClick(int position);
    }
}
