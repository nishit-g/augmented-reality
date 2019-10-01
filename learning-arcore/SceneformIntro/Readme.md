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

