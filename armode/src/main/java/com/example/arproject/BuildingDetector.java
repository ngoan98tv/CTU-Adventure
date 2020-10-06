package com.example.arproject;

import android.graphics.Bitmap;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerLocalModel;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerOptions;

public class BuildingDetector {
    private static BuildingDetector instance = null;
    private ImageLabeler labeler;

    interface OnResultListener {
        void onResult(String result);
    }

    private BuildingDetector() {
        setUpLabeler();
    }

    public static BuildingDetector getInstance() {
        if (instance == null) instance = new BuildingDetector();
        return instance;
    }

    private void setUpLabeler() {
        String LOCAL_MODEL_PATH = "models/manifest.json";
        AutoMLImageLabelerLocalModel localModel = new AutoMLImageLabelerLocalModel.Builder()
                .setAssetFilePath(LOCAL_MODEL_PATH)
                .build();
        float THRESHOLD = 0.6f;
        AutoMLImageLabelerOptions labelerOptions =
                new AutoMLImageLabelerOptions.Builder(localModel)
                        .setConfidenceThreshold(THRESHOLD)
                        .build();
        this.labeler = ImageLabeling.getClient(labelerOptions);
    }

    public void detect(Bitmap bitmap, OnResultListener onResult) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        labeler.process(image)
                .addOnSuccessListener(labels -> {
                    if (labels.size() == 0) {
                        onResult.onResult(null);
                    } else {
                        String label = labels.get(0).getText();
                        onResult.onResult(label);
                    }
                })
                .addOnFailureListener(e -> {
                    onResult.onResult(null);
                });

    }
}
