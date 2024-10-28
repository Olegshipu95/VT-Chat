package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.customer.request.CreateSubRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/subscribe")
public class SubscriptionController {


    @PostMapping
    public ResponseEntity<UUID> makeSubscription(@RequestBody @Valid CreateSubRequest request){
//        var savedId = service.createSub(request);
//
//        return new ResponseEntity<>(savedId, HttpStatus.LOCKED);
        return new ResponseEntity<>(UUID.randomUUID(), HttpStatus.LOCKED);
    }

    @GetMapping
    public ResponseEntity<UpdateUserInfoRequest> getSubscriptions() {
        return ResponseEntity.noContent().build();
    }
}
