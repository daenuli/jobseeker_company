package com.jobseeker.config;

import com.jobseeker.entity.*;
import com.jobseeker.repository.CandidateRepository;
import com.jobseeker.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
// @Profile("dev")
public class DataInitializer implements CommandLineRunner {
    
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;
    
    @Autowired
    public DataInitializer(CandidateRepository candidateRepository, VacancyRepository vacancyRepository) {
        this.candidateRepository = candidateRepository;
        this.vacancyRepository = vacancyRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Clear existing data
        candidateRepository.deleteAll();
        vacancyRepository.deleteAll();
        
        // Initialize sample candidates
        initializeCandidates();
        
        // Initialize sample vacancies
        initializeVacancies();
        
        System.out.println("Sample data initialized successfully!");
    }
    
    private void initializeCandidates() {
        Candidate candidate1 = new Candidate("Siti Rahayu", "siti.r@example.com", LocalDate.of(1996, 5, 15), "female", 5500000);
        Candidate candidate2 = new Candidate("Budi Santoso", "budi.s@example.com", LocalDate.of(1989, 11, 20), "male", 8000000);
        Candidate candidate3 = new Candidate("Indah Lestari", "indah.l@example.com", LocalDate.of(2002, 3, 1), "female", 4500000);
        Candidate candidate4 = new Candidate("Ahmad Wijaya", "ahmad.w@example.com", LocalDate.of(1992, 8, 12), "male", 7200000);
        Candidate candidate5 = new Candidate("Maya Sari", "maya.s@example.com", LocalDate.of(1998, 12, 25), "female", 6000000);
        Candidate candidate6 = new Candidate("Rizki Pratama", "rizki.p@example.com", LocalDate.of(1985, 4, 18), "male", 9500000);
        Candidate candidate7 = new Candidate("Dewi Anggraini", "dewi.a@example.com", LocalDate.of(2000, 7, 9), "female", 4800000);
        Candidate candidate8 = new Candidate("Fajar Nugroho", "fajar.n@example.com", LocalDate.of(1994, 1, 30), "male", 6800000);
        Candidate candidate9 = new Candidate("Rina Kusuma", "rina.k@example.com", LocalDate.of(1987, 10, 14), "female", 8500000);
        Candidate candidate10 = new Candidate("Doni Setiawan", "doni.s@example.com", LocalDate.of(1999, 6, 22), "male", 5200000);
        Candidate candidate11 = new Candidate("Lina Maharani", "lina.m@example.com", LocalDate.of(1991, 9, 5), "female", 7500000);
        Candidate candidate12 = new Candidate("Eko Prasetyo", "eko.p@example.com", LocalDate.of(1988, 2, 17), "male", 8800000);
        Candidate candidate13 = new Candidate("Sari Wulandari", "sari.w@example.com", LocalDate.of(2001, 11, 8), "female", 4200000);
        Candidate candidate14 = new Candidate("Hendra Gunawan", "hendra.g@example.com", LocalDate.of(1993, 3, 26), "male", 6500000);
        Candidate candidate15 = new Candidate("Fitri Handayani", "fitri.h@example.com", LocalDate.of(1997, 8, 11), "female", 5800000);
        Candidate candidate16 = new Candidate("Agus Suryanto", "agus.s@example.com", LocalDate.of(1986, 12, 3), "male", 9200000);
        Candidate candidate17 = new Candidate("Novi Ratnasari", "novi.r@example.com", LocalDate.of(2003, 4, 19), "female", 3800000);
        Candidate candidate18 = new Candidate("Bambang Hermawan", "bambang.h@example.com", LocalDate.of(1990, 7, 28), "male", 7800000);
        Candidate candidate19 = new Candidate("Yuni Astuti", "yuni.a@example.com", LocalDate.of(1995, 1, 16), "female", 6200000);
        Candidate candidate20 = new Candidate("Rudi Hartono", "rudi.h@example.com", LocalDate.of(1984, 5, 7), "male", 10500000);
        
        candidateRepository.saveAll(Arrays.asList(
            candidate1, candidate2, candidate3, candidate4, candidate5,
            candidate6, candidate7, candidate8, candidate9, candidate10,
            candidate11, candidate12, candidate13, candidate14, candidate15,
            candidate16, candidate17, candidate18, candidate19, candidate20
        ));
    }
    
