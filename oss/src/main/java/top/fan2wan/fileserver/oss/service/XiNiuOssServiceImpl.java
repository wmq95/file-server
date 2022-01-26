package top.fan2wan.fileserver.oss.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import top.fan2wan.fileserver.common.util.TryHelper;
import top.fan2wan.fileserver.oss.dto.IOssFile;

/**
 * @Author: fanT
 * @Date: 2021/10/19 13:26
 * @Description: impl for oss
 */
public class XiNiuOssServiceImpl implements OssService {
    private final String bucket;
    private final String accessKey;
    private final String secretKey;
    private final String domain;
    private final Region region;
    private final long expire;

    public XiNiuOssServiceImpl(String bucket, String accessKey, String secretKey,
                               String domain, Region region, long expire) {
        this.bucket = bucket;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.domain = domain;
        this.region = region;
        this.expire = expire;
    }

    /**
     * 保存oss文件
     *
     * @param ossFile 文件对象
     */
    @Override
    public void save(IOssFile ossFile) {

        Assert.notBlank(ossFile.getLocalPath(), "path can not be null");
        Assert.notBlank(ossFile.getOssFilePath(), "oss filePath can not be null");

        doSave(ossFile.getLocalPath(), ossFile.getOssFilePath());
    }

    private void doSave(String localFilePath, String ossFilePth) {
        Configuration cfg = new Configuration(region);
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Response response = TryHelper.function(uploadManager::put,localFilePath, ossFilePth, upToken);
        DefaultPutRet putRet = new Gson().fromJson(TryHelper.function(response::bodyString), DefaultPutRet.class);
        logger.info("save success, key was :{},hash was:{}", putRet.key, putRet.hash);
    }

    /**
     * 下载oss文件
     *
     * @param ossFile 文件对象
     */
    @Override
    public void download(IOssFile ossFile) {
        Assert.notBlank(ossFile.getOssFilePath(), " oss filePath can not be null");
        Assert.notBlank(ossFile.getLocalPath(), "oss file downloadPath can not be null");

        doDownload(ossFile.getOssFilePath(), ossFile.getLocalPath());
    }

    private void doDownload(String ossFilePath, String downloadPath) {
        String url = getOssFileUrl(ossFilePath);
        HttpUtil.downloadFile(url, downloadPath);
    }

    private String getOssFileUrl(String ossFilePath) {
        DownloadUrl url = new DownloadUrl(domain, false, ossFilePath);
        Auth auth = Auth.create(accessKey, secretKey);
        return TryHelper.function(url::buildURL, auth, System.currentTimeMillis() + expire);
    }
}
