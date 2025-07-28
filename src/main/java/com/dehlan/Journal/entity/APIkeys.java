package com.dehlan.Journal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * ✅ Is @Entity class mandatory to fetch data from DB?
 *
 * ➤ If you're using Spring Data JPA or Hibernate → YES, it's required.
 *    JPA works on ORM (Object-Relational Mapping), so it maps Java objects to database tables.
 *    Without an @Entity, JPA won't know how to map the result from DB.
 *
 * 🔽 BUT if you don't want to use Entity classes, here are ALTERNATIVES:
 *
 * 1️⃣ Native SQL + DTO (no @Entity needed)
 *    - You can write native queries and return values into simple POJOs (DTOs)
 *    - Example:
 *        @Query(value = "SELECT name, age FROM users", nativeQuery = true)
 *        List<UserDto> findAllUsers();
 *    - Your DTO must have a constructor matching the selected columns
 *
 * 2️⃣ JdbcTemplate
 *    - You can skip JPA and use Spring's JdbcTemplate for raw SQL
 *    - Example:
 *        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM users");
 *    - No need for Entity or DTO — result comes as list of key-value maps
 *
 * 3️⃣ Spring Data Interface Projections
 *    - Use interfaces to fetch partial data (e.g., name only)
 *    - Example:
 *        interface UserNameOnly { String getName(); }
 *        List<UserNameOnly> findByStatus(String status);
 *
 * ✅ Summary:
 * | Use Case                          | Entity Required? | Notes                            |
 * |----------------------------------|------------------|----------------------------------|
 * | Spring Data JPA (standard)       | ✅ Yes           | Must annotate class with @Entity |
 * | Native SQL with DTO              | ❌ No            | DTO must match query structure   |
 * | JdbcTemplate                     | ❌ No            | Manual mapping, flexible         |
 * | Spring Data Projections          | ⚠️ Sometimes     | Interface-based result mapping   |
 *
 */

/*
 * ✅ Why @NoArgsConstructor is needed with Lombok for POJOs:
 *
 * ➤ Spring (and many frameworks like Hibernate, Jackson) often use **reflection** to create objects.
 *    They do this by calling the class's **default (no-arg) constructor**.
 *
 * ➤ If your class has only fields and no explicitly defined constructors,
 *    Java provides a no-arg constructor by default.
 *    BUT if you add any constructor manually (like AllArgsConstructor), Java **removes** the default one.
 *
 * ➤ Lombok annotations like @Data or @AllArgsConstructor DO NOT add a no-arg constructor unless you tell it to.
 *    So you must explicitly add `@NoArgsConstructor` to avoid `NoSuchMethodException`.
 *
 * ✅ Required when:
 *   - Spring creates beans (e.g., `@Component`, `@Service`)
 *   - Jackson maps JSON to Java objects (e.g., in REST APIs)
 *   - Hibernate creates entities from DB rows
 *
 * ✅ Best Practice:
 *   - Always add @NoArgsConstructor to DTOs, Entities, and Response/Request classes.
 *
 * ✅ Example:
 *   @Data
 *   @NoArgsConstructor
 *   public class User {
 *       private String name;
 *       private int age;
 *   }
 */


@Document(collection = "APIkeys")
@Data
@NoArgsConstructor
public class APIkeys {

    @Id
    ObjectId id;

    String apiName;
    String apikey;
}
