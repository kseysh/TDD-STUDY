package chap03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class ExpiryDateCalculatorTest {
    private void assertExpiryDate(
            PayData payData, LocalDate expectedExpiryDate){
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);
        assertEquals(expectedExpiryDate,realExpiryDate);
    }

    @Test
    void 만원_납부하면_한달_뒤가_만료일이_됨() { // 이게 가장 쉬울 것 같으므로
//        LocalDate billingDate = LocalDate.of(2019, 3, 1);
//        int payAmount = 10_000;
//
//        ExpiryDateCalculator cal = new ExpiryDateCalculator();
//        LocalDate expiryDate = cal.calculateExpiryDate(billingDate, payAmount);
//
//        assertEquals(LocalDate.of(2019,4,1),expiryDate);
//
//        LocalDate billingDate2 = LocalDate.of(2019, 5, 5);
//        int payAmount2 = 10_000;
//
//        ExpiryDateCalculator cal2 = new ExpiryDateCalculator();
//        LocalDate expiryDate2 = cal2.calculateExpiryDate(billingDate2, payAmount2);
//
//        assertEquals(LocalDate.of(2019,6,5),expiryDate2);

//        assertExpiryDate(
//                LocalDate.of(2019,3,1),10000,
//                LocalDate.of(2019,4,1));
//        assertExpiryDate(
//                LocalDate.of(2019,5,5),10000,
//                LocalDate.of(2019,6,5));
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,3,1))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019,4,1));
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,5,5))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019,6,5));
    }

    @Test
    void 납부일과_한달_뒤_일자가_같지_않음(){
//        assertExpiryDate(
//                LocalDate.of(2019,1,31),10_000,
//                LocalDate.of(2019,2,28));
//        assertExpiryDate(
//                LocalDate.of(2019,5,31),10_000,
//                LocalDate.of(2019,6,30));
//        assertExpiryDate(
//                LocalDate.of(2020,1,31),10_000,
//                LocalDate.of(2020,2,29));//
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,1,31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019,2,28));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,5,31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019,6,30));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,1,31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019,2,28));//오류 발견!

    }// 납부일 기준으로 다음달의 같은 날이 만료일이 아닌 상황

    @Test
    void 첫_납부일과_만료일_일자가_다를때_만원_납부(){
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2019,1,31))// 첫 납부일을 추가해준다.
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData, LocalDate.of(2019,3,31));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019,1,30))// 첫 납부일을 추가해준다.
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData2, LocalDate.of(2019,3,30));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019,5,31))// 첫 납부일을 추가해준다.
                .billingDate(LocalDate.of(2019,6,30))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData3, LocalDate.of(2019,7,31));

    }

    @Test
    void 이만원_이상_납부하면_비례해서_만료일_계산(){
        assertExpiryDate(
                PayData.builder()
                .billingDate(LocalDate.of(2019,3,1))
                .payAmount(20_000)
                .build(),
                LocalDate.of(2019,5,1));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019,3,1))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2019,6,1));
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부(){
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019,1,31))
                        .billingDate(LocalDate.of(2019,2,28))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2019,4,30));
    }
}
