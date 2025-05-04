package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.models.Employee;
import com.example.EmployeeManagement.models.Response;
import com.example.EmployeeManagement.service.EmployeeService;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chart")
public class AdminChartController {

    private static final Logger logger = LoggerFactory.getLogger(AdminChartController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPieChartDetails() {
        Map<String, Object> data = new HashMap<>();
        ArrayList<Employee> employeeList = employeeService.fetchAllEmployee();
        if(employeeList != null) {
            Long inActive = employeeList.stream().filter(e -> e.getStatus() == 0).count();
            Long active = employeeList.stream().filter(e -> e.getStatus() == 1).count();

//            Pie Chart One
            Map<String, Long> pieChart = employeeList.stream()
                    .collect(Collectors.groupingBy(e -> e.getDepartment().getDepartmentName(), Collectors.counting()));

//          Line Chart

            Map<String, Long> lineChart = employeeList.stream()
                    .collect(Collectors.groupingBy(e -> {
                                YearMonth yearMonth = YearMonth.from(e.getJoiningDate());
                                String month = yearMonth.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                                return month + " " + yearMonth.getYear();
                            },
                            TreeMap::new, Collectors.counting()));

            data.put("active", active);
            data.put("inActive", inActive);
            data.put("pieChartData", pieChart);
            data.put("lineChartData", lineChart);
        }
        Response response = new Response();
        response.setData(data);
        response.setMessage("Data Found");
        response.setStatus("1");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
