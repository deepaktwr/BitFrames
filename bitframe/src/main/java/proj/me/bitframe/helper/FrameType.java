package proj.me.bitframe.helper;

/**
 * Created by root on 13/9/16.
 * at first UNFRAMED_LOCAL should be used for better bitmap handling to process local images
 * UNFRAMED_LOCAL type for all unframed local uri's, use it for local images of higher resolution
 * UNFRAMED_MIXED type for mixed type of image uri's local as well as server.it uses piccaso to load images
 * FRAMED_MIXED you have all the details about the images
 */

public enum FrameType {
    UNFRAMED,
    FRAMED
}
