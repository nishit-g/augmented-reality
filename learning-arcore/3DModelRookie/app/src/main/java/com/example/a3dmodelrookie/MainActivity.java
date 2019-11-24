package com.example.a3dmodelrookie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArFragment arFragment;
    private final static int Truck_Renderable = 1;
    private final static int Knife_Renderable = 2;

    private ModelRenderable truckRenderable;
    private ModelRenderable knifeRenderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);

        ModelLoader modelLoader = new ModelLoader(this);
        modelLoader.loadModel(Truck_Renderable, R.raw.model);
        modelLoader.loadModel(Knife_Renderable, R.raw.knife);

        Button one = findViewById(R.id.button1);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
                    placeObject(hitResult.createAnchor(),one.getId());
                }));
            }
        });
        Button two = findViewById(R.id.button2);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
                    placeObject(hitResult.createAnchor(),two.getId());
                }));
            }
        });

    }

    private void placeObject(Anchor anchor, int id) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        if(id==R.id.button1){
            TransformableNode knife = new TransformableNode(arFragment.getTransformationSystem());
            knife.setParent(anchorNode);
            knife.setRenderable(knifeRenderable);
            knife.select();
        }
        else if(id == R.id.button2){
            TransformableNode truck = new TransformableNode(arFragment.getTransformationSystem());
            truck.setParent(anchorNode);
            truck.setRenderable(truckRenderable);
            truck.select();
        }
    }


    void setRenderable(int id, ModelRenderable renderable) {
        if (id == Truck_Renderable) {
            this.truckRenderable = renderable;
        } else {
            this.knifeRenderable = renderable;
        }
    }

    void onException(int id, Throwable throwable) {
        Toast toast = Toast.makeText(this, "Unable to load renderable: " + id, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        Log.e(TAG, "Unable to load renderable", throwable);
    }
}
