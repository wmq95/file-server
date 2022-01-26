package top.fan2wan.fileserver.oss.config;

/**
 * @Author: fanT
 * @Date: 2022/1/26 13:17
 * @Description: property for oss
 */
public abstract class AbstractOssProperty {

    private String domain;

    private String bucket;

    private String accessKey;

    private String secretKey;

    private Long expire;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
