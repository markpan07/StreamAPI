package controller;

import model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.DepartmentService;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

  private final DepartmentService departmentService;

  public DepartmentController(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @GetMapping("/max-salary")
  public Employee findEmployeeWithMaxSalaryFromDepartment(
      @RequestParam("departmentId") int department) {
    return departmentService.findEmployeeWithMaxSalaryFromDepartment(department);
  }

  @GetMapping("/min-salary")
  public Employee findEmployeeWithMinSalaryFromDepartment(
      @RequestParam("departmentId") int department) {
    return departmentService.findEmployeeWithMinSalaryFromDepartment(department);
  }

  @GetMapping(value = "/all", params = "departmentId")
  public List<Employee> findEmployeesFromDepartment(@RequestParam("departmentId") int department) {
    return departmentService.findAllEmployeesFromDepartment(department);
  }

  @GetMapping("/all")
  public Map<Integer, List<Employee>> findEmployees() {
    return departmentService.findEmployeesByDepartment();
  }

}
