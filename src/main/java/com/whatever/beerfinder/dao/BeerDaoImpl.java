package com.whatever.beerfinder.dao;

import com.whatever.beerfinder.dao.interfaces.BeerDao;
import com.whatever.beerfinder.entities.Beer;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BeerDaoImpl extends DataDaoImpl<Beer> implements BeerDao {

    public List<Beer> listBeers(List<Integer> breweryIds) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Beer> cr = builder.createQuery(Beer.class);
        Root<Beer> root = cr.from(Beer.class);
        cr.select(root).where(root.get("brewery_id").in(breweryIds));

        TypedQuery<Beer> query = entityManager.createQuery(cr);
        return query.getResultList();
    }

}
