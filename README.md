# BitFrames
Frame of Bitmaps


<a href='https://play.google.com/store/apps/details?id=proj.me.bitframedemo&utm_source=global_co&utm_medium=prtnr&utm_content=Mar2515&utm_campaign=PartBadge&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width='232' height='90'/></a>







![logo](https://github.com/deepaktwr/BitFrames/blob/master/IMG_3055.jpg)                                                                                                                                   ![logo](https://github.com/deepaktwr/BitFrames/blob/master/IMG_3056.jpg)



You may find a working implementation in /app


### Description
ViewFrame takes set of image links (local or network based) and will frame them based on their dimensions.The container may contain 1, 2, 3 or max 4 images and they will occupy space based on their dimensions.The image count with greater than 4 will show the overflow number.

You may shuffle and frames by setting different primary and secondary count values and setting 
```xml
frame:shouldSortImages
```
to true.

Palette colors has been used to set background of the image.Inverse color and mixed color of all the images used to create divider color of the container


# Usage


### Add a widget to the layout file-

```xml
<proj.me.bitframe.ViewFrame
  android:id="@+id/view_frame"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"/>
```


by default the width and height has been set as 192 dp and 256 dp.

```xml
frame:max_container_width="@dimen/d_192"
frame:max_container_height="@dimen/d_256"
```
you may alter these or can set it from ViewFrame's method:

```java
public void setFrameDimensions(float minFrameWidth, float minFrameHeight, float maxContainerWidth, float maxContainerHeight);
```
passing 0's to all will set width and height of the frame as 99.96% 0f widthPixelsOfDevice and same in height if it's not greater than widthPixelsOfDevice.


### Make frames based of local path or links to the images:

```java
viewFrame.showBitFrame(beanImageList, callback, frameType);
```

##### beanImageList :
the list of image uris, comments, primary and secondary count.
it can be a list of @BeanImages if you don't have image dimensions and colors otherwise you can pass @BeanBitFrame with dimensions and colors to the image to show palette* colors until they load.

#### callback:
gives you the all image dimensions , colors in that frame(you might ignore it and pass it null if you don't want any callback)
it'll also provide you mixed and inverse colors to the framed images.

#### frameType:
Provide frame type of UNFRAMED if you are passing @BeanImages which don't have any image dimensions or colors
otherwise pass FRAMED in case you are passing @BeanBitFrame with image dimensions and colors


### *_Attributes(optional):_*

```xml
frame:minFrameWidth
```
the minimum width of the image in the frame(howevere it'll calculate it as per other images and their dimensions)

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
you may provide add in the layout i.e if sapce remains then it'll show add a new image button in the container itself(you'll have it in callback)

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
if true, frame may varry it's width and height as image dimensions.(but always be within maxContainerWidth and maxContainerHeight)

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
if true, it'll sort the image based on primary and secondary count provided

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





## Include with android project as gradle dependency:

```code
repositories {
    jcenter()
}
dependencies{
    compile 'com.github.deepaktwr:bitframe:0.1.3'
}
```




# Dependencies*:

this library usage two gradle dependencies:
```code
dependencies {
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.android.support:palette-v7:23.2.1'
}
```

which has been included in the library.




### Note :
include
```code
dataBinding{
        enabled = true;
   }
```

into your gradle as library uses databinding.

**_*_** *_the library have dependencies of picasso and palette to load images and fetch palette.please don't include these depedencies into your own build.gradle, you may use them directly from the library._*
