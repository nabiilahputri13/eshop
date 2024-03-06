package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNull;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
     List<Product> products;
     List<Order> orders;

    List<Payment> payments;

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

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
        this.orders.add(order2);

        payments = new ArrayList<>();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment1 = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "VOUCHER_CODE", paymentData1, this.orders.getFirst());

        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "BANK KODOK");
        paymentData2.put("referenceCode", "NGANTUK123");

        Payment payment2 = new Payment("dba9cbfa-7c51-41cb-b35f-654ddff911a0",
                "BANK_TRANSFER", paymentData2, this.orders.getFirst());

        payments.add(payment2);
    }

    @Test
    void testAddPayment() {
        UUID uuid = UUID.randomUUID();
        String paymentId = uuid.toString();
        Payment payment = new Payment(paymentId, payments.getFirst().getMethod(), payments.getFirst().getStatus(), payments.getFirst().getPaymentData(), orders.getFirst());

        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(orders.getFirst(), payments.getFirst().getMethod(), payments.getFirst().getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatus() {
        Payment payment = payments.get(1);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());

        Payment newPayment = payments.get(0);
        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(newPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetInvalidStatus() {
        Payment payment = payments.get(1);

        Payment newPayment = new Payment(payment.getId(), payment.getMethod(), payment.getPaymentData(), payment.getOrder());

        assertThrows(NoSuchElementException.class, () -> {
            Payment result = paymentService.setStatus(newPayment, "HARAM");
        });
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> results = paymentService.getAllPayments();
        assertEquals(payments, results);
    }
}
