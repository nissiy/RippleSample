package info.nissiy.ripplesample;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewGroup contentsRoot = (ViewGroup) findViewById(android.R.id.content);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        final SampleRecyclerViewAdapter adapter = new SampleRecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ListSpacingItemDecoration(this, getResources().getDimensionPixelSize(R.dimen.card_elevation)));
        adapter.setOnClickItemListener((position) ->
                Snackbar.make(contentsRoot, String.format("Click Item No.%d", position), Snackbar.LENGTH_SHORT).show());
        recyclerView.setAdapter(adapter);
    }

    private static class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleViewHolder> {
        public static final int ITEM_COUNT = 50;
        private OnClickItemListener onClickItemListener;

        public interface OnClickItemListener {
            void onClick(int position);
        }

        public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
            this.onClickItemListener = onClickItemListener;
        }

        @Override
        public SampleViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new SampleViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final SampleViewHolder holder, int position) {
            holder.imageView.setImageResource(R.drawable.sample_image);
            holder.textView.setText(String.format(Locale.US, "Content Item No.%d", position));
            holder.itemView.setOnClickListener((v) -> {
                if (onClickItemListener != null) {
                    onClickItemListener.onClick(holder.getAdapterPosition());
                }
            });
        }

        @Override
        public long getItemId(final int position) {
            return (long) position;
        }

        @Override
        public int getItemCount() {
            return ITEM_COUNT;
        }
    }

    private static class SampleViewHolder extends RecyclerView.ViewHolder {
        public final AppCompatImageView imageView;
        public final AppCompatTextView textView;

        public SampleViewHolder(final View itemView) {
            super(itemView);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.image_view);
            textView = (AppCompatTextView) itemView.findViewById(R.id.text_view);
        }
    }

    private static class ListSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int unitMargin;
        private int cardElevation;

        public ListSpacingItemDecoration(Context context, int cardElevation) {
            unitMargin = context.getResources().getDimensionPixelSize(R.dimen.unit_margin);
            this.cardElevation = cardElevation;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);

            outRect.left = -cardElevation;
            outRect.right = -cardElevation;

            if (position == 0) {
                outRect.top = unitMargin - (int) (cardElevation * 1.5);
            }
            if (position == SampleRecyclerViewAdapter.ITEM_COUNT - 1) {
                outRect.bottom = unitMargin - (int) (cardElevation * 1.5);
            } else {
                outRect.bottom = unitMargin - cardElevation * 3;
            }
        }

    }

}
