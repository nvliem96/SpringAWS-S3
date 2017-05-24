package com.infamous.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infamous.Dao.NewsDao;
import com.infamous.Model.NewsModel;

@Service
public class NewsServiceImp implements NewsService {
	
	@Autowired
    private NewsDao newsDao;
	
	@Override
	public void addNews(NewsModel model) {
		newsDao.save(model);
	}

	@Override
	public NewsModel findNewsById(String id) {
		return newsDao.findNewsById(id);
	}

	@Override
	public void deleleNews(String id) {
		newsDao.deleteById(id);
		
	}

	@Override
	public Iterable<NewsModel> getAll() {
		return newsDao.findAll();
	}



	
	

}
