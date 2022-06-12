package buzz.rationalagents.parkinglottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkinglotteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkinglotteryApplication.class, args);

		
		Solution.mainlot(); //run this baby
		System.console().readLine(); //pause for the goods
	}

}
