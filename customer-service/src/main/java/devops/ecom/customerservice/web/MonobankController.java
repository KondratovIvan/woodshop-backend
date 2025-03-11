package devops.ecom.customerservice.web;

import devops.ecom.customerservice.service.MonobankService;
import devops.ecom.customerservice.web.model.MonobankInvoiceRequest;
import devops.ecom.customerservice.web.model.MonobankInvoiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monobank")
@CrossOrigin(origins = "http://localhost:4200")
public class MonobankController {
    private final MonobankService monobankService;

    public MonobankController(MonobankService monobankService) {
        this.monobankService = monobankService;
    }

    @PostMapping("/create-invoice")
    public ResponseEntity<MonobankInvoiceResponse> createInvoice(@RequestBody MonobankInvoiceRequest request) {
        MonobankInvoiceResponse response = monobankService.createInvoice(request);
        return ResponseEntity.ok(response);
    }
}
