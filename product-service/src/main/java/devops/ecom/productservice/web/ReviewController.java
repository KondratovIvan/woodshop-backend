package devops.ecom.productservice.web;

import devops.ecom.productservice.dao.entities.Review;
import devops.ecom.productservice.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getReviews(@RequestParam(required = false) String productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        if (review.getDate() == null) {
            review.setDate(LocalDateTime.now());
        }
        return reviewService.saveReview(review);
    }

}
