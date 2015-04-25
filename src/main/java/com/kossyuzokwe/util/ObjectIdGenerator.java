package com.kossyuzokwe.util;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ObjectIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		ObjectId id = ObjectId.get();
		return id.toString();
	}

}
