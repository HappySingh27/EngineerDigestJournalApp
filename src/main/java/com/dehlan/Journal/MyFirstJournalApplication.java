/*
* @SpringBootApplication equivalent to - @Configuration, @EnableAutoConfiguration, @ComponentScan
* @ComponentScan
* 	- Automatically scans the package (and sub-packages) for Spring components like @Component,
*   - @Service, @Repository, and @Controller to register them as beans.
*
*
*
*    @EnableTransactionManagement:-
* 📌 What it does:
*    @EnableTransactionManagement is a Spring annotation that enables Spring's annotation-driven transaction management,
*    such as @Transactional.
*
* 🛠️ Why it's needed:
*    It tells Spring to look for @Transactional annotations on classes or methods and apply transaction handling automatically.
*
*
* Spring Boot creates a transactional context for each method or class annotated with @Transactional.
*
* These transactions are managed by an implementation of PlatformTransactionManager.
*
* In most cases (like JPA or JDBC), Spring Boot auto-configures the correct PlatformTransactionManager.
*
* But for MongoDB:
*   - Transactions work only on a replica set or sharded cluster.
*   - Spring does NOT auto-configure MongoTransactionManager unless proper conditions are met.
*   - Therefore, we often need to manually define a @Bean of type PlatformTransactionManager.
*   - PlatformTransactionManager is an interface that MongoTransactionManager implements.
*   - We define the bean to tell Spring explicitly which transaction manager to use.
*
*  Interface - MogoDatabaseFactory - Helps in establishing connection with MongoDb
*  implementation of MogoDatabaseFactory - SimpleMongoClientDatabaseFactory
*/
/*
* -Profiles-
* mvn clean package -Dspring.profiles.active=dev - choosing profile while packaging through cmd
* 	-D flag is used set JVM properties
* if our project do not contains test then mvn clean package would also work - not tested
* if a bean would be loaded or not can also be set through profile using @Profile("profile name") annotation
* */



package com.dehlan.Journal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Profile("") - for properties/yml files
@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class MyFirstJournalApplication {

	public static void main(String[] args) {

		/*
		* Below code is to get current active profile,
		* i.e application is using which properties or yml file
		* */
		log.info("🚀 Starting Journal Application...");
		ConfigurableApplicationContext context = SpringApplication.run(MyFirstJournalApplication.class, args);
		log.info("✅ Journal Application started successfully.");
		ConfigurableEnvironment environment = context.getEnvironment();
		System.out.println(environment.getActiveProfiles()[0]);
	}

	/*
	* The @Bean annotation tells Spring to manage the return value of that method as a Spring Bean.
	* This method must be inside a class annotated with @Configuration.
	* */
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

}
