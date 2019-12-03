package com.example.animation;

import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.widget.Toast;

import com.google.ar.sceneform.rendering.ModelRenderable;

import java.lang.ref.WeakReference;
import java.util.concurrent.CompletableFuture;

class ModelLoader {

    private static final String TAG = "ModelLoader";


    private final SparseArray<CompletableFuture<ModelRenderable>> futureSet = new SparseArray<>();

    private final WeakReference<MainActivity> owner;

    ModelLoader(MainActivity owner) {
        this.owner = new WeakReference<MainActivity>(owner);
    }

    boolean loadModel(int id, int resourceId){
        MainActivity activity = owner.get();
        if (activity == null) {
            Log.d(TAG, "Activity is null.  Cannot load model.");
            return false;
        }

        CompletableFuture<ModelRenderable> future = ModelRenderable.builder()
                .setSource(owner.get(), resourceId)
                .build()
                .thenApply(renderable -> this.setRenderable(id, renderable))
                .exceptionally(throwable -> this.onException(id, throwable));


        Toast toast = Toast.makeText(owner.get(), "Model Loaded: " + id, Toast.LENGTH_LONG);

        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        if (future != null) {
            futureSet.put(id, future);
        }
        return future != null;

    }

    ModelRenderable onException(int id, Throwable throwable) {
        MainActivity activity = owner.get();
        if (activity != null) {
            activity.onException(id, throwable);
        }
        futureSet.remove(id);
        return null;
    }


    ModelRenderable setRenderable(int id, ModelRenderable modelRenderable) {
        MainActivity activity = owner.get();
        if (activity != null) {
            activity.setRenderable(id, modelRenderable);
        }
        futureSet.remove(id);
        return modelRenderable;
    }
}
