package com.example.demo.controller;

import java.util.*;

import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.*;

@RestController
@RequestMapping("parcelle")
public class ParcelleController {
    @Autowired
    private ParcelleRepository parcelleRepository;
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private FermeRepository fermeRepository;
    @Autowired
    private TYpeSoleRepository tYpeSoleRepository;
    @Autowired
    private GrandeurRepository grandeurRepository;
    @Autowired
    private TypePlanteRepository typePlanteRepository;

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

    //nbr parcelle par ferme
    @GetMapping("/countparcelle")
    public Map<String, Integer> countparcelle() {
        Map<String, Integer> map = new HashMap<>();
        for (Ferme f : fermeRepository.findAll()) {
            int nbr_parcelle = 0;
            for (Parcelle p : parcelleRepository.findAll()) {
                if (f.getId() == p.getFerme().getId()) {
                    nbr_parcelle++;
                }

            }
            map.put("ferme N" + f.getId(), nbr_parcelle);
        }
        return map;
    }

    //nbr arosage par parcelle
    @GetMapping("/countarosage")
    public Map<String, Integer> countarosage() {
        Map<String, Integer> map = new HashMap<>();
        int nbr_arosage = 0;
        for (Parcelle parcelle : parcelleRepository.findAll()) {

            for (TypePlante p : typePlanteRepository.findAll()) {
                nbr_arosage = 0;
                for (Grandeur f : grandeurRepository.findAll()) {

                    if (f.getType().equals("humedite") && f.getParcelle().getId() == parcelle.getId()) {
                        {
                            if (p.getHumiditeMin() >= f.getValeur()) {
                                nbr_arosage++;
                            }
                        }

                    }

                }

            }
            map.put("parcelle N" + parcelle.getId(), nbr_arosage);
        }
        return map;
    }

    //alert
    @GetMapping("/getAlerts")
    public List<Alert> getAlerts() {
        alertRepository.deleteAll();
        Grandeur ggar = new Grandeur();
        for (Parcelle parcelle : parcelleRepository.findAll()) {
            for (TypePlante typePlante : typePlanteRepository.findAll()) {

                for (Grandeur grandeur : grandeurRepository.findAll()) {
                    //humidite
                    if (grandeur.getType().equals("humedite") && grandeur.getParcelle().getId() == parcelle.getId()) {

                        if (typePlante.getHumiditeMin() >= grandeur.getValeur()) {

                            if (ggar!=grandeur)
                            {
                                // humidite, Float temperature, Float luminosite, Date date, Parcelle parcelle
                                alertRepository.save(new Alert(grandeur.getValeur(), null, null, grandeur.getDate(), parcelle));

                            }

                            ggar=grandeur;
                        }

                    }
                    //temperature
                    if (grandeur.getType().equals("temperature") && grandeur.getParcelle().getId() == parcelle.getId()) {
                        if (typePlante.getTemperatureMax() <= grandeur.getValeur() || typePlante.getTemperatureMin() >= grandeur.getValeur()) {
                            // humidite, Float temperature, Float luminosite, Date date, Parcelle parcelle
                            alertRepository.save(new Alert(null, grandeur.getValeur(), null, grandeur.getDate(), parcelle));

                        }
                    }
                    //luminosite
                    if (grandeur.getType().equals("luminosite") && grandeur.getParcelle().getId() == parcelle.getId()) {
                        if (typePlante.getLuminosite() >= grandeur.getValeur()||typePlante.getLuminosite() <= grandeur.getValeur()) {
                            // humidite, Float temperature, Float luminosite, Date date, Parcelle parcelle
                            alertRepository.save(new Alert(null, null, grandeur.getValeur(), grandeur.getDate(), parcelle));
                            break;
                        }
                    }
                }
                break;
            }

        }

        return alertRepository.findAll();
    }
  


}
