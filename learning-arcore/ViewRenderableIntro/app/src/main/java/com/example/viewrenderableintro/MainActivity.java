package com.example.viewrenderableintro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ViewRenderable sample1;
    private ArSceneView sceneView;
    private ArFragment arFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(this::renderView);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        arFragment.setOnTapArPlaneListener(this::onPlaneTap);
    }

    private void onPlaneTap(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        Anchor anchor = hitResult.createAnchor();

        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());


        CompletableFuture future = ViewRenderable.builder().setView(this, R.layout.portfolio)
                .build().thenAccept(renderable -> sample1 = renderable);
        if(sample1==null){
            Toast.makeText(this, "Renderable : Null", Toast.LENGTH_LONG).show();
        }
        anchorNode.setRenderable(sample1);
        anchorNode.setLocalScale(new Vector3(0.5f, 0.5f, 0.5f));
    }

    private void renderView(View view) {
        Toast toast = Toast.makeText(this, "Pressed", Toast.LENGTH_LONG);
        toast.show();

        arFragment.setOnTapArPlaneListener(this::onPlaneTap);

        CompletableFuture future = ViewRenderable.builder().setView(this, R.layout.portfolio)
                .build().thenAccept(renderable -> sample1 = renderable);

//        CompletableFuture.allOf(future).handle((notUsed,throwable) ->{
//
//                if(throwable!=null){
//                    Log.d("Main Acitivity", "Unable to load renderable", throwable);
//                    return null;
//                }
//                try{
//                    sample1 = future.get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            return null;
//        });

        Node base = new Node();
        base.setRenderable(sample1);
        base.setLocalScale(new Vector3(0.5f, 0.5f, 0.5f));

    }
}
