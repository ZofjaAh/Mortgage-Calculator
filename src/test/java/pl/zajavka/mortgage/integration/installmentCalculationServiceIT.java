package pl.zajavka.mortgage.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.mortgage.configuration.CalculatorConfiguration;
import pl.zajavka.mortgage.fixtures.TestDataFixtures;
import pl.zajavka.mortgage.services.InstallmentCalculationService;

@SpringJUnitConfig(classes = CalculatorConfiguration.class)
public class installmentCalculationServiceIT {
    @Autowired
    private InstallmentCalculationService installmentCalculationService;

     @BeforeEach
    public void setUp(){
         Assertions.assertNotNull( installmentCalculationService);
     }
     @Test
     @DisplayName("Test installment calculation")
    void test(){
         //given
         final  var inputData = TestDataFixtures.someInputData();
         //when
         final var result = installmentCalculationService.calculate(inputData);
         //then
         Assertions.assertEquals(180, result.size());
         Assertions.assertEquals(TestDataFixtures.someInstallment5(), result.get(4));
         Assertions.assertEquals(TestDataFixtures.someInstallment10(), result.get(9));
         Assertions.assertEquals(TestDataFixtures.someInstallment40(), result.get(39));
         Assertions.assertEquals(TestDataFixtures.someInstallment80(), result.get(79));
     }
}
