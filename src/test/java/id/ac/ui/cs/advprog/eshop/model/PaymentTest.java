package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class PaymentTest {
    private List<Product> products;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        this.products.add(product1);
        this.products.add(product2);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708560000L, "Safira Sudrajat");
        Order order2 = new Order("f420a187-f5b9-4fd2-bca2-e363ce14fe5e",
                this.products, 1708560000L, "Bambang Anak Safira");
        this.orders.add(order1);
        this.orders.add(order1);
    }

    //MAIN FEATURE
    @Test
    void testEmptyPaymentMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                    "", paymentData, this.orders.getFirst());
        });
    }

    @Test
    void testInvalidPaymentMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                    "GAK_BAYAR", paymentData, this.orders.getFirst());
        });
    }

    @Test
    void testEmptyPaymentData() {
        Map<String, String> paymentData = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                    "VOUCHER_CODE", paymentData, this.orders.getFirst());
        });
    }

    @Test
    void testNullOrder() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                    "VOUCHER_CODE", paymentData, null);
        });
    }

    @Test
    void testDuplicatePayment() {
        Map<String, String> paymentData = new HashMap<String, String>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "VOUCHER_CODE", paymentData, this.orders.getFirst());

        assertSame(this.orders.getFirst(), payment.getOrder());
    }

    //VOUCHER CODE
    @Test
    void testValidVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "VOUCHER_CODE", paymentData, this.orders.getFirst(), "SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testInvalid16CharacterVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC567");

        Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "VOUCHER_CODE", paymentData, this.orders.getFirst(), "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidESHOPVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "NGANTUK12345678A");

        Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "VOUCHER_CODE", paymentData, this.orders.getFirst(), "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalid8NumericalVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC56BS");

        Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "VOUCHER_CODE", paymentData, this.orders.getFirst(), "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    //BANK TRANSFER
    @Test
    void testValidBankTransfer() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BANK KODOK");
        paymentData.put("deliveryFee", "NGANTUK123");

        Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "BANK_TRANSFER", paymentData, this.orders.getFirst(), "SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testInvalidNullBankNameBankTransfer() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "NGANTUK123");

        Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "BANK_TRANSFER", paymentData, this.orders.getFirst(), "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidEmptyBankNameBankTransfer() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "NGANTUK123");

        Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "BANK_TRANSFER", paymentData, this.orders.getFirst(), "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidNullReferenceCodeBankTransfer() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BANK KODOK");
        paymentData.put("referenceCode", null);

        Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "BANK_TRANSFER", paymentData, this.orders.getFirst(), "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidEmptyBanknameBankTransfer() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BANK KODOK");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "BANK_TRANSFER", paymentData, this.orders.getFirst(), "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }
    }
