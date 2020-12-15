package ir.fyfood.repository.dao;

import ir.fyfood.repository.entity.Restaurant;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, Integer>, JpaSpecificationExecutor<Restaurant> {

    @Query("select distinct restaurant.serviceArea from Restaurant restaurant")
    List<Integer> getAllServiceArea();

    static Specification<Restaurant> findBy(String type, int area) {
        {
            return (Specification<Restaurant>) (root, criteriaQuery, criteriaBuilder) -> {

                CriteriaQuery<Restaurant> query = criteriaBuilder.createQuery(Restaurant.class);

                List<Predicate> conditions = new ArrayList<>();
                conditions.add(criteriaBuilder.equal(root.get("serviceArea"), area));
                if (type != null) {
                    Join join = root.joinList("foodsList");
                    conditions.add(criteriaBuilder.equal(join.get("type"), type));
                }

                CriteriaQuery<Restaurant> restaurantCriteriaQuery =
                        query.select(root)
                                .distinct(true)
                                .where(conditions.toArray(new Predicate[]{}));

                return restaurantCriteriaQuery.getRestriction();
            };
        }
    }

}
