package com.jobseeker.dto;

import com.jobseeker.entity.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Mapper utility to convert Vacancy entities to DTOs
 */
public class VacancyMapper {
    
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    
    static {
        CURRENCY_FORMAT.setMaximumFractionDigits(0);
    }
    
    /**
     * Convert Vacancy entity to VacancyResponseDTO
     */
    public static VacancyResponseDTO toResponseDTO(Vacancy vacancy) {
        if (vacancy == null) {
            return null;
        }
        
        List<CriteriaResponseDTO> criteriaList = vacancy.getCriteria().stream()
                .map(VacancyMapper::toCriteriaResponseDTO)
                .collect(Collectors.toList());
        
        return new VacancyResponseDTO(vacancy.getId(), vacancy.getName(), criteriaList);
    }
    
    /**
     * Convert list of Vacancy entities to list of VacancyResponseDTOs
     */
    public static List<VacancyResponseDTO> toResponseDTOList(List<Vacancy> vacancies) {
        return vacancies.stream()
                .map(VacancyMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Convert Criteria entity to CriteriaResponseDTO
     */
    private static CriteriaResponseDTO toCriteriaResponseDTO(Criteria criteria) {
        String criteriaType = getCriteriaTypeName(criteria.getType());
        String details = formatCriteriaDetails(criteria);
        
        return new CriteriaResponseDTO(criteriaType, details, criteria.getWeight());
    }
    
    /**
     * Get human-readable criteria type name
     */
    private static String getCriteriaTypeName(CriteriaType type) {
        switch (type) {
            case AGE:
                return "Age Criteria";
            case GENDER:
                return "Gender Criteria";
            case SALARY_RANGE:
                return "Salary Range Criteria";
            default:
                return type.toString();
        }
    }
    
    /**
     * Format criteria details based on criteria type
     */
    private static String formatCriteriaDetails(Criteria criteria) {
        if (criteria instanceof AgeCriteria) {
            AgeCriteria ageCriteria = (AgeCriteria) criteria;
            return String.format("Min Age: %d, Max Age: %d", 
                    ageCriteria.getMinimumAge(), ageCriteria.getMaximumAge());
        } else if (criteria instanceof GenderCriteria) {
            GenderCriteria genderCriteria = (GenderCriteria) criteria;
            return String.format("Gender: %s", genderCriteria.getGender().toUpperCase());
        } else if (criteria instanceof SalaryRangeCriteria) {
            SalaryRangeCriteria salaryCriteria = (SalaryRangeCriteria) criteria;
            return String.format("Min Salary: %s, Max Salary: %s",
                    formatCurrency(salaryCriteria.getMinimumSalary()),
                    formatCurrency(salaryCriteria.getMaximumSalary()));
        }
        return "Unknown criteria";
    }
    
    /**
     * Format currency in Indonesian Rupiah format
     */
    private static String formatCurrency(Integer amount) {
        if (amount == null) {
            return "N/A";
        }
        return "Rp. " + NumberFormat.getNumberInstance(new Locale("id", "ID")).format(amount.longValue());
    }
}