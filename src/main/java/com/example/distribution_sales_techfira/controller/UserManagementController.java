package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.entity.Branch;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.entity.Role;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.UserMapper;
import com.example.distribution_sales_techfira.repository.BranchRepository;
import com.example.distribution_sales_techfira.repository.CompanyRepository;
import com.example.distribution_sales_techfira.repository.RoleRepository;
import com.example.distribution_sales_techfira.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserManagementController {

    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserManagementController(UserService userService,
                                    RoleRepository roleRepository,
                                    CompanyRepository companyRepository,
                                    BranchRepository branchRepository,
                                    UserMapper userMapper) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
        this.branchRepository = branchRepository;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserManagementDTO registrationDTO) {
        try {
            if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
                return new ResponseEntity<>(
                        new RegistrationResponseDTO(false, "Passwords do not match", null),
                        HttpStatus.BAD_REQUEST
                );
            }

            Role selectedRole = roleRepository.findByName(registrationDTO.getRoleName());
            if (selectedRole == null) {
                return new ResponseEntity<>(
                        new RegistrationResponseDTO(false, "Role not found", null),
                        HttpStatus.BAD_REQUEST
                );
            }

            UserReqDTO userReqDTO = new UserReqDTO();
            userReqDTO.setName(registrationDTO.getName());
            userReqDTO.setEmail(registrationDTO.getEmail());
            userReqDTO.setPassword(registrationDTO.getPassword());
            userReqDTO.setRole(selectedRole);
            userReqDTO.setCompanyId(registrationDTO.getCompanyId());
            userReqDTO.setBranchId(registrationDTO.getBranchId());

            UserResDTO savedUser = userService.save(userReqDTO);
            return new ResponseEntity<>(new RegistrationResponseDTO(true, "Registration successful", savedUser), HttpStatus.CREATED);

        } catch (CustomException e) {
            return new ResponseEntity<>(new RegistrationResponseDTO(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new RegistrationResponseDTO(false, "An error occurred during registration", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdminUser(@Valid @RequestBody UserManagementDTO dto) {
        try {
            Role role = roleRepository.findByName(dto.getRoleName());
            if (role == null) {
                return new ResponseEntity<>(
                        new RegistrationResponseDTO(false, "Invalid role specified", null),
                        HttpStatus.BAD_REQUEST
                );
            }

            UserReqDTO userReqDTO = new UserReqDTO();
            userReqDTO.setName(dto.getName());
            userReqDTO.setEmail(dto.getEmail());
            userReqDTO.setPassword(dto.getPassword());
            userReqDTO.setRole(role);
            userReqDTO.setCompanyId(dto.getCompanyId());
            userReqDTO.setBranchId(dto.getBranchId());

            UserResDTO savedUser = userService.save(userReqDTO);
            return new ResponseEntity<>(new RegistrationResponseDTO(true, "Admin user registration successful", savedUser), HttpStatus.CREATED);

        } catch (CustomException e) {
            return new ResponseEntity<>(new RegistrationResponseDTO(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new RegistrationResponseDTO(false, "An error occurred during admin registration", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserFullResDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserFullResDTO> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<>(
                userMapper.toFullDTO(userService.getUserById(id)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(new ResponseDTO(true, "User deleted successfully", null), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(new ResponseDTO(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(false, "An error occurred during user deletion", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable Integer id, @Valid @RequestBody UserUpdateDTO updateDTO) {
        try {
            UserResDTO updatedUser = userService.updateUserById(id, updateDTO);
            return new ResponseEntity<>(new ResponseDTO(true, "User updated successfully", updatedUser), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(new ResponseDTO(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(false, "An error occurred during user update", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/status/{id}/{status}")
    public ResponseEntity<ResponseDTO> updateUserStatus(@PathVariable Integer id, @PathVariable int status) {
        try {
            userService.updateStatus(id, status);
            return new ResponseEntity<>(new ResponseDTO(true, "Status updated", null), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(new ResponseDTO(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/paginated")
    public ResponseEntity<Map<String, Object>> getUsersPaginated(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(userService.findUsersPaginated(page, size), HttpStatus.OK);
    }

    @GetMapping("/companies")
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping("/branches/by-company/{companyId}")
    public List<Branch> getBranchesByCompany(@PathVariable Integer companyId) {
        return branchRepository.findByCompanyId(companyId);
    }
}
