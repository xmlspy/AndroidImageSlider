package com.daimajia.slider.library.SliderTypes;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.daimajia.slider.library.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.MatrixDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;

/**
 * When you want to make your own slider view, you must extends from this class.
 * BaseSliderView provides some useful methods.
 * I provide two example: {@link com.daimajia.slider.library.SliderTypes.DefaultSliderView} and
 * {@link com.daimajia.slider.library.SliderTypes.TextSliderView}
 * if you want to show progressbar, you just need to set a progressbar id as @+id/loading_bar.
 */
public abstract class BaseSliderView {

    protected Context mContext;

    private Bundle mBundle;

    /**
     * Error place holder image.
     */
    private int mErrorPlaceHolderRes;

    /**
     * Empty imageView placeholder.
     */
    private int mEmptyPlaceHolderRes;

    private String mUrl;

    private String mSmallImageUrl;

    private Integer mPlaceHolderResId;

    protected OnSliderClickListener mOnSliderClickListener;

    private boolean mErrorDisappear;

    private ImageLoadListener mLoadListener;

    private String mDescription;

    /**
     * Scale type of the image.
     */
    private ScaleType mScaleType = ScaleType.FIT_XY;

    /**
     * Options for scaling the child bounds to the parent bounds.
     * <p>
     * Similar to {@link android.widget.ImageView.ScaleType}, but ScaleType.MATRIX is not supported.
     * To use matrix scaling, use a {@link MatrixDrawable}. An additional scale type (FOCUS_CROP) is
     * provided.
     * <p>
     * Note: The enum values should be in sync with the scaleType attribute values in attrs.xml
     */
    public enum ScaleType {

        /**
         * Scales width and height independently, so that the child matches the parent exactly.
         * This may change the aspect ratio of the child.
         */
        FIT_XY,

        /**
         * Scales the child so that it fits entirely inside the parent. At least one dimension (width or
         * height) will fit exactly. Aspect ratio is preserved.
         * Child is aligned to the top-left corner of the parent.
         */
        FIT_START,

        /**
         * Scales the child so that it fits entirely inside the parent. At least one dimension (width or
         * height) will fit exactly. Aspect ratio is preserved.
         * Child is centered within the parent's bounds.
         */
        FIT_CENTER,

        /**
         * Scales the child so that it fits entirely inside the parent. At least one dimension (width or
         * height) will fit exactly. Aspect ratio is preserved.
         * Child is aligned to the bottom-right corner of the parent.
         */
        FIT_END,

        /**
         * Performs no scaling.
         * Child is centered within parent's bounds.
         */
        CENTER,

        /**
         * Scales the child so that it fits entirely inside the parent. Unlike FIT_CENTER, if the child
         * is smaller, no up-scaling will be performed. Aspect ratio is preserved.
         * Child is centered within parent's bounds.
         */
        CENTER_INSIDE,

        /**
         * Scales the child so that both dimensions will be greater than or equal to the corresponding
         * dimension of the parent. At least one dimension (width or height) will fit exactly.
         * Child is centered within parent's bounds.
         */
        CENTER_CROP,

        /**
         * Scales the child so that both dimensions will be greater than or equal to the corresponding
         * dimension of the parent. At least one dimension (width or height) will fit exactly.
         * The child's focus point will be centered within the parent's bounds as much as possible
         * without leaving empty space.
         * It is guaranteed that the focus point will be visible and centered as much as possible.
         * If the focus point is set to (0.5f, 0.5f), result will be equivalent to CENTER_CROP.
         */
        FOCUS_CROP
    }

    protected BaseSliderView(Context context) {
        mContext = context;
    }

    /**
     * the placeholder image when loading image from url or file.
     * @param resId Image resource id
     * @return
     */
    public BaseSliderView empty(int resId){
        mEmptyPlaceHolderRes = resId;
        return this;
    }

    /**
     * determine whether remove the image which failed to download or load from file
     * @param disappear
     * @return
     */
    public BaseSliderView errorDisappear(boolean disappear){
        mErrorDisappear = disappear;
        return this;
    }

    /**
     * if you set errorDisappear false, this will set a error placeholder image.
     * @param resId image resource id
     * @return
     */
    public BaseSliderView error(int resId){
        mErrorPlaceHolderRes = resId;
        return this;
    }

    /**
     * the description of a slider image.
     * @param description
     * @return
     */
    public BaseSliderView description(String description){
        mDescription = description;
        return this;
    }

