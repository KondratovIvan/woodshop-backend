package devops.ecom.pageeventconsumerservice.web;

import devops.ecom.pageeventconsumerservice.entities.PageEventClickProduct;
import devops.ecom.pageeventconsumerservice.entities.PageEventSearchByCategory;
import devops.ecom.pageeventconsumerservice.entities.PageEventSearchByKeyword;
import devops.ecom.pageeventconsumerservice.repos.PageEventClickProductRepo;
import devops.ecom.pageeventconsumerservice.repos.PageEventSearchByCategoryRepo;
import devops.ecom.pageeventconsumerservice.repos.PageEventSearchByKeywordRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
@CrossOrigin(origins = "*")
public class PageEventMetricsController {

    private final PageEventClickProductRepo clickRepo;
    private final PageEventSearchByCategoryRepo categoryRepo;
    private final PageEventSearchByKeywordRepo keywordRepo;

    public PageEventMetricsController(
            PageEventClickProductRepo clickRepo,
            PageEventSearchByCategoryRepo categoryRepo,
            PageEventSearchByKeywordRepo keywordRepo) {
        this.clickRepo = clickRepo;
        this.categoryRepo = categoryRepo;
        this.keywordRepo = keywordRepo;
    }

    @GetMapping("/clicks")
    public List<PageEventClickProduct> allClicks() {
        return clickRepo.findAll();
    }

    @GetMapping("/categories")
    public List<PageEventSearchByCategory> allCategorySearches() {
        return categoryRepo.findAll();
    }

    @GetMapping("/keywords")
    public List<PageEventSearchByKeyword> allKeywordSearches() {
        return keywordRepo.findAll();
    }

}
