package com.example.a3dmodelintro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private final int TRUCK_RENDERABLE = 1;
    private ModelRenderable truckRenderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get that fragment which provides the AR functionalities
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);


        // Load the model while the app is loading
        // and store the model in ModelRenderable
        ModelRenderable.builder()
                .setSource(this,R.raw.model)
                .build()
                .thenAccept(renderable -> truckRenderable=renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the model", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                            toast.show();
                            return null;
                        }
                        );



        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) ->{
                placeObject(hitResult.createAnchor());
        });

    }


    private void placeObject(Anchor anchor) {

        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        TransformableNode truck = new TransformableNode(arFragment.getTransformationSystem());
        truck.setParent(anchorNode);
        truck.setRenderable(truckRenderable);
        truck.select();
    }

}
