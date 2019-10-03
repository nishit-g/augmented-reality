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