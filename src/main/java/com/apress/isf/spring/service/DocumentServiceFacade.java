package com.apress.isf.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apress.isf.java.model.Document;
import com.apress.isf.java.service.DocumentService;
import com.apress.isf.spring.data.DocumentDAO;

@Component("documentFacade")
public class DocumentServiceFacade implements DocumentService {

	@Autowired
	DocumentDAO documentDAO;
	
	@Override
	public List<Document> getAllDocuments() {
		return documentDAO.getAll();
	}

	@Override
	public Document findDocumentById(String id) {
		return documentDAO.findById(id);
	}

	@Override
	public Document saveDocument(String id, Document document) {
		return documentDAO.save(id, document);
	}

	@Override
	public Document removeDocumentById(String id) {
		return documentDAO.removeById(id);
	}

	@Override
	public boolean updateLocationFromDocumentId(String documentId, String location) {
		Document document = documentDAO.findById(documentId);
		if(null == document)
			return false;
		document.setLocation(location);
		saveDocument(documentId, document);
		return true;
	}

}
