package service;

import exception.*;
import model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.apache.commons.lang3.StringUtils;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.stream.Stream;

import static constants.ConstantsForTest.*;

class EmployeeServiceTest {
    public EmployeeService out = new EmployeeService();

    public static Stream<Arguments> addIncorrectNameParams() {
        return Stream.of(
                Arguments.of("name1"),
                Arguments.of("name&"),
                Arguments.of("name!"),
                Arguments.of("name_"),
                Arguments.of(" "),
                Arguments.of("")

        );
    }

    public static Stream<Arguments> addIncorrectSurnameParams() {
        return Stream.of(
                Arguments.of("name1"),
                Arguments.of("name&"),
                Arguments.of("name!"),
                Arguments.of("name_"),
                Arguments.of(" "),
                Arguments.of("")

        );
    }

    public static Stream<Arguments> addIncorrectDepartmentParams() {
        return Stream.of(
                Arguments.of(-10),
                Arguments.of(0)
        );
    }

    public static Stream<Arguments> addIncorrectSalaryParams() {
        return Stream.of(
                Arguments.of(-10),
                Arguments.of(0)
        );
    }

    @BeforeEach
    public void beforeEach() {
        for (int i = 0; i < 9; i++) {
            if (i < 3) {
                out.add("Name" + StringUtils.repeat("f", i), "Surname" + StringUtils.repeat("f", i), 1, 5000 + i * 1000);
            } else {
                out.add("Name" + StringUtils.repeat("f", i), "Surname" + StringUtils.repeat("f", i), 1, 5000 + i * 1000);
            }
        }
    }

    @AfterEach
    public void afterEach() {
        out.getAll()
                .forEach(employee -> out.remove(employee.getName(), employee.getSurname(), employee.getDepartment(), employee.getSalary()));
    }

    //Adding tests

    @Test
    void addCorrectTest() {
        int countBefore = out.getAll().size();
        Employee expected = new Employee(NAME, SURNAME, DEPARTMENT_1, SALARY);
        Employee actual = out.add(NAME, SURNAME, DEPARTMENT_1, SALARY);
        Assertions.assertThat(actual)
                .isEqualTo(expected)
                .isIn(out.getAll());
        Assertions.assertThat(out.getAll()).hasSize(countBefore + 1);
        Assertions.assertThat(out.find(NAME, SURNAME, DEPARTMENT_1, SALARY)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("addIncorrectNameParams")
    void addIncorrectNameTest(String incorrectName) {
        Assertions.assertThatExceptionOfType(EmployeeBadNameException.class)
                .isThrownBy(() -> out.add(incorrectName, SURNAME, DEPARTMENT_1, SALARY));
    }

    @ParameterizedTest
    @MethodSource("addIncorrectSurnameParams")
    void addIncorrectSurnameTest(String incorrectSurname) {
        Assertions.assertThatExceptionOfType(EmployeeBadNameException.class)
                .isThrownBy(() -> out.add(NAME, incorrectSurname, DEPARTMENT_1, SALARY));
    }


    @ParameterizedTest
    @MethodSource("addIncorrectDepartmentParams")
    void addIncorrectDepartmentTest(int incorrectDepartment) {
        Assertions.assertThatExceptionOfType(EmployeeBadDepartmentException.class)
                .isThrownBy(() -> out.add(NAME, SURNAME, incorrectDepartment, SALARY));
    }

    @ParameterizedTest
    @MethodSource("addIncorrectSalaryParams")
    void addIncorrectSalaryTest(int incorrectSalary) {
        Assertions.assertThatExceptionOfType(EmployeeBadSalaryException.class)
                .isThrownBy(() -> out.add(NAME, SURNAME, DEPARTMENT_1, incorrectSalary));
    }

    @Test
    void addAlreadyAddedTest() {
        out.add(NAME, SURNAME, DEPARTMENT_1, SALARY);
        Assertions.assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> out.add(NAME, SURNAME, DEPARTMENT_1, SALARY));
    }

    @Test
    void storageIsFullTest() {
        out.add("asd", "hbasyjd", 1, 10_000);
        Assertions.assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> out.add(NAME, SURNAME, DEPARTMENT_1, SALARY));
    }


    @Test
    void removeCorrect() {
        Employee expected = out.add(NAME, SURNAME, DEPARTMENT_1, SALARY);
        Assertions.assertThat(expected).isIn(out.getAll());
        int countBefore = out.getAll().size();
        Assertions.assertThat(out.remove(NAME, SURNAME, DEPARTMENT_1, SALARY))
                .isEqualTo(expected)
                .isNotIn(out.getAll());
        Assertions.assertThat(out.getAll().size()).isEqualTo(countBefore - 1);
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.find(NAME, SURNAME, DEPARTMENT_1, SALARY));

    }

    @Test
    void removeWhenNotFound() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.remove(NAME, SURNAME, DEPARTMENT_1, SALARY));

    }

    @Test
    void findCorrect() {
        Employee expected = new Employee("Name", "Surname", 1, 5000);
        Assertions.assertThat(out.find("Name", "Surname", 1, 5000))
                .isIn(out.getAll())
                .isEqualTo(expected);
    }

    @Test
    void findWhenNotFound() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.find(NAME, SURNAME, DEPARTMENT_1, SALARY));

    }

    @Test
    void getAll() {
        ArrayList<Employee> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (i < 3) {
                list.add(new Employee("Name" + StringUtils.repeat("f", i), "Surname" + StringUtils.repeat("f", i), 1, 5000 + i * 1000));
            } else {
                list.add(new Employee("Name" + StringUtils.repeat("f", i), "Surname" + StringUtils.repeat("f", i), 1, 5000 + i * 1000));
            }
        }
        Assertions.assertThat(out.getAll())
                .containsAll(list);

    }
}