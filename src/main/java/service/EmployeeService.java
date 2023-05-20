package service;

import exception.*;
import model.Employee;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private static final int LIMIT = 10;
    private final List<Employee> employees = new ArrayList<>();

    public Employee add(String name,
                        String surname,
                        int department,
                        int salary) {
        if (!StringUtils.isAlpha(name) || !StringUtils.isAlpha(surname)) {
            throw new EmployeeBadNameException();
        }
        if (department <= 0) {
            throw new EmployeeBadDepartmentException();
        }
        if (salary <= 0) {
            throw new EmployeeBadSalaryException();
        }
        Employee employee = new Employee(name, surname, department, salary);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < LIMIT) {
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }

    public Employee remove(String name,
                           String surname,
                           int department,
                           int salary) {
        Employee employee = new Employee(name, surname, department, salary);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        employees.remove(employee);
        return employee;
    }

    public Employee find(String name,
                         String surname,
                         int department,
                         int salary) {
        Employee employee = new Employee(name, surname, department, salary);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }


}
