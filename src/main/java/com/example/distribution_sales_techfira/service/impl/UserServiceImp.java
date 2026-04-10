package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.UserFullResDTO;
import com.example.distribution_sales_techfira.dto.UserResDTO;
import com.example.distribution_sales_techfira.dto.UserUpdateDTO;
import com.example.distribution_sales_techfira.entity.Branch;
import com.example.distribution_sales_techfira.entity.Company;
import com.example.distribution_sales_techfira.entity.Role;
import com.example.distribution_sales_techfira.entity.User;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.repository.BranchRepository;
import com.example.distribution_sales_techfira.repository.CompanyRepository;
import com.example.distribution_sales_techfira.repository.RoleRepository;
import com.example.distribution_sales_techfira.repository.UserRepository;
import com.example.distribution_sales_techfira.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.distribution_sales_techfira.mapper.UserMapper;
import com.example.distribution_sales_techfira.dto.UserReqDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final CompanyRepository companyRepository;
    private  final BranchRepository branchRepository;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public UserServiceImp(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          CompanyRepository companyRepository,
                          BranchRepository branchRepository,
                          UserMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository=companyRepository;
        this.branchRepository=branchRepository;
        this.mapper=mapper;

    }

    @Override
    @Transactional
    public UserResDTO save(UserReqDTO userReqDTO){
        User byEmail = userRepository.findByEmail(userReqDTO.getEmail());
        if (byEmail != null){
            throw new CustomException("Email already exists!");
        }

        // Encode password
        userReqDTO.setPassword(passwordEncoder.encode(userReqDTO.getPassword()));

        // Get role
        Role role = roleRepository.findByName(userReqDTO.getRole().getName());
        if (role == null) {
            throw new CustomException("Role not found!");
        }

        // Create user with all required fields
        User user = new User();
        user.setEmail(userReqDTO.getEmail());
        user.setName(userReqDTO.getName());
        user.setPassword(userReqDTO.getPassword());
        user.setRole(role);

        Company company = companyRepository.findById(userReqDTO .getCompanyId())
                .orElseThrow(() -> new CustomException("Company not found"));
        Branch branch = branchRepository.findById(userReqDTO.getBranchId())
                .orElseThrow(() -> new CustomException("Branch not found"));

        user.setCompany(company);
        user.setBranch(branch);
        // Set default status to true (active)
        user.setStatus(2);

        // Save user
        User saved = userRepository.save(user);
        return mapper.toDTO(saved);
    }

    @Override
    public UserResDTO findUserByEmail(String email){
        return mapper.toDTO(userRepository.findByEmail(email));
    }

    @Override
    public List<UserFullResDTO> findAllUsers(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email);

        List<User> visibleUsers = userRepository.findAllByStatusNot(3);

        List<User> nonSuperAdmins = visibleUsers.stream()
                .filter(user -> !user.getRole().getName().equals("SUPER_ADMIN"))
                .toList();

        String role = currentUser.getRole().getName();
        if (role.equals("ADMIN") || role.equals("SUPER_ADMIN")) {
            return mapper.toListFullDTO(nonSuperAdmins);
        }

        return mapper.toListFullDTO(
                visibleUsers.stream()
                        .filter(user -> user.getEmail().equals(email))
                        .toList()
        );

    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
        User user=userRepository.findById(id).orElseThrow(() -> new CustomException("User not found with id: " + id));
        user.setStatus(3);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResDTO updateUserById(Integer id, UserUpdateDTO updateDTO) {
        // Find the user by ID
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new CustomException("User not found with id: " + id);
        }

        User user = userOptional.get();

        // Check if email is already in use by another user
        User existingUser = userRepository.findByEmail(updateDTO.getEmail());
        if (existingUser != null && existingUser.getId() != id) {
            throw new CustomException("Email is already in use by another user");
        }

        // Update user information
        user.setName(updateDTO.getName());
        user.setEmail(updateDTO.getEmail());

        // Update password if provided
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        // Update role if provided
        if (updateDTO.getRoleName() != null && !updateDTO.getRoleName().isEmpty()) {
            Role role = roleRepository.findByName(updateDTO.getRoleName());
            if (role == null) {
                throw new CustomException("Invalid role specified");
            }
            user.setRole(role);
        }

        if (updateDTO.getCompanyId() != null) {
            Company company = companyRepository.findById(updateDTO.getCompanyId())
                    .orElseThrow(() -> new CustomException("Company not found"));
            user.setCompany(company);
        }

        if (updateDTO.getBranchId() != null) {
            Branch branch = branchRepository.findById(updateDTO.getBranchId())
                    .orElseThrow(() -> new CustomException("Branch not found"));
            user.setBranch(branch);
        }


        // Save updated user
        User updatedUser = userRepository.save(user);

        // Return DTO
        return mapper.toDTO(updatedUser);
    }

    @PostConstruct
    @Override
    public void initUsers() {
        // Create roles if they don't exist
        initializeRoles();
    }

    private void initializeRoles() {
        // Create SUPER_ADMIN role if not exists
        if (roleRepository.findByName("SUPER_ADMIN") == null) {
            Role superAdminRole = new Role();
            superAdminRole.setName("SUPER_ADMIN");
            roleRepository.save(superAdminRole);
        }

        // Create ADMIN role if not exists
        if (roleRepository.findByName("ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        // Create USER role if not exists
        if (roleRepository.findByName("USER") == null) {
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found with ID: " + id));
    }

    @Override
    public void updateStatus(Integer id, int status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found with id: " + id));
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public Integer getLoggedInUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("Logged-in user not found");
        }
        return user.getId();
    }


    @Override
    public Map<String, Object> findUsersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> userPage = userRepository.findAllByStatusNot(3, pageable);

        List<UserResDTO> users = userPage.getContent()
                .stream()
                .map(user -> {
                    UserResDTO dto = new UserResDTO();
                    dto.setId(user.getId()); // ✅ REQUIRED
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    dto.setStatus(user.getStatus());
                    dto.setRole(user.getRole().getName());
                    dto.setCompanyName(user.getCompany() != null ? user.getCompany().getName() : null);
                    dto.setBranchName(user.getBranch() != null ? user.getBranch().getName() : null);
                    return dto;
                })
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("content", users);
        response.put("totalElements", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());
        response.put("number", userPage.getNumber()); // current page

        return response;
    }

}