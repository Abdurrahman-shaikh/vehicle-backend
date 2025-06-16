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

@Configuration
public class DataInitializer {

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
                Login azmiLogin = new Login("azmi", encoder.encode("123"), "UNDERWRITER");
                loginRepo.save(azmiLogin);

                Underwriter azmi = new Underwriter();
                azmi.setName("azmi");
                azmi.setDob(LocalDate.of(1999, 1, 1));
                azmi.setDateOfJoining(LocalDate.now());
                azmi.setLogin(azmiLogin);
                underwriterRepo.save(azmi);

                Vehicle vehicle1 = new Vehicle();
                vehicle1.setVehicleNo("UP50AA8888");
                vehicle1.setVehicleType("Car");
                vehicle1.setCustomerName("Ayaan Khan");
                vehicle1.setEngineNo("ENG123AZMI");
                vehicle1.setChasisNo("CHS123AZMI");
                vehicle1.setPhoneNo("9876543210");
                vehicle1.setPolicyType("Comprehensive");
                vehicle1.setPremiumAmount(6000.0);
                vehicle1.setClaimStatus("Not Claimed");
                vehicle1.setFromDate(LocalDate.now());
                vehicle1.setToDate(LocalDate.now().plusYears(1));
                vehicle1.setUnderwriter(azmi);
                vehicleRepo.save(vehicle1);
            }

            // Underwriter: shifa
            if (loginRepo.findByUsername("shifa").isEmpty()) {
                Login shifaLogin = new Login("shifa", encoder.encode("123"), "UNDERWRITER");
                loginRepo.save(shifaLogin);

                Underwriter shifa = new Underwriter();
                shifa.setName("shifa");
                shifa.setDob(LocalDate.of(2000, 2, 2));
                shifa.setDateOfJoining(LocalDate.now());
                shifa.setLogin(shifaLogin);
                underwriterRepo.save(shifa);

                Vehicle vehicle2 = new Vehicle();
                vehicle2.setVehicleNo("UP50AA7777");
                vehicle2.setVehicleType("Scooter");
                vehicle2.setCustomerName("Zara Fatima");
                vehicle2.setEngineNo("ENG123SHIFA");
                vehicle2.setChasisNo("CHS123SHIFA");
                vehicle2.setPhoneNo("9123456789");
                vehicle2.setPolicyType("Third Party");
                vehicle2.setPremiumAmount(3000.0);
                vehicle2.setClaimStatus("Not Claimed");
                vehicle2.setFromDate(LocalDate.now());
                vehicle2.setToDate(LocalDate.now().plusYears(1));
                vehicle2.setUnderwriter(shifa);
                vehicleRepo.save(vehicle2);
            }
        };
    }
}
