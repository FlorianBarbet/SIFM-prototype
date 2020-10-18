package com.shoppo.presentation;

import com.shoppo.domain.InstanceFactory;
import com.shoppo.infrastructure.Ville;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
@RestController
@RequestMapping(value="/make",produces = MediaType.APPLICATION_JSON_VALUE)
public class InstanceFactoryController {

    @Value("${server.port}")
    private int serverPort;

    private int counterInstance = 0;

    @GetMapping("/{name}")
    public String launchVilleJar(
            @PathVariable("name") String name
    ) throws IOException {
        System.out.printf("Launch ville with name : %s%n",name);

        InstanceFactory.getInstance(Ville.class)
                .serverPort(serverPort+(++counterInstance))
                .name(name)
                .launch();

        return "test";
    }

}
