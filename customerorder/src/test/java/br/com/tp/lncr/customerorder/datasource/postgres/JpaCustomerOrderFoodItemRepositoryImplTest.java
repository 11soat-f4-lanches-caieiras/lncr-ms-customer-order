package br.com.tp.lncr.customerorder.datasource.postgres;

import br.com.tp.lncr.core.dtos.customerorder.CustomerOrderFoodItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaCustomerOrderFoodItemRepositoryImplTest {

    @Mock
    private JpaCustomerOrderFoodItemRepository jpaCustomerOrderFoodItemRepository;
    @Mock
    private JpaCustomerOrderMapper jpaCustomerOrderMapper;

    private JpaCustomerOrderFoodItemRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new JpaCustomerOrderFoodItemRepositoryImpl();
    }

    @Test
    void shouldSaveAllFoodItemsSuccessfully() {
        List<CustomerOrderFoodItemDTO> inputDtos = Arrays.asList(
                createCustomerOrderFoodItemDTO(1, 1, "Hambúrguer", "Hambúrguer artesanal", 25.0, "Sem cebola"),
                createCustomerOrderFoodItemDTO(2, 1, "Batata Frita", "Batata frita crocante", 15.0, null)
        );

        List<JpaCustomerOrderFoodItemEntity> mappedEntities = Arrays.asList(
                createJpaCustomerOrderFoodItemEntity(null, 1, 1, 25.0, "Sem cebola", "Hambúrguer", "Hambúrguer artesanal"),
                createJpaCustomerOrderFoodItemEntity(null, 1, 2, 15.0, null, "Batata Frita", "Batata frita crocante")
        );

        List<JpaCustomerOrderFoodItemEntity> savedEntities = Arrays.asList(
                createJpaCustomerOrderFoodItemEntity(1, 1, 1, 25.0, "Sem cebola", "Hambúrguer", "Hambúrguer artesanal"),
                createJpaCustomerOrderFoodItemEntity(2, 1, 2, 15.0, null, "Batata Frita", "Batata frita crocante")
        );

        List<CustomerOrderFoodItemDTO> expectedDtos = Arrays.asList(
                createCustomerOrderFoodItemDTO(1, 1, "Hambúrguer", "Hambúrguer artesanal", 25.0, "Sem cebola"),
                createCustomerOrderFoodItemDTO(2, 1, "Batata Frita", "Batata frita crocante", 15.0, null)
        );

        when(jpaCustomerOrderMapper.customerOrderFoodItemDtoToJpa(inputDtos.get(0))).thenReturn(mappedEntities.get(0));
        when(jpaCustomerOrderMapper.customerOrderFoodItemDtoToJpa(inputDtos.get(1))).thenReturn(mappedEntities.get(1));
        when(jpaCustomerOrderFoodItemRepository.saveAll(mappedEntities)).thenReturn(savedEntities);
        when(jpaCustomerOrderMapper.jpaCustomerOrderFoodItemToDTO(savedEntities.get(0))).thenReturn(expectedDtos.get(0));
        when(jpaCustomerOrderMapper.jpaCustomerOrderFoodItemToDTO(savedEntities.get(1))).thenReturn(expectedDtos.get(1));

        List<CustomerOrderFoodItemDTO> result = repository.saveAll(inputDtos, jpaCustomerOrderFoodItemRepository, jpaCustomerOrderMapper);

        assertEquals(2, result.size());
        assertEquals(expectedDtos.get(0), result.get(0));
        assertEquals(expectedDtos.get(1), result.get(1));
        verify(jpaCustomerOrderFoodItemRepository).saveAll(mappedEntities);
    }

    @Test
    void shouldSaveEmptyListSuccessfully() {
        List<CustomerOrderFoodItemDTO> emptyInputList = Collections.emptyList();
        List<JpaCustomerOrderFoodItemEntity> emptyMappedList = Collections.emptyList();
        List<JpaCustomerOrderFoodItemEntity> emptySavedList = Collections.emptyList();

        when(jpaCustomerOrderFoodItemRepository.saveAll(emptyMappedList)).thenReturn(emptySavedList);

        List<CustomerOrderFoodItemDTO> result = repository.saveAll(emptyInputList, jpaCustomerOrderFoodItemRepository, jpaCustomerOrderMapper);

        assertTrue(result.isEmpty());
        verify(jpaCustomerOrderFoodItemRepository).saveAll(emptyMappedList);
    }

    @Test
    void shouldFindFoodItemsByCustomerOrderId() {
        Integer customerOrderId = 1;
        List<JpaCustomerOrderFoodItemEntity> entities = Arrays.asList(
                createJpaCustomerOrderFoodItemEntity(1, 1, 1, 25.0, "Sem cebola", "Hambúrguer", "Hambúrguer artesanal"),
                createJpaCustomerOrderFoodItemEntity(2, 1, 2, 15.0, null, "Batata Frita", "Batata frita crocante")
        );

        List<CustomerOrderFoodItemDTO> expectedDtos = Arrays.asList(
                createCustomerOrderFoodItemDTO(1, 1, "Hambúrguer", "Hambúrguer artesanal", 25.0, "Sem cebola"),
                createCustomerOrderFoodItemDTO(2, 1, "Batata Frita", "Batata frita crocante", 15.0, null)
        );

        when(jpaCustomerOrderFoodItemRepository.findByCustomerOrderId(customerOrderId)).thenReturn(entities);
        when(jpaCustomerOrderMapper.jpaCustomerOrderFoodItemToDTO(entities.get(0))).thenReturn(expectedDtos.get(0));
        when(jpaCustomerOrderMapper.jpaCustomerOrderFoodItemToDTO(entities.get(1))).thenReturn(expectedDtos.get(1));

        List<CustomerOrderFoodItemDTO> result = repository.findByCustomerOrderId(customerOrderId, jpaCustomerOrderFoodItemRepository, jpaCustomerOrderMapper);

        assertEquals(2, result.size());
        assertEquals(expectedDtos.get(0), result.get(0));
        assertEquals(expectedDtos.get(1), result.get(1));
        verify(jpaCustomerOrderFoodItemRepository).findByCustomerOrderId(customerOrderId);
    }

    @Test
    void shouldReturnEmptyListWhenNoFoodItemsFoundByCustomerOrderId() {
        Integer customerOrderId = 999;

        when(jpaCustomerOrderFoodItemRepository.findByCustomerOrderId(customerOrderId)).thenReturn(Collections.emptyList());

        List<CustomerOrderFoodItemDTO> result = repository.findByCustomerOrderId(customerOrderId, jpaCustomerOrderFoodItemRepository, jpaCustomerOrderMapper);

        assertTrue(result.isEmpty());
        verify(jpaCustomerOrderFoodItemRepository).findByCustomerOrderId(customerOrderId);
        verifyNoInteractions(jpaCustomerOrderMapper);
    }

    @Test
    void shouldFindFoodItemsByCustomerOrderIdList() {
        List<Integer> customerOrderIdsList = Arrays.asList(1, 2);
        List<JpaCustomerOrderFoodItemEntity> entities = Arrays.asList(
                createJpaCustomerOrderFoodItemEntity(1, 1, 1, 25.0, "Sem cebola", "Hambúrguer", "Hambúrguer artesanal"),
                createJpaCustomerOrderFoodItemEntity(2, 1, 2, 15.0, null, "Batata Frita", "Batata frita crocante"),
                createJpaCustomerOrderFoodItemEntity(3, 2, 3, 30.0, "Bem passado", "X-Bacon", "Hambúrguer com bacon")
        );

        List<CustomerOrderFoodItemDTO> expectedDtos = Arrays.asList(
                createCustomerOrderFoodItemDTO(1, 1, "Hambúrguer", "Hambúrguer artesanal", 25.0, "Sem cebola"),
                createCustomerOrderFoodItemDTO(2, 1, "Batata Frita", "Batata frita crocante", 15.0, null),
                createCustomerOrderFoodItemDTO(3, 2, "X-Bacon", "Hambúrguer com bacon", 30.0, "Bem passado")
        );

        when(jpaCustomerOrderFoodItemRepository.findByCustomerOrderIdList(customerOrderIdsList)).thenReturn(entities);
        when(jpaCustomerOrderMapper.jpaCustomerOrderFoodItemToDTO(entities.get(0))).thenReturn(expectedDtos.get(0));
        when(jpaCustomerOrderMapper.jpaCustomerOrderFoodItemToDTO(entities.get(1))).thenReturn(expectedDtos.get(1));
        when(jpaCustomerOrderMapper.jpaCustomerOrderFoodItemToDTO(entities.get(2))).thenReturn(expectedDtos.get(2));

        List<CustomerOrderFoodItemDTO> result = repository.findByCustomerOrderIdList(customerOrderIdsList, jpaCustomerOrderFoodItemRepository, jpaCustomerOrderMapper);

        assertEquals(3, result.size());
        assertEquals(expectedDtos.get(0), result.get(0));
        assertEquals(expectedDtos.get(1), result.get(1));
        assertEquals(expectedDtos.get(2), result.get(2));
        verify(jpaCustomerOrderFoodItemRepository).findByCustomerOrderIdList(customerOrderIdsList);
    }

    @Test
    void shouldReturnEmptyListWhenNoFoodItemsFoundByCustomerOrderIdList() {
        List<Integer> customerOrderIdsList = Arrays.asList(999, 998);

        when(jpaCustomerOrderFoodItemRepository.findByCustomerOrderIdList(customerOrderIdsList)).thenReturn(Collections.emptyList());

        List<CustomerOrderFoodItemDTO> result = repository.findByCustomerOrderIdList(customerOrderIdsList, jpaCustomerOrderFoodItemRepository, jpaCustomerOrderMapper);

        assertTrue(result.isEmpty());
        verify(jpaCustomerOrderFoodItemRepository).findByCustomerOrderIdList(customerOrderIdsList);
        verifyNoInteractions(jpaCustomerOrderMapper);
    }

    @Test
    void shouldFindFoodItemsByEmptyCustomerOrderIdList() {
        List<Integer> emptyOrderIdsList = Collections.emptyList();

        when(jpaCustomerOrderFoodItemRepository.findByCustomerOrderIdList(emptyOrderIdsList)).thenReturn(Collections.emptyList());

        List<CustomerOrderFoodItemDTO> result = repository.findByCustomerOrderIdList(emptyOrderIdsList, jpaCustomerOrderFoodItemRepository, jpaCustomerOrderMapper);

        assertTrue(result.isEmpty());
        verify(jpaCustomerOrderFoodItemRepository).findByCustomerOrderIdList(emptyOrderIdsList);
        verifyNoInteractions(jpaCustomerOrderMapper);
    }

    private CustomerOrderFoodItemDTO createCustomerOrderFoodItemDTO(Integer id, Integer orderId, String name, String description, Double price, String notes) {
        return new CustomerOrderFoodItemDTO(id, orderId, name, description, price, notes);
    }

    private JpaCustomerOrderFoodItemEntity createJpaCustomerOrderFoodItemEntity(Integer id, Integer orderId, Integer foodItemId, Double price, String notes, String name, String description) {
        return new JpaCustomerOrderFoodItemEntity(id, orderId, foodItemId, price, notes, name, description);
    }
}
