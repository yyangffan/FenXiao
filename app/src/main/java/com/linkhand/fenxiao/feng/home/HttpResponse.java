package com.linkhand.fenxiao.feng.home;

/**
 * Created by user on 2018/4/25.
 */

public class HttpResponse {

    /**
     * code : 100
     * info : alipay_sdk=alipay-sdk-php-20161101&app_id=2018032002412575&biz_content=%7B%22body%22%3A%22%5Cu652f%5Cu4ed8%5Cu6d4b%5Cu8bd5%22%2C%22subject%22%3A%22%5Cu652f%5Cu4ed8%5Cu6d4b%5Cu8bd5%22%2C%22out_trade_no%22%3Anull%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A0.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Fwww.sjfxs.com%2Findex.php%2Fsjapi%2Fpay%2Falipay_notify%2Fvip_order_id%2F46&sign_type=RSA2×tamp=2018-04-25+13%3A58%3A31&version=1.0&sign=nAYBAmtJ%2FNaCecuEN9SO%2FqW0xDTqlxorF3JTUE6NG9d5IAR%2FZIJln5ATxbtRpSX4MeGWfhGKRrZYaQ6dCbZ8Bh1buYWRIRfQpjvsNRCKxKBCNu%2BWIxIgXY1oU7WIrZLIvAQTDGcCynA%2FGq5mPKhrew0UF5E8Y1CPGmDIySxsg70SNfThu956KFCGiSVWXfMM0YjpWpwdwzP3zbOatz%2B4vLVH3SNHGSsh4qlbvZpegjqNE5ZdalsdIDUZgClupoSNmFRMONQjJ3ZRfykZtAsaiG0l8qHRjbczHC37ekgnNm%2Fe5BwjSpZ4VEA9jy5MOtIe1610LVe7RinDZJZll%2Fwn4Q%3D%3D
     * success : 下单成功
     */

    private int code;
    private String info;
    private String success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
