package com.persproj.KamyarRostami.carcare.CarSearch;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.persproj.KamyarRostami.carcare.CarView.MileageCalculator;
import com.persproj.KamyarRostami.carcare.DataModel.Cars;
import com.persproj.KamyarRostami.carcare.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class CarListAdapter extends ArrayAdapter<Cars> {
    private static final String TAG = "CarListAdapter";
    private LayoutInflater layoutInflater;
    private List<Cars> cars = null;
    private int layoutResource;
    private Context mContext;
    private MileageCalculator mileageCalculator = new MileageCalculator(mContext);

    public CarListAdapter(@NonNull Context context, int resource, @NonNull List<Cars> objects) {
        super(context, resource, objects);
        mContext = context;
        layoutResource = resource;
        cars = objects;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class viewHolder {
        TextView carName, plateNO, mileage, usage;
        ImageView carImage;
        AVLoadingIndicatorView indicatorView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final viewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            holder = new viewHolder();
            holder.carName = (TextView) convertView.findViewById(R.id.car_model2);
            holder.mileage = (TextView) convertView.findViewById(R.id.car_kilometer2);
            holder.usage = (TextView) convertView.findViewById(R.id.car_usage);
            holder.plateNO = (TextView) convertView.findViewById(R.id.car_id);
            holder.carImage = (ImageView) convertView.findViewById(R.id.car_image);
            holder.indicatorView = (AVLoadingIndicatorView) convertView.findViewById(R.id.AVLoadingItem);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.carName.setText(cars.get(position).getModel());
        holder.plateNO.setText(cars.get(position).getPlate());
        holder.mileage.setText(cars.get(position).getMileage());
        holder.usage.setText(cars.get(position).getUsage());
        Picasso.get().load(mileageCalculator.carUrl(cars.get(position).getModel())).into(holder.carImage, new Callback() {
            @Override
            public void onSuccess() {
                holder.indicatorView.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(R.drawable.ic_error_image).into(holder.carImage);
                Toast.makeText(mContext, "Loading failed. check your connection", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
