package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional // без этого в 33-й строке ошибка - что за аннотация?
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCarParameters(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(
              "select user from User user join user.car car " +
                      "where car.model = :carModel and car.series = :carSeries");

      query.setParameter("carModel", model);
      query.setParameter("carSeries", series);

      return query.getSingleResult();
   }

}
