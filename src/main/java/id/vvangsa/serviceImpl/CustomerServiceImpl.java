package id.vvangsa.serviceImpl;

import id.vvangsa.model.Customer;
import id.vvangsa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private EntityManagerFactory emf;

    @Autowired
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Customer> getAllCustomer() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("from Customer", Customer.class).getResultList();
    }

    @Override
    public void doDeleteBy(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Customer.class, id));
        em.getTransaction().commit();
    }

    @Override
    public Customer doSaveOrUpdate(Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Customer saved = em.merge(customer);
        em.getTransaction().commit();
        return saved;
    }

    //TODO
    @Override
    public Customer getCustomerBy(Integer id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Customer.class, id);
    }
}
