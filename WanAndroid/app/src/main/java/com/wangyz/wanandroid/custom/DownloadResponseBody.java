package com.wangyz.wanandroid.custom;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.blankj.utilcode.util.Utils;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.util.NotificationUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @author wangyz
 * @time 2019/3/22 15:43
 * @description DownloadResponseBody
 */
public class DownloadResponseBody extends ResponseBody {

    private ResponseBody mResponseBody;

    private BufferedSource mBufferedSource;

    public DownloadResponseBody(ResponseBody mResponseBody) {
        this.mResponseBody = mResponseBody;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(handleSource(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source handleSource(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                int progress = (int) (totalBytesRead * 100 / mResponseBody.contentLength());
                NotificationUtils.showNotification(Utils.getApp().getString(R.string.download), Utils.getApp().getString(R.string.downloading) + progress + "%", 1, "1", progress, 100);
                return bytesRead;
            }
        };
    }
}
