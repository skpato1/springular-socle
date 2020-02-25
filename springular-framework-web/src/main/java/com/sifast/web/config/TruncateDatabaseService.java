package com.sifast.web.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Metamodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TruncateDatabaseService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void truncate() {
        Metamodel metamodel = entityManager.getMetamodel();
        List<String> tableNames = metamodel.getEntities().stream().map(entityType -> transformTableName(entityType.getName())).collect(Collectors.toList());
        List<String> joinTableNames = new ArrayList<>();
        joinTableNames.add("role_authority");
        joinTableNames.add("user_role");
        joinTableNames.add("user_site");
        joinTableNames.add("lot_mere_file");
        joinTableNames.add("lot_original_purchase_sheets");
        joinTableNames.add("lot_mere_lot");
        joinTableNames.add("lot_reception_original_files");
        joinTableNames.add("lot_conditioning_file");
        tableNames.remove("external_laboratory");
        tableNames.remove("internal_laboratory");
        tableNames.addAll(joinTableNames);
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        tableNames.forEach(tableName -> {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            if (!joinTableNames.contains(tableName)) {
                entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1").executeUpdate();
            }
        });

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private String transformTableName(String tableName) {
        return tableName.replaceAll("(.)([A-Z])", "$1_$2").toLowerCase();
    }

}
