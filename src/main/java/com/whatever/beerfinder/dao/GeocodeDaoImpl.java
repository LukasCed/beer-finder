package com.whatever.beerfinder.dao;

import com.whatever.beerfinder.dao.interfaces.GeocodeDao;
import com.whatever.beerfinder.entities.Geocode;
import org.springframework.stereotype.Repository;

@Repository
public class GeocodeDaoImpl extends DataDaoImpl<Geocode> implements GeocodeDao {
}
