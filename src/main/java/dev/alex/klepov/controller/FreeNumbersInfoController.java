package dev.alex.klepov.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import dev.alex.klepov.controller.view.FreeNumbersByCountryResponseView;
import dev.alex.klepov.model.FreeNumberModel;
import dev.alex.klepov.service.FreeSimsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/freeNumbers") // in case we'd want to move to the new version of api
public class FreeNumbersInfoController {

    @Autowired
    private FreeSimsService freeSimsService;

    @GetMapping("/byCountry")
    public ResponseEntity<FreeNumbersByCountryResponseView> freeNumbersByCountry() {
        Map<Long, List<FreeNumberModel>> numbersByCountry = freeSimsService.getFreeNumbersAndUpdateDb(Set.of());

        return ResponseEntity.ok(new FreeNumbersByCountryResponseView(numbersByCountry));
    }
}
