package com.spring.jwt.service;

import com.spring.jwt.MapperClasses.EmployeeMapper;
import com.spring.jwt.dto.EmployeeDTO;
import com.spring.jwt.entity.Employee;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.toEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toDTO(savedEmployee);
    }



    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return EmployeeMapper.toDTO(employee);
    }

    public EmployeeDTO updateEmployeePartially(Long id, Map<String, Object> updates) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        Map<String, BiConsumer<Employee, Object>> fieldUpdaters = Map.of(
                "name", (emp, value) -> emp.setName((String) value),
                "position", (emp, value) -> emp.setPosition((String) value),
                "contact", (emp, value) -> emp.setContact((String) value),
                "address", (emp, value) -> emp.setAddress((String) value),
                "email", (emp, value) -> emp.setEmail((String) value),
                "username", (emp, value) -> emp.setUsername((String) value),
                "userId", (emp, value) -> emp.setUserId((Integer) value),
                "componentNames", (emp, value) -> emp.setComponentNames((List<String>) value)
        );

        updates.entrySet().stream()
                .filter(entry -> fieldUpdaters.containsKey(entry.getKey()))
                .forEach(entry -> fieldUpdaters.get(entry.getKey()).accept(existingEmployee, entry.getValue()));

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeMapper.toDTO(updatedEmployee);
    }


    public void deleteEmployee(Long id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        employeeRepository.delete(existingEmployee);
    }
}
