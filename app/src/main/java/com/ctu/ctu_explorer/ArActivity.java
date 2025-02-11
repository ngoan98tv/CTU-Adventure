package com.ctu.ctu_explorer;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.service.voice.VoiceInteractionService;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.text.method.ScrollingMovementMethod;
import android.view.PixelCopy;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ArActivity extends AppCompatActivity {
    private ArFragment fragment;
    private PointerDrawable pointer = new PointerDrawable();
    private boolean isTracking;
    private boolean isHitting;
    private boolean isModelAdded;
    private ModelLoader modelLoader;
    private Uri modelUri = Uri.parse("andy_dance.sfb");
    private TextView headerText;
    private String detectedBuilding;
    private Buildings buildings;
    private Integer triedCount;
    private ImageButton reloadBtn;
    private CountDownTimer timer;
    private TextView subtitle;
    private TextToSpeech tts;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            subtitle.scrollBy(0,46);
            timerHandler.postDelayed(this, 4600);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        ImageButton backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(view -> finish());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startDetectBuilding());

        reloadBtn = findViewById(R.id.reload_btn);
        reloadBtn.setOnClickListener(view -> startDetectBuilding());
        headerText = findViewById(R.id.ar_header_text);
        subtitle = findViewById(R.id.ar_subtitle);
        buildings = new Buildings(this);

        subtitle.setMovementMethod(new ScrollingMovementMethod());
        subtitle.post(() -> subtitle.setSelected(true));

        fragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        assert fragment != null;
        fragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            fragment.onUpdate(frameTime);
            onUpdate();
        });

        modelLoader = new ModelLoader(new WeakReference<>(this));

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setSpeechRate((float) 0.9);
                tts.setPitch((float) 0.5);
                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {
                        runOnUiThread(() -> {
                            subtitle.setVisibility(View.VISIBLE);
                            timerHandler.postDelayed(timerRunnable, 4600);
                        });
                    }

                    @Override
                    public void onDone(String utteranceId) {
                        runOnUiThread(() -> {
                            subtitle.setVisibility(View.INVISIBLE);
                            timerHandler.removeCallbacks(timerRunnable);
                        });
                    }

                    @Override
                    public void onError(String utteranceId) {
                        runOnUiThread(() -> {
                            Toast.makeText(ArActivity.this, "Speech error!", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });
    }

    private void addObject(Uri model) {
        Frame frame = fragment.getArSceneView().getArFrame();
        android.graphics.Point pt = getScreenCenter();
        List<HitResult> hits;
        if (frame != null) {
            hits = frame.hitTest(pt.x, pt.y);
            for (HitResult hit : hits) {
                Trackable trackable = hit.getTrackable();
                if (trackable instanceof Plane &&
                        ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                    modelLoader.loadModel(hit.createAnchor(), model);
                    break;

                }
            }
        }
    }

    private void onUpdate() {
        boolean trackingChanged = updateTracking();
        View contentView = findViewById(android.R.id.content);
        if (trackingChanged) {
            if (isTracking) {
                contentView.getOverlay().add(pointer);
            } else {
                contentView.getOverlay().remove(pointer);
            }
            contentView.invalidate();
        }

        if (isTracking) {
            boolean hitTestChanged = updateHitTest();
            if (hitTestChanged) {
                pointer.setEnabled(isHitting);
                contentView.invalidate();
                if (isHitting && !isModelAdded) {
                    addObject(modelUri);
                    isModelAdded = true;
                }
            }
        }

        if (detectedBuilding == null && triedCount == null && timer == null) {
            ArSceneView view = fragment.getArSceneView();
            if (view.getHeight() > 0 && view.getWidth() > 0) {
                timer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long l) {}

                    @Override
                    public void onFinish() {
                        startDetectBuilding();
                    }
                };
                timer.start();
            }
        }
    }

    private boolean updateTracking() {
        Frame frame = fragment.getArSceneView().getArFrame();
        boolean wasTracking = isTracking;
        isTracking = frame != null &&
                frame.getCamera().getTrackingState() == TrackingState.TRACKING;
        return isTracking != wasTracking;
    }

    private boolean updateHitTest() {
        Frame frame = fragment.getArSceneView().getArFrame();
        android.graphics.Point pt = getScreenCenter();
        List<HitResult> hits;
        boolean wasHitting = isHitting;
        isHitting = false;
        if (frame != null) {
            hits = frame.hitTest(pt.x, pt.y);
            for (HitResult hit : hits) {
                Trackable trackable = hit.getTrackable();
                if (trackable instanceof Plane &&
                        ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                    isHitting = true;
                    break;
                }
            }
        }
        return wasHitting != isHitting;
    }

    public void startAnimation(TransformableNode node, ModelRenderable renderable){
        if(renderable==null || renderable.getAnimationDataCount() == 0) {
            return;
        }

        ModelAnimator animator = new ModelAnimator(renderable.getAnimationData(0), renderable);
        animator.start();
        node.setOnTapListener(
                (hitTestResult, motionEvent) -> {
                    togglePauseAndResume(animator);
                });
    }

    private android.graphics.Point getScreenCenter() {
        View vw = findViewById(android.R.id.content);
        return new android.graphics.Point(vw.getWidth()/2, vw.getHeight()/2);
    }

    public void onException(Throwable throwable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(throwable.getMessage())
                .setTitle("Codelab error!");
        AlertDialog dialog = builder.create();
        dialog.show();
        return;
    }

    public void addNodeToScene(Anchor anchor, ModelRenderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        fragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
        startAnimation(node, renderable);
    }

    public void togglePauseAndResume(ModelAnimator animator) {
        if (animator.isPaused()) {
            animator.resume();
        } else if (animator.isStarted()) {
            animator.pause();
        } else {
            animator.start();
        }
    }

    private void startDetectBuilding() {
        triedCount = 0;
        try {
            takePhoto();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void onBuildingDetected(String result) {
        detectedBuilding = result;
        headerText.setText(buildings.getNameByCode(result));
        reloadBtn.setVisibility(View.VISIBLE);
        startReading(buildings.getDescriptionByCode(result));
    }

    private  void detectBuilding(Bitmap image) {
        triedCount += 1;
        runOnUiThread(() -> headerText.setText("(" + triedCount +") Detecting..."));

        BuildingDetector detector = BuildingDetector.getInstance();
        detector.detect(image, result -> {
            if (result == null || result.isEmpty()) {
                CountDownTimer timer = new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long remainingMillis) {}

                    @Override
                    public void onFinish() {
                        takePhoto();
                    }
                };
                timer.start();
            } else {
                runOnUiThread(() -> onBuildingDetected(result));
            }
        });
    }

    private void takePhoto() {
        ArSceneView view = fragment.getArSceneView();

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();

        // Make the request to copy.
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                detectBuilding(bitmap);
            } else {
                Toast.makeText(this,"Failed to copyPixels: " + copyResult, Toast.LENGTH_LONG).show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }

    private void startReading(String text) {
        runOnUiThread(() -> {
            subtitle.setText(text);
        });
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "");
    }

}