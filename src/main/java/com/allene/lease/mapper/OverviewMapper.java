package com.allene.lease.mapper;

import com.allene.lease.dto.ContractDTO;
import com.allene.lease.dto.ContractOverviewDTO;
import com.allene.lease.dto.CustomerDTO;
import com.allene.lease.dto.VehicleDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The OverviewMapper class is responsible for mapping ContractDTO objects to ContractOverviewDTO objects.
 * It provides methods to perform this mapping, as well as helper methods to fetch specific data from related entities.
 */
@Component
public class OverviewMapper {
    /**
     * The base URL for the vehicle contracts API
     */
    public static final String API_VEHICLE_CONTRACTS = "/api/vehicle/contracts/";

    /**
     * Maps a list of ContractDTO objects to a list of ContractOverviewDTO objects.
     *
     * @param contractDTO A list of ContractDTO objects to be mapped.
     * @return A list of ContractOverviewDTO objects containing the mapped contract overview data.
     */
    public List<ContractOverviewDTO> mapToOverviewDTOs(List<ContractDTO> contractDTO) {
        return contractDTO.stream().map(this::mapToOverviewDTO).collect(Collectors.toList());
    }

    /**
     * Maps a single ContractDTO object to a ContractOverviewDTO object.
     *
     * @param contract A ContractDTO object to be mapped.
     * @return A ContractOverviewDTO object containing the mapped contract overview data.
     */
    private ContractOverviewDTO mapToOverviewDTO(ContractDTO contract) {
        ContractOverviewDTO contractOverview = new ContractOverviewDTO();
        contractOverview.setContractNumber(contract.getContractNumber());
        contractOverview.setMonthlyRate(contract.getMonthlyRate());
        contractOverview.setCustomer(fetchCustomerSummary(contract.getCustomer()));
        contractOverview.setVehicle(fetchVehicleSummary(contract.getVehicle()));
        contractOverview.setVin(fetchVin(contract.getVehicle()));
        contractOverview.setVehiclePrice(contract.getVehicle().getPrice());
        contractOverview.setLinkToDetails(API_VEHICLE_CONTRACTS + contract.getContractNumber());
        return contractOverview;
    }

    /**
     * Fetches the VIN (Vehicle Identification Number) from a VehicleDTO object.
     *
     * @param vehicle A VehicleDTO object from which to fetch the VIN.
     * @return The VIN of the vehicle as a String, or "-" if the VIN is null.
     */
    private String fetchVin(VehicleDTO vehicle) {
        return Objects.nonNull(vehicle.getVin()) ? vehicle.getVin() : "-";
    }

    /**
     * Fetches the summary of a customer by concatenating the first name and last name from a CustomerDTO object.
     *
     * @param customer A CustomerDTO object from which to fetch the customer summary.
     * @return The summary of the customer as a String, in the format "firstName lastName".
     */
    private String fetchCustomerSummary(CustomerDTO customer) {
        return customer.getFirstName() + " " + customer.getLastName();
    }

    /**
     * Fetches a summary of a vehicle by combining the brand name, model name, and model year from a VehicleDTO object.
     *
     * @param vehicle A VehicleDTO object from which to fetch the vehicle summary.
     * @return The summary of the vehicle as a String, in the format "brandName modelName (modelYear)".
     */
    private String fetchVehicleSummary(VehicleDTO vehicle) {
        String brand = vehicle.getBrand().getName();
        String model = vehicle.getModel().getName();
        return brand + " " + model + " (" + vehicle.getModelYear() + ")";
    }
}
