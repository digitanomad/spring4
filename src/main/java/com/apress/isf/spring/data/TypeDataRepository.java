package com.apress.isf.spring.data;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apress.isf.java.model.Type;

public class TypeDataRepository implements TypeDataDAO {
	
	private static final Logger log = LoggerFactory.getLogger(TypeDataRepository.class);
	private Map<String, Type> types = null;
	
	public Map<String, Type> getTypes() {
		return types;
	}

	public void setTypes(Map<String, Type> types) {
		this.types = types;
	}

	@Override
	public Type[] getAll() {
		return types.values().toArray(new Type[types.values().size()]);
	}

	@Override
	public Type findById(String id) {
		if (log.isDebugEnabled()) {
			log.debug("Start <findById> Params: " + id);
		}
		
		Type type = types.get(id);
		
		if (log.isDebugEnabled()) {
			log.debug("End <findById> Params: " + types);
		}
		
		return type;
	}

}
