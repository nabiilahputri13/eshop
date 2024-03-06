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
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).save(payment);

        Payment result = paymentService.addPayment(payment.getOrder(), payment.getMethod(), payment.getPaymentData());
        verify(paymentRepository, times(1)).save(payment);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testSetStatus() {
        Payment payment = payments.get(1);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());

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

        assertThrows(IllegalArgumentException.class, () -> {
            Payment result = paymentService.setStatus(newPayment, "HARAM");
        });
    }

    @Test
    void testSetValidStatus() {
        Payment payment = payments.get(1);

        Payment newPayment1 = new Payment(payment.getId(), payment.getMethod(), payment.getPaymentData(), payment.getOrder());
        Payment result1 = paymentService.setStatus(newPayment1, "SUCCESS");
        assertEquals(newPayment1.getId(), result1.getId());
        assertEquals(OrderStatus.SUCCESS.getValue(), result1.getOrder().getStatus());

        Payment newPayment2 = new Payment(payment.getId(), payment.getMethod(), payment.getPaymentData(), payment.getOrder());
        Payment result2 = paymentService.setStatus(newPayment1, "REJECTED");
        assertEquals(newPayment2.getId(), result2.getId());
        assertEquals(OrderStatus.FAILED.getValue(), result1.getOrder().getStatus());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        String paymentId = "nonExistentId";
        when(paymentRepository.findById(paymentId)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> {
            paymentService.getPayment(paymentId);
        });
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> results = paymentService.getAllPayments();
        assertEquals(payments, results);
    }
}
