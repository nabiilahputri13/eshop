package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
public class Payment {
    String id;
    String method;
    Map<String, String> paymentData;
    Order order;

    String status;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.paymentData = paymentData;
        this.method = method;
        this.order = order;

        if (method.equals("VOUCHER_CODE")) {
            this.status = verifyCode();
        }
        else if (method.equals("BANK_TRANSFER")) {
            this.status = verifyTransfer();
        }
        else {
            throw new IllegalArgumentException("Payment method not valid");
        }

        if (status==null) {
            updateStatus();
        }

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("Payment method type cannot be empty");
        }

        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be empty");
        }
    }

    public String verifyCode() {
        String voucherCode = this.paymentData.get("voucherCode");

        if (voucherCode.length()!=16) {
            return "REJECTED";
        }
        else if (!voucherCode.startsWith("ESHOP")) {
            return "REJECTED";
        }
        int num = 0;
        for (int i=0; i<voucherCode.length(); i++) {
            if (Character.isDigit((voucherCode.charAt(i)))) {
                num+=1;
            }
        }
        if (num!=8) {
            return "REJECTED";
        }
        return "SUCCESS";
    }

    public String verifyTransfer() {
        String bankName = this.paymentData.get("bankName");
        String referenceCode = this.paymentData.get("referenceCode");

        if (bankName==null | Objects.equals(bankName, "") | referenceCode==null | Objects.equals(referenceCode, "")) {
            return "REJECTED";
        }
        return "SUCCESS";
    }

    public void updateStatus() {
        if (this.method.equals("VOUCHER_CODE")) {
            if (! this.paymentData.containsKey("voucherCode")) {
                throw new IllegalArgumentException("Cannot proceed payment");
            }
            this.status = verifyCode();
        }
        else if (this.method.equals("BANK_TRANSFER")) {
            if (! this.paymentData.containsKey("voucherCode")) {
                throw new IllegalArgumentException("Cannot proceed payment");
            }
            this.status = verifyTransfer();
        }
    }

    public Payment(String id, String method, String status, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;
        setStatus(status);
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
            if (status.equals(PaymentStatus.SUCCESS.getValue())) {
                order.setStatus("SUCCESS");
            } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
                order.setStatus("FAILED");
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}