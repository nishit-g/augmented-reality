package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SkeletonNode;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    // Firstly, have all the AR features in you app by creating a
    // fragment in xml file.
    private ArFragment arFragment;

    // To load a 3D model you need to have ModelLoader class
    private ModelLoader modelLoader;

    // To render the 3D model
    private ModelRenderable carRenderable;
    private ModelRenderable andyRenderable;

    // 3D model will be attached to this node
    private AnchorNode node;

    // TODO
    private SkeletonNode andy;

    // Controls animation playback.
    private ModelAnimator animator;
    private AnimationData andyData;

    // Index of the current animation playing.
    private int nextAnimation;

    // The UI to play next animation.
    private Button animationButton;


    // For having a reference for the callback of the method loadModel()
    private static final int CAR_RENDERABLE = 1;
    private static final int ANDY_RENDERABLE = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get that ArFragment
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);

        modelLoader = new ModelLoader(this);

        modelLoader.loadModel(CAR_RENDERABLE, R.raw.dodge);
        modelLoader.loadModel(ANDY_RENDERABLE, R.raw.andy);


        // When a plane is tapped, the model is placed on an Anchor node anchored to the plane.
        arFragment.setOnTapArPlaneListener(this::onPlaneTap);

        animationButton = findViewById(R.id.animate);
        animationButton.setOnClickListener(this::onPlayAnimation);


    }

    private void onPlayAnimation(View view) {
        Toast toaste = Toast.makeText(this, "Button Pressed", Toast.LENGTH_LONG);
        toaste.setGravity(Gravity.CENTER_VERTICAL,0,24);
        toaste.show();
        if(animator==null || !animator.isRunning()){
            AnimationData data = andyRenderable.getAnimationData(nextAnimation);
            nextAnimation=(nextAnimation+1)%andyRenderable.getAnimationDataCount();
            animator = new ModelAnimator(data,andyRenderable);

            Toast toast = Toast.makeText(this, data.getName(), Toast.LENGTH_SHORT);
            Log.d(
                    TAG,
                    String.format(
                            "Starting animation %s - %d ms long", data.getName(), data.getDurationMs()));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            animator.start();
        }
    }

    private void onPlaneTap(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

        if(carRenderable==null){
            return;
        }

        Anchor anchor = hitResult.createAnchor();

            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
        TransformableNode andyNode = new TransformableNode(arFragment.getTransformationSystem());

        andyNode.setParent(anchorNode);
        andyNode.setRenderable(andyRenderable);
        andyNode.select();

    }

    void setRenderable(int id, ModelRenderable renderable) {
        if (id == CAR_RENDERABLE) {
            this.carRenderable = renderable;
        }else{
            this.andyRenderable=renderable;
        }
    }

    void onException(int id, Throwable throwable) {
        Toast toast = Toast.makeText(this, "Unable to load renderable: " + id, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        Log.e(TAG, "Unable to load andy renderable", throwable);
    }
}
