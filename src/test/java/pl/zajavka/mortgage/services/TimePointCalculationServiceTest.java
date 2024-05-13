package pl.zajavka.mortgage.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zajavka.mortgage.fixtures.TestDataFixtures;
import pl.zajavka.mortgage.model.InputData;
import pl.zajavka.mortgage.model.Installment;
import pl.zajavka.mortgage.model.TimePoint;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class TimePointCalculationServiceTest {
    @InjectMocks
    TimePointCalculationService timePointCalculationService = new TimePointCalculationServiceImpl();
    @Test
    @DisplayName("Should calculate for first installment TimePoint successfully")
    void calculateTimePointForFirstInstallment(){
        //given
        TimePoint expected = TestDataFixtures.someTimePoint();
        InputData inputData = TestDataFixtures.someInputData();
        //when
        TimePoint result = timePointCalculationService.calculate(BigDecimal.ONE, inputData);
        //then
        Assertions.assertEquals(expected, result);
    }
     @ParameterizedTest
    @MethodSource(value = "testMortgageData")
    @DisplayName("Should calculate other then first installments time points successfully")
    void calculateTimePointsOtherInstalments(
        LocalDate expectedLocalDate,
        BigDecimal instalmentNumber,
        BigDecimal year,
        BigDecimal month,
        LocalDate date)
     {
        //given
         TimePoint timePoint = TestDataFixtures.someTimePoint()
                 .withYear(year)
                 .withMonth(month)
                 .withDate(date);
         Installment installment = TestDataFixtures.someInstallment().withTimePoint(timePoint);
         TimePoint expectedTimePoint = timePoint.withDate(expectedLocalDate);
         //when
         TimePoint result = timePointCalculationService.calculate(instalmentNumber, installment);
         //then
         Assertions.assertEquals(expectedTimePoint,result);

     }
     public static Stream<Arguments> testMortgageData(){
        return Stream.of(
             Arguments.of(
                     LocalDate.of(2010,2,1),
                     BigDecimal.valueOf(12),
                     BigDecimal.ONE,
                     BigDecimal.valueOf(12),
                     LocalDate.of(2010,1,1)),
                Arguments.of(
                        LocalDate.of(2010,2,1),
                        BigDecimal.valueOf(15),
                        BigDecimal.valueOf(2),
                        BigDecimal.valueOf(3),
                        LocalDate.of(2010,1,1)),
                Arguments.of(
                        LocalDate.of(2013,10,1),
                        BigDecimal.valueOf(76),
                        BigDecimal.valueOf(7),
                        BigDecimal.valueOf(4),
                        LocalDate.of(2013,9,1))
                );
    }

}