package pro.skypro.skyshop.model.service;

import org.springframework.stereotype.Service;
import pro.skypro.skyshop.model.search.SearchResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    // Метод поиска
    public List<SearchResult> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        String queryLower = query.toLowerCase();

        return storageService.getAllSearchables().stream()
                .filter(searchable -> searchable != null)
                .filter(searchable -> searchable.getSearchTerm() != null)
                .filter(searchable -> searchable.getSearchTerm().toLowerCase().contains(queryLower))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}
