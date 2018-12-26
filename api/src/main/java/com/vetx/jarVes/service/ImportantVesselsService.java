package com.vetx.jarVes.service;

import com.vetx.jarVes.exceptions.UserNotFoundException;
import com.vetx.jarVes.model.User;
import com.vetx.jarVes.model.Vessel;
import com.vetx.jarVes.payload.ImportantVesselDTO;
import com.vetx.jarVes.repository.UserRepository;
import com.vetx.jarVes.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImportantVesselsService {

    private VesselRepository vesselRepository;

    private UserRepository userRepository;

    @Autowired
    public ImportantVesselsService(VesselRepository vesselRepository, UserRepository userRepository) {
        this.vesselRepository = vesselRepository;
        this.userRepository = userRepository;
    }

    public List<ImportantVesselDTO> getImportantVessels(Long id){



        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        List<ImportantVesselDTO> importantVessels = new ArrayList<>();

        vesselRepository.findAll().forEach(vessel -> {
            ImportantVesselDTO importantVessel = ImportantVesselDTO.builder().id(vessel.getId()).name(vessel.getName())
                    .dwt(vessel.getDwt()).type(vessel.getType()).flag(vessel.getFlag()).built(vessel.getBuilt())
                    .gear(vessel.getGear()).grain(vessel.getGrain()).manager(vessel.getManager()).pic(vessel.getPic())
                    .speed(vessel.getSpeed()).ifo_ballast(vessel.getIfo_ballast()).ifo_laden(vessel.getIfo_laden())
                    .port_idle(vessel.getPort_idle()).port_working(vessel.getPort_working())
                    .mgo_port_idle(vessel.getMgo_port_idle()).mgo_port_working(vessel.getMgo_port_working())
                    .boiler(vessel.getBoiler()).important(user.getImportantVessels()
                    .contains(vessel) ? true : false).build();

            importantVessels.add(importantVessel);
        });


        return importantVessels;

    }
}
