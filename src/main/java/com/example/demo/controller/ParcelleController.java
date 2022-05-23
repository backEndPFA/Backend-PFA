package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.*;
import com.example.demo.repository.FermeRepository;
import com.example.demo.repository.ParcelleRepository;
import com.example.demo.repository.TYpeSoleRepository;

@RestController
@RequestMapping("parcelle")
public class ParcelleController {
	@Autowired
	private ParcelleRepository parcelleRepository;
	@Autowired
	private FermeRepository fermeRepository;
	@Autowired
	private TYpeSoleRepository tYpeSoleRepository;

	@GetMapping("/all")
	public List<Parcelle> findAll() {
		return parcelleRepository.findAll();
	}

	@GetMapping(value = "/count")
	public int count() {
		int i = 0;
		for (Parcelle parcelle : parcelleRepository.findAll()) {
			++i;
		}
		return i;
	}

	@GetMapping(value = "/{id}")
	public Optional<Parcelle> findByCode(@PathVariable final int id) {
		return parcelleRepository.findById(id);
	}
	@PostMapping(value = "/save")
	public void save(@RequestBody final Parcelle parcelle) {
		parcelleRepository.save(parcelle);
	}
	// add new parcelle 
	@PostMapping(value = "/saves")
	public void saves(@RequestBody Parcelle parcelle) {
		Ferme fi = new Ferme();
		TypeSole ti = new TypeSole();
		for (Ferme f : fermeRepository.findAll()) {
			if (f.getId() == parcelle.getFerme().getId()) {
				fi = f;
			}
		}
		for (TypeSole t : tYpeSoleRepository.findAll()) {
			if (t.getId() == parcelle.getTypeSole().getId()) {
				ti = t;
			}
		}
		Parcelle p = new Parcelle(parcelle.getSurface(), parcelle.getPhoto(), fi, ti);
		parcelleRepository.save(p);

	}

	@DeleteMapping(value = "/delete/{id}")
	public void delete(@PathVariable(required = true) int id) {
		parcelleRepository.deleteById(id);
	}
}
