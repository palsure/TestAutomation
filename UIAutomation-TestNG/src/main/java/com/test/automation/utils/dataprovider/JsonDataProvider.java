package com.test.automation.utils.dataprovider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.test.automation.utils.dataprovider.model.TestData;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.test.automation.utils.CSVDataUtil.getAccount;
import static com.test.automation.utils.CommonUtil.readJsonFile;
import static com.test.automation.utils.Constants.ACCOUNT_TYPE;
import static com.test.automation.utils.Constants.JSON_DATA_FILE;
import static com.test.automation.utils.Constants.JSON_DATA_PROVIDER;

@Slf4j
public class JsonDataProvider {

    /**
     * Data Provider gets data from JSON based on Test executed. Test will be executed for no of data sets available.
     *
     * @param testContext (ITestNGMethod) testng ITestNGMethod has various methods to get current test method executed
     * @return data list iterator
     */
    @DataProvider(name = JSON_DATA_PROVIDER, parallel = true)
    public Iterator<Object[]> jsonDataProvider(ITestNGMethod testContext) throws IOException {
        String testName = testContext.getMethodName();
        String jsonFileName = testContext.getRealClass().getSimpleName();
        File jsonFile = new File(String.format(JSON_DATA_FILE, jsonFileName));
        List<TestData> jsonDataList = getJsonDataArray(jsonFile, testName);
        if (jsonDataList.isEmpty()) {
            jsonDataList = getJsonDataArray(jsonFile, testName);
        }
        Collection<Object[]> dataList = new ArrayList<>();
        for (TestData jsonData : jsonDataList) {
            dataList.add(new Object[]{jsonData});
        }
        return dataList.iterator();
    }

    /**
     * Reads JSON file content and returns the value for key as a List of TestData object's. Used for JSON Array data.
     * Test data can be excluded using 'excludeTest' parameter.
     *
     * @param jsonFile JSON file
     * @param key      to get a specific JSONObject from JSON file
     * @throws IOException when file not found or unable to get data
     */
    private List<TestData> getJsonDataArray(File jsonFile, String key) throws IOException {
        Map<String, Object> jsonData = readJsonFile(jsonFile);
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) jsonData.get(key);
        if (Objects.isNull(dataList)) {
            log.info(String.format("Data not exists for key '%s' in file %s.", key, jsonFile));
            return Collections.emptyList();
        }
        List<TestData> testDataList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        dataList.forEach(data -> {
            // integrates accounts.csv
            if (data.containsKey(ACCOUNT_TYPE)) {
                Map<String, String> accountData = getAccount(data.get(ACCOUNT_TYPE).toString());
                if (!accountData.isEmpty()) {
                    data.putAll(accountData);
                }
            }
            testDataList.add(objectMapper.convertValue(data, TestData.class));
        });
        return testDataList;
    }
}
