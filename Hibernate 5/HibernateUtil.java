import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

// Hibernate 5.
public class HibernateUtil {

	private static StandardServiceRegistry registry;
	
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				System.out.println("===== sessionFactory =====");
				String approach = "configuration";
				if (approach == "settings") {
					// Approach 1. Hibernate settings equivalent to hibernate.cfg.xml's properties.
					System.out.println("===== settings =====");
					// Create registry builder.
					StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
					Map<String, String> settings = new HashMap<>();
					settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
					settings.put(Environment.URL, "jdbc:mysql://localhost:3306/jpadb?useSSL=false&allowPublicKeyRetrieval=true");
					settings.put(Environment.USER, "root");
					settings.put(Environment.PASS, "wing");
					settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
					settings.put("hibernate.hbm2ddl.auto", "update");
					// Apply settings.
					registryBuilder.applySettings(settings);
					// Create registry.
					registry = registryBuilder.build();
					// Create MetadataSources.
					MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Author.class);
					// Create Metadata.
					Metadata metadata = sources.getMetadataBuilder().build();
					// Create SessionFactory.
					sessionFactory = metadata.getSessionFactoryBuilder().build();
					System.out.println("===== settings done =====");
					// Ref. https://www.boraji.com/hibernate-5-basic-configuration-example-without-hibernatecfgxml
					// https://www.boraji.com/hibernate-5-save-or-persist-an-entity-example
				} else if (approach == "configuration") {
					// Approach 2. Hibernate configuration equivalent to hibernate.cfg.xml's properties.
					System.out.println("===== configuration =====");
					Configuration configuration = new Configuration();
					configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
					configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
					configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jpadb?useSSL=false&allowPublicKeyRetrieval=true");
					configuration.setProperty("hibernate.connection.username", "root");
					configuration.setProperty("hibernate.connection.password", "wing");
					configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
					configuration.addAnnotatedClass(Author.class);
					StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
					sessionFactory = configuration.buildSessionFactory(builder.build());
					System.out.println("===== configuration done =====");
					// Ref. https://www.programcreek.com/java-api-examples/org.hibernate.cfg.Configuration
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}
		return sessionFactory;
	}
	
	public void checkjdbcjar() {
	    try{
	        Class.forName("com.mysql.jdbc.Driver");
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jpadb?useSSL=false&allowPublicKeyRetrieval=true", "root", "wing");
	        System.out.println("===== y: com.mysql.jdbc.Driver =====");
	    }
	    catch(Exception e){
	    	System.out.println(e.getMessage());
	    	System.out.println("===== n: com.mysql.jdbc.Driver =====");
	    }
	}
	
	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

}
