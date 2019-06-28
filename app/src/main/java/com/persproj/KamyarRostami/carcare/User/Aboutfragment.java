package com.persproj.KamyarRostami.carcare.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.persproj.KamyarRostami.carcare.R;

public class Aboutfragment extends Fragment {
    private ImageButton telegram, website, mail;

    public static Aboutfragment newInstance() {
        Aboutfragment aboutfragment = new Aboutfragment();
        return aboutfragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        init(v);
        onClickListener();
        return v;
    }

    public void init(View view) {
        telegram = (ImageButton) view.findViewById(R.id.telegram_btn);
        website = (ImageButton) view.findViewById(R.id.web_btn);
        mail = (ImageButton) view.findViewById(R.id.mail_btn);
    }

    public void onClickListener() {
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/count_kamyar"));
                startActivity(browserIntent);
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kamyarrostami.ir/"));
                startActivity(browserIntent);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:Kamyar21Rostami@gmail.com"));
                startActivity(browserIntent);
            }
        });
    }

}
