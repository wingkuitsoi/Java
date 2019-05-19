import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

// SessionFactory.
public class JDBC {

	private static SessionFactory factory;
	
	public void getConnection() {		
		try {
			System.out.println("===== Hibernate =====");			
			int approach = 0;
			if (approach == 1) {
				// Configure SessionFactory 1. 
				StandardServiceRegistry standardRegistry = 
						new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
				Metadata metaData = 
						new MetadataSources(standardRegistry).getMetadataBuilder().build();
				factory = metaData.getSessionFactoryBuilder().build();
			} else if (approach == 2) {
				// Configure SessionFactory 2. 
				factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
			}
			System.out.println("===== Hibernate connected =====");	
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
}
