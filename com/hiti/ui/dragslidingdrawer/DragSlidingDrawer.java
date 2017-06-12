package com.hiti.ui.dragslidingdrawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.hiti.utility.LogManager;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import java.lang.reflect.Method;
import org.apache.commons.net.ftp.FTPClient;

public class DragSlidingDrawer extends ViewGroup {
    private static final int ANIMATION_FRAME_DURATION = 16;
    private static final int COLLAPSED_SEMI_CLOSED = -10003;
    private static final int EXPANDED_FULL_OPEN = -10001;
    private static final float MAXIMUM_ACCELERATION = 2000.0f;
    private static final float MAXIMUM_MAJOR_VELOCITY = 200.0f;
    private static final float MAXIMUM_MINOR_VELOCITY = 150.0f;
    private static final float MAXIMUM_TAP_VELOCITY = 100.0f;
    private static final int MSG_ANIMATE = 1000;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    private static final int TAP_THRESHOLD = 6;
    private static final int VELOCITY_UNITS = 1000;
    private LogManager LOG;
    private int[] R_STYLEABLE_DragSlidingDrawer;
    private int R_STYLEABLE_DragSlidingDrawer_allowSingleTap;
    private int R_STYLEABLE_DragSlidingDrawer_animateOnClick;
    private int R_STYLEABLE_DragSlidingDrawer_closedContentSize;
    private int R_STYLEABLE_DragSlidingDrawer_content;
    private int R_STYLEABLE_DragSlidingDrawer_handle;
    private boolean mAllowSingleTap;
    private boolean mAnimateOnClick;
    private float mAnimatedAcceleration;
    private float mAnimatedVelocity;
    private boolean mAnimating;
    private long mAnimationLastTime;
    private float mAnimationPosition;
    private int mBottomOffset;
    public int mClosedContentSize;
    private View mContent;
    private int mContentId;
    private long mCurrentAnimationTime;
    private boolean mExpanded;
    private final Rect mFrame;
    private View mHandle;
    private int mHandleHeight;
    private int mHandleId;
    private final Handler mHandler;
    private int mLastY;
    private boolean mLocked;
    private final int mMaximumAcceleration;
    private final int mMaximumMajorVelocity;
    private final int mMaximumMinorVelocity;
    private final int mMaximumTapVelocity;
    private OnDrawerCloseListener mOnDrawerCloseListener;
    private OnDrawerOpenListener mOnDrawerOpenListener;
    private OnDrawerScrollListener mOnDrawerScrollListener;
    private final int mTapThreshold;
    private int mTopOffset;
    private int mTouchDelta;
    private boolean mTracking;
    private VelocityTracker mVelocityTracker;
    private final int mVelocityUnits;
    private int mYOffset;

    private class DrawerToggler implements OnClickListener {
        private DrawerToggler() {
        }

        public void onClick(View v) {
            if (!DragSlidingDrawer.this.mLocked) {
                if (DragSlidingDrawer.this.mAnimateOnClick) {
                    DragSlidingDrawer.this.animateToggle();
                } else {
                    DragSlidingDrawer.this.toggle();
                }
            }
        }
    }

    public interface OnDrawerCloseListener {
        void onDrawerClosed();
    }

    public interface OnDrawerOpenListener {
        void onDrawerOpened();
    }

    public interface OnDrawerScrollListener {
        void onScrollEnded();

        void onScrollStarted();
    }

    private class SlidingHandler extends Handler {
        private SlidingHandler() {
        }

        public void handleMessage(Message m) {
            switch (m.what) {
                case DragSlidingDrawer.VELOCITY_UNITS /*1000*/:
                    DragSlidingDrawer.this.doAnimation();
                default:
            }
        }
    }

    public DragSlidingDrawer(Context context, AttributeSet attrs) {
        this(context, attrs, ORIENTATION_HORIZONTAL);
    }

