package com.petcare.pet_care.archTest;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class MyArchitectureTest {

    private static final String BASE_PACKAGE = "com.petcare.pet_care";
    private static JavaClasses importedClasses;

    private static final ArchCondition<JavaClass> IMPLEMENT_USECASE_CONTRACT =
            new ArchCondition<>("implement at least one contract from ..application.usecases..") {
                @Override
                public void check(JavaClass javaClass, ConditionEvents events) {
                    boolean implementsUseCase = javaClass.getAllRawInterfaces().stream()
                            .anyMatch(contract -> contract.getPackageName().contains(".application.usecases"));

                    events.add(new SimpleConditionEvent(
                            javaClass,
                            implementsUseCase,
                            javaClass.getName() + (implementsUseCase
                                    ? " implements a use case contract"
                                    : " does not implement any use case contract")
                    ));
                }
            };

    private static final ArchCondition<JavaClass> IMPLEMENT_DOMAIN_REPOSITORY_CONTRACT =
            new ArchCondition<>("implement at least one contract from ..domain.. ending with Repository") {
                @Override
                public void check(JavaClass javaClass, ConditionEvents events) {
                    boolean implementsDomainRepository = javaClass.getAllRawInterfaces().stream()
                            .anyMatch(contract ->
                                    contract.getPackageName().contains(".domain.")
                                            && contract.getSimpleName().endsWith("Repository"));

                    events.add(new SimpleConditionEvent(
                            javaClass,
                            implementsDomainRepository,
                            javaClass.getName() + (implementsDomainRepository
                                    ? " implements a domain repository contract"
                                    : " does not implement a domain repository contract")
                    ));
                }
            };

    private static final ArchCondition<JavaClass> EXTEND_SPRING_JPA_REPOSITORY =
            new ArchCondition<>("extend Spring Data JpaRepository") {
                @Override
                public void check(JavaClass javaClass, ConditionEvents events) {
                    boolean extendsJpaRepository = javaClass.getAllRawInterfaces().stream()
                            .anyMatch(parent -> parent.getName().equals(JpaRepository.class.getName()));

                    events.add(new SimpleConditionEvent(
                            javaClass,
                            extendsJpaRepository,
                            javaClass.getName() + (extendsJpaRepository
                                    ? " extends JpaRepository"
                                    : " does not extend JpaRepository")
                    ));
                }
            };

    @BeforeAll
    static void setUp() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(BASE_PACKAGE);
    }

    @Test
    void domain_nao_deve_depender_de_camadas_externas() {
        noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..application..", "..adapters..", "..infra..")
                .check(importedClasses);
    }

    @Test
    void domain_nao_deve_depender_de_frameworks() {
        noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework..",
                        "org.hibernate..",
                        "jakarta.persistence..",
                        "jakarta.validation..")
                .check(importedClasses);
    }

    @Test
    void contratos_de_repositorio_do_dominio_devem_ser_interfaces() {
        classes()
                .that().resideInAPackage("..domain..")
                .and().haveSimpleNameEndingWith("Repository")
                .should().beInterfaces()
                .check(importedClasses);
    }

    @Test
    void contratos_de_usecase_devem_ser_interfaces() {
        classes()
                .that().resideInAPackage("..application.usecases..")
                .should().beInterfaces()
                .check(importedClasses);
    }

    @Test
    void usecases_nao_devem_depender_de_adapters_nem_infra() {
        noClasses()
                .that().resideInAPackage("..application.usecases..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..adapters..", "..infra..")
                .check(importedClasses);
    }

    @Test
    void services_devem_estar_na_camada_application_service() {
        classes()
                .that().haveSimpleNameEndingWith("ServiceImpl")
                .should().resideInAPackage("..application.service..")
                .check(importedClasses);
    }

    @Test
    void services_devem_ser_annotados_com_service() {
        classes()
                .that().resideInAPackage("..application.service..")
                .and().haveSimpleNameEndingWith("ServiceImpl")
                .should().beAnnotatedWith(Service.class)
                .check(importedClasses);
    }

    @Test
    void services_devem_implementar_contrato_de_usecase() {
        classes()
                .that().resideInAPackage("..application.service..")
                .and().haveSimpleNameEndingWith("ServiceImpl")
                .should(IMPLEMENT_USECASE_CONTRACT)
                .check(importedClasses);
    }

    @Test
    void controllers_devem_ser_rest_controller() {
        classes()
                .that().resideInAPackage("..adapters.inbound.controller..")
                .should().beAnnotatedWith(RestController.class)
                .check(importedClasses);
    }

    @Test
    void classes_annotadas_com_rest_controller_devem_estar_no_pacote_de_controller() {
        classes()
                .that().areAnnotatedWith(RestController.class)
                .should().resideInAPackage("..adapters.inbound.controller..")
                .check(importedClasses);
    }

    @Test
    void controller_nao_deve_depender_de_dominio_outbound_nem_service_impl() {
        noClasses()
                .that().resideInAPackage("..adapters.inbound.controller..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..domain..", "..adapters.outbound..", "..application.service..")
                .check(importedClasses);
    }

    @Test
    void inbound_nao_deve_depender_do_outbound() {
        noClasses()
                .that().resideInAPackage("..adapters.inbound..")
                .should().dependOnClassesThat()
                .resideInAPackage("..adapters.outbound..")
                .check(importedClasses);
    }

    @Test
    void outbound_nao_deve_depender_do_inbound() {
        noClasses()
                .that().resideInAPackage("..adapters.outbound..")
                .should().dependOnClassesThat()
                .resideInAPackage("..adapters.inbound..")
                .check(importedClasses);
    }

    @Test
    void repositories_impl_devem_estar_no_pacote_outbound_repositories() {
        classes()
                .that().haveSimpleNameEndingWith("RepositoryImpl")
                .should().resideInAPackage("..adapters.outbound.repositories..")
                .check(importedClasses);
    }

    @Test
    void repositories_impl_devem_ser_annotados_com_repository() {
        classes()
                .that().haveSimpleNameEndingWith("RepositoryImpl")
                .should().beAnnotatedWith(Repository.class)
                .check(importedClasses);
    }

    @Test
    void repositories_impl_devem_implementar_contrato_de_repositorio_do_dominio() {
        classes()
                .that().haveSimpleNameEndingWith("RepositoryImpl")
                .should(IMPLEMENT_DOMAIN_REPOSITORY_CONTRACT)
                .check(importedClasses);
    }

    @Test
    void jpa_repositories_devem_ser_interfaces_e_estender_jpa_repository() {
        classes()
                .that().haveSimpleNameStartingWith("Jpa")
                .and().haveSimpleNameEndingWith("Repository")
                .should().beInterfaces()
                .andShould().resideInAPackage("..adapters.outbound.repositories..")
                .andShould(EXTEND_SPRING_JPA_REPOSITORY)
                .check(importedClasses);
    }

    @Test
    void camada_application_nao_deve_depender_do_outbound() {
        noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat()
                .resideInAPackage("..adapters.outbound..")
                .check(importedClasses);
    }

    @Test
    void camada_infra_nao_deve_depender_de_adapters() {
        noClasses()
                .that().resideInAPackage("..infra..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..adapters.inbound..", "..adapters.outbound..")
                .check(importedClasses);
    }
}
