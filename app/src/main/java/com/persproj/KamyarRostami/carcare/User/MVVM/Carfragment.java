package com.persproj.KamyarRostami.carcare.User.MVVM;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.persproj.KamyarRostami.carcare.R;

import java.util.List;


public class Carfragment extends Fragment {
    private Cars_ViewModel viewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private ProgressBar progressBar;

    private static final String TAG = "Carfragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cars, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.cars_recycler_view);
        actionButton = (FloatingActionButton) view.findViewById(R.id.action_btn);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        viewModel = ViewModelProviders.of(getActivity()).get(Cars_ViewModel.class);
        viewModel.init(getContext());//retrieve data from repository
        viewModel.getcars().observe(this, new Observer<List<CarModel>>() {
            @Override
            public void onChanged(@Nullable List<CarModel> carModels) {
                Toast.makeText(getActivity(), "data has been changed", Toast.LENGTH_SHORT).show();
                Recycler_Adapter adapter = new Recycler_Adapter(getContext(), viewModel.getcars().getValue(), viewModel.getImageUrl());
                recyclerView.setAdapter(adapter);

            }


        });

        viewModel.getCarupdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.smoothScrollToPosition(viewModel.getcars().getValue().size() - 1);
                } else {

                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.addnewValue(new CarModel("Mitsubishi", "400", "2015", "2"));
            }
        });
        initRecyclerView(view);
        return view;

    }

    public void initRecyclerView(View view) {
        Log.d(TAG, "initRecyclerView: viewModel"+viewModel.getImageUrl());
        Recycler_Adapter adapter = new Recycler_Adapter(getContext(), viewModel.getcars().getValue(), viewModel.getImageUrl());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


}
