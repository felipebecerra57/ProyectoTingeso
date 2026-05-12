package com.example.demo.Services;

import com.example.demo.controllers.DTO.TuristicPackageInDTO;
import com.example.demo.controllers.DTO.TuristicPackageOutDTO;
import com.example.demo.entities.TuristicPackageEntity;
import com.example.demo.repositories.TuristicPackageRepository;
import com.example.demo.services.TuristicPackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TuristicPackageService - Pruebas Unitarias")
class TuristicPackageServiceTest {

    @Mock
    private TuristicPackageRepository repository;

    @InjectMocks
    private TuristicPackageService service;

    // Test data
    private TuristicPackageEntity packageEntity;
    private TuristicPackageInDTO packageInDTO;

    @BeforeEach
    void setUp() {
        packageEntity = new TuristicPackageEntity();
        packageEntity.setId(1L);
        packageEntity.setDestiny("Cartagena");
        packageEntity.setPrice(1500.0f);
        packageEntity.setCapacity(20);
        packageEntity.setStatus(true);
        packageEntity.setDurationDays(7);

        packageInDTO = new TuristicPackageInDTO();
        packageInDTO.setDestiny("Cartagena");
        packageInDTO.setPrice(1500.0f);
        packageInDTO.setCapacity(20);
        packageInDTO.setStatus(true);
        packageInDTO.setDurationDays(7);
    }
    @Test
    void createPackage_Success() throws Exception {
        // GIVEN
        TuristicPackageInDTO input = new TuristicPackageInDTO();
        input.setName("Tour Torres del Paine");
        input.setDestiny("Patagonia");
        input.setPrice(500000f);
        input.setCapacity(20);

        Date inicio = new Date();
        Date fin = new Date(inicio.getTime() + 86400000); // +1 día
        input.setInicialDate(inicio);
        input.setFinalDate(fin);

        when(repository.save(any(TuristicPackageEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN
        TuristicPackageOutDTO result = service.createPackage(input);

        // THEN
        assertThat(result).isNotNull();
        verify(repository, times(1)).save(any());
    }

    @Test
    void createPackageWhenNameIsEmpty() {
        // GIVEN:
        TuristicPackageInDTO input = new TuristicPackageInDTO();
        input.setName("");

        // WHEN & THEN
        assertThatThrownBy(() -> service.createPackage(input))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("El nombre debe tener un largo mayor a cero");
        verify(repository, never()).save(any());
    }

    @Test
    void createPackageWhenPriceIsZero() {
        // GIVEN: Precio inválido
        TuristicPackageInDTO input = new TuristicPackageInDTO();
        input.setName("Valparaíso");
        input.setDestiny("Chile");
        input.setPrice(0f);

        // WHEN & THEN
        assertThatThrownBy(() -> service.createPackage(input))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("El precio debe ser mayor a cero");
    }

    @Test
    void createPackageWhenDatesAreInvalid() {
        // GIVEN
        TuristicPackageInDTO input = new TuristicPackageInDTO();
        input.setName("San Pedro");
        input.setDestiny("Atacama");

        Date hoy = new Date();
        Date ayer = new Date(hoy.getTime() - 86400000);
        input.setInicialDate(hoy);
        input.setFinalDate(ayer);

        // WHEN & THEN
        assertThatThrownBy(() -> service.createPackage(input))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("La fecha de término debe ser mayor a la de inicio");
    }


    // =====================================================
    //  deletePackage
    // =====================================================
    @Nested
    @DisplayName("deletePackage()")
    class DeletePackage {

        @Test
        @DisplayName("Debe retornar true cuando el paquete existe y se elimina")
        void shouldReturnTrueWhenPackageExists() {
            doNothing().when(repository).deleteById(1L);

            boolean result = service.deletePackage(1L);

            assertThat(result).isTrue();
            verify(repository, times(1)).deleteById(1L);
        }

        @Test
        @DisplayName("Debe retornar false cuando ocurre una excepcion al eliminar")
        void shouldReturnFalseWhenExceptionOccurs() {
            doThrow(new RuntimeException("Error al eliminar"))
                    .when(repository).deleteById(99L);

            boolean result = service.deletePackage(99L);

            assertThat(result).isFalse();
        }
    }

    // =====================================================
    //  updatePackage
    // =====================================================
    @Nested
    @DisplayName("updatePackage()")
    class UpdatePackage {

        @Test
        @DisplayName("Debe actualizar y retornar el paquete modificado")
        void shouldUpdateAndReturnOutDTO() {
            when(repository.findById(1L))
                    .thenReturn(Optional.of(packageEntity));
            when(repository.save(any(TuristicPackageEntity.class)))
                    .thenReturn(packageEntity);

            TuristicPackageOutDTO result = service.updatePackage(1L, packageInDTO);

            assertThat(result).isNotNull();
            verify(repository, times(1)).findById(1L);
            verify(repository, times(1)).save(any(TuristicPackageEntity.class));
        }

        @Test
        @DisplayName("Debe lanzar RuntimeException si el paquete no existe")
        void shouldThrowWhenPackageNotFound() {
            when(repository.findById(99L))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.updatePackage(99L, packageInDTO))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Paquetito no encontrado");
        }
    }

    // =====================================================
    //  getAllTuristicPackages
    // =====================================================
    @Nested
    @DisplayName("getAllTuristicPackages()")
    class GetAllPackages {

        @Test
        @DisplayName("Debe retornar todos los paquetes cuando existen registros")
        void shouldReturnAllPackages() {
            TuristicPackageEntity second = new TuristicPackageEntity();
            second.setId(2L);
            second.setDestiny("Medellin");

            when(repository.findAll())
                    .thenReturn(List.of(packageEntity, second));

            List<TuristicPackageEntity> result = service.getAllTuristicPackages();

            assertThat(result)
                    .isNotNull()
                    .hasSize(2)
                    .extracting(TuristicPackageEntity::getDestiny)
                    .containsExactlyInAnyOrder("Cartagena", "Medellin");

            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("Debe retornar lista vacia si no hay paquetes")
        void shouldReturnEmptyListWhenNoPackages() {
            when(repository.findAll()).thenReturn(List.of());

            List<TuristicPackageEntity> result = service.getAllTuristicPackages();

            assertThat(result).isNotNull().isEmpty();
        }
    }

    // =====================================================
    //  findByDestiny
    // =====================================================
    @Nested
    @DisplayName("findByDestiny()")
    class FindByDestiny {

        @Test
        @DisplayName("Debe retornar paquetes que coincidan con el destino")
        void shouldReturnPackagesByDestiny() {
            when(repository.findByDestiny("Cartagena"))
                    .thenReturn(List.of(packageEntity));

            List<TuristicPackageEntity> result = service.findByDestiny("Cartagena");

            assertThat(result)
                    .hasSize(1)
                    .first()
                    .extracting(TuristicPackageEntity::getDestiny)
                    .isEqualTo("Cartagena");
        }

        @Test
        @DisplayName("Debe retornar lista vacia si no hay coincidencias")
        void shouldReturnEmptyWhenNoMatch() {
            when(repository.findByDestiny("Lima")).thenReturn(List.of());

            assertThat(service.findByDestiny("Lima")).isEmpty();
        }
    }

    // =====================================================
    //  findByPriceLessThan
    // =====================================================
    @Nested
    @DisplayName("findByPriceLessThan()")
    class FindByPrice {

        @Test
        @DisplayName("Debe retornar paquetes con precio menor al dado")
        void shouldReturnPackagesBelowPrice() {
            when(repository.findByPriceLessThan(2000.0f))
                    .thenReturn(List.of(packageEntity));

            List<TuristicPackageEntity> result = service.findByPriceLessThan(2000.0f);

            assertThat(result)
                    .isNotEmpty()
                    .allSatisfy(p ->
                            assertThat(p.getPrice()).isLessThan(2000.0f)
                    );
        }

        @Test
        @DisplayName("Debe retornar lista vacia si ningun paquete cumple el precio")
        void shouldReturnEmptyWhenNoBelowPrice() {
            when(repository.findByPriceLessThan(100.0f)).thenReturn(List.of());

            assertThat(service.findByPriceLessThan(100.0f)).isEmpty();
        }
    }

    // =====================================================
    //  findByCapacity
    // =====================================================
    @Nested
    @DisplayName("findByCapacity()")
    class FindByCapacity {

        @Test
        @DisplayName("Debe retornar paquetes con capacidad mayor a la indicada")
        void shouldReturnPackagesAboveCapacity() {
            when(repository.findByCapacityGreaterThan(10))
                    .thenReturn(List.of(packageEntity));

            List<TuristicPackageEntity> result = service.findByCapacity(10);

            assertThat(result)
                    .isNotEmpty()
                    .allSatisfy(p ->
                            assertThat(p.getCapacity()).isGreaterThan(10)
                    );
        }

        @Test
        @DisplayName("Debe retornar lista vacia si no hay capacidad mayor")
        void shouldReturnEmptyWhenNoCapacity() {
            when(repository.findByCapacityGreaterThan(100)).thenReturn(List.of());

            assertThat(service.findByCapacity(100)).isEmpty();
        }
    }

    // =====================================================
    //  findByStatus
    // =====================================================
    @Nested
    @DisplayName("findByStatus()")
    class FindByStatus {

        @Test
        @DisplayName("Debe retornar paquetes con el estado indicado")
        void shouldReturnPackagesByStatus() {
            when(repository.findByStatus(true))
                    .thenReturn(List.of(packageEntity));

            List<TuristicPackageEntity> result = service.findByStatus(true);

            assertThat(result)
                    .hasSize(1)
                    .first()
                    .extracting(TuristicPackageEntity::getStatus)
                    .isEqualTo(true);
        }

        @Test
        @DisplayName("Debe retornar lista vacia para estado inexistente")
        void shouldReturnEmptyForUnknownStatus() {
            when(repository.findByStatus(false)).thenReturn(List.of());

            assertThat(service.findByStatus(false)).isEmpty();
        }
    }

    // =====================================================
    //  findByDurationDays
    // =====================================================
    @Nested
    @DisplayName("findByDurationDays()")
    class FindByDuration {

        @Test
        @DisplayName("Debe retornar paquetes con duracion mayor a la indicada")
        void shouldReturnPackagesAboveDuration() {
            when(repository.findByDurationDaysGreaterThan(5))
                    .thenReturn(List.of(packageEntity));

            List<TuristicPackageEntity> result = service.findByDurationDays(5);

            assertThat(result)
                    .isNotEmpty()
                    .allSatisfy(p ->
                            assertThat(p.getDurationDays()).isGreaterThan(5)
                    );
        }

        @Test
        @DisplayName("Debe retornar lista vacia si ninguna duracion supera el valor")
        void shouldReturnEmptyWhenNoDurationMatch() {
            when(repository.findByDurationDaysGreaterThan(30)).thenReturn(List.of());

            assertThat(service.findByDurationDays(30)).isEmpty();
        }
    }
}

