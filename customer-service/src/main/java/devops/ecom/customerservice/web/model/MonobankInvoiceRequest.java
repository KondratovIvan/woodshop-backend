package devops.ecom.customerservice.web.model;

import lombok.Data;

import java.util.List;

@Data
public class MonobankInvoiceRequest {
    private int amount;
    private int ccy;
    private MerchantPaymInfo merchantPaymInfo;
    private String redirectUrl;
    private int validity;
    private String paymentType;

    @Data
    public static class MerchantPaymInfo {
        private String reference;
        private String destination;
        private String comment;
        private List<BasketOrderItem> basketOrder;
    }

    @Data
    public static class BasketOrderItem {
        private String name;
        private int qty;
        private int sum;
        private int total;
        private String icon;
        private String unit;
        private String code;
    }
}

