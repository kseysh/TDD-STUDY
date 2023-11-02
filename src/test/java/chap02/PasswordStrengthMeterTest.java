package chap02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordStrengthMeterTest {

    private PasswordStrengthMeter meter = new PasswordStrengthMeter();

    @Test
    void meetsAllCriteria_Then_Strong() {
        PasswordStrength result = meter.meter("ab12!@AB");
        assertEquals(PasswordStrength.STRONG, result);

    }

    @Test
    void meetsOtherCriteria_except_for_Length_Then_Normal() {
        PasswordStrength result = meter.meter("ab12!@A");
        assertEquals(PasswordStrength.NOMAL, result);
    }

    @Test
    void meetsOtherCriteria_except_for_number_Then_Normal() {
        PasswordStrength result = meter.meter("abc!@ABC");
        assertEquals(PasswordStrength.NOMAL, result);
    }

    @Test
    void meetsOtherCriteria_except_for_Uppercase_Then_Normal() {
        PasswordStrength result = meter.meter("abc12@!!");
        assertEquals(PasswordStrength.NOMAL, result);
    }

    @Test
    void 길이가_8글자_이상인_조건만_충족() {
        PasswordStrength result = meter.meter("abcdefgh");
        assertEquals(PasswordStrength.WEAK, result);
    }

    @Test
    void nullInput_Then_Invalid() {
        PasswordStrength result = meter.meter(null);
        assertEquals(PasswordStrength.INVALID, result);
    }

    @Test
    void emptyInput_Then_Invalid() {
        PasswordStrength result = meter.meter("");
        assertEquals(PasswordStrength.INVALID, result);
    }

    @Test
    void 숫자_포함_조건만_충족() {
        PasswordStrength result = meter.meter("12345");
        assertEquals(PasswordStrength.WEAK, result);
    }

    @Test
    void 대문자_포함_조건만_충족() {
        PasswordStrength result = meter.meter("ABCEF");
        assertEquals(PasswordStrength.WEAK, result);
    }

    @Test
    void 아무조건도_충족하지_않음() {
        PasswordStrength result = meter.meter("abc");
        assertEquals(PasswordStrength.WEAK, result);
    }
}
