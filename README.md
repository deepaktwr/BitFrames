# BitFrames
Frame of Bitmaps

![alt frame3](http://oi68.tinypic.com/30a9mpu.jpg)                                                                                                                                   ![alt framen](http://oi66.tinypic.com/n4caya.jpg)



You may find a working implementation in /app


#Usage


###Add a widget to the layout file-

```xml
<proj.me.bitframe.ViewFrame
  android:id="@+id/view_frame"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"/>
```


by default the width and height of the frame will be set as widthPixelsOfDevice - 50, heightPixelsOfDevice - 500.
but you may explicitly give these as widget attributes:

```xml
frame:max_container_width="@dimen/d_192"
frame:max_container_height="@dimen/d_256"
```

or can set it from ViewFrame's method:

```java
public void setFrameDimensions(float minFrameWidth, float minFrameHeight, float maxContainerWidth, float maxContainerHeight);
```



###Make frames based of local path or links to the images:

```java
viewFrame.showBitFrame(beanImageList, callback, frameType);
```

#####beanImageList :
the list of image uris, comments, primary and secondary count.
it can be a list of @BeanImages if you don't have image dimension and colors otherwise you can pass @BeanBitFrame with dimentions and colors to the image to show pallet colors until they load.

####callback:
gives you the all image dimentions , colors in that frame(you might ignore it and pass it null if you don't want any callback)
it'll also provide you mixed and inverse colors to the framed images.

####frameType:
Provide frame type of UNFRAMED if you are passing @BeanImages which don't have any image dimentions or colors
otherwise pass FRAMED in case you are passing @BeanBitFrame with image dimention and colors




### Attributes:

```xml
frame:minFrameWidth
```
the minimum width to the image in the frame(howevere it'll calculate it as per other images and their dimentions)

```xml
frame:minFrameHeight
```
the minimum height to the image in the frame

```xml
frame:maxContainerWidth
```
the frame width which will contain all images

```xml
frame:maxContainerHeight
```
the frame height which will contain all images

```xml
frame:maxFrameCount
```
the max frame count to show(it'll show +N if images exceeds that count)

```xml
frame:isAddInLayout
```
you may provide add in the layout i.e if sapce remains then it'll show add a new image button in the containe itself(you'll have it in callback)

```xml
frame:minAddRatio
```
the add buton ratio with the images

```xml
frame:shouldShowComment
```
you may provide comment to the image and pass this as true to show that

```xml
frame:commentTransparencyPercent
```
it'll set the transparency of the comment background related to image between 1 to 100

```xml
frame:hasScroll
```
shoud container has scroll as it's parent

```xml
frame:hasFixedDimensions
```
if true, frame may varry it's width and height as image dimentions.(but always be within maxContainerWidth and maxContainerHeight)

```xml
frame:shouldStoreImages
```
should cache the images when using picasso*

```xml
frame:shouldRecycleBitmaps
```
if true, it'll recycle all the bitmaps after framing done

```xml
frame:shouldSortImages
```
it true, it'll sort the image based on primary and secondary count provided

```xml
frame:colorCombination
```
VIBRANT_TO_MUTED or MUTED_TO_VIBRANT i.e which color combination will be priorties to set background of the image

```xml
frame:errorDrawable
```
in case image failed to load

```xml
frame:imageScaleType
```
default is centerInside





##Include with android project as grdle dependency:

```code
compile 'com.github.deepaktwr:bitframe:0.1.1'
```




#Dependencies*:

this library usage two gradle dependencies:
```code
dependencies {
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.android.support:palette-v7:23.2.1'
}
```

which has been included in the library.




###Note :
include
```code
dataBinding{
        enabled = true;
   }
```

into your gradles as library uses databinding.

##*
the library have dependencies of picasso and pallete to load images and fetch pallets.please don't include these depedencies into your own build.gradle, you may use them directly from the library.
