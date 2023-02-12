# FileCreateBroadcastReceiver
### Library version and jitpack repo: https://jitpack.io/#farhanarnob/FileCreateBroadcastReceiver

## How to use:

#### In project level gradle:


```groovy
buildscript {

    dependencies {
    
        classpath "org.jacoco:org.jacoco.core:0.8.5" // You can use the latest version 
        
    }
   
}

allprojects {
  repositories {
    maven { url "https://jitpack.io" }
  }
}
```




#### In app level gradle:

```groovy
apply plugin: 'jacoco'

jacoco {

    toolVersion = "0.8.5"            // You can use the latest version instead of 0.8.5
    
}

android {

    compileSdkVersion 33
    
    defaultConfig {
    
        multiDexEnabled true
        
    }
    
    buildTypes {
    
        debug {
        
            applicationIdSuffix ".debug"
            
            testCoverageEnabled true
            
        }
        
    }
    
}

dependencies {

    implementation 'com.android.support:multidex:1.0.3'
    
    implementation 'com.github.farhanarnob:FileCreateBroadcastReceiver:1.5'
    
} 
```


#### In AndroidManifest
```xml
  <!-- Without this folders will be inaccessible in Android-11 and above devices -->
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />

    <!-- Without this entry storage-permission entry will not be visible under app-info permissions list Android-10 and below -->
<uses-permission
android:name="android.permission.WRITE_EXTERNAL_STORAGE"
android:maxSdkVersion="29"
tools:ignore="ScopedStorage"/>


<application android:requestLegacyExternalStorage="true">
    </application>

```


  
  
  #### In Main Activity
  
```java
  private final FileBroadcastReceiver fileBroadcastReceiver = new FileBroadcastReceiver();

  protected void onCreate(Bundle savedInstanceState) {
    //        Check permission
    PermissionUtil.INSTANCE.requestPermission(this);
    if(PermissionUtil.INSTANCE.getPermissions().length == 0){
      FileManager.INSTANCE.writeFile(this, FileManager.INSTANCE.createFile(this));
    }
    registerReceiver(fileBroadcastReceiver,
            new IntentFilter(CustomBroadcastReceiverName.com_context_FINISH_TESTING.getStringName()));
  }


  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode != RESULT_CANCELED){
      PermissionUtil.INSTANCE.checkPermissionResult(this, requestCode, permissions, grantResults);
    }
  }
  

  protected void onStop() {
    super.onStop();
    if( PermissionUtil.INSTANCE.getPermissionToWriteAccepted()){
      File file = FileManager.INSTANCE.createFile(this);
      if(file!= null) {
        FileManager.INSTANCE.writeFile(this,file);
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unregisterReceiver(fileBroadcastReceiver);
  }
```
#### Storage location

```xml
/storage/emulated/0/Download/Coverage.ec
```

  


