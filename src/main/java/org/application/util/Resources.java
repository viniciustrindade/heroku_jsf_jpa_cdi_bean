package com.azzafe.api.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

public class Resources {
  
  @Inject
  EntityManagerFactory emf;

 /*  @Produces
   @PersistenceContext(unitName="default")
   private EntityManager entityManager;*/

/*   @PersistenceContext(unitName="incorpPU")
   private EntityManager incorpEntityManager;
   */
   @Produces 
   @ApplicationScoped 
   EntityManagerFactory createEntityManagerFactory(){

      String databaseUrl = System.getenv("DATABASE_URL");
      StringTokenizer st = new StringTokenizer(databaseUrl, ":@/");
      String dbVendor = st.nextToken(); //if DATABASE_URL is set
      String userName = st.nextToken();
      String password = st.nextToken();
      String host = st.nextToken();
      String port = st.nextToken();
      String databaseName = st.nextToken();
      String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", host, port, databaseName);
      Map<String, String> properties = new HashMap<String, String>();
      properties.put("javax.persistence.jdbc.url", databaseUrl );
      properties.put("javax.persistence.jdbc.user", userName );
      properties.put("javax.persistence.jdbc.password", password );
      properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
      properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
      return Persistence.createEntityManagerFactory("default", properties);

   }
   
   @Produces
   @RequestScoped
   EntityManager createEntityManager(){
     return emf.createEntityManager();
   }

   void disposeEntityMaganer(@Disposes EntityManager em){
    em.close();
   }

   @Produces Logger getLogger(InjectionPoint caller){
       return Logger.getLogger(caller.getMember().getDeclaringClass().getName());
   }
}
