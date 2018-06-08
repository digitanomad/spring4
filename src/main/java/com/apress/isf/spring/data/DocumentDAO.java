package com.apress.isf.spring.data;

import java.util.List;

import com.apress.isf.java.model.Document;

public interface DocumentDAO {
	public List<Document> getAll();
	public void save(Document document);
	public Document save(String id, Document document);
	public Document findById(String id);
	public Document removeById(String id);
}
