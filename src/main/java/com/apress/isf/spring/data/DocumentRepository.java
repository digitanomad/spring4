package com.apress.isf.spring.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.apress.isf.java.model.Document;

@Repository("documentDAO")
public class DocumentRepository implements DocumentDAO {
	
	@Autowired
    private DataSource dataSource;

    @Resource
    private Map<String,String> sql;

	@Override
    public List<Document> getAll() {
		return new JdbcTemplate(dataSource).query(sql.get("query"), 
				new DocumentRowMapper());
    }
    
    @Override
    public Document findById(String id) {
    	Document document = null;
    	JdbcTemplate template = new JdbcTemplate(dataSource);
    	
    	try {
    		document = template.queryForObject(sql.get("find"),
    				new Object[] { id },
    				new DocumentRowMapper());
    	} catch (EmptyResultDataAccessException ex) {
    		
    	}
    	
    	return document;
    }
    
    @Override
    public void save(Document document) {
    	throw new UnsupportedOperationException();
    }

	@SuppressWarnings("null")
	@Override
	public Document save(String id, Document document) {
		try {
    		JdbcTemplate template = new JdbcTemplate(dataSource);
            Document _document = findById(id);
            if (_document != null) {
                template.update(sql.get("insert"),
                		new Object[] { id,
	                    document.getName(), document.getLocation(),
	                    document.getDescription(),
	                    document.getType().getTypeId(),
	                    document.getCreated(), 
	                    document.getModified() });
            } else {
                _document.setName((document.getName() == null) ? _document.getName() : document.getName());
                _document.setLocation((document.getLocation() == null) ? _document.getLocation() : document.getLocation());
                _document.setDescription((document.getDescription() == null) ? _document.getDescription() : document.getDescription());
                _document.setType((document.getType() == null) ? _document.getType() : document.getType());
                _document.setModified(new Date());
                
                template.update(sql.get("update"),
                    new Object[] { _document.getName(),
                        _document.getLocation(),
                        _document.getDescription(),
                        _document.getType().getTypeId(),
                        new Date(),
                        id });
                document = _document;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return document;
	}

	@Override
	public Document removeById(String id) {
		Document document = findById(id);
        if (document != null) {
            try {
                JdbcTemplate template = new JdbcTemplate(dataSource);
                int rows  = template.update(sql.get("delete"), new Object[] { id });
                if (rows <= 0) {
                	document = null;
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return document;
	}

}
