package service;

import exception.EmployeeNotFoundException;
import model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService out;

    private List<Employee> list = new ArrayList<>();

    public static Stream<Arguments> findEmployeeWithMinSalaryFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Name", "Surname", 1, 5000)),
                Arguments.of(2, new Employee("Nameffff", "Surnameffff", 2, 9000))
        );
    }

    public static Stream<Arguments> findEmployeeWithMaxSalaryFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Namefff", "Surnamefff", 1, 8000)),
                Arguments.of(2, new Employee("Nameffffffff", "Surnameffffffff", 2, 13000))
        );
    }

    public static Stream<Arguments> findAllEmployeesByDepartmentParams() {
        List<Employee> list1 = new ArrayList<>();
        List<Employee> list2 = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (i < 4) {
                list1.add(new Employee("Name" + StringUtils.repeat("f", i), "Surname" + StringUtils.repeat("f", i), 1, 5000 + i * 1000));
            } else {
                list2.add(new Employee("Name" + StringUtils.repeat("f", i), "Surname" + StringUtils.repeat("f", i), 2, 5000 + i * 1000));
            }
        }
        return Stream.of(
                Arguments.of(1, list1),
                Arguments.of(2, list2),
                Arguments.of(5, Collections.emptyList())


        );
    }

    public static Stream<Arguments> totalSalariesForDepartmentParams() {
        List<Employee> list1 = new ArrayList<>();
        List<Employee> list2 = new ArrayList<>();
        int sum1 = 0;
        int sum2 = 0;

        for (int i = 0; i < 9; i++) {
            if (i < 4) {
                list1.add(new Employee("Name" + StringUtils.repeat("f", i), "Surname" + StringUtils.repeat("f", i), 1, 5000 + i * 1000));
            } else {
                list2.add(new Employee("Name" + StringUtils.repeat("f", i), "Surname" + StringUtils.repeat("f", i), 2, 5000 + i * 1000));
            }
        }

        for (int i = 0; i < list1.size(); i++) {
            sum1 += list1.get(i).getSalary();
        }

        for (int i = 0; i < list2.size(); i++) {
            sum2 += list2.get(i).getSalary();
        }
        return Stream.of(
                Arguments.of(1, sum1),
                Arguments.of(2, sum2)
        );
    }

    @BeforeEach
    public void beforeEach() {
        for (int i = 0; i < 9; i++) {
            if (i < 4) {
                list.add(new Employee("Name" + StringUtils.repeat("f", i), "Surname" + StringUtils.repeat("f", i), 1, 5000 + i * 1000));
            } else {
                list.add(new Employee("Name" + StringUtils.repeat("f", i), "Surname" + StringUtils.repeat("f", i), 2, 5000 + i * 1000));
            }
        }
        Mockito.when(employeeService.getAll()).thenReturn(list);
    }

    @ParameterizedTest
    @MethodSource("totalSalariesForDepartmentParams")
    void totalSalariesForDepartment(int department, int sum) {
        Assertions.assertThat(out.totalSalariesForDepartment(department))
                .isEqualTo(sum);
    }

    @ParameterizedTest
    @MethodSource("findEmployeeWithMinSalaryFromDepartmentParams")
    public void findEmployeeWithMinSalaryFromDepartment(int department, Employee expected) {
        Assertions.assertThat(expected)
                .isEqualTo(out.findEmployeeWithMinSalaryFromDepartment(department));
    }

    @Test
    public void findEmployeeWithMinSalaryFromIncorrectDepartment() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.findEmployeeWithMinSalaryFromDepartment(4));
    }

    @ParameterizedTest
    @MethodSource("findEmployeeWithMaxSalaryFromDepartmentParams")
    public void findEmployeeWithMaxSalaryFromDepartment(int department, Employee expected) {
        Assertions.assertThat(expected)
                .isEqualTo(out.findEmployeeWithMaxSalaryFromDepartment(department));
    }

    @Test
    public void findEmployeeWithMaxSalaryFromIncorrectDepartment() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.findEmployeeWithMaxSalaryFromDepartment(4));
    }

    @ParameterizedTest
    @MethodSource("findAllEmployeesByDepartmentParams")
    public void findAllEmployeesByDepartment(int department, List<Employee> expected) {
        Assertions.assertThat(out.findAllEmployeesFromDepartment(department))
                .containsExactlyInAnyOrderElementsOf(expected);
    }


}