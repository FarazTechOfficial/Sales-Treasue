package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.LoginReqDTO;
import com.example.distribution_sales_techfira.dto.LoginResponseDTO;
import com.example.distribution_sales_techfira.entity.License;
import com.example.distribution_sales_techfira.entity.User;
import com.example.distribution_sales_techfira.repository.LicenceRepository;
import com.example.distribution_sales_techfira.repository.UserRepository;
import com.example.distribution_sales_techfira.service.AuthService;
import com.example.distribution_sales_techfira.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthServiceImp implements AuthService  {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    private LicenceRepository licenceRepository;

    public AuthServiceImp(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponseDTO verify(LoginReqDTO loginReqDTO){
        User userAuth = userRepository.findByEmail(loginReqDTO.getEmail());

        if(userAuth == null){
            return new LoginResponseDTO(null, null, null, false, "User not found");
        }

        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReqDTO.getEmail(), loginReqDTO.getPassword()));
        } catch (Exception e) {
            return new LoginResponseDTO(null, null, null, false, "Invalid credentials");
        }

        if (authenticate.isAuthenticated()){
            User user=userRepository.findByEmail(loginReqDTO.getEmail());

            if(user==null){
                return new LoginResponseDTO(null, null, null, false, "User not found");
            }

            if (authenticate.isAuthenticated()) {
                if (user.getCompany() == null) {
                    return new LoginResponseDTO(null, null, null, false, "User is not associated with any company");
                }

                Integer companyId = user.getCompany().getId();


                Optional<License> optionalLicense = licenceRepository.findByCompanyId(companyId);

                if (optionalLicense.isEmpty()) {
                    return new LoginResponseDTO(null, null, null, false, "Your company has not registered for a license");
                }

                License license = optionalLicense.get();
                if (license.getValidTo() == null || license.getValidTo().isBefore(LocalDate.now())) {
                    return new LoginResponseDTO(null, null, null, false, "Your company license has expired");
                }


                String token = jwtService.generate(loginReqDTO);
                return new LoginResponseDTO(token, user.getName(), user.getRole().getName(), true, "Login successful");
            }
        }
        return new LoginResponseDTO(null, null, null, false, "Authentication failed");
    }

    public boolean isCompanyLicenseValid(Integer companyId) {
        return licenceRepository.findByCompanyId(companyId)
                .map(license -> license.getValidTo() != null && license.getValidTo().isAfter(LocalDate.now()))
                .orElse(false);
    }
}
