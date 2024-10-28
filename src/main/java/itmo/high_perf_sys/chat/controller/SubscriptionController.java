package itmo.high_perf_sys.chat.controller;

import itmo.high_perf_sys.chat.dto.subs.request.CreateSubRequest;
import itmo.high_perf_sys.chat.dto.customer.request.UpdateUserInfoRequest;
import itmo.high_perf_sys.chat.dto.subs.response.SubscriptionResponse;
import itmo.high_perf_sys.chat.entity.Subscribers;
import itmo.high_perf_sys.chat.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subscribe")
public class SubscriptionController {

    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.service = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<UUID> makeSubscription(@RequestBody @Valid CreateSubRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSub(request));
    }

    @GetMapping("/{subId}")
    public ResponseEntity<Subscribers> getSubscriptionById(@PathVariable UUID subId) {
        Subscribers subscription = service.getSub(subId);
        return ResponseEntity.ok(subscription);
    }

    @DeleteMapping("/{subId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable UUID subId) {
        service.deleteSub(subId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptionsByUserId(@PathVariable UUID userId) {
        List<SubscriptionResponse> subscriptions = service.getSubscriptionsByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }



}
