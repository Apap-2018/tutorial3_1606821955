package com.apap.tutorial3.controller;

import java.util.List;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value = "id", required = true) String id,
					@RequestParam(value = "licenseNumber", required = true) String licenseNumber,
					@RequestParam(value = "name", required = true) String name,
					@RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/view/license-number/{licenseNumber}")
	public String viewpath(@PathVariable String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		if (archive != null) {
			model.addAttribute("pilot", archive);
			return "view-pilot";
			
		} else {
			model.addAttribute("notfound", "License Number");
			return "not-found";
		}
	}
	
	@RequestMapping("/pilot/update/license-number/{licenseNumberOld}/fly-hour/{licenseNumberNew}")
	public String changelicense(@PathVariable String licenseNumberOld, @PathVariable String licenseNumberNew, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumberOld);
		
		if (archive != null) {
			pilotService.changePilotLicenseNumber(licenseNumberOld, licenseNumberNew);
			return "change-license-number";
			
		} else {
			model.addAttribute("notfound", "License Number");
			return "not-found";
		}
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		
		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}
	
	@RequestMapping("/pilot/delete/id/{id}")
	public String delete(@PathVariable String id, Model model) {
		PilotModel archive = pilotService.getPilotDetailById(id);
		
		if (archive != null) {
			pilotService.deletePilot(id);
			
			return "delete";
			
		} else {
			model.addAttribute("notfound", "Id");
			return "not-found";
		}
	}
}
