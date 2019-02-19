package com.vetx.jarVes.service;

import com.vetx.jarVes.model.VoyEstimate;
import com.vetx.jarVes.repository.VoyEstimateRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ToTextService {

    private VoyEstimateRepository voyEstimateRepository;

    public ToTextService(VoyEstimateRepository voyEstimateRepository) {
        this.voyEstimateRepository = voyEstimateRepository;
    }

    public String vesselToText(Long id) {
        VoyEstimate voyEstimate = voyEstimateRepository.findById(id).get();


        return "VOYAGE ESTIMATE\n" +
                "---------------\n" +
                "BRAZEN\n" +
                "Speed/Ifobal/Ifoldn/Mdo sea/Mdo Port\n"
                + voyEstimate.getName() + voyEstimate.getIfoBallast() + voyEstimate.getIfoLaden() + voyEstimate.getMgoPortWork() + voyEstimate.getMgoPortIdle() +
                "\n" +
                "Variables\n" +
                "Load Disch. Steaming\n" +
                "40% 40% 0%\n" +
                "\n" +
                "Voy : RBAY / YANBU\n" +
                "Acct : SRS\n" +
                "Commty: MINERALS L/rate : 6,200:X:\n" +
                "lcan : 8/20 DEC D/rate : 3,500:X:\n" +
                "Qty : 26,500.00 mts Commission : 7.50 %\n" +
                "\n" +
                "Reposition : RSEA\n" +
                "\n" +
                "Frt usd : 27.50 Per MT Intake" +
                "id=" + id +
                '}';
    }
}
