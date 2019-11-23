package com.example.a3dmodelrookie;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.rendering.ModelRenderable;

import java.lang.ref.WeakReference;
import java.util.concurrent.CompletableFuture;

class ModelLoader extends AppCompatActivity {

    private static final String TAG = "ModelLoader";
    private WeakReference<MainActivity> owner;

    ModelLoader(MainActivity owner){
        this.owner = new WeakReference<>(owner);
    }

    boolean loadModel(int id , int resourceId){
        MainActivity activity = owner.get();
        if(activity==null){
            Log.d(TAG, "Activity is null, cannot load the model");
            return false;
        }

        CompletableFuture<ModelRenderable> model = ModelRenderable.builder()
                .setSource(owner.get(), resourceId)
                .build()
                .thenApply(renderable -> this.setRenderable(id,renderable))
                .exceptionally(throwable -> this.onException(id,throwable));

        return model!=null;
    }


    ModelRenderable onException(int id, Throwable throwable) {
        MainActivity activity = owner.get();
        if (activity != null) {
            activity.onException(id, throwable);
        }
        return null;
    }

    ModelRenderable setRenderable(int id, ModelRenderable modelRenderable) {
        MainActivity activity = owner.get();
        if (activity != null) {
            activity.setRenderable(id, modelRenderable);
        }
        return modelRenderable;
    }

}
