package com.example.EmployeeManagement.serviceImpl;

import com.example.EmployeeManagement.dao.DepartmentRepository;
import com.example.EmployeeManagement.dao.EmployeeRepository;
import com.example.EmployeeManagement.models.Department;
import com.example.EmployeeManagement.models.Employee;
import com.example.EmployeeManagement.models.User;
import com.example.EmployeeManagement.service.EmailService;
import com.example.EmployeeManagement.service.EmployeeService;
import com.example.EmployeeManagement.service.SMSService;
import com.example.EmployeeManagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.UUID;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Integer addEmployee(Employee employee) {
        logger.info("Inside add Employee Method serviceimpl");
        try {
            if(employee.getEmp_id() != null) {
                employeeRepository.getReferenceById(employee.getEmp_id());
                employee.setUpdatedDate(LocalDateTime.now());
                employeeRepository.save(employee);
            }
            else {
                User user = new User();
                String username = getUniqueUserName("EMPLOYEE");
                user.setUserName(username);
                user.setPassword(encoder.encode("EmsUser@1234"));
                user.setStatus(1);
                user.setRole("EMPLOYEE");
                Integer res = userService.addNewUser(user);
                if(res == 1) {
                    employee.setUser(user);
                    employee.setCreateDate(LocalDateTime.now());
                    employee.setStatus(1);
                    employeeRepository.save(employee);
                    String name = employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName();
                    String to = employee.getEmail();
                    String subject = "Employee Registration Completed Successfully";
                    String body = "Dear " + name + ",\n\n" +
                            "Your account has been successfully created on EMS.\n\n" +
                            "Login Credentials\n" +
                            "Username: " + employee.getEmail() + "\n" +
                            "Password: " + "EmsUser@1234" + "\n\n" +
                            "Thanks and Regards,\n" +
                            "Team EMS";
                    emailService.sendEmail(to, subject, body);
                    String mobileNumber = "+91" + employee.getMobile();
                    String smsMessageBody = "Hi " + name +
                            ", your EMS account is active. Check your email for login details. - EMS Team";

                    smsService.sendSMS(mobileNumber, smsMessageBody);
                }
                else {
                    return 0;
                }
            }
        } catch (Exception e) {
            logger.error("Error found",e);
            return 0;
        }
        logger.info("Outside add Employee Method serviceimpl");
        return 1;
    }

    @Override
    public Page<Employee> fetchEmployee(PageRequest pageRequest) {
        logger.info("Inside Fetch Employee method of service impl");
        try {
            if(pageRequest != null) {
                Page<Employee> pageEmployees = employeeRepository.findAll(pageRequest);
                return pageEmployees;
            }
            else {
                Page<Employee> employeeList = (Page<Employee>) employeeRepository.findAll();
                return employeeList;
            }
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public ArrayList<Employee> fetchAllEmployee() {
        logger.info("Inside Fetch All Employee method of service impl");
        try {
                return (ArrayList<Employee>) employeeRepository.findAll();
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    @Override
    public Integer deleteEmployee(Long emp_id) {
        logger.info("Inside Delete Employee method of Service Impl");
        try {
            if(emp_id != null) {
                Employee emp = employeeRepository.getReferenceById(emp_id);
                emp.setStatus(0);
                employeeRepository.save(emp);
                return 1;
            }
            else {
                return 0;
            }
        } catch (Exception e) {
            logger.error("Error Found", e);
            return 0;
        }
    }

    @Override
    public Integer addDepartment(String departmentName) {
        logger.info("Inside Add Department method of Service Impl");
        try {
            Department department = new Department();
            department.setDepartmentName(departmentName);
            department.setCreatedDate(LocalDateTime.now());
            department.setStatus(1);
            departmentRepository.save(department);
            return 1;
        } catch (Exception e) {
            logger.error("Error Found", e);
            return 0;
        }
    }

    @Override
    public ArrayList<Department> fetchDepartment() {
        logger.info("Inside View Department method of Service Impl");
        try {
            return (ArrayList<Department>) departmentRepository.findAll();
        } catch (Exception e) {
            logger.error("Error Found", e);
            return null;
        }
    }

    public String getUniqueUserName(String role) {
        String prefix = role.substring(0, 3).toUpperCase();
        String year = String.valueOf(Year.now().getValue());
        int random2Digit = (int)(Math.random() * 90) + 10;
        char roleInitial = Character.toUpperCase(role.charAt(0));
        String randomUUID = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 3).toUpperCase();
        return (prefix + year + random2Digit + roleInitial + randomUUID);
    }
}
