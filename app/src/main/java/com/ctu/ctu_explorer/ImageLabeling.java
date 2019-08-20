package com.ctu.ctu_explorer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;


import java.util.List;

public class ImageLabeling extends AppCompatActivity {
    private Bitmap imageBitmap;
    private FirebaseVisionImageLabeler labeler;
    public static final String Data = "result";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    //private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Float THRESHOLD = 0.6f;
    private String LOCAL_MODEL_NAME = "automl_image_labeling_model";
    private String LOCAL_MODEL_PATH = "models/manifest.json";
    /**private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_image_labeling);
        setUpLabeler();
        dispatchTakePictureIntent();
    }

    private void setUpLabeler()
    {
        FirebaseLocalModel localModel = new FirebaseLocalModel.Builder(LOCAL_MODEL_NAME)
                .setAssetFilePath(LOCAL_MODEL_PATH)
                .build();
        FirebaseModelManager.getInstance().registerLocalModel(localModel);

        FirebaseVisionOnDeviceAutoMLImageLabelerOptions labelerOptions =
                new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder()
                        .setLocalModelName(LOCAL_MODEL_NAME)
                        .setConfidenceThreshold(THRESHOLD)
                        .build();
        try
        {
            this.labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(labelerOptions);
        }
        catch(FirebaseMLException e)
        {
            Log.d("Error", "Firebase");
        }

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void showDialog()
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Notification");
        b.setMessage("Image labeling failed. Do you want to change picture?");
        b.setCancelable(false);
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                //dispatchTakePictureIntent();
                imageBitmap = null;
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                finish();
            }
        });

        AlertDialog alert = b.create();
        alert.show();
    }

    private void labeling(Bitmap bitmap)
    {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        labeler.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                        // Task completed successfully
                        // ...
                        if (labels.size() == 0)
                        {
                            final Intent data = new Intent();
                            data.putExtra(Data, "Blank");
                            setResult(Activity.RESULT_OK, data);
                            finish();
                        }
                        else {
                            String label = labels.get(0).getText();
                            final Intent data = new Intent();
                            data.putExtra(Data, label);
                            setResult(Activity.RESULT_OK, data);
                            finish();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...
                        //showDialog();
                        //Log.d("Result", "Null");
                        final Intent data = new Intent();
                        data.putExtra(Data, e.getMessage());
                        setResult(Activity.RESULT_OK, data);
                        finish();

                    }
                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            labeling(imageBitmap);
        }
    }
}
