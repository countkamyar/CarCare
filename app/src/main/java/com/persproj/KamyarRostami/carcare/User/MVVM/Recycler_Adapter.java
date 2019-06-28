package com.persproj.KamyarRostami.carcare.User.MVVM;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.persproj.KamyarRostami.carcare.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.Viewholder> {
    private List<CarModel> carModels;
    private Context context;
    private ArrayList<String> imageUrl;

    public Recycler_Adapter(Context context, List<CarModel> carModels, ArrayList<String> imageUrl) {
        this.carModels = carModels;
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_view_item, viewGroup, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder viewholder, int i) {
        viewholder.car_model.setText(carModels.get(i).getCarModel());
        viewholder.car_speed.setText(carModels.get(i).getSpeed());
        viewholder.car_guarantee.setText(carModels.get(i).getGuarantee());
        viewholder.car_year.setText(carModels.get(i).getCreationDate());
        Picasso.get().load(imageUrl.get(i)).into(viewholder.car_image, new Callback() {
            @Override
            public void onSuccess() {
                viewholder.indicatorView.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return carModels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView car_model, car_speed, car_guarantee, car_year;
        private ImageView car_image;
        private AVLoadingIndicatorView indicatorView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            car_image = (ImageView) itemView.findViewById(R.id.car_image_view);
            car_model = (TextView) itemView.findViewById(R.id.car_model3);
            car_speed = (TextView) itemView.findViewById(R.id.car_speed);
            car_guarantee = (TextView) itemView.findViewById(R.id.car_guarantee);
            car_year = (TextView) itemView.findViewById(R.id.car_model_year);
            indicatorView= (AVLoadingIndicatorView)itemView.findViewById(R.id.AVLoadingItemcars);
        }
    }
}
