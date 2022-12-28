package com.dpiqb.db;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

public class DatabaseMigrateServiceForTest {
  public static void migrateDatabase(){
    Flyway flyway = Flyway
      .configure()
      .dataSource("jdbc:h2:mem:./space_travel_TESTS;DB_CLOSE_DELAY=-1", null, null)
      .load();
    try{
      flyway.migrate();
    }catch (FlywayException fex){
      StackTraceElement[] stackTrace = fex.getStackTrace();
      for (StackTraceElement stackTraceElement : stackTrace) {
        System.out.println(stackTraceElement);
      }
    }
  }
}
