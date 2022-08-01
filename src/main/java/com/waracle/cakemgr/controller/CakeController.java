package com.waracle.cakemgr.controller;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.service.CakeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CakeController {

	private static final String ADD_CAKE_FORM = "add_cake_form";

	private static final String CAKE_UPDATED_SUCCESS = "cake_updated_success";

	private static final String CAKE_LIST = "cake_list";

	private static final String CAKE_ADDED_SUCCESS = "cake_added_success";

	private static final String INDEX = "index";

	Logger logger = LoggerFactory.getLogger(CakeController.class);

	private final CakeService cakeService;

	@GetMapping("/")
	public String index() throws ServletException {
		cakeService.retrieveCakeDataFromServer();
		return INDEX;
	}

	@GetMapping("/cakes/add")
	public String displayAddCakeForm(Model model) throws ServletException {
		model.addAttribute("cake", new CakeEntity());
		return ADD_CAKE_FORM;
	}

	@PostMapping("/cakes")
	public String addNewCake(@Validated @ModelAttribute("cake") CakeEntity cake, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return ADD_CAKE_FORM;
		}
		cakeService.save(cake);
		return CAKE_ADDED_SUCCESS;
	}

	@GetMapping("/cakes")
	public String getCakes(Model model) {
		model.addAttribute("cakes", cakeService.getAllCakes());
		return CAKE_LIST;
	}

	@GetMapping("/cakes/{id}")
	public String getSingleCake(Model model, @PathVariable("id") long id) {
		model.addAttribute("cake", cakeService.getCake(id));
		return CAKE_LIST;
	}

	@PutMapping("/cakes")
	public String updateCake(@ModelAttribute("cake") CakeEntity cake) {
		cakeService.save(cake);

		return CAKE_UPDATED_SUCCESS;
	}

}