    private void initializeVacancies() {
        Vacancy vacancy1 = createVacancy("Junior Software Engineer", 22, 30, 3, "any", 1, 4500000, 6500000, 5);
        Vacancy vacancy2 = createVacancy("Senior Data Scientist", 30, 45, 4, "male", 2, 7500000, 10000000, 6);
        Vacancy vacancy3 = createVacancy("Senior Developer", 22, 35, 1, "any", 1, 5000000, 7000000, 3);
        Vacancy vacancy4 = createVacancy("Frontend Developer", 23, 32, 2, "female", 1, 5000000, 7500000, 4);
        Vacancy vacancy5 = createVacancy("Backend Engineer", 25, 40, 3, "any", 1, 6000000, 9000000, 5);
        Vacancy vacancy6 = createVacancy("DevOps Engineer", 28, 45, 4, "male", 2, 8000000, 12000000, 6);
        Vacancy vacancy7 = createVacancy("UI/UX Designer", 22, 35, 2, "female", 3, 4000000, 6500000, 3);
        Vacancy vacancy8 = createVacancy("Product Manager", 30, 50, 5, "any", 1, 9000000, 15000000, 7);
        Vacancy vacancy9 = createVacancy("Quality Assurance", 23, 35, 2, "any", 1, 4500000, 6500000, 4);
        Vacancy vacancy10 = createVacancy("Mobile Developer", 24, 38, 3, "male", 1, 5500000, 8500000, 5);
        Vacancy vacancy11 = createVacancy("Data Analyst", 22, 32, 2, "female", 2, 4800000, 7200000, 4);
        Vacancy vacancy12 = createVacancy("System Administrator", 26, 42, 4, "male", 3, 6500000, 9500000, 5);
        Vacancy vacancy13 = createVacancy("Business Analyst", 25, 40, 3, "any", 1, 6000000, 9000000, 4);
        Vacancy vacancy14 = createVacancy("Cybersecurity Specialist", 28, 45, 5, "male", 2, 8500000, 13000000, 7);
        Vacancy vacancy15 = createVacancy("Machine Learning Engineer", 26, 40, 4, "any", 1, 7500000, 11500000, 6);
        Vacancy vacancy16 = createVacancy("Technical Writer", 23, 35, 2, "female", 2, 4200000, 6200000, 3);
        Vacancy vacancy17 = createVacancy("Database Administrator", 27, 45, 4, "male", 1, 7000000, 10500000, 5);
        Vacancy vacancy18 = createVacancy("Cloud Architect", 32, 50, 6, "any", 1, 10000000, 16000000, 8);
        Vacancy vacancy19 = createVacancy("Scrum Master", 28, 42, 3, "female", 1, 7500000, 11000000, 5);
        Vacancy vacancy20 = createVacancy("Software Architect", 35, 55, 7, "male", 2, 12000000, 20000000, 9);
        
        vacancyRepository.saveAll(Arrays.asList(
            vacancy1, vacancy2, vacancy3, vacancy4, vacancy5,
            vacancy6, vacancy7, vacancy8, vacancy9, vacancy10,
            vacancy11, vacancy12, vacancy13, vacancy14, vacancy15,
            vacancy16, vacancy17, vacancy18, vacancy19, vacancy20
        ));
    }
    
    private Vacancy createVacancy(String name, int minAge, int maxAge, int ageWeight, 
                                 String gender, int genderWeight, 
                                 int minSalary, int maxSalary, int salaryWeight) {
        Vacancy vacancy = new Vacancy();
        vacancy.setName(name);
        vacancy.setCriteria(Arrays.asList(
            new AgeCriteria(minAge, maxAge, ageWeight),
            new GenderCriteria(gender, genderWeight),
            new SalaryRangeCriteria(minSalary, maxSalary, salaryWeight)
        ));
        return vacancy;
    }
}