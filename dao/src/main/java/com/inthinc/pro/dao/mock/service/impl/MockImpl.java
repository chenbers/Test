package com.inthinc.pro.dao.mock.service.impl;

import com.inthinc.pro.dao.mock.data.MockDataContainer;

public class MockImpl
{
    private MockDataContainer mockDataContainer;

    public MockDataContainer getMockDataContainer()
    {
        if (mockDataContainer == null)
        {
            mockDataContainer = new MockDataContainer();
        }
        return mockDataContainer;
    }

    public void setMockDataContainer(MockDataContainer mockDataContainer)
    {
        this.mockDataContainer = mockDataContainer;
    }
    
    
    

}
