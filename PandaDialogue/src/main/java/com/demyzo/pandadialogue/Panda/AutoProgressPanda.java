package com.demyzo.pandadialogue.Panda;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;


import androidx.annotation.RequiresApi;

import com.demyzo.pandadialogue.CircleImageView;
import com.demyzo.pandadialogue.R;
import com.demyzo.pandadialogue.addListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class AutoProgressPanda {


    private Context context;
    private Dialog doalog;
    private AlertDialog.Builder builder;
    private CircleImageView circleImageView;
    private ProgressBar progressBar;
    private int progressINT = 0;
    private addListener addListener;
    private boolean isListener = false;
    private boolean TouchOutside = true;
    private int borderW = 2;
    private int color = Color.BLACK;
    private int image = R.drawable.protectedimage;
    private int Elev = 2;


    public AutoProgressPanda(Context context) {
        this.context = context;
    }
    public void setAddListener(addListener addListener) {
        this.addListener = addListener;
        setListener(true);
    }

    private void setListener(boolean listener) {
        isListener = listener;
    }

    public void setTouchOutside(boolean touchOutside) {
        TouchOutside = touchOutside;
    }

    public void setBorderW(int borderW) {
        this.borderW = borderW;
    }

    public void setBorderColor(int color) {
        this.color = color;
    }

    public void setImage(int drawable) {
        this.image = drawable;
    }

    public void setElev(int elev) {
        Elev = elev;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void show() {
        doalog = new Dialog(context);
        if (!doalog.isShowing()) {

            doalog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            doalog.setContentView(R.layout.view);
            circleImageView = doalog.findViewById(R.id.imageView);
            progressBar = doalog.findViewById(R.id.progressBar);

            circleImageView.setBorderColor(color);
            circleImageView.setBorderWidth(borderW);
            circleImageView.setElevation(Elev);
            doalog.setCanceledOnTouchOutside(TouchOutside);
            if (isListener) {
                Picasso.get().load(image).into(circleImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        addListener.onImageSuccess();
                    }

                    @Override
                    public void onError(Exception e) {
                        addListener.onImageError();
                    }
                });
            } else {
                Picasso.get().load(image).into(circleImageView);
            }

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    progressINT++;
                    progressBar.setProgress(progressINT);
                    if (progressINT == 100) {
                        progressINT = 0;
                    }
                }
            };
            timer.schedule(timerTask, 0, 10);

            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isListener) {
                        addListener.onClick();
                    }

                }
            });
            circleImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (isListener) {
                        addListener.onLongClick();
                    }
                    return true;
                }
            });
            doalog.show();
        }
    }
    public  void dismiss(){
        if (doalog.isShowing()){
            doalog.dismiss();
        }
    }
}
