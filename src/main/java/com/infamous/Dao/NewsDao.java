package com.infamous.Dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.infamous.Model.NewsModel;

@Transactional
public interface NewsDao extends CrudRepository<NewsModel, String>{
	NewsModel findNewsById(String id);

	void deleteById(String id); 
}