    public DragSlidingDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mFrame = new Rect();
        this.mHandler = new SlidingHandler();
        this.mYOffset = ORIENTATION_HORIZONTAL;
        this.mLastY = ORIENTATION_HORIZONTAL;
        this.R_STYLEABLE_DragSlidingDrawer = null;
        this.R_STYLEABLE_DragSlidingDrawer_allowSingleTap = ORIENTATION_HORIZONTAL;
        this.R_STYLEABLE_DragSlidingDrawer_animateOnClick = ORIENTATION_HORIZONTAL;
        this.R_STYLEABLE_DragSlidingDrawer_closedContentSize = ORIENTATION_HORIZONTAL;
        this.R_STYLEABLE_DragSlidingDrawer_handle = ORIENTATION_HORIZONTAL;
        this.R_STYLEABLE_DragSlidingDrawer_content = ORIENTATION_HORIZONTAL;
        this.LOG = null;
        if (isInEditMode()) {
            this.mTapThreshold = ORIENTATION_HORIZONTAL;
            this.mMaximumTapVelocity = ORIENTATION_HORIZONTAL;
            this.mMaximumMinorVelocity = ORIENTATION_HORIZONTAL;
            this.mMaximumMajorVelocity = ORIENTATION_HORIZONTAL;
            this.mMaximumAcceleration = ORIENTATION_HORIZONTAL;
            this.mVelocityUnits = ORIENTATION_HORIZONTAL;
            return;
        }
        GetResourceID(context);
        this.LOG = new LogManager(ORIENTATION_HORIZONTAL);
        for (int i = ORIENTATION_HORIZONTAL; i < this.R_STYLEABLE_DragSlidingDrawer.length; i += ORIENTATION_VERTICAL) {
            this.LOG.m384e("attrArray", Integer.toHexString(this.R_STYLEABLE_DragSlidingDrawer[i]));
        }
        TypedArray a = context.obtainStyledAttributes(attrs, this.R_STYLEABLE_DragSlidingDrawer);
        this.LOG.m384e(" DragSlidingDrawer_allowSingleTap ", String.valueOf(this.R_STYLEABLE_DragSlidingDrawer_allowSingleTap));
        this.LOG.m384e(" DragSlidingDrawer_animateOnClick ", String.valueOf(this.R_STYLEABLE_DragSlidingDrawer_animateOnClick));
        this.LOG.m384e(" DragSlidingDrawer_closedContentSize ", String.valueOf(this.R_STYLEABLE_DragSlidingDrawer_closedContentSize));
        this.mAllowSingleTap = a.getBoolean(this.R_STYLEABLE_DragSlidingDrawer_allowSingleTap, true);
        this.mAnimateOnClick = a.getBoolean(this.R_STYLEABLE_DragSlidingDrawer_animateOnClick, true);
        this.mClosedContentSize = (int) a.getDimension(this.R_STYLEABLE_DragSlidingDrawer_closedContentSize, 0.0f);
        int handleId = a.getResourceId(this.R_STYLEABLE_DragSlidingDrawer_handle, ORIENTATION_HORIZONTAL);
        if (handleId == 0) {
            throw new IllegalArgumentException("The handle attribute is required and must refer to a valid child.");
        }
        int contentId = a.getResourceId(this.R_STYLEABLE_DragSlidingDrawer_content, ORIENTATION_HORIZONTAL);
        if (contentId == 0) {
            throw new IllegalArgumentException("The content attribute is required and must refer to a valid child.");
        } else if (handleId == contentId) {
            throw new IllegalArgumentException("The content and handle attributes must refer to different children.");
        } else {
            this.mHandleId = handleId;
            this.mContentId = contentId;
            float density = getResources().getDisplayMetrics().density;
            this.mTapThreshold = (int) ((6.0f * density) + 0.5f);
            this.mMaximumTapVelocity = (int) ((MAXIMUM_TAP_VELOCITY * density) + 0.5f);
            this.mMaximumMinorVelocity = (int) ((MAXIMUM_MINOR_VELOCITY * density) + 0.5f);
            this.mMaximumMajorVelocity = (int) ((MAXIMUM_MAJOR_VELOCITY * density) + 0.5f);
            this.mMaximumAcceleration = (int) ((MAXIMUM_ACCELERATION * density) + 0.5f);
            this.mVelocityUnits = (int) ((1000.0f * density) + 0.5f);
            this.LOG.m384e("mTapThreshold", String.valueOf(this.mTapThreshold));
            this.LOG.m384e("mMaximumTapVelocity", String.valueOf(this.mMaximumTapVelocity));
            this.LOG.m384e("mMaximumMinorVelocity", String.valueOf(this.mMaximumMinorVelocity));
            this.LOG.m384e("mMaximumMajorVelocity", String.valueOf(this.mMaximumMajorVelocity));
            this.LOG.m384e("mMaximumAcceleration", String.valueOf(this.mMaximumAcceleration));
            this.LOG.m384e("mVelocityUnits", String.valueOf(this.mVelocityUnits));
            a.recycle();
            setAlwaysDrawnWithCacheEnabled(false);
        }
    }

    private void GetResourceID(Context context) {
        this.R_STYLEABLE_DragSlidingDrawer = ResourceSearcher.getIds(context, RS_TYPE.STYLEABLE, "DragSlidingDrawer");
        this.R_STYLEABLE_DragSlidingDrawer_allowSingleTap = ResourceSearcher.getId(context, RS_TYPE.STYLEABLE, "DragSlidingDrawer_allowSingleTap");
        this.R_STYLEABLE_DragSlidingDrawer_animateOnClick = ResourceSearcher.getId(context, RS_TYPE.STYLEABLE, "DragSlidingDrawer_animateOnClick");
        this.R_STYLEABLE_DragSlidingDrawer_closedContentSize = ResourceSearcher.getId(context, RS_TYPE.STYLEABLE, "DragSlidingDrawer_closedContentSize");
        this.R_STYLEABLE_DragSlidingDrawer_handle = ResourceSearcher.getId(context, RS_TYPE.STYLEABLE, "DragSlidingDrawer_handle");
        this.R_STYLEABLE_DragSlidingDrawer_content = ResourceSearcher.getId(context, RS_TYPE.STYLEABLE, "DragSlidingDrawer_content");
    }

    protected void onFinishInflate() {
        if (!isInEditMode()) {
            this.mHandle = findViewById(this.mHandleId);
            if (this.mHandle == null) {
                throw new IllegalArgumentException("The handle attribute is must refer to an existing child.");
            }
            this.mContent = findViewById(this.mContentId);
            if (this.mContent == null) {
                throw new IllegalArgumentException("The content attribute is must refer to an existing child.");
            }
            this.mContent.setVisibility(ORIENTATION_HORIZONTAL);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInEditMode()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == 0 || heightSpecMode == 0) {
            throw new RuntimeException("SlidingDrawer cannot have UNSPECIFIED dimensions");
        }
        View handle = this.mHandle;
        measureChild(handle, widthMeasureSpec, heightMeasureSpec);
        this.mContent.measure(MeasureSpec.makeMeasureSpec(widthSpecSize, 1073741824), MeasureSpec.makeMeasureSpec((heightSpecSize - handle.getMeasuredHeight()) - this.mTopOffset, 1073741824));
        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    protected void dispatchDraw(Canvas canvas) {
        if (!isInEditMode()) {
            long drawingTime = getDrawingTime();
            View handle = this.mHandle;
            drawChild(canvas, handle, drawingTime);
            if (this.mTracking || this.mAnimating || !this.mExpanded) {
                Bitmap cache = this.mContent.getDrawingCache();
                if (cache != null) {
                    canvas.drawBitmap(cache, 0.0f, (float) handle.getBottom(), null);
                    return;
                }
                canvas.save();
                canvas.translate(0.0f, (float) (handle.getTop() - this.mTopOffset));
                drawChild(canvas, this.mContent, drawingTime);
                canvas.restore();
            } else if (this.mExpanded) {
                drawChild(canvas, this.mContent, drawingTime);
            }
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isInEditMode() && !this.mTracking) {
            int width = r - l;
            int height = b - t;
            View handle = this.mHandle;
            View content = this.mContent;
            int handleWidth = handle.getMeasuredWidth();
            int handleHeight = handle.getMeasuredHeight();
            int handleLeft = (width - handleWidth) / 2;
            int handleTop = this.mExpanded ? this.mTopOffset : ((height - handleHeight) - this.mClosedContentSize) + this.mBottomOffset;
            content.layout(ORIENTATION_HORIZONTAL, this.mTopOffset + handleHeight, content.getMeasuredWidth(), (this.mTopOffset + handleHeight) + content.getMeasuredHeight());
            handle.layout(handleLeft, handleTop, handleLeft + handleWidth, handleTop + handleHeight);
            this.mHandleHeight = handle.getHeight();
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.mLocked) {
            return false;
        }
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        Rect frame = this.mFrame;
        View handle = this.mHandle;
        handle.getHitRect(frame);
        if (!this.mTracking && !frame.contains((int) x, (int) y)) {
            return false;
        }
        if (action == 0) {
            this.mTracking = true;
            handle.setPressed(true);
            if (this.mOnDrawerScrollListener != null) {
                this.mOnDrawerScrollListener.onScrollStarted();
            }
            this.mTouchDelta = ((int) y) - this.mHandle.getTop();
            this.mLastY = (int) y;
            this.mVelocityTracker = VelocityTracker.obtain();
            this.mVelocityTracker.addMovement(event);
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean z = false;
        if (this.mLocked) {
            return true;
        }
        if (this.mTracking) {
            this.mVelocityTracker.addMovement(event);
            switch (event.getAction()) {
                case ORIENTATION_VERTICAL /*1*/:
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    this.mTracking = false;
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(this.mVelocityUnits);
                    float yVelocity = velocityTracker.getYVelocity();
                    float xVelocity = velocityTracker.getXVelocity();
                    if (xVelocity < 0.0f) {
                        xVelocity = -xVelocity;
                    }
                    if (xVelocity > ((float) this.mMaximumMinorVelocity)) {
                        xVelocity = (float) this.mMaximumMinorVelocity;
                    }
                    float velocity = (float) Math.hypot((double) xVelocity, (double) yVelocity);
                    if (yVelocity < 0.0f) {
                        velocity = -velocity;
                    }
                    int top = this.mHandle.getTop();
                    if (Math.abs(((float) this.mLastY) - event.getY()) < ((float) (this.mHandleHeight / 2)) && ((top < this.mTapThreshold + this.mTopOffset || top > ((((getBottom() - getTop()) - this.mHandleHeight) - this.mClosedContentSize) - this.mTapThreshold) + this.mBottomOffset) && this.mAllowSingleTap)) {
                        if (!this.mExpanded) {
                            animateOpen(top);
                            this.mTopOffset = ORIENTATION_HORIZONTAL;
                            this.LOG.m384e("animateOpen", "animateOpen");
                            break;
                        }
                        animateClose(top);
                        this.LOG.m384e("animateClose", "animateClose");
                        break;
                    }
                    this.LOG.m384e("performFling", "performFling");
                    if (top <= ((((getBottom() - getTop()) - this.mHandleHeight) - this.mClosedContentSize) - this.mTapThreshold) + this.mBottomOffset) {
                        performFling(top, velocity);
                        break;
                    }
                    animateClose(top);
                    break;
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    this.mYOffset = (int) (event.getY() - ((float) this.mTouchDelta));
                    this.mTopOffset = this.mYOffset;
                    moveHandle(this.mYOffset);
                    break;
            }
        }
        if (this.mTracking || this.mAnimating || super.onTouchEvent(event)) {
            z = true;
        }
        return z;
    }

    private void animateClose(int position) {
        prepareTracking(position);
        performFling(position, (float) this.mMaximumAcceleration, true);
    }

    private void animateOpen(int position) {
        prepareTracking(position);
        performFling(position, (float) (-this.mMaximumAcceleration), true);
    }

    private void performFling(int position, float velocity) {
        this.mAnimationPosition = (float) position;
        this.mAnimatedVelocity = velocity;
        this.mAnimatedAcceleration = (float) (-this.mMaximumAcceleration);
        if (velocity > 0.0f) {
            this.mAnimatedVelocity = 0.0f;
        }
        long now = SystemClock.uptimeMillis();
        this.mAnimationLastTime = now;
        this.mCurrentAnimationTime = 16 + now;
        this.mAnimating = true;
        this.mHandler.removeMessages(VELOCITY_UNITS);
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(VELOCITY_UNITS), this.mCurrentAnimationTime);
        stopTracking();
    }

    private void performFling(int position, float velocity, boolean always) {
        this.mAnimationPosition = (float) position;
        this.mAnimatedVelocity = velocity;
        if (this.mExpanded) {
            if (always || velocity > ((float) this.mMaximumMajorVelocity) || (position > this.mTopOffset + this.mHandleHeight && velocity > ((float) (-this.mMaximumMajorVelocity)))) {
                this.mAnimatedAcceleration = (float) this.mMaximumAcceleration;
                if (velocity < 0.0f) {
                    this.mAnimatedVelocity = 0.0f;
                }
            } else {
                this.mAnimatedAcceleration = (float) (-this.mMaximumAcceleration);
                if (velocity > 0.0f) {
                    this.mAnimatedVelocity = 0.0f;
                }
            }
        } else if (always || (velocity <= ((float) this.mMaximumMajorVelocity) && (position <= getHeight() / 2 || velocity <= ((float) (-this.mMaximumMajorVelocity))))) {
            this.mAnimatedAcceleration = (float) (-this.mMaximumAcceleration);
            if (velocity > 0.0f) {
                this.mAnimatedVelocity = 0.0f;
            }
        } else {
            this.mAnimatedAcceleration = (float) this.mMaximumAcceleration;
            if (velocity < 0.0f) {
                this.mAnimatedVelocity = 0.0f;
            }
        }
        long now = SystemClock.uptimeMillis();
        this.mAnimationLastTime = now;
        this.mCurrentAnimationTime = 16 + now;
        this.mAnimating = true;
        this.mHandler.removeMessages(VELOCITY_UNITS);
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(VELOCITY_UNITS), this.mCurrentAnimationTime);
        stopTracking();
    }

    private void prepareTracking(int position) {
        boolean opening;
        this.mTracking = true;
        this.mVelocityTracker = VelocityTracker.obtain();
        if (this.mExpanded) {
            opening = false;
        } else {
            opening = true;
        }
        if (opening) {
            this.mAnimatedAcceleration = (float) this.mMaximumAcceleration;
            this.mAnimatedVelocity = (float) this.mMaximumMajorVelocity;
            this.mAnimationPosition = (float) (((getHeight() - this.mHandleHeight) - this.mClosedContentSize) + this.mBottomOffset);
            moveHandle((int) this.mAnimationPosition);
            this.mAnimating = true;
            this.mHandler.removeMessages(VELOCITY_UNITS);
            long now = SystemClock.uptimeMillis();
            this.mAnimationLastTime = now;
            this.mCurrentAnimationTime = 16 + now;
            this.mAnimating = true;
            return;
        }
        if (this.mAnimating) {
            this.mAnimating = false;
            this.mHandler.removeMessages(VELOCITY_UNITS);
        }
        moveHandle(position);
    }

    private void moveHandle(int position) {
        View handle = this.mHandle;
        prepareContent();
        if (position == EXPANDED_FULL_OPEN) {
            handle.offsetTopAndBottom(this.mTopOffset - handle.getTop());
            invalidate();
        } else if (position == COLLAPSED_SEMI_CLOSED) {
            handle.offsetTopAndBottom(((((getBottom() - getTop()) - this.mHandleHeight) - this.mClosedContentSize) - handle.getTop()) + this.mBottomOffset);
            invalidate();
        } else {
            int top = handle.getTop();
            int deltaY = position - top;
            if (position < this.mTopOffset) {
                deltaY = this.mTopOffset - top;
            } else if (deltaY > ((((getBottom() - getTop()) - this.mHandleHeight) - this.mClosedContentSize) - top) + this.mBottomOffset) {
                deltaY = ((((getBottom() - getTop()) - this.mHandleHeight) - this.mClosedContentSize) - top) + this.mBottomOffset;
            }
            handle.offsetTopAndBottom(deltaY);
            this.LOG.m384e("deltaY", String.valueOf(deltaY));
            this.LOG.m384e("top", String.valueOf(top));
            invalidate();
        }
    }

    private void prepareContent() {
        if (this.mTopOffset < 0) {
            this.mTopOffset = ORIENTATION_HORIZONTAL;
        } else if (this.mTopOffset >= (getBottom() - this.mClosedContentSize) - this.mHandleHeight) {
            this.mTopOffset = (getBottom() - this.mClosedContentSize) - this.mHandleHeight;
        }
        View content = this.mContent;
        int HandleHeight = this.mHandleHeight;
        content.measure(MeasureSpec.makeMeasureSpec(getRight() - getLeft(), 1073741824), MeasureSpec.makeMeasureSpec((getBottom() - getTop()) - HandleHeight, 1073741824));
        content.layout(ORIENTATION_HORIZONTAL, this.mTopOffset + HandleHeight, content.getMeasuredWidth(), (this.mTopOffset + HandleHeight) + content.getMeasuredHeight());
        content.getViewTreeObserver().dispatchOnPreDraw();
        if (!isViewHardwareAccelerated(content)) {
            content.buildDrawingCache();
        }
        content.setVisibility(8);
    }

    private boolean isViewHardwareAccelerated(View view) {
        try {
            Method isHardwareAcceleratedMethod = view.getClass().getDeclaredMethod("isHardwareAccelerated", new Class[ORIENTATION_HORIZONTAL]);
            if (isHardwareAcceleratedMethod == null) {
                return false;
            }
            Boolean result = (Boolean) isHardwareAcceleratedMethod.invoke(view, new Object[ORIENTATION_HORIZONTAL]);
            if (result == null || !result.booleanValue()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void stopTracking() {
        this.mHandle.setPressed(false);
        this.mTracking = false;
        if (this.mOnDrawerScrollListener != null) {
            this.mOnDrawerScrollListener.onScrollEnded();
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void doAnimation() {
        if (this.mAnimating) {
            incrementAnimation();
            if (this.mAnimationPosition >= ((float) ((getHeight() - 1) + this.mBottomOffset))) {
                this.mAnimating = false;
                closeDrawer();
            } else if (this.mAnimationPosition < ((float) this.mTopOffset)) {
                this.LOG.m384e("mTopOffset ooo", String.valueOf(this.mTopOffset));
                this.mAnimating = false;
                openDrawer();
                this.LOG.m384e("openDrawer", "openDrawer");
            } else {
                moveHandle((int) this.mAnimationPosition);
                this.mCurrentAnimationTime += 16;
                this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(VELOCITY_UNITS), this.mCurrentAnimationTime);
            }
        }
    }

    private void incrementAnimation() {
        long now = SystemClock.uptimeMillis();
        float t = ((float) (now - this.mAnimationLastTime)) / 1000.0f;
        float position = this.mAnimationPosition;
        float v = this.mAnimatedVelocity;
        float a = this.mAnimatedAcceleration;
        this.mAnimationPosition = ((v * t) + position) + (((0.5f * a) * t) * t);
        this.mAnimatedVelocity = (a * t) + v;
        this.mAnimationLastTime = now;
    }

    public void toggle() {
        if (this.mExpanded) {
            closeDrawer();
        } else {
            openDrawer();
        }
        invalidate();
        requestLayout();
    }

    public void animateToggle() {
        if (this.mExpanded) {
            animateClose();
        } else {
            animateOpen();
        }
    }

    public void open() {
        openDrawer();
        invalidate();
        requestLayout();
        sendAccessibilityEvent(32);
    }

    public void close() {
        closeDrawer();
        invalidate();
        requestLayout();
    }

    public void animateClose() {
        prepareContent();
        OnDrawerScrollListener scrollListener = this.mOnDrawerScrollListener;
        if (scrollListener != null) {
            scrollListener.onScrollStarted();
        }
        animateClose(this.mHandle.getTop());
        if (scrollListener != null) {
            scrollListener.onScrollEnded();
        }
    }

    public void animateOpen() {
        prepareContent();
        OnDrawerScrollListener scrollListener = this.mOnDrawerScrollListener;
        if (scrollListener != null) {
            scrollListener.onScrollStarted();
        }
        animateOpen(this.mHandle.getTop());
        sendAccessibilityEvent(32);
        if (scrollListener != null) {
            scrollListener.onScrollEnded();
        }
    }

    private void closeDrawer() {
        moveHandle(COLLAPSED_SEMI_CLOSED);
        this.mContent.setVisibility(8);
        this.mContent.destroyDrawingCache();
        if (this.mExpanded) {
            this.mExpanded = false;
            if (this.mOnDrawerCloseListener != null) {
                this.mOnDrawerCloseListener.onDrawerClosed();
            }
        }
    }

    private void openDrawer() {
        moveHandle(EXPANDED_FULL_OPEN);
        this.mContent.setVisibility(ORIENTATION_HORIZONTAL);
        if (!this.mExpanded) {
            this.mExpanded = true;
            if (this.mOnDrawerOpenListener != null) {
                this.mOnDrawerOpenListener.onDrawerOpened();
            }
        }
    }

    public void setOnDrawerOpenListener(OnDrawerOpenListener onDrawerOpenListener) {
        this.mOnDrawerOpenListener = onDrawerOpenListener;
    }

    public void setOnDrawerCloseListener(OnDrawerCloseListener onDrawerCloseListener) {
        this.mOnDrawerCloseListener = onDrawerCloseListener;
    }

    public void setOnDrawerScrollListener(OnDrawerScrollListener onDrawerScrollListener) {
        this.mOnDrawerScrollListener = onDrawerScrollListener;
    }

    public View getHandle() {
        return this.mHandle;
    }

    public View getContent() {
        return this.mContent;
    }

    public void unlock() {
        this.mLocked = false;
    }

    public void lock() {
        this.mLocked = true;
    }

    public boolean isOpened() {
        return this.mExpanded;
    }

    public boolean isMoving() {
        return this.mTracking || this.mAnimating;
    }

    public void SetClosedContentSize(int iSize) {
        this.mClosedContentSize = iSize;
    }

    public void SetTopOffset(int iOffset) {
        View content = this.mContent;
        int HandleHeight = this.mHandleHeight;
        this.mTopOffset = iOffset;
        this.mTracking = true;
        this.mHandle.setPressed(true);
        this.mHandle.offsetTopAndBottom(this.mTopOffset);
        invalidate();
        content.measure(MeasureSpec.makeMeasureSpec(getRight() - getLeft(), 1073741824), MeasureSpec.makeMeasureSpec((getBottom() - getTop()) - HandleHeight, 1073741824));
        content.layout(ORIENTATION_HORIZONTAL, this.mTopOffset + HandleHeight, content.getMeasuredWidth(), (this.mTopOffset + HandleHeight) + content.getMeasuredHeight());
        content.getViewTreeObserver().dispatchOnPreDraw();
        if (!isViewHardwareAccelerated(content)) {
            content.buildDrawingCache();
        }
        content.setVisibility(8);
        this.mTracking = false;
        performFling(this.mTopOffset, -150.0f);
    }

    public void SetButtonBackground(int resid) {
        this.mHandle.setBackgroundResource(resid);
    }
}
