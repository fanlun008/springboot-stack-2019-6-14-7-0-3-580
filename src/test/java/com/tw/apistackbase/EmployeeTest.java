package com.tw.apistackbase;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tw.apistackbase.entity.Employee;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void testGetAll() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee")).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        Map<Integer, JSONObject> map = JSON.parseObject(contentAsString, Map.class);
        List<Employee> collect = map.values().stream().map((jsonObject) -> JSONObject.toJavaObject(jsonObject, Employee.class)).collect(Collectors.toList());
        System.out.println(collect);
        Assertions.assertEquals("John", collect.get(0).getName());
    }

    @Test
    public void testGetOne() throws Exception {
        String contentAsString = mockMvc.perform(
                MockMvcRequestBuilders.get("/employee/1")
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Employee employee = JSON.parseObject(contentAsString, Employee.class);
        Assertions.assertEquals("John", employee.getName());
    }

    @Test
    public void testPut() throws Exception {

        String requestBody = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"John9090\",\n" +
                "    \"age\": 45,\n" +
                "    \"gender\": \"Male\"\n" +
                "}";
        String contentAsString = mockMvc.perform(
                MockMvcRequestBuilders.put("/employee/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andReturn().getResponse().getContentAsString();
        Assertions.assertEquals("update successful: John9090", contentAsString);
    }

    @Test
    public void testDelete() throws Exception {
        String contentAsString = mockMvc.perform(
                MockMvcRequestBuilders.delete("/employee/{id}", 1)
        ).andReturn().getResponse().getContentAsString();
        Assertions.assertEquals("you delete John OK", contentAsString);
    }

}
