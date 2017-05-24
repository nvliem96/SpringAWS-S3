package com.infamous.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.infamous.AmazonService.AmazonService;
import com.infamous.AmazonService.AmazonServiceManager;
//import com.infamous.GDservice.GoogleDriveService;
import com.infamous.Model.InformationFile;
import com.infamous.Model.NewsModel;
import com.infamous.Service.NewsService;

@Controller
public class MainController {

	//private final GoogleDriveService serviceGoogle;
	private final AmazonService serviceAmazon;

	@Autowired
	public MainController(AmazonService serviceAmazon) {
		this.serviceAmazon = serviceAmazon;
	}

	@Autowired
	private NewsService newsService;

	// Home
	@GetMapping("/")
	public String Home(Model model) {
		return "index";
	}

	@GetMapping("/upload-news")
	public String uploadNews(Model model) {
		return "upload";
	}

	@PostMapping("/upload-news")
	public String handlerUploadNews(
			@RequestParam("file") MultipartFile file, @RequestParam("title") String title,
			@RequestParam("content") String content, RedirectAttributes redirectAttributes, HttpServletRequest request)
			throws IOException {
		try {
			NewsModel model = new NewsModel();

			model.setTitle(title);
			model.setContent(content);
			String flagupload = serviceAmazon.uploadFile(file.getOriginalFilename(), file.getInputStream());
				

			if (flagupload != null) {
				model.setAttactLink(AmazonServiceManager.DOWNLOAD_LINK + flagupload);
			}
			redirectAttributes.addFlashAttribute("message", "Upload Success");
			newsService.addNews(model);

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Upload fail");
		}

		return "redirect:/upload-news";
	}

	@GetMapping("view")
	@ModelAttribute("news")
	public NewsModel handlerFindNews(String id) {
		return newsService.findNewsById(id);
	}

	@GetMapping("all-file")
	@ModelAttribute("list")
	public List<InformationFile> Download() {
		return serviceAmazon.getAllFile();
	}

	@GetMapping("all-news")
	@ModelAttribute("list")
	public Iterable<NewsModel> allNews() {
		return newsService.getAll();
	}
	
	
//GOOGLE
//	@GetMapping("/download/{fileid}")
//	@ResponseBody
//	public void Download(@PathVariable String fileid, HttpServletResponse response) {
//		ByteArrayOutputStream out = serviceGoogle.downloadFile(fileid);
//		InformationFile info = serviceGoogle.printInformationFile(fileid);
//		System.out.println(info.toString());
//		response.setHeader("Content-Type", info.getType());
//
//		response.setHeader("Content-Length", String.valueOf(out.size()));
//
//		response.setHeader("Content-Disposition", "inline; filename=\"" + info.getTitle() + "\"");
//
//		try {
//			response.getOutputStream().write(out.toByteArray(), 0, out.size());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}