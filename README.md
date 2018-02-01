# SimpleAd
Fullscreen banner using webView

# Implementation

Add this to your top-level gradle file:

```groovy
repositories{

  ...
  maven { url 'https://jitpack.io' }
  
}
```

Then add dependency into your build.gradle file:

```groovy
implementation 'com.github.Skullper:SimpleAd:1.1'
```

If you have some issues with support libraries you can exclude support group from this dependency:

```groovy
implementation ('com.github.Skullper:SimpleAd:1.1'){
        exclude group: 'com.android.support', module: 'appcompat-v7'
}
```
    
# How to use   

**1. Add permission**

You need to add ```<uses-permission android:name="android.permission.INTERNET"/>``` into your manifest file. Without this permission ad will not be loaded

**2. Init banner view**

To get your ad you need to pass your app's package name:

```java 
BannerView bannerView = BannerView.newInstance(getPackageName());
```

**3. Launch ad**

To show an ad you just need to add this:

```java
bannerView.show(getFragmentManager(), "YOUR_TAG");
```

If you need more info u can try app project

That's all!
