package org.application.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This bean is used to hold the entity manager factory and to produce the
 * conversation scoped entity manager for injection
 */
@Singleton
public class DataRepositoryProducer {

	private static EntityManagerFactory factory;

	@Produces
	public EntityManagerFactory getEntityManagerFactory() {
		if (factory == null) {

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
      properties.put("hibernate.connection.url", jdbcUrl);
      properties.put("hibernate.connection.username", userName );
      properties.put("hibernate.connection.password", password );
      properties.put("jhibernate.connection.driver_class", "org.postgresql.Driver");
      properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");      

      /*properties.put("javax.persistence.jdbc.url", jdbcUrl);
      properties.put("javax.persistence.jdbc.user", userName );
      properties.put("javax.persistence.jdbc.password", password );
      properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
      properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");*/
      factory = Persistence.createEntityManagerFactory("default", properties);
		}
		return factory;
	}

	@Produces
	@DataRepository
	@ConversationScoped
	public EntityManager produceEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

}
