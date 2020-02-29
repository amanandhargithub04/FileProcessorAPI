package com.processor.fileprocessor.integrationTests;

import com.processor.fileprocessor.testHelper.FileProcessorTestHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.processor.fileprocessor.testHelper.ProcessorTestConstants.REQUEST_PARAM_NAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileProcessorIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static FileProcessorTestHelper helper;

    @BeforeClass
    public static void setUp(){
        helper = new FileProcessorTestHelper();
    }

    @Test
    public void testMultipleFileProcessBackToBackWithMockMVC() throws Exception {
        MockMultipartFile mockMultipartFileCSV1 = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "test_csv2.csv", "text/csv", "D:/test_csv2.csv");
        mockMvc.perform(multipart("/processFile/CSVFile").file(mockMultipartFileCSV1))
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(helper.getExpectedJsonContentString_NotBalanced()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MockMultipartFile mockMultipartFileXML = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "testXml2.xml", "text/xml", "D:/testXml2.xml");

        mockMvc.perform(multipart("/processFile/XMLFile").file(mockMultipartFileXML))
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(helper.getExpectedJsonContentString_NotBalanced()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MockMultipartFile mockMultipartFileCSV2 = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "test_csv3.csv", "text/csv", "D:/test_csv3.csv");
        mockMvc.perform(multipart("/processFile/CSVFile").file(mockMultipartFileCSV2))
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(helper.getExpectedJsonContentString_Balanced()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testXMLFileProcessWithMockMVC() throws Exception {
        MockMultipartFile mockMultipartFile = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "testXml2.xml", "text/xml", "D:/testXml2.xml");

        mockMvc.perform(multipart("/processFile/XMLFile").file(mockMultipartFile))
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(helper.getExpectedJsonContentString_NotBalanced()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}
