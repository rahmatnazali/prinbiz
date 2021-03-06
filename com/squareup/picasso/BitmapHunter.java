package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.NetworkInfo;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Picasso.Priority;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

class BitmapHunter implements Runnable {
    private static final Object DECODE_LOCK;
    private static final RequestHandler ERRORING_HANDLER;
    private static final ThreadLocal<StringBuilder> NAME_BUILDER;
    private static final AtomicInteger SEQUENCE_GENERATOR;
    Action action;
    List<Action> actions;
    final Cache cache;
    final Request data;
    final Dispatcher dispatcher;
    Exception exception;
    int exifRotation;
    Future<?> future;
    final String key;
    LoadedFrom loadedFrom;
    final int memoryPolicy;
    int networkPolicy;
    final Picasso picasso;
    Priority priority;
    final RequestHandler requestHandler;
    Bitmap result;
    int retryCount;
    final int sequence;
    final Stats stats;

    /* renamed from: com.squareup.picasso.BitmapHunter.1 */
    static class C04971 extends ThreadLocal<StringBuilder> {
        C04971() {
        }

        protected StringBuilder initialValue() {
            return new StringBuilder("Picasso-");
        }
    }

    /* renamed from: com.squareup.picasso.BitmapHunter.3 */
    static class C04983 implements Runnable {
        final /* synthetic */ RuntimeException val$e;
        final /* synthetic */ Transformation val$transformation;

        C04983(Transformation transformation, RuntimeException runtimeException) {
            this.val$transformation = transformation;
            this.val$e = runtimeException;
        }

        public void run() {
            throw new RuntimeException("Transformation " + this.val$transformation.key() + " crashed with exception.", this.val$e);
        }
    }

    /* renamed from: com.squareup.picasso.BitmapHunter.4 */
    static class C04994 implements Runnable {
        final /* synthetic */ StringBuilder val$builder;

        C04994(StringBuilder stringBuilder) {
            this.val$builder = stringBuilder;
        }

        public void run() {
            throw new NullPointerException(this.val$builder.toString());
        }
    }

    /* renamed from: com.squareup.picasso.BitmapHunter.5 */
    static class C05005 implements Runnable {
        final /* synthetic */ Transformation val$transformation;

        C05005(Transformation transformation) {
            this.val$transformation = transformation;
        }

        public void run() {
            throw new IllegalStateException("Transformation " + this.val$transformation.key() + " returned input Bitmap but recycled it.");
        }
    }

    /* renamed from: com.squareup.picasso.BitmapHunter.6 */
    static class C05016 implements Runnable {
        final /* synthetic */ Transformation val$transformation;

        C05016(Transformation transformation) {
            this.val$transformation = transformation;
        }

        public void run() {
            throw new IllegalStateException("Transformation " + this.val$transformation.key() + " mutated input Bitmap but failed to recycle the original.");
        }
    }

    /* renamed from: com.squareup.picasso.BitmapHunter.2 */
    static class C08162 extends RequestHandler {
        C08162() {
        }

        public boolean canHandleRequest(Request data) {
            return true;
        }

        public Result load(Request request, int networkPolicy) throws IOException {
            throw new IllegalStateException("Unrecognized type of request: " + request);
        }
    }

    static {
        DECODE_LOCK = new Object();
        NAME_BUILDER = new C04971();
        SEQUENCE_GENERATOR = new AtomicInteger();
        ERRORING_HANDLER = new C08162();
    }

    BitmapHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, RequestHandler requestHandler) {
        this.sequence = SEQUENCE_GENERATOR.incrementAndGet();
        this.picasso = picasso;
        this.dispatcher = dispatcher;
        this.cache = cache;
        this.stats = stats;
        this.action = action;
        this.key = action.getKey();
        this.data = action.getRequest();
        this.priority = action.getPriority();
        this.memoryPolicy = action.getMemoryPolicy();
        this.networkPolicy = action.getNetworkPolicy();
        this.requestHandler = requestHandler;
        this.retryCount = requestHandler.getRetryCount();
    }

    static Bitmap decodeStream(InputStream stream, Request request) throws IOException {
        InputStream markStream = new MarkableInputStream(stream);
        stream = markStream;
        long mark = markStream.savePosition(AccessibilityNodeInfoCompat.ACTION_CUT);
        Options options = RequestHandler.createBitmapOptions(request);
        boolean calculateSize = RequestHandler.requiresInSampleSize(options);
        boolean isWebPFile = Utils.isWebPFile(stream);
        markStream.reset(mark);
        if (isWebPFile) {
            byte[] bytes = Utils.toByteArray(stream);
            if (calculateSize) {
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, options, request);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        }
        if (calculateSize) {
            BitmapFactory.decodeStream(stream, null, options);
            RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, options, request);
            markStream.reset(mark);
        }
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        if (bitmap != null) {
            return bitmap;
        }
        throw new IOException("Failed to decode stream.");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r5 = this;
        r2 = r5.data;	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        updateThreadName(r2);	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        r2 = r5.picasso;	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        r2 = r2.loggingEnabled;	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        if (r2 == 0) goto L_0x0016;
    L_0x000b:
        r2 = "Hunter";
        r3 = "executing";
        r4 = com.squareup.picasso.Utils.getLogIdsForHunter(r5);	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        com.squareup.picasso.Utils.log(r2, r3, r4);	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
    L_0x0016:
        r2 = r5.hunt();	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        r5.result = r2;	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        r2 = r5.result;	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        if (r2 != 0) goto L_0x002f;
    L_0x0020:
        r2 = r5.dispatcher;	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        r2.dispatchFailed(r5);	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
    L_0x0025:
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
    L_0x002e:
        return;
    L_0x002f:
        r2 = r5.dispatcher;	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        r2.dispatchComplete(r5);	 Catch:{ ResponseException -> 0x0035, ContentLengthException -> 0x0051, IOException -> 0x0063, OutOfMemoryError -> 0x0075, Exception -> 0x00a3 }
        goto L_0x0025;
    L_0x0035:
        r0 = move-exception;
        r2 = r0.localCacheOnly;	 Catch:{ all -> 0x00b6 }
        if (r2 == 0) goto L_0x0040;
    L_0x003a:
        r2 = r0.responseCode;	 Catch:{ all -> 0x00b6 }
        r3 = 504; // 0x1f8 float:7.06E-43 double:2.49E-321;
        if (r2 == r3) goto L_0x0042;
    L_0x0040:
        r5.exception = r0;	 Catch:{ all -> 0x00b6 }
    L_0x0042:
        r2 = r5.dispatcher;	 Catch:{ all -> 0x00b6 }
        r2.dispatchFailed(r5);	 Catch:{ all -> 0x00b6 }
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
        goto L_0x002e;
    L_0x0051:
        r0 = move-exception;
        r5.exception = r0;	 Catch:{ all -> 0x00b6 }
        r2 = r5.dispatcher;	 Catch:{ all -> 0x00b6 }
        r2.dispatchRetry(r5);	 Catch:{ all -> 0x00b6 }
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
        goto L_0x002e;
    L_0x0063:
        r0 = move-exception;
        r5.exception = r0;	 Catch:{ all -> 0x00b6 }
        r2 = r5.dispatcher;	 Catch:{ all -> 0x00b6 }
        r2.dispatchRetry(r5);	 Catch:{ all -> 0x00b6 }
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
        goto L_0x002e;
    L_0x0075:
        r0 = move-exception;
        r1 = new java.io.StringWriter;	 Catch:{ all -> 0x00b6 }
        r1.<init>();	 Catch:{ all -> 0x00b6 }
        r2 = r5.stats;	 Catch:{ all -> 0x00b6 }
        r2 = r2.createSnapshot();	 Catch:{ all -> 0x00b6 }
        r3 = new java.io.PrintWriter;	 Catch:{ all -> 0x00b6 }
        r3.<init>(r1);	 Catch:{ all -> 0x00b6 }
        r2.dump(r3);	 Catch:{ all -> 0x00b6 }
        r2 = new java.lang.RuntimeException;	 Catch:{ all -> 0x00b6 }
        r3 = r1.toString();	 Catch:{ all -> 0x00b6 }
        r2.<init>(r3, r0);	 Catch:{ all -> 0x00b6 }
        r5.exception = r2;	 Catch:{ all -> 0x00b6 }
        r2 = r5.dispatcher;	 Catch:{ all -> 0x00b6 }
        r2.dispatchFailed(r5);	 Catch:{ all -> 0x00b6 }
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
        goto L_0x002e;
    L_0x00a3:
        r0 = move-exception;
        r5.exception = r0;	 Catch:{ all -> 0x00b6 }
        r2 = r5.dispatcher;	 Catch:{ all -> 0x00b6 }
        r2.dispatchFailed(r5);	 Catch:{ all -> 0x00b6 }
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
        goto L_0x002e;
    L_0x00b6:
        r2 = move-exception;
        r3 = java.lang.Thread.currentThread();
        r4 = "Picasso-Idle";
        r3.setName(r4);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.picasso.BitmapHunter.run():void");
    }

    Bitmap hunt() throws IOException {
        Bitmap bitmap = null;
        if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
            bitmap = this.cache.get(this.key);
            if (bitmap != null) {
                this.stats.dispatchCacheHit();
                this.loadedFrom = LoadedFrom.MEMORY;
                if (this.picasso.loggingEnabled) {
                    Utils.log("Hunter", "decoded", this.data.logId(), "from cache");
                }
                return bitmap;
            }
        }
        this.data.networkPolicy = this.retryCount == 0 ? NetworkPolicy.OFFLINE.index : this.networkPolicy;
        Result result = this.requestHandler.load(this.data, this.networkPolicy);
        if (result != null) {
            this.loadedFrom = result.getLoadedFrom();
            this.exifRotation = result.getExifOrientation();
            bitmap = result.getBitmap();
            if (bitmap == null) {
                InputStream is = result.getStream();
                try {
                    bitmap = decodeStream(is, this.data);
                } finally {
                    Utils.closeQuietly(is);
                }
            }
        }
        if (bitmap != null) {
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "decoded", this.data.logId());
            }
            this.stats.dispatchBitmapDecoded(bitmap);
            if (this.data.needsTransformation() || this.exifRotation != 0) {
                synchronized (DECODE_LOCK) {
                    if (this.data.needsMatrixTransform() || this.exifRotation != 0) {
                        bitmap = transformResult(this.data, bitmap, this.exifRotation);
                        if (this.picasso.loggingEnabled) {
                            Utils.log("Hunter", "transformed", this.data.logId());
                        }
                    }
                    if (this.data.hasCustomTransformations()) {
                        bitmap = applyCustomTransformations(this.data.transformations, bitmap);
                        if (this.picasso.loggingEnabled) {
                            Utils.log("Hunter", "transformed", this.data.logId(), "from custom transformations");
                        }
                    }
                }
                if (bitmap != null) {
                    this.stats.dispatchBitmapTransformed(bitmap);
                }
            }
        }
        return bitmap;
    }

    void attach(Action action) {
        boolean loggingEnabled = this.picasso.loggingEnabled;
        Request request = action.request;
        if (this.action == null) {
            this.action = action;
            if (!loggingEnabled) {
                return;
            }
            if (this.actions == null || this.actions.isEmpty()) {
                Utils.log("Hunter", "joined", request.logId(), "to empty hunter");
                return;
            } else {
                Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
                return;
            }
        }
        if (this.actions == null) {
            this.actions = new ArrayList(3);
        }
        this.actions.add(action);
        if (loggingEnabled) {
            Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
        }
        Priority actionPriority = action.getPriority();
        if (actionPriority.ordinal() > this.priority.ordinal()) {
            this.priority = actionPriority;
        }
    }

    void detach(Action action) {
        boolean detached = false;
        if (this.action == action) {
            this.action = null;
            detached = true;
        } else if (this.actions != null) {
            detached = this.actions.remove(action);
        }
        if (detached && action.getPriority() == this.priority) {
            this.priority = computeNewPriority();
        }
        if (this.picasso.loggingEnabled) {
            Utils.log("Hunter", "removed", action.request.logId(), Utils.getLogIdsForHunter(this, "from "));
        }
    }

    private Priority computeNewPriority() {
        boolean hasMultiple;
        boolean hasAny;
        Priority priority = Priority.LOW;
        if (this.actions == null || this.actions.isEmpty()) {
            hasMultiple = false;
        } else {
            hasMultiple = true;
        }
        if (this.action != null || hasMultiple) {
            hasAny = true;
        } else {
            hasAny = false;
        }
        if (!hasAny) {
            return priority;
        }
        if (this.action != null) {
            priority = this.action.getPriority();
        }
        if (hasMultiple) {
            int n = this.actions.size();
            for (int i = 0; i < n; i++) {
                Priority actionPriority = ((Action) this.actions.get(i)).getPriority();
                if (actionPriority.ordinal() > priority.ordinal()) {
                    priority = actionPriority;
                }
            }
        }
        return priority;
    }

    boolean cancel() {
        if (this.action != null) {
            return false;
        }
        if ((this.actions == null || this.actions.isEmpty()) && this.future != null && this.future.cancel(false)) {
            return true;
        }
        return false;
    }

    boolean isCancelled() {
        return this.future != null && this.future.isCancelled();
    }

    boolean shouldRetry(boolean airplaneMode, NetworkInfo info) {
        boolean hasRetries;
        if (this.retryCount > 0) {
            hasRetries = true;
        } else {
            hasRetries = false;
        }
        if (!hasRetries) {
            return false;
        }
        this.retryCount--;
        return this.requestHandler.shouldRetry(airplaneMode, info);
    }

    boolean supportsReplay() {
        return this.requestHandler.supportsReplay();
    }

    Bitmap getResult() {
        return this.result;
    }

    String getKey() {
        return this.key;
    }

    int getMemoryPolicy() {
        return this.memoryPolicy;
    }

    Request getData() {
        return this.data;
    }

    Action getAction() {
        return this.action;
    }

    Picasso getPicasso() {
        return this.picasso;
    }

    List<Action> getActions() {
        return this.actions;
    }

    Exception getException() {
        return this.exception;
    }

    LoadedFrom getLoadedFrom() {
        return this.loadedFrom;
    }

    Priority getPriority() {
        return this.priority;
    }

    static void updateThreadName(Request data) {
        String name = data.getName();
        StringBuilder builder = (StringBuilder) NAME_BUILDER.get();
        builder.ensureCapacity("Picasso-".length() + name.length());
        builder.replace("Picasso-".length(), builder.length(), name);
        Thread.currentThread().setName(builder.toString());
    }

    static BitmapHunter forRequest(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        Request request = action.getRequest();
        List<RequestHandler> requestHandlers = picasso.getRequestHandlers();
        int count = requestHandlers.size();
        for (int i = 0; i < count; i++) {
            RequestHandler requestHandler = (RequestHandler) requestHandlers.get(i);
            if (requestHandler.canHandleRequest(request)) {
                return new BitmapHunter(picasso, dispatcher, cache, stats, action, requestHandler);
            }
        }
        return new BitmapHunter(picasso, dispatcher, cache, stats, action, ERRORING_HANDLER);
    }

    static Bitmap applyCustomTransformations(List<Transformation> transformations, Bitmap result) {
        int i = 0;
        int count = transformations.size();
        while (i < count) {
            Transformation transformation = (Transformation) transformations.get(i);
            try {
                Bitmap newResult = transformation.transform(result);
                if (newResult == null) {
                    StringBuilder builder = new StringBuilder().append("Transformation ").append(transformation.key()).append(" returned null after ").append(i).append(" previous transformation(s).\n\nTransformation list:\n");
                    for (Transformation t : transformations) {
                        builder.append(t.key()).append('\n');
                    }
                    Picasso.HANDLER.post(new C04994(builder));
                    return null;
                } else if (newResult == result && result.isRecycled()) {
                    Picasso.HANDLER.post(new C05005(transformation));
                    return null;
                } else if (newResult == result || result.isRecycled()) {
                    result = newResult;
                    i++;
                } else {
                    Picasso.HANDLER.post(new C05016(transformation));
                    return null;
                }
            } catch (RuntimeException e) {
                Picasso.HANDLER.post(new C04983(transformation, e));
                return null;
            }
        }
        return result;
    }

    static Bitmap transformResult(Request data, Bitmap result, int exifRotation) {
        int inWidth = result.getWidth();
        int inHeight = result.getHeight();
        boolean onlyScaleDown = data.onlyScaleDown;
        int drawX = 0;
        int drawY = 0;
        int drawWidth = inWidth;
        int drawHeight = inHeight;
        Matrix matrix = new Matrix();
        if (data.needsMatrixTransform()) {
            int targetWidth = data.targetWidth;
            int targetHeight = data.targetHeight;
            float targetRotation = data.rotationDegrees;
            if (targetRotation != 0.0f) {
                if (data.hasRotationPivot) {
                    matrix.setRotate(targetRotation, data.rotationPivotX, data.rotationPivotY);
                } else {
                    matrix.setRotate(targetRotation);
                }
            }
            float widthRatio;
            float heightRatio;
            if (data.centerCrop) {
                float scaleX;
                float scaleY;
                widthRatio = ((float) targetWidth) / ((float) inWidth);
                heightRatio = ((float) targetHeight) / ((float) inHeight);
                int newSize;
                if (widthRatio > heightRatio) {
                    newSize = (int) Math.ceil((double) (((float) inHeight) * (heightRatio / widthRatio)));
                    drawY = (inHeight - newSize) / 2;
                    drawHeight = newSize;
                    scaleX = widthRatio;
                    scaleY = ((float) targetHeight) / ((float) drawHeight);
                } else {
                    newSize = (int) Math.ceil((double) (((float) inWidth) * (widthRatio / heightRatio)));
                    drawX = (inWidth - newSize) / 2;
                    drawWidth = newSize;
                    scaleX = ((float) targetWidth) / ((float) drawWidth);
                    scaleY = heightRatio;
                }
                if (shouldResize(onlyScaleDown, inWidth, inHeight, targetWidth, targetHeight)) {
                    matrix.preScale(scaleX, scaleY);
                }
            } else if (data.centerInside) {
                float scale;
                widthRatio = ((float) targetWidth) / ((float) inWidth);
                heightRatio = ((float) targetHeight) / ((float) inHeight);
                if (widthRatio < heightRatio) {
                    scale = widthRatio;
                } else {
                    scale = heightRatio;
                }
                if (shouldResize(onlyScaleDown, inWidth, inHeight, targetWidth, targetHeight)) {
                    matrix.preScale(scale, scale);
                }
            } else if (!((targetWidth == 0 && targetHeight == 0) || (targetWidth == inWidth && targetHeight == inHeight))) {
                float sx = targetWidth != 0 ? ((float) targetWidth) / ((float) inWidth) : ((float) targetHeight) / ((float) inHeight);
                float sy = targetHeight != 0 ? ((float) targetHeight) / ((float) inHeight) : ((float) targetWidth) / ((float) inWidth);
                if (shouldResize(onlyScaleDown, inWidth, inHeight, targetWidth, targetHeight)) {
                    matrix.preScale(sx, sy);
                }
            }
        }
        if (exifRotation != 0) {
            matrix.preRotate((float) exifRotation);
        }
        Bitmap newResult = Bitmap.createBitmap(result, drawX, drawY, drawWidth, drawHeight, matrix, true);
        if (newResult == result) {
            return result;
        }
        result.recycle();
        return newResult;
    }

    private static boolean shouldResize(boolean onlyScaleDown, int inWidth, int inHeight, int targetWidth, int targetHeight) {
        return !onlyScaleDown || inWidth > targetWidth || inHeight > targetHeight;
    }
}
