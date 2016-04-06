package com.time.admin.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/index")
public class AdminIndexController {

	private static final Log logger = LogFactory.getLog(AdminIndexController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login(){
        return new ModelAndView("hello");
    }

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public ModelAndView welcome(@PathVariable String name, ModelMap model) {
		model.addAttribute("message", "Welcome " + name);
		logger.debug("[Welcome counter :{}" + name);
		return new ModelAndView("hello");
	}

}
