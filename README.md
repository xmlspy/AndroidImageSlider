# Android Image Slider [![Build Status](https://travis-ci.org/chan32167/AndroidImageSlider.svg)](https://travis-ci.org/chan32167/AndroidImageSlider)

This is based on daimajia's work. I changed it to use frescolib, and fixed some memory leaks.
 
This is an amazing image slider for the Android platform. I decided to open source this because there is really not an attractive, convenient slider widget in Android.
 
You can easily load images from any resource using the URI. As it is based on frescolib, read more at http://frescolib.org/docs/supported-uris.html#_

And there are many kinds of amazing animations you can choose. :-D
 
## Demo
 
![](http://ww3.sinaimg.cn/mw690/610dc034jw1egzor66ojdg20950fknpe.gif)

[Download Apk](https://github.com/daimajia/AndroidImageSlider/releases/download/v1.0.8/demo-1.0.8.apk)

This is the old original demo from daimajia
 
## Usage

### Step 1

#### Gradle

```groovy
dependencies {
    	compile 'com.android.support:support-v4:+'
    	compile 'com.facebook.fresco:fresco:0.7.0+'
    	compile 'com.nineoldandroids:library:2.4.0'
    	compile 'com.amstronghuang.slider:library:1.1.3@aar'
}
```


#### Maven

```xml
<dependency>
    <groupId>com.facebook.fresco</groupId>
    <artifactId>fresco</artifactId>
    <version>0.7.0</version>
</dependency>
<dependency>
    <groupId>com.nineoldandroids</groupId>
    <artifactId>library</artifactId>
    <version>2.4.0</version>
</dependency>
<dependency>
    <groupId>com.amstronghuang.slider</groupId>
    <artifactId>library</artifactId>
    <version>1.1.3</version>
    <type>apklib</type>
</dependency>
```

### Step 2

Add permissions (if necessary) to your `AndroidManifest.xml`

```xml
<!-- if you want to load images from the internet -->
<uses-permission android:name="android.permission.INTERNET" /> 

<!-- if you want to load images from a file OR from the internet -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

**Note:** If you want to load images from the internet, you need both the `INTERNET` and `READ_EXTERNAL_STORAGE` permissions to allow files from the internet to be cached into local storage.

If you want to load images from drawable, then no additional permissions are necessary.

### Step 3

Add the Slider to your layout:
 
```java
<com.daimajia.slider.library.SliderLayout
        android:id="@+id/slider"
        android:layout_width="match_parent"
        android:layout_height="200dp"
/>
```        
 
There are some default indicators. If you want to use a provided indicator:
 
```java
<com.daimajia.slider.library.Indicators.PagerIndicator
        android:id="@+id/custom_indicator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center"
        />
```

[Code example](https://github.com/chan32167/AndroidImageSlider/blob/master/demo%2Fsrc%2Fmain%2Fjava%2Fcom%2Fdaimajia%2Fslider%2Fdemo%2FMainActivity.java)
 
====
 
## Advanced usage

Please visit [Wiki](https://github.com/daimajia/AndroidImageSlider/wiki)
 
## Thanks

- [FrescoLib](https://www.frescolib.org)
- [NineOldAndroids](https://github.com/JakeWharton/NineOldAndroids)
- [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)

##About me

If you get any problems when using this library, please feel free to [email me](mailto:chan32167@gmail.com).


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/chan32167/androidimageslider/trend.png)](https://bitdeli.com/free "Bitdeli Badge")