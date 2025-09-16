package pro.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.skypro.skyshop.model.search.SearchResult;
import pro.skypro.skyshop.model.search.Searchable;
import pro.skypro.skyshop.model.service.SearchService;
import pro.skypro.skyshop.model.service.StorageService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_WhenNoObjectsInStorage_ReturnEmptyList() {
        when(storageService.getAllSearchables()).thenReturn(List.of());

        List<SearchResult> result = searchService.search("test");

        assertTrue(result.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenObjectsExistButNoMatch_ReturnsEmptyList() {
        Searchable mockSearchable = mock(Searchable.class);
        when(mockSearchable.getSearchTerm()).thenReturn("iphone");
        when(storageService.getAllSearchables()).thenReturn(List.of(mockSearchable));

        List<SearchResult> result = searchService.search("samsung");

        assertTrue(result.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenMatchingObjectExists_ReturnsListWithResult() {
        Searchable mockSearchable = mock(Searchable.class);
        when(mockSearchable.getSearchTerm()).thenReturn("iphone 13 pro");
        when(mockSearchable.getName()).thenReturn("iPhone 13 Pro");
        when(mockSearchable.getContentType()).thenReturn("PRODUCT");
        when(mockSearchable.getId()).thenReturn(UUID.randomUUID());

        when(storageService.getAllSearchables()).thenReturn(List.of(mockSearchable));

        List<SearchResult> result = searchService.search("iphone");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("iPhone 13 Pro", result.get(0).getName());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenQueryIsNull_ReturnsEmptyList() {
        // Act
        List<SearchResult> result = searchService.search(null);

        // Assert
        assertTrue(result.isEmpty());
        verifyNoInteractions(storageService);
    }

    @Test
    void search_WhenQueryIsEmpty_ReturnsEmptyList() {
        // Act
        List<SearchResult> result = searchService.search("");

        // Assert
        assertTrue(result.isEmpty());
        verifyNoInteractions(storageService);
    }

    @Test
    void search_WhenSearchableIsNull_IgnoresNullItems() {
        when(storageService.getAllSearchables()).thenReturn(List.of(null));

        List<SearchResult> result = searchService.search("test");

        assertTrue(result.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenSearchTermIsNull_IgnoresItemsWithNullSearchTerm() {
        Searchable mockSearchable = mock(Searchable.class);
        when(mockSearchable.getSearchTerm()).thenReturn(null);
        when(storageService.getAllSearchables()).thenReturn(List.of(mockSearchable));

        List<SearchResult> result = searchService.search("test");

        assertTrue(result.isEmpty());
        verify(storageService).getAllSearchables();
    }
}
