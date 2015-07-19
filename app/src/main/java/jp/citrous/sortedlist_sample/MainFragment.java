package jp.citrous.sortedlist_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by citrous on 2015/07/12.
 */
public class MainFragment extends Fragment implements SelectDataDialogFragment.OnDataSelectListener {

    public static final String TAG = MainFragment.class.getName();

    private static final String ID_LIST = "id_list";

    private RecyclerView recyclerView;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        TextView addButton = (TextView) view.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDataDialogFragment
                        .newInstance(SelectDataDialogFragment.OPERATION_ADD, MainFragment.this)
                        .show(getFragmentManager(), SelectDataDialogFragment.TAG);
            }
        });
        TextView removeButton = (TextView) view.findViewById(R.id.remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDataDialogFragment
                        .newInstance(SelectDataDialogFragment.OPERATION_REMOVE, MainFragment.this)
                        .show(getFragmentManager(), SelectDataDialogFragment.TAG);
            }
        });

        final SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDataList();
                swipeRefresh.setRefreshing(false);
            }
        });
        if (recyclerView == null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (recyclerView.getAdapter() == null) {
            SampleAdapter adapter = new SampleAdapter();
            recyclerView.setAdapter(adapter);
            initDataList();
        }

        if (savedInstanceState != null) {
            restoreData(savedInstanceState);
        }
    }

    private void initDataList() {
        ((SampleAdapter) recyclerView.getAdapter()).clearData();
        ArrayList<SampleData> initialDataList = new ArrayList<>();
        initialDataList.add(new SampleData(1, "1"));
        initialDataList.add(new SampleData(2, "2"));
        initialDataList.add(new SampleData(3, "3"));
        initialDataList.add(new SampleData(4, "4"));
        initialDataList.add(new SampleData(5, "5"));
        ((SampleAdapter) recyclerView.getAdapter()).addDataOf(initialDataList);
    }

    @Override
    public void onDataSelected(int operation, ArrayList<SampleData> selectedDataList) {
        switch (operation) {
            case SelectDataDialogFragment.OPERATION_ADD:
                ((SampleAdapter) recyclerView.getAdapter()).addDataOf(selectedDataList);
                break;
            case SelectDataDialogFragment.OPERATION_REMOVE:
                ((SampleAdapter) recyclerView.getAdapter()).removeDataOf(selectedDataList);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Integer> currentIdList = new ArrayList<>();
        SortedList<SampleData> sortedList = ((SampleAdapter) recyclerView.getAdapter()).getList();
        for (int i = 0; i < sortedList.size(); i++) {
            currentIdList.add(sortedList.get(i).getId());
        }
        outState.putIntegerArrayList(ID_LIST, currentIdList);
    }

    private void restoreData(Bundle bundle) {
        ((SampleAdapter) recyclerView.getAdapter()).clearData();
        ArrayList<Integer> dataIdList = bundle.getIntegerArrayList(ID_LIST);
        if (dataIdList == null) {
            initDataList();
        } else {
            ArrayList<SampleData> dataList = new ArrayList<>();
            for (int id : dataIdList) {
                SampleData data = new SampleData(id, String.valueOf(id));
                dataList.add(data);
            }
            ((SampleAdapter) recyclerView.getAdapter()).addDataOf(dataList);
        }
    }
}
