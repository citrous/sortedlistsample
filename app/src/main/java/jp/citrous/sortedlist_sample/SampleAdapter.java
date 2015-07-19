package jp.citrous.sortedlist_sample;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by citrous on 2015/07/12.
 */
public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleViewHolder> {

    private SortedList<SampleData> sortedList;

    public SampleAdapter() {
        sortedList = new SortedList<>(SampleData.class, new SampleCallback(this));
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_list, viewGroup, false);
        return new SampleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SampleViewHolder sampleViewHolder, int i) {
        SampleData data = sortedList.get(i);
        if (data != null) {
            sampleViewHolder.bind(data);
        }
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void addDataOf(List<SampleData> dataList) {
        sortedList.addAll(dataList);
    }

    public void removeDataOf(List<SampleData> dataList) {
        sortedList.beginBatchedUpdates();
        for (SampleData data : dataList) {
            sortedList.remove(data);
        }
        sortedList.endBatchedUpdates();
    }

    public void clearData() {
        sortedList.clear();
    }

    public SortedList<SampleData> getList() {
        return sortedList;
    }

    public static class SampleViewHolder extends RecyclerView.ViewHolder {

        private TextView idText;

        private TextView textText;

        public SampleViewHolder(View itemView) {
            super(itemView);
            idText = (TextView) itemView.findViewById(R.id.id);
            textText = (TextView) itemView.findViewById(R.id.text);
        }

        public void bind(@NonNull SampleData data) {
            idText.setText(String.valueOf(data.getId()));
            textText.setText(data.getText());
        }
    }

    private static class SampleCallback extends SortedList.Callback<SampleData> {

        private RecyclerView.Adapter adapter;

        SampleCallback(@NonNull RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int compare(SampleData data1, SampleData data2) {
            return data1.getId() - data2.getId();
        }

        @Override
        public void onInserted(int position, int count) {
            adapter.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            adapter.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            adapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            adapter.notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(SampleData oldData, SampleData newData) {
            String oldText = oldData.getText();
            if (oldText == null) {
                return newData.getText() == null;
            }
            return oldText.equals(newData.getText());
        }

        @Override
        public boolean areItemsTheSame(SampleData data1, SampleData data2) {
            return data1.getId() == data2.getId();
        }
    }
}
