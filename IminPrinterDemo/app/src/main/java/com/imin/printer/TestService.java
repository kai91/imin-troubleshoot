package com.imin.printer;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.imin.printerlib.IminPrintUtils;

/**
 * @author xghsir
 * Created by 肖根华 on 2020/4/29.
 */
public class TestService extends Service {

    String text = "";
    private IminPrintUtils mIminPrintUtils;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        text = getString(R.string.text_print);

        mIminPrintUtils = IminPrintUtils.getInstance(TestService.this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int connectType = intent.getIntExtra("connectType",0);
        if(connectType == 1){
            mIminPrintUtils.initPrinter(IminPrintUtils.PrintConnectType.USB);
        }else if(connectType == 2){
            mIminPrintUtils.initPrinter(IminPrintUtils.PrintConnectType.BLUETOOTH);
        }else  if(connectType == 3){
            mIminPrintUtils.initPrinter(IminPrintUtils.PrintConnectType.SPI);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                mIminPrintUtils.printText(text);
                mIminPrintUtils.setAlignment(1);
                mIminPrintUtils.printText(text);
                mIminPrintUtils.setAlignment(0);
                mIminPrintUtils.setTextSize(38);
                mIminPrintUtils.printText(text);
                mIminPrintUtils.setTextSize(28);
                mIminPrintUtils.setTextWidth(300);
                mIminPrintUtils.printText(text);
                mIminPrintUtils.setTextWidth(576);
                mIminPrintUtils.setTextLineSpacing(1.5f);
                mIminPrintUtils.printText(text);
                mIminPrintUtils.setTextLineSpacing(1.0f);
                mIminPrintUtils.setTextStyle(Typeface.BOLD_ITALIC);
                mIminPrintUtils.printText(text);
                mIminPrintUtils.setTextStyle(Typeface.NORMAL);
                mIminPrintUtils.setTextTypeface(Typeface.MONOSPACE);
                mIminPrintUtils.printText(text);
                mIminPrintUtils.setTextTypeface(Typeface.DEFAULT);


                String[] strings3 = new String[]{"Description", "Description Description Description Description Description@48", "192.00"};
                int[] colsWidthArr3 = new int[]{1, 2, 1};
                int[] colsAlign3 = new int[]{0, 1, 2};
                int[] colsSize3 = new int[]{26, 26, 26};
                mIminPrintUtils.printColumnsText(strings3, colsWidthArr3,
                        colsAlign3, colsSize3);
                mIminPrintUtils.printColumnsText(strings3, colsWidthArr3,
                        colsAlign3, colsSize3);
                mIminPrintUtils.printColumnsText(strings3, colsWidthArr3,
                        colsAlign3, colsSize3);

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_rabit);
                mIminPrintUtils.printSingleBitmap(bitmap);
                mIminPrintUtils.printSingleBitmap(bitmap,1);
                mIminPrintUtils.printSingleBitmap(bitmap,2);
                mIminPrintUtils.printAndFeedPaper(100);
                if (Build.MODEL.contains("M2") || Build.MODEL.equals("D1") || Build.MODEL.equals("D1-Pro") || Build.MODEL.contains("Swift")|| Build.MODEL.contains("D3-510")) {
                    mIminPrintUtils.setPageFormat(1);
                }else {
                    mIminPrintUtils.setPageFormat(0);
                }

            }
        }).start();


        return super.onStartCommand(intent, flags, startId);
    }
}
