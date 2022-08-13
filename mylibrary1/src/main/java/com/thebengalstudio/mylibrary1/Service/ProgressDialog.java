package com.thebengalstudio.mylibrary1.Service;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.core.content.ContextCompat;

import com.thebengalstudio.mylibrary1.R;

public class ProgressDialog {

    public static Dialog dialog;
    public static void Show_loader(Context context) {

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_loading_layout);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.color_no));

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.show();

    }

    public static void cancel_loader(){
        if(dialog!=null){
            dialog.cancel();

        }
    }
}
