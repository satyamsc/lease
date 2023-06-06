package com.allene.lease.config;

import com.allene.lease.dao.*;
import com.allene.lease.dto.VehicleBrandAndModelDataDTO;
import com.allene.lease.model.Brand;
import com.allene.lease.model.Model;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInsertConfig {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    CustomerRepository customerRepository;

    /**
     * Load data into the database from vehicle_data.json if no brand and model data
     * exists in the repositories.
     *
     * @return The CommandLineRunner bean.
     */
    @Bean
    public CommandLineRunner dataInsertRunner() {
        return args -> {
            if (brandRepository.findAll().isEmpty() && modelRepository.findAll().isEmpty()) {
                ObjectMapper objectMapper = new ObjectMapper();
                new ClassPathResource("data/vehicle_data.json").getInputStream();
                InputStream inputStream = new ClassPathResource("data/vehicle_data.json").getInputStream();
                List<VehicleBrandAndModelDataDTO> data = objectMapper.readValue(inputStream,
                        new TypeReference<>() {
                        });
                saveVehicleBrandAndModelData(data);
            }
        };
    }

    /** To save the brand and its models
     *
     * @param brandAndModelDataList
     */
    private void saveVehicleBrandAndModelData(List<VehicleBrandAndModelDataDTO> brandAndModelDataList) {
        brandAndModelDataList.forEach(carData -> {
            Brand brand = new Brand();
            brand.setName(carData.getBrandName());
            brandRepository.save(brand);
            List<Model> models = new ArrayList<>();
            carData.getModelNames().forEach(modelName -> {
                Model model = new Model();
                model.setName(modelName);
                model.setBrand(brand);
                models.add(model);
            });
            if (!models.isEmpty()) {
                modelRepository.saveAll(models);
            }
        });
    }
}
