package com.bbcivil.toss.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 抛硬币的3D动画，该动画在每半个旋转周期调用接口里的方法，请求View切换View里的内容
 *
 * Created by qingtian on 2016/1/15.
 */
public class TossAnimation extends Animation {

    public static final int DIRECTION_NONE = 0; // 在该方向上不变化
    public static final int DIRECTION_CLOCKWISE = 1; // 顺时针
    public static final int DIRECTION_ABTUCCLOCKWISE = -1; // 逆时针

    public static final int RESULT_FRONT = 1; // 正面
    public static final int RESULT_REVERSE = -1; // 反面

    /**
     * 圈数
     */
    private int mCircleCount;
    /**
     * x轴旋转方向
     */
    private int mXAxisDirection;
    /**
     * y轴旋转方向
     */
    private int mYAxisDirection;
    /**
     * z轴旋转方向
     */
    private int mZAxisDirection;
    /**
     * 抛硬币的结果
     */
    private int mResult;

    // 总共需要转动的度数
    private int mTotalAngle;
    // 当前ImageView显示的drawable的序号
    private int mCurrentResult = -1;

    private Camera mCamera;

    private int mWidth;
    private int mHeight;

    public TossAnimation(int circleCount, int xAxisDirection, int yAxisDirection, int zAxisDirection, int result) {
        this.mCircleCount = circleCount;
        this.mXAxisDirection = xAxisDirection;
        this.mYAxisDirection = yAxisDirection;
        this.mZAxisDirection = zAxisDirection;
        this.mResult = result;

        mTotalAngle = 360 * mCircleCount;
        mCamera = new Camera();
    }

    private TossAnimationListener mTossAnimationListener;

    public void setTossAnimationListener(TossAnimationListener mTossAnimationListener) {
        this.mTossAnimationListener = mTossAnimationListener;
        setAnimationListener(mTossAnimationListener);
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mWidth = width;
        mHeight = height;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        // 在当前周期里的角度数
        int degreeInCircle = ((int) (interpolatedTime * mTotalAngle)) % 360;

        // 改变ImageView里的Drawable
        if (degreeInCircle > 90 && degreeInCircle < 270) {
            if (mCurrentResult != -mResult) {
                mCurrentResult = -mResult;
                // 调用接口，改变ImageView的Drawable
                if (mTossAnimationListener != null) {
                    mTossAnimationListener.onDrawableChange(mCurrentResult, this);
                }
            }
        } else {
            if (mCurrentResult != mResult) {
                mCurrentResult = mResult;
                // 调用接口，改变ImageView的Drawable
                if (mTossAnimationListener != null) {
                    mTossAnimationListener.onDrawableChange(mCurrentResult, this);
                }
            }
        }

        Matrix matrix = t.getMatrix();

        // 设置偏转的角度
        mCamera.save();
        mCamera.rotate(mXAxisDirection * degreeInCircle, mYAxisDirection * degreeInCircle, mZAxisDirection * degreeInCircle);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        // 在View的中心点旋转
        matrix.preTranslate(-(mWidth >> 1), -(mHeight >> 1));
        matrix.postTranslate(mWidth >> 1, mHeight >> 1);

    }

    public interface TossAnimationListener extends AnimationListener {

        /**
         * 需要显示硬币的正面/反面
         *
         * @param result    需要显示正面还是反面   TossImageView.RESULT_FRONT或者TossImageView.RESULT_REVERSE
         * @param animation The started animation.
         */
        void onDrawableChange(int result, TossAnimation animation);
    }
}
