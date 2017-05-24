package com.infamous.Service;
import com.infamous.Model.NewsModel;
public interface NewsService {
	
	void addNews(NewsModel model);
	
//	void editNewsTitle(String id,String title);
//	
//	void editNewsContent(String id,String content);
//	
//	void editNewsAttactLink(String id,String attactLink);
//	
//	void editNews(String id,String title,String content,String attactLink);
	
	void deleleNews(String id);
	
	NewsModel findNewsById(String id);
	
	Iterable<NewsModel> getAll();
}
