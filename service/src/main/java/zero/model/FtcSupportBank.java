package zero.model;

import java.io.Serializable;

/**
 * 基金支持的银行。
 *
 * @author zhangbo
 * @since 2015/12/19
 */
public class FtcSupportBank implements Serializable {
    /**
     *
     */
    private int id;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 单笔限额
     */
    private String singlePaymentLimit;

    /**
     * 日限额
     */
    private String dayPaymentLimit;

    /**
     * 月限额
     */
    private String monthPaymentLimit;

    /**
     * 银行logo
     */
    private String bankLogo;

    /**
     *
     */
    private String createTime;

    /**
     *
     */
    private String modifyTime;


    @Override
    public String toString() {
        return "FtcSupportBank{" +
                "id=" + id +
                ", bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", singlePaymentLimit='" + singlePaymentLimit + '\'' +
                ", dayPaymentLimit='" + dayPaymentLimit + '\'' +
                ", monthPaymentLimit='" + monthPaymentLimit + '\'' +
                ", bankLogo='" + bankLogo + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSinglePaymentLimit() {
        return singlePaymentLimit;
    }

    public void setSinglePaymentLimit(String singlePaymentLimit) {
        this.singlePaymentLimit = singlePaymentLimit;
    }

    public String getDayPaymentLimit() {
        return dayPaymentLimit;
    }

    public void setDayPaymentLimit(String dayPaymentLimit) {
        this.dayPaymentLimit = dayPaymentLimit;
    }

    public String getMonthPaymentLimit() {
        return monthPaymentLimit;
    }

    public void setMonthPaymentLimit(String monthPaymentLimit) {
        this.monthPaymentLimit = monthPaymentLimit;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
