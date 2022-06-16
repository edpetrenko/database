package edu.petrenko.petrenkodb.controller;


import edu.petrenko.petrenkodb.entity.Department;
import edu.petrenko.petrenkodb.entity.Employee;
import edu.petrenko.petrenkodb.service.DepartmentService;
import edu.petrenko.petrenkodb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/")
    public String showAllEmployees(Model model) {
        List<Employee> employeeList = employeeService.getAllEmployees();
        model.addAttribute("employeeList", employeeList);
        return "all-employees";
    }

    @RequestMapping("/addEmployee")
    public String addEmployee(Model model) {
        List<Department> departmentList = departmentService.getAllDepartments();
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        model.addAttribute("departmentList", departmentList);
        return "info-employee";
    }

    @RequestMapping("/changeEmployee")
    public String changeEmployee(@RequestParam("empId") int id, Model model) {
        List<Department> departmentList = departmentService.getAllDepartments();
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employee);
        model.addAttribute("departmentList", departmentList);
        return "info-employee";
    }

    @RequestMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee, @RequestParam(value = "depart") int depId) {
        if (depId != 0) {
            Department department = departmentService.getDepartment(depId);
            employee.setDepartment(department);
        }
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @RequestMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("empId") int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

}
