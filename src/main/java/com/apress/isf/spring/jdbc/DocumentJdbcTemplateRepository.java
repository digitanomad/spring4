package com.apress.isf.spring.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.apress.isf.java.model.Document;
import com.apress.isf.spring.data.DocumentDAO;

public class DocumentJdbcTemplateRepository implements DocumentDAO {

	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	private String query;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}
	
	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public List<Document> getAll() {
		return jdbcTemplate.query(query, new DocumentRowMapper());
	}

	@Override
	public void save(Document document) {
		throw new UnsupportedOperationException();
	}
}
