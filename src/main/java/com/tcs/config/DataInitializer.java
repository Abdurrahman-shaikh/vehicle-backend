package com.tcs.config;

import com.tcs.entity.Login;
import com.tcs.entity.Underwriter;
import com.tcs.entity.Vehicle;
import com.tcs.repository.LoginRepository;
import com.tcs.repository.UnderwriterRepository;
import com.tcs.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

/**
 * Configuration class to initialize seed data into the database
 * on application startup. It adds:
 * - One admin user (bcr_admin)
 * - Two underwriters (azmi and shifa)
 * - One vehicle for each underwriter
 */
@Configuration
public class DataInitializer {

    /**
     * Initializes sample login, underwriter, and vehicle data at startup.
     *
     * @param loginRepo        the login repository
     * @param underwriterRepo  the underwriter repository
     * @param vehicleRepo      the vehicle repository
     * @return a CommandLineRunner that populates the initial data
     */
    @Bean
    public CommandLineRunner initData(LoginRepository loginRepo,
                                      UnderwriterRepository underwriterRepo,
                                      VehicleRepository vehicleRepo) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

            // Admin login
            if (loginRepo.findByUsername("bcr_admin").isEmpty()) {
                Login adminLogin = new Login("bcr_admin", encoder.encode("123"), "ADMIN");
                loginRepo.save(adminLogin);
            }

            // Underwriter: azmi
            if (loginRepo.findByUsername("azmi").isEmpty()) {
                // Create login credentials
                Login azmiLogin = new Login("azmi", encoder.encode("123"), "UNDERWRITER");
                loginRepo.save(azmiLogin);

                // Create underwriter entity
                Underwriter azmi = new Underwriter();
                azmi.setName("azmi");
                azmi.setDob(LocalDate.of(1999, 1, 1));
                azmi.setDateOfJoining(LocalDate.now());
                azmi.setLogin(azmiLogin);
                underwriterRepo.save(azmi);

                // Assign a vehicle
                Vehicle vehicle1 = new Vehicle();
                vehicle1.setVehicleNo("UP 50 AA 8888");
                vehicle1.setUnderwriter(azmi);
                vehicle1.setFromDate(LocalDate.now());
                vehicle1.setToDate(LocalDate.now().plusYears(1));
                vehicleRepo.save(vehicle1);
            }

            // Underwriter: shifa
            if (loginRepo.findByUsername("shifa").isEmpty()) {
                // Create login credentials
                Login shifaLogin = new Login("shifa", encoder.encode("123"), "UNDERWRITER");
                loginRepo.save(shifaLogin);

                // Create underwriter entity
                Underwriter shifa = new Underwriter();
                shifa.setName("shifa");
                shifa.setDob(LocalDate.of(2000, 2, 2));
                shifa.setDateOfJoining(LocalDate.now());
                shifa.setLogin(shifaLogin);
                underwriterRepo.save(shifa);

                // Assign a vehicle
                Vehicle vehicle2 = new Vehicle();
                vehicle2.setVehicleNo("UP 50 AA 8888");
                vehicle2.setUnderwriter(shifa);
                vehicle2.setFromDate(LocalDate.now());
                vehicle2.setToDate(LocalDate.now().plusYears(1));
                vehicleRepo.save(vehicle2);
            }
        };
    }
}
