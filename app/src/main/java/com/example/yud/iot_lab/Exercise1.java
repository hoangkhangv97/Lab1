package com.example.yud.iot_lab;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.Pwm;

import java.io.IOException;

public class Exercise1 extends Activity {
    private static final String TAG = Exercise1.class.getSimpleName();
    private Gpio mLedGpioR;
    private Gpio mLedGpioG;
    private Gpio mLedGpioB;
    private int mLedState = 0;
    private int INTERVAL_BETWEEN_BLINKS_MS = 500;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting Exercise1");
        try {
            String R = "BCM26";
            String G = "BCM16";
            String B = "BCM6";
            Log.i(TAG, "Registering button driver " + "BCM21");

            mLedGpioR = PeripheralManager.getInstance().openGpio(R);
            mLedGpioR.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mLedGpioG = PeripheralManager.getInstance().openGpio(G);
            mLedGpioG.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mLedGpioB = PeripheralManager.getInstance().openGpio(B);
            mLedGpioB.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mHandler.post(mRGBRunnable);
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove pending blink Runnable from the handler.
        mHandler.removeCallbacks(mRGBRunnable);


        // Close the Gpio pin.
        Log.i(TAG, "Closing LED GPIO pin");
        try {
            mLedGpioR.close();
            mLedGpioG.close();
            mLedGpioB.close();
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        } finally {
            mLedGpioR = null;
            mLedGpioG = null;
            mLedGpioB = null;
        }
    }


    private void redColor(){
        if (mLedGpioR == null) {
            return;
        }
        try {
            mLedGpioR.setValue(false);
            mLedGpioG.setValue(true);
            mLedGpioB.setValue(true);
        } catch (IOException e) {
            Log.e("Error", "Error on PeripheralIO API", e);
        }
    }

    private void blueColor(){
        if (mLedGpioR == null) {
            return;
        }
        try {
            mLedGpioB.setValue(false);
            mLedGpioG.setValue(true);
            mLedGpioR.setValue(true);
        } catch (IOException e) {
            Log.e("Error", "Error on PeripheralIO API", e);
        }
    }

    private void greenColor(){
        if (mLedGpioR == null) {
            return;
        }
        try {
            mLedGpioG.setValue(false);
            mLedGpioR.setValue(true);
            mLedGpioB.setValue(true);
        } catch (IOException e) {
            Log.e("Error", "Error on PeripheralIO API", e);
        }
    }

    private void rgColor(){
        if (mLedGpioR == null || mLedGpioG == null) {
            return;
        }
        try {
            mLedGpioR.setValue(false);
            mLedGpioG.setValue(false);
            mLedGpioB.setValue(true);
        } catch (IOException e) {
            Log.e("Error", "Error on PeripheralIO API", e);
        }
    }

    private void rbColor(){
        if (mLedGpioR == null || mLedGpioB == null) {
            return;
        }
        try {
            mLedGpioR.setValue(false);
            mLedGpioB.setValue(false);
            mLedGpioG.setValue(true);
        } catch (IOException e) {
            Log.e("Error", "Error on PeripheralIO API", e);
        }
    }

    private void gbColor(){
        if (mLedGpioG == null || mLedGpioB == null) {
            return;
        }
        try {
            mLedGpioB.setValue(false);
            mLedGpioG.setValue(false);
            mLedGpioR.setValue(true);
        } catch (IOException e) {
            Log.e("Error", "Error on PeripheralIO API", e);
        }
    }

    private void rgbColor(){
        if (mLedGpioR == null || mLedGpioG == null || mLedGpioB == null) {
            return;
        }
        try {
            mLedGpioR.setValue(false);
            mLedGpioG.setValue(false);
            mLedGpioB.setValue(false);
        } catch (IOException e) {
            Log.e("Error", "Error on PeripheralIO API", e);
        }
    }

    private Runnable mRGBRunnable = new Runnable() {
        @Override
        public void run() {
            // Exit Runnable if the GPIO is already closed
            if (mLedGpioR == null || mLedGpioG == null || mLedGpioB == null) {
                return;
            }
            try {
                switch (mLedState){
                    case 0:{
                        redColor();
                        mLedState ++;
                        break;
                    }
                    case 1:{
                        blueColor();
                        mLedState ++;
                        break;
                    }
                    case 2:{
                        greenColor();
                        mLedState ++;
                        break;
                    }
                    case 3:{
                        rgColor();
                        mLedState ++;
                        break;
                    }
                    case 4:{
                        gbColor();
                        mLedState ++;
                        break;
                    }
                    case 5:{
                        rbColor();
                        mLedState ++;
                        break;
                    }
                    case 6:{
                        rgbColor();
                        mLedState = 0;
                        break;
                    }
                }
                mHandler.postDelayed(mRGBRunnable, INTERVAL_BETWEEN_BLINKS_MS);
            } catch (Exception e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }
        }
    };
}
