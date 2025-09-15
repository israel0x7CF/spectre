package com.spectrun.spectrum.controllers.host;

import com.spectrun.spectrum.DTO.HostDto;
import com.spectrun.spectrum.MessageTemplate.initializeServerTemplate;
import com.spectrun.spectrum.models.Host;
import com.spectrun.spectrum.services.Implementations.HostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/host")
public class host {
    private HostService hostService;
   private KafkaTemplate<String, initializeServerTemplate> intializeServer;
    public host(HostService hostService,KafkaTemplate<String, initializeServerTemplate> intializeServer) {

        this.hostService = hostService;
     this.intializeServer = intializeServer;

    }

    @PostMapping("/host")
    public ResponseEntity<HostDto> createNewHost(@RequestBody Host host){
        HostDto newHost = this.hostService.createHost(host);
        return  new ResponseEntity<>(newHost, HttpStatus.OK);
    }
    @GetMapping("/host/{id}")
    public ResponseEntity<HostDto> getHostById(@PathVariable long id)
    {
        HostDto hostInformation = this.hostService.findHostById(id).orElse(null);
        if(hostInformation == null){
            return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(hostInformation, HttpStatus.OK);
    }

    @PutMapping("/host/{id}")
    public ResponseEntity<HostDto> updateHost(@PathVariable long hostId,Host host){
        HostDto updatedHost = this.hostService.updateHost(hostId,host);
        return new ResponseEntity<>(updatedHost, HttpStatus.OK);
    }
    @GetMapping("/initialize-server/{id}")
    public ResponseEntity<?> intializeServer(@PathVariable long id){
        HostDto host = this.hostService.findHostById(id).orElse(null);
        if (host != null){
            initializeServerTemplate template = new initializeServerTemplate();
            template.setHost(host.getHostname());
            template.setUsername(host.getSshUsername());
            template.setPassword(host.getSshPassword());

//            this.intializeServer.send("initialize-server",template);
        }
        return  new ResponseEntity<>("data",HttpStatus.OK);
    }

}
