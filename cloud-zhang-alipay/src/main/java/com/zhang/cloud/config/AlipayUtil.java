package com.zhang.cloud.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.zhang.cloud.entities.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author 98549
 * @date 2021/11/6 12:28
 */
@Component
public class AlipayUtil {

    @Resource
    private AlipayConfig aliPayProperties ;

    private static AlipayConfig staticAliPayProperties;

    static AlipayClient alipayClient;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        staticAliPayProperties = aliPayProperties;
        alipayClient = new DefaultAlipayClient(staticAliPayProperties.getAli_gatewayUrl(), staticAliPayProperties.getAli_app_id(), staticAliPayProperties.getAli_merchant_private_key(), "json", staticAliPayProperties.getAli_charset(), staticAliPayProperties.getAli_alipay_public_key(), staticAliPayProperties.getAli_sign_type());
    }
    /**
     * alipay.trade.wap.pay：H5手机网站支付接口2.0（外部商户创建订单并支付）
     * @param order：订单信息
     * @return
     */
    public static String alipayTradeWapPay(Order order){
        try {
            //（1）封装bizmodel信息
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            model.setOutTradeNo(order.getOrderId());
            model.setSubject(order.getSubject());
            model.setBody("支付宝手机网站支付");
            model.setProductCode("QUICK_WAP_WAY");
            model.setTotalAmount(order.getAmount());
            model.setTimeoutExpress(staticAliPayProperties.getAli_timeout_express());
            model.setQuitUrl(staticAliPayProperties.getAli_quit_url());
            //（2）设置请求参数
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
            alipayRequest.setReturnUrl(staticAliPayProperties.getAli_return_url());
            alipayRequest.setNotifyUrl(staticAliPayProperties.getAli_notify_url());
            alipayRequest.setBizModel(model);
            //（3）请求
            String form = alipayClient.pageExecute(alipayRequest).getBody();
            return form;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}