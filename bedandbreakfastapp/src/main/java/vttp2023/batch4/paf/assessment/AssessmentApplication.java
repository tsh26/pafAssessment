package vttp2023.batch4.paf.assessment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp2023.batch4.paf.assessment.models.User;

@SpringBootApplication
public class AssessmentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// User u = new User("fred@gmail.com", "Fred Flintstone");

		// System.out.println("User exist: " + u);
	}
}
