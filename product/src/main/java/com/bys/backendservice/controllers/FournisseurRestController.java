
package com.bys.backendservice.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bys.backendservice.entities.Fournisseur;
import com.bys.backendservice.services.IFournisseurService;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/fournisseur")
public class FournisseurRestController {

	@Autowired
	IFournisseurService fournisseurService;

	@GetMapping("/retrieve-all-fournisseurs")
	@ResponseBody
	public List<Fournisseur> getFournisseurs() {
		List<Fournisseur> fournisseurs = fournisseurService.retrieveAllFournisseurs();
		return fournisseurs;
	}

	@GetMapping("/retrieve-fournisseur/{fournisseur-id}")
	@ResponseBody
	public Fournisseur retrieveFournisseur(@PathVariable("fournisseur-id") Long fournisseurId) {
		return fournisseurService.retrieveFournisseur(fournisseurId);
	}

	@PostMapping("/add-fournisseur")
	@ResponseBody
	public Fournisseur addFournisseur(@RequestBody Fournisseur f) {
		Fournisseur fournisseur = fournisseurService.addFournisseur(f);
		return fournisseur;
	}

	@DeleteMapping("/remove-fournisseur/{fournisseur-id}")
	@ResponseBody
	public void removeFournisseur(@PathVariable("fournisseur-id") Long fournisseurId) {
		fournisseurService.deleteFournisseur(fournisseurId);
	}

	@PutMapping("/modify-fournisseur")
	@ResponseBody
	public Fournisseur modifyFournisseur(@RequestBody Fournisseur fournisseur) {
		return fournisseurService.updateFournisseur(fournisseur);
	}

		@PutMapping(value = "/assignSecteurActiviteToFournisseur/{idSecteurActivite}/{idFournisseur}")
		public void assignProduitToStock(@PathVariable("idSecteurActivite") Long idSecteurActivite, @PathVariable("idFournisseur") Long idFournisseur) {
			fournisseurService.assignSecteurActiviteToFournisseur(idSecteurActivite, idFournisseur);
		}

}
