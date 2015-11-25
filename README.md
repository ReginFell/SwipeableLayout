
SwipeableLayout
================

Library to use swipe to close on your Layout.

## Demo

[Simple Transparent Activity (Video)](https://www.youtube.com/watch?v=bY_j41duY0E&feature=youtu.be)

[Transparent Activity with PagerView (Video)](https://www.youtube.com/watch?v=BlwmiTlm8Ro)

## Requirements

SwipeableLayout supports Android 4.0.0 (Ice Cream Sandwich) and later. 

## Gradle Dependency (jCenter)

If you are building with Gradle, simply add the following line to the `dependencies` section of your `build.gradle` file:

```groovy
compile 'ua.zabelnikiov:swipeLayout:0.7'
```
[ ![Download](https://api.bintray.com/packages/reginfell/ua.zabelnikov/swipeLayout/images/download.svg) ](https://bintray.com/reginfell/ua.zabelnikov/swipeLayout/_latestVersion)

Getting started
==========

It's very easy, just add SwipeableLayout to your xml

```xml

    <ua.zabelnikov.swipelayout.layout.frame.SwipeableLayout
        android:id="@+id/swipeableLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

You can add some view to your SwipeableLayout

```xml
    <ua.zabelnikov.swipelayout.layout.frame.SwipeableLayout
        android:id="@+id/swipeableLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ImageView
            android:id="@+id/imageView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scaleType="centerCrop" />

    </ua.zabelnikov.swipelayout.layout.frame.SwipeableLayout>
```

If you need listener, in your code add: 

```java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwipeableLayout swipeableLayout = (SwipeableLayout) findViewById(R.id.swipeableLayout);

        swipeableLayout.setOnLayoutPercentageChangeListener(new OnLayoutPercentageChangeListener() {
            //OnLayoutPercentageChange return value from 0 to 1,
            //where 0 - view not swiped, 1 - view swiped to action
    
            @Override
            public void percentageY(float percentage) {
             
            }

            @Override
            public void percentageX(float percentage) {
                
            }
        });
        swipeableLayout.setOnSwipedListener(new OnLayoutSwipedListener() {
            @Override
            public void onLayoutSwiped() {
                //Do some action, when view was swiped. For example, you can close activity
            }
        });
    }

```

## List of supported attributes:

```xml
     <ua.zabelnikov.swipelayout.layout.frame.SwipeableLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:swipeSpeed="3" 
        app:swipeOrientation="leftToRight"/>
```

## Changelog

[Changelog](https://github.com/ReginFell/SwipeableLayout/wiki/Changelog)

## License:

```
The MIT License (MIT)

Copyright (c) 2015 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
