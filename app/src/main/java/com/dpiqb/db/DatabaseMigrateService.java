package com.dpiqb.db;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

public class DatabaseMigrateService {
  public static void migrateDatabase(){
    Flyway flyway = Flyway
      .configure()
      .dataSource("jdbc:h2:./space_travel", null, null)
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
