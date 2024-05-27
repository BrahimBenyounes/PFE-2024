package com.bys.backendservice.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bys.backendservice.entities.SecteurActivite;
import com.bys.backendservice.services.ISecteurActiviteService;

import java.util.List;

@RestController

@RequestMapping("/secteurActivite")
@CrossOrigin("*")
public class SecteurActiviteController {

	@Autowired
	ISecteurActiviteService secteurActiviteService;
	
	@GetMapping("/retrieve-all-secteurActivite")
	@ResponseBody
	public List<SecteurActivite> getSecteurActivite() {
		List<SecteurActivite> list = secteurActiviteService.retrieveAllSecteurActivite();
		return list;
	}

	@GetMapping("/retrieve-secteurActivite/{secteurActivite-id}")
	@ResponseBody
	public SecteurActivite retrieveSecteurActivite(@PathVariable("secteurActivite-id") Long secteurActiviteId) {
		return secteurActiviteService.retrieveSecteurActivite(secteurActiviteId);
	}

	@PostMapping("/add-secteurActivite")
	@ResponseBody
	public SecteurActivite addSecteurActivite(@RequestBody SecteurActivite sa) {
		SecteurActivite secteurActivite = secteurActiviteService.addSecteurActivite(sa);
		return secteurActivite;
	}

	@DeleteMapping("/remove-secteurActivite/{secteurActivite-id}")
	@ResponseBody
	public void removeSecteurActivite(@PathVariable("secteurActivite-id") Long secteurActiviteId) {
		secteurActiviteService.deleteSecteurActivite(secteurActiviteId);
	}

	@PutMapping("/modify-secteurActivite")
	@ResponseBody
	public SecteurActivite modifySecteurActivite(@RequestBody SecteurActivite secteurActivite) {
		return secteurActiviteService.updateSecteurActivite(secteurActivite);
	}

	
}
