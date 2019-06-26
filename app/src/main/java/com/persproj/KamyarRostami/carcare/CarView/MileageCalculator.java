package com.persproj.KamyarRostami.carcare.CarView;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MileageCalculator {

    private Context mContext;

    private String TAG = "MileageCalculator";

    public MileageCalculator(Context mContext) {

        this.mContext = mContext;
    }

    public MileageCalculator() {


    }

    /***
     * barresi mikone mahe database+service ro ba mahi ke alan tooshim age reside bood return true
     * @param usage
     * @param carname
     * @param dbmonth
     * @return
     */
    public boolean mileageCalculator(int usage, String carname, int dbmonth) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        int presentMonth = Integer.parseInt(dateFormat.format(date));
        int month;
        switch (carname) {
            case "Audi":
                month = 4000 / usage;
                dbmonth += month;
                break;
            case "Benz":
                month = 6000 / usage;
                dbmonth += month;
                break;
            case "BMW":
                month = 7000 / usage;
                dbmonth += month;
                break;
            case "Dena":
                month = 8000 / usage;
                dbmonth += month;
                break;
            case "L-90":
                month = 9000 / usage;
                dbmonth += month;
                break;
            case "Pride":
                month = 10000 / usage;
                dbmonth += month;
                break;
            case "Samand":
                month = 9000 / usage;
                dbmonth += month;
                break;
        }
        if (dbmonth == presentMonth) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * return mikone url ro baraye display ba tavajoh be carmodel az db
     * @param carname
     * @return
     */
    public String carUrl(String carname) {
        switch (carname) {
            case "Audi":
                return "https://res.cloudinary.com/carsguide/image/upload/f_auto,fl_lossy,q_auto,t_cg_hero_large/v1/editorial/2019-Audi-A7-black-press-image-1001x565p%20%282%29.jpg";

            case "Benz":
                return "https://media.caradvice.com.au/image/private/c_fill,q_auto,f_auto,w_960,h_500/328a7befd10c85a23a44392704f7f392.jpg";

            case "BMW":
                return "https://st.motortrend.com/uploads/sites/5/2019/03/2019-BMW-Z4-sDrive30i-24.jpg";

            case "Dena":
                return "https://i.wheelsage.org/image/format/picture/picture-preview-large/i/iran_khodro/dena/iran_khodro_dena_18.jpg";

            case "L-90":
                return "https://static.turbosquid.com/Preview/2016/01/15__14_27_29/FR_01.png8ad9599a-e838-4b37-aa94-383d671c6c06Original.jpg";

            case "Pride":
                return "https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/Kia_Pride_silver_vl.jpg/1200px-Kia_Pride_silver_vl.jpg";

            case "Samand":
                return "https://360view.hum3d.com/zoom/Iran-Khodro/Iran-Khodro_Samand_2011_1000_0001.jpg";

            case "Mitsubishi":
                return "https://images.hgmsites.net/hug/mitsubishi-lancer_100684861_h.jpg";

            default:
                return "H:\\CarCare\\CarCare\\app\\src\\main\\res\\drawable\\car_thumbnail.png";
        }
    }

}
