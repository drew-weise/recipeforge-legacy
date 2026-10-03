package persistence;

import Util.SessionFactoryProvider;
import entity.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GenericDao<T> {

    private final Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());


    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    public T getById(int id) {
        try (Session session = getSession()) {
            return session.get(type, id);
        }
    }

    public void delete(T entity) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }


}