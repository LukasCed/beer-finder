package com.whatever.beerfinder.dao;

import com.whatever.beerfinder.dao.interfaces.BreweryDao;
import com.whatever.beerfinder.entities.Brewery;
import org.springframework.stereotype.Repository;

@Repository
public class BreweryDaoImpl extends DataDaoImpl<Brewery> implements BreweryDao {

}
