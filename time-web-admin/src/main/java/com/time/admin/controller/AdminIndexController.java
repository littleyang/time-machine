package com.time.admin.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AdminIndexController {

	private static int counter = 0;
	private static final String VIEW_INDEX = "hello";
	private static final Log logger = LogFactory.getLog(AdminIndexController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(ModelMap model) {
		model.addAttribute("message", "Welcome");
		model.addAttribute("counter", ++counter);
		logger.debug("[Welcome counter :{}" + counter);
		return VIEW_INDEX;// 返回index.jsp
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String welcome(@PathVariable String name, ModelMap model) {
		model.addAttribute("message", "Welcome " + name);
		model.addAttribute("counter", ++counter);
		logger.debug("[Welcome counter :{}" + counter);
		return VIEW_INDEX;// 返回index.jsp
	}

}
