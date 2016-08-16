package com.project.estevao.csvproject.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.project.estevao.csvproject.R;
import com.project.estevao.csvproject.model.SuccessInterface;
import com.project.estevao.csvproject.model.YourEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Estevao on 15/08/2016.
 */
public class CSVControl {


    public static final String folder = Environment.getExternalStorageDirectory() + "/Archives";

    public static void toArchive(final ArrayList<YourEntity> yourEntities, final SuccessInterface successInterface) {
        File file = new File(folder);

        boolean var = false;
        if (!file.exists())
            var = file.mkdir();

        System.out.println("" + var);

        final String filename = file.toString() + "/" + "test.csv";

        new Thread() {
            public void run() {
                try {
                    FileWriter fw = new FileWriter(filename);
                    fw.append("[");
                    for (int i = 0; i < yourEntities.size(); i++) {
                        if (i == yourEntities.size()-1)
                            fw.append(yourEntities.get(i).toJSON());
                        else
                            fw.append(yourEntities.get(i).toJSON() + ",\n");
                    }
                    fw.append("]");
                    fw.flush();
                    fw.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public static String fromArchive() throws IOException {
        String csvFilename = Environment.getExternalStorageDirectory() + "/Archives" + "/test.csv";
        FileInputStream fIn = new FileInputStream(csvFilename);
        BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
        String aDataRow = "";
        String aBuffer = "";


        while ((aDataRow = myReader.readLine()) != null) {
            aBuffer += aDataRow + "\n";
        }
        myReader.close();

        return aBuffer.replace("\n", "");
    }


    public static void requestPermission(final Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 23
                );
            }
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23
                );
            }
        }
    }
}
