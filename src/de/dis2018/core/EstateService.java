package de.dis2018.core;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransientObjectException;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import de.dis2018.data.House;
import de.dis2018.data.Estate;
import de.dis2018.data.PurchaseContract;
import de.dis2018.data.EstateAgent;
import de.dis2018.data.TenancyContract;
import de.dis2018.data.Person;
import de.dis2018.data.Apartment;

/**
 *  Class for managing all database entities.
 * 
 * TODO: All data is currently stored in memory. 
 * The aim of the exercise is to gradually outsource data management to the 
 * database. When the work is done, all sets in this class become obsolete!
 */
public class EstateService {
	//TODO All these sets should be commented out after successful implementation.
	private Set<EstateAgent> estateAgents = new HashSet<EstateAgent>();
	private Set<Person> persons = new HashSet<Person>();
	private Set<House> houses = new HashSet<House>();
	private Set<Apartment> apartments = new HashSet<Apartment>();
	private Set<TenancyContract> tenancyContracts = new HashSet<TenancyContract>();
	private Set<PurchaseContract> purchaseContracts = new HashSet<PurchaseContract>();
	
	//Hibernate Session
	private SessionFactory sessionFactory;
	
	public EstateService() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	/**
	 * Find an estate agent with the given id
	 * @param id The ID of the agent
	 * @return Agent with ID or null
	 */
	public EstateAgent getEstateAgentByID(int id) {
		//Hibernate Session erzeugen
		EstateAgent agent;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			agent = session.get(EstateAgent.class, id);
		}catch(NoResultException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			return null;
		}finally {
			session.close();
		}
		return agent;
	}
	
	/**
	 * Find estate agent with the given login.
	 * @param login The login of the estate agent
	 * @return Estate agent with the given ID or null
	 */
	public EstateAgent getEstateAgentByLogin(String login) {
		//Hibernate Session erzeugen
		EstateAgent agent;
		Session session = sessionFactory.openSession();
		try {
		session.beginTransaction();
		CriteriaQuery<EstateAgent> criteriaQuery = session.getCriteriaBuilder().createQuery(EstateAgent.class);
		Root<EstateAgent> root = criteriaQuery.from(EstateAgent.class);
		criteriaQuery.where(session.getCriteriaBuilder().equal(root.get("login"), login));
		agent = session.createQuery(criteriaQuery).getSingleResult();
		}catch(NoResultException e) {
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        return null;
		}finally {
		  session.close();
		}
		return agent;
	}
	
	/**
	 * Returns all estateAgents
	 */
	public Set<EstateAgent> getAllEstateAgents() {
		//Hibernate Session erzeugen
		Set<EstateAgent> agents;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			CriteriaQuery<EstateAgent> criteriaQuery = session.getCriteriaBuilder().createQuery(EstateAgent.class);
			Root<EstateAgent> root = criteriaQuery.from(EstateAgent.class);
		    criteriaQuery.select(root);
			List<EstateAgent> list_agents = session.createQuery(criteriaQuery).getResultList();
			agents = new HashSet<EstateAgent>(list_agents);
		}catch(NoResultException e) {
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        return null;
		}finally {
		  session.close();
		}
		return agents;
	}
	
	/**
	 * Find an person with the given id
	 * @param id The ID of the person
	 * @return Person with ID or null
	 */
	public Person getPersonById(int id) {
		//Hibernate Session erzeugen
		Person person;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			person = session.get(Person.class, id);
		}catch(NoResultException e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			return null;
		}finally {
			session.close();
		}
		return person;
	}
	
	/**
	 * Adds an estate agent
	 * @param ea The estate agent
	 */
	public void addEstateAgent(EstateAgent ea) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(ea);
			session.getTransaction().commit();
		}catch(TransientObjectException e){
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
		}finally {
			session.close();
		}
	}
	
	/**
	 * Deletes an estate agent
	 * @param ea The estate agent
	 */
	public void deleteEstateAgent(EstateAgent ea) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(ea);
			session.getTransaction().commit();
		}catch(TransientObjectException e){
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
		}finally {
			session.close();
		}
	}
	
	/**
	 * Adds a person
	 * @param p The person
	 */
	public void addPerson(Person p) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(p);
			session.getTransaction().commit();
		}catch(TransientObjectException e){
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
		}finally {
			session.close();
		}
	}
	
	/**
	 * Returns all persons
	 */
	public Set<Person> getAllPersons() {
		//Hibernate Session erzeugen
		Set<Person> persons;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			CriteriaQuery<Person> criteriaQuery = session.getCriteriaBuilder().createQuery(Person.class);
			Root<Person> root = criteriaQuery.from(Person.class);
		    criteriaQuery.select(root);
			List<Person> list_persons = session.createQuery(criteriaQuery).getResultList();
			persons = new HashSet<Person>(list_persons);
		}catch(NoResultException e) {
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        return null;
		}finally {
		  session.close();
		}
		return persons;
	}
	
	/**
	 * Deletes a person
	 * @param p The person
	 */
	public void deletePerson(Person p) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(p);
			session.getTransaction().commit();
		}catch(TransientObjectException e){
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
		}finally {
			session.close();
		}
	}
	
	/**
	 * Adds a house
	 * @param h The house
	 */
	public void addHouse(House h) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(h);
			session.getTransaction().commit();
		}catch(TransientObjectException e){
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
		}finally {
			session.close();
		}
	}
	
	/**
	 * Returns all houses of an estate agent
	 * @param ea the estate agent
	 * @return All houses managed by the estate agent
	 */
	public Set<House> getAllHousesForEstateAgent(EstateAgent ea) {
		//Hibernate Session erzeugen
		Set<House> houses;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			CriteriaQuery<House> criteriaQuery = session.getCriteriaBuilder().createQuery(House.class);
			Root<House> root = criteriaQuery.from(House.class);
			criteriaQuery.where(session.getCriteriaBuilder().equal(root.get("manager"), ea.getId()));
			List<House> list_houses = session.createQuery(criteriaQuery).getResultList();
			houses = new HashSet<House>(list_houses);
		}catch(NoResultException e) {
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        return null;
		}finally {
		  session.close();
		}
		return houses;
	}
	
	/**
	 * Find a house with a given ID
	 * @param  id the house id
	 * @return The house or null if not found
	 */
	public House getHouseById(int id) {
		//Hibernate Session erzeugen
		House house;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			house = session.get(House.class, id);
		}catch(NoResultException e) {
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
        	return null;
		}finally {
			session.close();
		}
		return house;
	}
	
	/**
	 * Deletes a house
	 * @param h The house
	 */
	public void deleteHouse(House h) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(h);
			session.getTransaction().commit();
		}catch(TransientObjectException e){
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
		}finally {
			session.close();
		}
	}
	
	/**
	 * Adds an apartment
	 * @param w the aparment
	 */
	public void addApartment(Apartment w) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(w);
			session.getTransaction().commit();
		}catch(TransientObjectException e){
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
		}finally {
			session.close();
		}
	}
	
	/**
	 * Returns all apartments of an estate agent
	 * @param ea The estate agent
	 * @return All apartments managed by the estate agent
	 */
	public Set<Apartment> getAllApartmentsForEstateAgent(EstateAgent ea) {
		//Hibernate Session erzeugen
		Set<Apartment> apartments;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			CriteriaQuery<Apartment> criteriaQuery = session.getCriteriaBuilder().createQuery(Apartment.class);
			Root<Apartment> root = criteriaQuery.from(Apartment.class);
			criteriaQuery.where(session.getCriteriaBuilder().equal(root.get("manager"), ea.getId()));
			List<Apartment> list_apartments = session.createQuery(criteriaQuery).getResultList();
			apartments = new HashSet<Apartment>(list_apartments);
		}catch(NoResultException e) {
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        return null;
		}finally {
		  session.close();
		}
		return apartments;
	}
	
	/**
	 * Find an apartment with given ID
	 * @param id The ID
	 * @return The apartment or zero, if not found
	 */
	public Apartment getApartmentByID(int id) {
		//Hibernate Session erzeugen
		Apartment apartment;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			apartment = session.get(Apartment.class, id);
		}catch(NoResultException e) {
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
        	return null;
		}finally {
			session.close();
		}
		return apartment;
	}
	
	/**
	 * Deletes an apartment
	 * @param p The apartment
	 */
	public void deleteApartment(Apartment w) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(w);
			session.getTransaction().commit();
		}catch(TransientObjectException e){
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
		}finally {
			session.close();
		}
	}
	
	
	/**
	 * Adds a tenancy contract
	 * @param t The tenancy contract
	 */
	public void addTenancyContract(TenancyContract t) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(t);
			session.getTransaction().commit();
		}catch(TransientObjectException e){
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
		}finally {
			session.close();
		}
	}
	
	/**
	 * Adds a purchase contract
	 * @param p The purchase contract
	 */
	public void addPurchaseContract(PurchaseContract p) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(p);
			session.getTransaction().commit();
		}catch(TransientObjectException e){
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
		}finally {
			session.close();
		}
	}
	
	/**
	 * Finds a tenancy contract with a given ID
	 * @param id Die ID
	 * @return The tenancy contract or zero if not found
	 */
	public TenancyContract getTenancyContractByID(int id) {
		//Hibernate Session erzeugen
		TenancyContract t_contract;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			t_contract = session.get(TenancyContract.class, id);
		}catch(NoResultException e) {
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
        	return null;
		}finally {
			session.close();
		}
		return t_contract;
	}
	
	/**
	 * Finds a purchase contract with a given ID
	 * @param id The id of the purchase contract
	 * @return The purchase contract or null if not found
	 */
	public PurchaseContract getPurchaseContractById(int id) {
		//Hibernate Session erzeugen
		PurchaseContract p_contract;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			p_contract = session.get(PurchaseContract.class, id);
		}catch(NoResultException e) {
			if (session.getTransaction() != null) {
            	session.getTransaction().rollback();
        	}
        	return null;
		}finally {
			session.close();
		}
		return p_contract;
	}
	
	/**
	 * Returns all tenancy contracts for apartments of the given estate agent
	 * @param m The estate agent
	 * @return All contracts belonging to apartments managed by the estate agent
	 */
	public Set<TenancyContract> getAllTenancyContractsForEstateAgent(EstateAgent ea) {
		//Hibernate Session erzeugen
		Set<TenancyContract> t_contracts;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			CriteriaQuery<TenancyContract> criteriaQuery = session.getCriteriaBuilder().createQuery(TenancyContract.class);
			Root<TenancyContract> contract = criteriaQuery.from(TenancyContract.class);
			Join<Apartment, TenancyContract> join = contract.join("apartment");
			//join.on(session.getCriteriaBuilder().equal(join.get("manager", ea.getId())));
			criteriaQuery.where(session.getCriteriaBuilder().equal(join.get("manager"), ea.getId()));
			List<TenancyContract> list_contracts = session.createQuery(criteriaQuery).getResultList();
			
			// NEED TO INITIALIZE OBJECT CAUSE HIBERNATE SUCKS
			// LOOK AT THIS SHIT
			for(TenancyContract t_c : list_contracts) {
				Hibernate.initialize(t_c.getContractPartner().getFirstname());
				Hibernate.initialize(t_c.getContractPartner().getName());
				Hibernate.initialize(t_c.getApartment().getCity());
				Hibernate.initialize(t_c.getApartment().getPostalcode());
				Hibernate.initialize(t_c.getApartment().getStreet());
				Hibernate.initialize(t_c.getApartment().getStreetnumber());
				Hibernate.initialize(t_c.getApartment().getPostalcode());
			}
			
			t_contracts = new HashSet<TenancyContract>(list_contracts);
		}catch(NoResultException e) {
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        return null;
		}finally {
		  session.close();
		}
		return t_contracts;
	}
	
	/**
	 * Returns all purchase contracts for houses of the given estate agent
	 * @param m The estate agent
	 * @return All purchase contracts belonging to houses managed by the given estate agent
	 */
	public Set<PurchaseContract> getAllPurchaseContractsForEstateAgent(EstateAgent ea) {
		//Hibernate Session erzeugen
		Set<PurchaseContract> p_contracts;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			CriteriaQuery<PurchaseContract> criteriaQuery = session.getCriteriaBuilder().createQuery(PurchaseContract.class);
			Root<PurchaseContract> contract = criteriaQuery.from(PurchaseContract.class);
			Join<Apartment, TenancyContract> join = contract.join("house");
			//join.on(session.getCriteriaBuilder().equal(join.get("manager", ea.getId())));
			criteriaQuery.where(session.getCriteriaBuilder().equal(join.get("manager"), ea.getId()));
			List<PurchaseContract> list_contracts = session.createQuery(criteriaQuery).getResultList();
			
			// NEED TO INITIALIZE OBJECT CAUSE HIBERNATE SUCKS
			// LOOK AT THIS SHIT
			for(PurchaseContract p_c : list_contracts) {
				Hibernate.initialize(p_c.getContractPartner().getFirstname());
				Hibernate.initialize(p_c.getContractPartner().getName());
				Hibernate.initialize(p_c.getHouse().getStreet());
				Hibernate.initialize(p_c.getHouse().getStreetnumber());
				Hibernate.initialize(p_c.getHouse().getPostalcode());
				Hibernate.initialize(p_c.getHouse().getCity());
			}
			
			p_contracts = new HashSet<PurchaseContract>(list_contracts);
		}catch(NoResultException e) {
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        return null;
		}finally {
		  session.close();
		}
		return p_contracts;
	}
	
	/**
	 * Finds all tenancy contracts relating to the apartments of a given estate agent	 
	 * @param ea The estate agent
	 * @return set of tenancy contracts
	 */
	public Set<TenancyContract> getTenancyContractByEstateAgent(EstateAgent ea) {
		//Hibernate Session erzeugen
		Set<TenancyContract> t_contracts;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			CriteriaQuery<TenancyContract> criteriaQuery = session.getCriteriaBuilder().createQuery(TenancyContract.class);
			Root<TenancyContract> contract = criteriaQuery.from(TenancyContract.class);
			Join<Apartment, TenancyContract> join = contract.join("apartment");
			//join.on(session.getCriteriaBuilder().equal(join.get("manager", ea.getId())));
			criteriaQuery.where(session.getCriteriaBuilder().equal(join.get("manager"), ea.getId()));
			List<TenancyContract> list_contracts = session.createQuery(criteriaQuery).getResultList();
			
			// NEED TO INITIALIZE OBJECT CAUSE HIBERNATE SUCKS
			// LOOK AT THIS SHIT
			for(TenancyContract t_c : list_contracts) {
				Hibernate.initialize(t_c.getContractPartner().getFirstname());
				Hibernate.initialize(t_c.getContractPartner().getName());
				Hibernate.initialize(t_c.getApartment().getCity());
				Hibernate.initialize(t_c.getApartment().getPostalcode());
				Hibernate.initialize(t_c.getApartment().getStreet());
				Hibernate.initialize(t_c.getApartment().getStreetnumber());
				Hibernate.initialize(t_c.getApartment().getPostalcode());
			}
			
			t_contracts = new HashSet<TenancyContract>(list_contracts);
		}catch(NoResultException e) {
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        return null;
		}finally {
		  session.close();
		}
		return t_contracts;
	}
	
	/**
	 * Finds all purchase contracts relating to the houses of a given estate agent
	 * @param  ea The estate agent
	 * @return set of purchase contracts
	 */
	public Set<PurchaseContract> getPurchaseContractByEstateAgent(EstateAgent ea) {
		//Hibernate Session erzeugen
		Set<PurchaseContract> p_contracts;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			CriteriaQuery<PurchaseContract> criteriaQuery = session.getCriteriaBuilder().createQuery(PurchaseContract.class);
			Root<PurchaseContract> contract = criteriaQuery.from(PurchaseContract.class);
			Join<Apartment, TenancyContract> join = contract.join("house");
			//join.on(session.getCriteriaBuilder().equal(join.get("manager", ea.getId())));
			criteriaQuery.where(session.getCriteriaBuilder().equal(join.get("manager"), ea.getId()));
			List<PurchaseContract> list_contracts = session.createQuery(criteriaQuery).getResultList();
			
			// NEED TO INITIALIZE OBJECT CAUSE HIBERNATE SUCKS
			// LOOK AT THIS SHIT
			for(PurchaseContract p_c : list_contracts) {
				Hibernate.initialize(p_c.getContractPartner().getFirstname());
				Hibernate.initialize(p_c.getContractPartner().getName());
				Hibernate.initialize(p_c.getHouse().getStreet());
				Hibernate.initialize(p_c.getHouse().getStreetnumber());
				Hibernate.initialize(p_c.getHouse().getPostalcode());
				Hibernate.initialize(p_c.getHouse().getCity());
			}
			
			p_contracts = new HashSet<PurchaseContract>(list_contracts);
		}catch(NoResultException e) {
	        if (session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        return null;
		}finally {
		  session.close();
		}
		return p_contracts;
	}

	
	/**
	 * Deletes a tenancy contract
	 * @param tc the tenancy contract
	 */
	public void deleteTenancyContract(TenancyContract tc) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(tc);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Deletes a purchase contract
	 * @param tc the purchase contract
	 */
	public void deletePurchaseContract(PurchaseContract pc) {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(pc);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Adds some test data
	 */
	public void addTestData() {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		EstateAgent m = new EstateAgent();
		m.setName("Max Mustermann");
		m.setAddress("Am Informatikum 9");
		m.setLogin("max");
		m.setPassword("max");
		
		//TODO: This estate agent is kept in memory and the DB
		//this.addEstateAgent(m);
		session.save(m);
		session.getTransaction().commit();

		session.beginTransaction();
		
		Person p1 = new Person();
		p1.setAddress("Informatikum");
		p1.setName("Mustermann");
		p1.setFirstname("Erika");
		
		
		Person p2 = new Person();
		p2.setAddress("Reeperbahn 9");
		p2.setName("Albers");
		p2.setFirstname("Hans");
		
		session.save(p1);
		session.save(p2);
		
		//TODO: These persons are kept in memory and the DB
		//this.addPerson(p1);
		//this.addPerson(p2);
		session.getTransaction().commit();
		
		// Create Hibernate Session
		session = sessionFactory.openSession();
		session.beginTransaction();
		EstateAgent m2 = (EstateAgent)session.get(EstateAgent.class, m.getId());
		Set<Estate> immos = m2.getEstates();
		Iterator<Estate> it = immos.iterator();
		
		while(it.hasNext()) {
			Estate i = it.next();
			System.out.println("Estate: "+i.getCity());
		}
		session.close();
		
		session = sessionFactory.openSession();
		
		session.beginTransaction();
		House h = new House();
		h.setCity("Hamburg");
		h.setPostalcode(22527);
		h.setStreet("Vogt-Kölln-Street");
		h.setStreetnumber("2a");
		h.setSquareArea(384);
		h.setFloors(5);
		h.setPrice(10000000);
		h.setGarden(true);
		h.setManager(m);
		
		session.save(h);
		
		//TODO: This house is held in memory and the DB
		//this.addHouse(h);
		session.getTransaction().commit();
		
		session.beginTransaction();
		
		Apartment w = new Apartment();
		w.setCity("Hamburg");
		w.setPostalcode(22527);
		w.setStreet("Vogt-Kölln-Street");
		w.setStreetnumber("3");
		w.setSquareArea(120);
		w.setFloor(4);
		w.setRent(790);
		w.setKitchen(true);
		w.setBalcony(false);
		w.setManager(m);
		
		session.save(w);
		//this.addApartment(w);
		
		w = new Apartment();
		w.setCity("Berlin");
		w.setPostalcode(22527);
		w.setStreet("Vogt-Kölln-Street");
		w.setStreetnumber("3");
		w.setSquareArea(120);
		w.setFloor(4);
		w.setRent(790);
		w.setKitchen(true);
		w.setBalcony(false);
		w.setManager(m);
		
		session.save(w);
		//this.addApartment(w);
		session.getTransaction().commit();
	
		session.beginTransaction();
		PurchaseContract pc = new PurchaseContract();
		pc.setHouse(h);
		pc.setContractPartner(p1);
		pc.setContractNo(9234);
		pc.setDate(new Date(System.currentTimeMillis()));
		pc.setPlace("Hamburg");
		pc.setNoOfInstallments(5);
		pc.setIntrestRate(4);
		
		session.save(pc);
		//this.addPurchaseContract(pc);
		session.getTransaction().commit();
		
		session.beginTransaction();
		TenancyContract tc = new TenancyContract();
		tc.setApartment(w);
		tc.setContractPartner(p2);
		tc.setContractNo(23112);
		tc.setDate(new Date(System.currentTimeMillis()-1000000000));
		tc.setPlace("Berlin");
		tc.setStartDate(new Date(System.currentTimeMillis()));
		tc.setAdditionalCosts(65);
		tc.setDuration(36);
		
		session.save(tc);
		//this.addTenancyContract(tc);
		session.getTransaction().commit();
		
		session.close();
	}
}
