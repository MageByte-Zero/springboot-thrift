package zero.model;

import com.zero.database.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "vc_merchant")
public class Merchant extends BaseEntity implements Serializable {

    @Column(name = "merchant_appkey")
    private String appKey;

    @Column(name = "merchant_appsecret")
    private String appSecret;

    private String source;

    private int channel;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }


    @Override
    public String toString() {
        return "Merchant{" +
                "appKey='" + appKey + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", source='" + source + '\'' +
                ", channel=" + channel +
                '}';
    }
}
