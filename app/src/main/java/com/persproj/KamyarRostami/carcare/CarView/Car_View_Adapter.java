package com.persproj.KamyarRostami.carcare.CarView;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.persproj.KamyarRostami.carcare.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class Car_View_Adapter extends RecyclerView.Adapter<Car_View_Adapter.Viewholder> {
    private static final String TAG = "Car_View_Adapter";
    private Context mContext;
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mCarplate = new ArrayList<>();
    private ArrayList<String> mCarnames = new ArrayList<>();
    private ArrayList<String> mCarmileage = new ArrayList<>();
    private ArrayList<String> mCarusage = new ArrayList<>();


    public Car_View_Adapter(Context mContext, ArrayList<String> mImages, ArrayList<String> mCarplate, ArrayList<String> mCarnames, ArrayList<String> mCarmileage, ArrayList<String> mCarusage) {
        this.mContext = mContext;
        this.mImages = mImages;
        this.mCarplate = mCarplate;
        this.mCarnames = mCarnames;
        this.mCarmileage = mCarmileage;
        this.mCarusage = mCarusage;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_car_view, viewGroup, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder viewholder, int i) {
        Log.d(TAG, "onBindViewHolder: called");

        Picasso.get().load(mImages.get(i)).into(viewholder.car_image, new Callback() {
            @Override
            public void onSuccess() {
                viewholder.loadingIndicatorView.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(R.drawable.ic_error_image).into(viewholder.car_image);
                Toast.makeText(mContext, "Loading failed. check your connection", Toast.LENGTH_SHORT).show();
            }
        });
        viewholder.car_model.setText(mCarnames.get(i));
        viewholder.car_mileage.setText(mCarmileage.get(i));
        viewholder.car_usage.setText(mCarusage.get(i));
        viewholder.car_plate.setText(mCarplate.get(i));

    }

    @Override
    public int getItemCount() {
        return mCarnames.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView car_plate, car_model, car_mileage, car_usage;
        ImageView car_image;
        CardView relativeLayout;
        AVLoadingIndicatorView loadingIndicatorView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            car_image = (ImageView) itemView.findViewById(R.id.car_image);
            car_model = (TextView) itemView.findViewById(R.id.car_model2);
            car_mileage = (TextView) itemView.findViewById(R.id.car_kilometer2);
            car_usage = (TextView) itemView.findViewById(R.id.car_usage);
            car_plate = (TextView) itemView.findViewById(R.id.car_id);
            relativeLayout = (CardView) itemView.findViewById(R.id.single_car_layout);
            loadingIndicatorView = (AVLoadingIndicatorView) itemView.findViewById(R.id.AVLoadingItem);
        }
    }


}