package com.propertymanagement.portal;

import com.propertymanagement.portal.auth.AuthenticationService;
import com.propertymanagement.portal.domain.Owner;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.dto.request.RegisterRequest;
import com.propertymanagement.portal.email.EmailService;
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
			/*var admin = RegisterRequest.builder()
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
			Owner owner =  ownerRepository.findById(1L).get();

			Property property1 = new Property();
			property1.setName("Property 1");
			Property property2 = new Property();
			property1.setName("Property 2");
			property1.setOwner(owner);
			//property2.setOwner(owner);
			owner.addProperty(property1);
			owner.addProperty(property2);
			propertyRepository.save(property1);
			propertyRepository.save(property2);
			//ownerRepository.save(owner);

		};
	}*/
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