    /**
     * set a url as a image that preparing to load, http://frescolib.org/docs/supported-uris.html#_
     * @param url
     * @return
     */
    public BaseSliderView image(String url){
        mUrl = url;
        return this;
    }

    /**
     * set a url as a image that preparing to load, http://frescolib.org/docs/supported-uris.html#_
     * @param smallImageUrl
     * @return
     */
    public BaseSliderView imageLowRes(String smallImageUrl){
        mSmallImageUrl = smallImageUrl;
        return this;
    }

    /**
     * set a url as a image that preparing to load, http://frescolib.org/docs/supported-uris.html#_
     * @param idDrawable
     * @return
     */
    public BaseSliderView imagePlaceHolder(Integer idDrawable){
        mPlaceHolderResId = idDrawable;
        return this;
    }



    /**
     * lets users add a bundle of additional information
     * @param bundle
     * @return
     */
    public BaseSliderView bundle(Bundle bundle){
        mBundle = bundle;
        return this;
    }

    public String getUrl(){
        return mUrl;
    }

    public boolean isErrorDisappear(){
        return mErrorDisappear;
    }

    public int getEmpty(){
        return mEmptyPlaceHolderRes;
    }

    public int getError(){
        return mErrorPlaceHolderRes;
    }

    public String getDescription(){
        return mDescription;
    }

    public Context getContext(){
        return mContext;
    }

    /**
     * set a slider image click listener
     * @param l
     * @return
     */
    public BaseSliderView setOnSliderClickListener(OnSliderClickListener l){
        mOnSliderClickListener = l;
        return this;
    }

    /**
     * When you want to implement your own slider view, please call this method in the end in `getView()` method
     * @param v the whole view
     * @param targetImageView where to place image
     */
    protected void bindEventAndShow(final View v, SimpleDraweeView targetImageView){
        final BaseSliderView me = this;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(mOnSliderClickListener != null){
                mOnSliderClickListener.onSliderClick(me);
            }
            }
        });

        if (targetImageView == null)
            return;

        if (mLoadListener != null) {
            mLoadListener.onStart(me);
        }

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if(v.findViewById(R.id.loading_bar) != null){
                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
                }
                if (imageInfo == null) {
                    return;
                }
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                if(v.findViewById(R.id.loading_bar) != null){
                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
                }
            }
        };

        GenericDraweeHierarchy hierarchy = targetImageView.getHierarchy();


        DraweeController controller = null;
        if(mUrl!=null && mSmallImageUrl == null){
            controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                    .setImageRequest(ImageRequest.fromUri(mUrl))
                    .build();
        }else if(mUrl!=null && mSmallImageUrl != null){
            controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener).setLowResImageRequest(ImageRequest.fromUri(mSmallImageUrl))
                    .setImageRequest(ImageRequest.fromUri(mUrl))
                    .build();
        }else{
            return;
        }

        switch (mScaleType){
            case FIT_XY:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                break;
            case FIT_START:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_START);
                break;
            case FIT_CENTER:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
                break;
            case FIT_END:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_END);
                break;
            case CENTER:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER);
                break;
            case CENTER_INSIDE:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
                break;
            case CENTER_CROP:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
                break;
            case FOCUS_CROP:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
                break;
        }

        if(mPlaceHolderResId!=null){
            hierarchy.setPlaceholderImage(mPlaceHolderResId);
        }

        targetImageView.setHierarchy(hierarchy);
        targetImageView.setController(controller);

   }



    public BaseSliderView setScaleType(ScaleType type){
        mScaleType = type;
        return this;
    }

    public ScaleType getScaleType(){
        return mScaleType;
    }

    /**
     * the extended class have to implement getView(), which is called by the adapter,
     * every extended class response to render their own view.
     * @return
     */
    public abstract View getView();

    /**
     * set a listener to get a message , if load error.
     * @param l
     */
    public void setOnImageLoadListener(ImageLoadListener l){
        mLoadListener = l;
    }

    public interface OnSliderClickListener {
        public void onSliderClick(BaseSliderView slider);
    }

    /**
     * when you have some extra information, please put it in this bundle.
     * @return
     */
    public Bundle getBundle(){
        return mBundle;
    }

    public interface ImageLoadListener{
        public void onStart(BaseSliderView target);
        public void onEnd(boolean result,BaseSliderView target);
    }
}
