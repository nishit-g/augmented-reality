# Initializing the project

* Android Studio --> New Project --> Basic Activity --> *API level 24*


# Configuring the project
* If your projects have API level less than 26 then add support for JAVA 8.
* Add dependency for Sceneform API and the Sceneform UX elements in `app/build.gradle` file : 

`implementation "com.google.ar.sceneform.ux:sceneform-ux:1.8.0"`

* Sync the project


# Baby Steps

* Add *ArFragment* to the `app/res/layout/content_main.xml` file replacing the `<TextView ....` portion. 

```
<fragment
   android:id="@+id/sceneform_fragment"
   android:name="com.google.ar.sceneform.ux.ArFragment"
   android:layout_width="match_parent"
   android:layout_height="match_parent" />
```

* Keep in mind the **id** of the `<fragment>` section --> sceneform_fragment


* Open `app/manifests/AndroidManifest.xml` and in the `<manifest>` section add these elements:

```
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera.ar" android:required="true" />
```

* Add the metadata in the `<application>` section :
```
<meta-data android:name="com.google.ar.core" android:value="required" />
```

* Now it's time to reference the `<fragment...` in out `MainActivity.java` file, so that we can use the AR functionalities.
* Start by creating a *private* variable of type `ArFragment`
```
private ArFragmemt fragment;
```

* use the below code to bind the fragment into our variable.
```
fragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
```

* Try running your app now !


# Adding a pointer 
#### We could use drag and drop to select one of the models and drag it onto the view. However, what seems to work best is to just look at where you want to place the model and tap it. This keeps your fingers out of the way so you can see better, and also makes it less cumbersome to hold the phone in the right place as dragging. 

#### To do this, we'll add a pointer in the form of an overlay. The overlay will always be centered on the screen, and when we take a picture of the scene later, the pointer will not be in the image.


* Create a new java class.
* New -> Java class -> Name it `PointerDrawable`
* make the superclass as `android.graphics.drawable.Drawable`
* Implement the methods to remove the error by clicking the red bulb.


### Adding fields and overriding the methods
* Add these fields in your `PointerDrawable` class
```
private final Paint paint = new Paint();
private boolean enabled;
```

* Add getter and setter for `enabled` variable.
* Right click -> Generate -> Getters and Setters -> Select `enabled` -> Click OK.

```
public boolean isEnabled() {
 return enabled;
}

public void setEnabled(boolean enabled) {
 this.enabled = enabled;
}
```


### Overriding the `draw()` method

This should be the final method
```
    @Override
    public void draw(@NonNull Canvas canvas) {
        float cx = canvas.getWidth()/2;
        float cy = canvas.getHeight()/2;
        if (enabled) {
            paint.setColor(Color.GREEN);
            canvas.drawCircle(cx, cy, 10, paint);
        } else {
            paint.setColor(Color.GRAY);
            canvas.drawText("X", cx, cy, paint);
        }
    }
```





# Controlling the pointer

We want to enable or disable the pointer based on the tracking state of the ARCore.

* Add the `PointerDrawable pointer` in `MainActivity.class`
* Add `boolean isTracking` - To know whether ArCore is in tracking state or not.
* Add `boolean isHitting` - This will let us know if the user is looking at plane detected by ArCore.

### Adding a listener
For updating the pointer we need to make ArCore's api call and for calling the api we need to add a listener.

We need to add a listener on ArSceneView's scene

For finding the ArSceneView :
```
fragment.getArSceneView().getScene()
```

After finding the scene add the listener to it.

```
fragment.getArSceneView().getScene().addOnUpdateListener(....)
```



* `frameTime` provides time information of current frame.


Finally the code should be 
```
fragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
 fragment.onUpdate(frameTime);
 onUpdate();
});
```

* `onUpdate()` is our method that we will be implementing.


### Implementing the `onUpdate()` method

An overlay is an extra layer that sits on top of a View (the "host view") which is drawn after all other content in that view (including children, if the view is a ViewGroup). Interaction with the overlay layer is done by adding and removing drawables. 


Here our pointer is an overlay.

And overlay lies on View, thus we need to get the view.

```
View contentView = findViewById(android.R.id.content);
```


What the above code does is it finds the root view of the activity.

The `android.R.id.content` ID value indicates the ViewGroup of the entire content area of an Activity.

We need to 
* update the tracking state. If ARCore is not tracking, remove the pointer until tracking is restored.
* if ARCore is tracking, check for the gaze of the user hitting a plane detected by ARCore and enable the pointer accordingly.

### Updating the tracking state
