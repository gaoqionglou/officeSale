package com.example.myapplication.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

//工具类-弹窗
public class AlertDialogUtils {

    public static void show(Context context, String msg, final View.OnClickListener listener) {
        show(context, msg, "取消", "确定", listener);
    }

    public static void show(Context context, String msg, String cancelStr, String okStr, final View.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View myView = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
        builder.setView(myView);
        final AlertDialog dialog = builder.create();

        ((TextView) myView.findViewById(R.id.messageTV)).setText(Html.fromHtml(msg));
        TextView cancelBtn = (TextView) myView.findViewById(R.id.cancelBtn);
        cancelBtn.setText(cancelStr);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView okBtn = (TextView) myView.findViewById(R.id.okBtn);
        okBtn.setText(okStr);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onClick(null);
            }
        });
        dialog.show();


    }
}
