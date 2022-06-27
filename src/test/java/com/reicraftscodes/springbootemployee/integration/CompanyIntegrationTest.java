package com.reicraftscodes.springbootemployee.integration;

import com.reicraftscodes.springbootemployee.models.Company;
import com.reicraftscodes.springbootemployee.repository.ICompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {

    @Autowired
    private ICompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    void should_get_all_companies_when_get_all() throws Exception {
        Company company = new Company("OOCL", Collections.emptyList());
        companyRepository.save(company);

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(jsonPath("$[0].employees").isEmpty());
    }

    @Test
    void should_return_created_company_when_create_a_company() throws Exception {
        String companyJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"name\": \"colomce\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 100000000\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"manalch\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 100000000\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //when then
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employees").isNotEmpty())
                .andExpect(jsonPath("$.employees[0].id").isNumber())
                .andExpect(jsonPath("$.employees[0].name").value("colomce"))
                .andExpect(jsonPath("$.employees[0].age").value(10))
                .andExpect(jsonPath("$.employees[0].gender").value("male"))
                .andExpect(jsonPath("$.employees[0].salary").value(100000000))
                .andExpect(jsonPath("$.employees[1].id").isNumber())
                .andExpect(jsonPath("$.employees[1].name").value("manalch"))
                .andExpect(jsonPath("$.employees[1].age").value(10))
                .andExpect(jsonPath("$.employees[1].gender").value("male"))
                .andExpect(jsonPath("$.employees[1].salary").value(100000000));
    }

    @Test
    void should_get_company_with_company_id_1_when_search_by_id_given_company_with_id_1() throws Exception {
        //given
        Company company = new Company("OOCL", Collections.emptyList());
        Company createdCompany = companyRepository.save(company);

        //when then
        mockMvc.perform(get("/companies/{companyId}", createdCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employees").isEmpty());
    }

    @Test
    void should_return_the_error_response_with_message_and_status_when_search_by_id_given_invalid_company_id() throws Exception {
        //given
        Integer companyId = 12345;

        // when then
        mockMvc.perform(get("/companies/{companyId}", companyId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Company with id:12345 not found"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andReturn();
    }

    @Test
    void should_return_the_error_response_with_message_and_status_when_delete_by_id_given_invalid_company_id() throws Exception {
        //given
        Integer companyId = 12345;

        // when then
        mockMvc.perform(delete("/companies/{companyId}", companyId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Company with id:12345 not found"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andReturn();
    }

    @Test
    void should_return_the_error_response_with_message_and_status_when_update_id_given_invalid_company_id() throws Exception {
        //given
        Integer companyId = 12345;

        String companyUpdateJson = "{\"companyName\": \"SME\"}";
        // when then
        mockMvc.perform(put("/companies/{companyId}", companyId)
                        .content(companyUpdateJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Company with id:12345 not found"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andReturn();
    }

    @Test
    void should_return_2_companies_when_getCompaniesByPageAndPageSize_given_3_companies_page_1_pageSize_2() throws Exception {
        //given
        Company company1 = new Company("OOCL", Collections.emptyList());
        Company company2 = new Company("YG", Collections.emptyList());
        Company company3 = new Company("JYP", Collections.emptyList());

        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);

        // when then
        mockMvc.perform(get("/companies?page=1&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(jsonPath("$[0].employees").isEmpty())
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].companyName").value("YG"))
                .andExpect(jsonPath("$[1].employees").isEmpty());
    }

    @Test
    void should_get_updated_company_when_update_given_company_id() throws Exception {
        //given
        Company company = new Company("OOCL", Collections.emptyList());
        Company createdCompany = companyRepository.save(company);

        String companyUpdateJson = "{\"companyName\": \"SME\"}";

        //when then
        mockMvc.perform(put("/companies/{companyId}", createdCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyUpdateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("SME"))
                .andExpect(jsonPath("$.employees").isEmpty());
    }

    @Test
    void should_return_the_error_response_with_message_and_status_when_getEmployeesByCompanyId_given_invalid_company_id() throws Exception {
        //given
        Integer companyId = 12345;

        // when then
        mockMvc.perform(get("/companies/{companyId}/employees", companyId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Company with id:12345 not found"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andReturn();
    }

    @Test
    void should_return_invalid_company_exception_when_creating_a_invalid_company() throws Exception {
        //given
        String companyRequestJson = "{}";

        // when then
        mockMvc.perform(post("/companies")
                        .content(companyRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Company given has null fields!"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andReturn();
    }

    @Test
    void should_return_the_error_response_with_messasge_and_status_when_update_id_given_invalid_company_id() throws Exception {
        //given
        Company company = new Company("OOCL", Collections.emptyList());
        Company createdCompany = companyRepository.save(company);

        String companyRequestJson = "{}";

        // when then
        mockMvc.perform(put("/companies/{companyId}", createdCompany.getId())
                        .content(companyRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Company given has null fields!"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andReturn();
    }
}