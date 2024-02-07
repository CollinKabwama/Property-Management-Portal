package com.propertymanagement.portal;

import com.propertymanagement.portal.auth.AuthenticationService;
import com.propertymanagement.portal.domain.Owner;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.dto.request.RegisterRequest;
import com.propertymanagement.portal.repository.OwnerRepository;
import com.propertymanagement.portal.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.propertymanagement.portal.user.Role.*;

@SpringBootApplication
@Transactional
public class PortalApplication {
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private PropertyRepository propertyRepository;

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Owner")
					.lastname("Owner")
					.email("ownwer@mail.com")
					.password("password")
					.role(OWNER)
					.build();
			System.out.println("Owner token: " + service.registerOwner(admin).getAccessToken());

			var customer = RegisterRequest.builder()
					.firstname("Customer")
					.lastname("Customer")
					.email("customer@mail.com")
					.password("password")
					.role(CUSTOMER)
					.build();
			System.out.println("Customer token: " + service.registerCustomer(customer).getAccessToken());
		};
	}*/
	/*@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}*/

}
