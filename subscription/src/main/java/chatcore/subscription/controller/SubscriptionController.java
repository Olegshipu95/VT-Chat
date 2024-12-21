package chatcore.subscription.controller;

import chatcore.subscription.dto.subs.request.CreateSubRequest;
import chatcore.subscription.dto.subs.response.SubscriptionResponse;
import chatcore.subscription.entity.Subscribers;
import chatcore.subscription.service.SubscriptionService;
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
