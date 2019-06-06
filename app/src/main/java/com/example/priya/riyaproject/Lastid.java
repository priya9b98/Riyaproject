package com.example.priya.riyaproject;


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "hungrymind-mobilehub-593518188-Last_Ids")

public class Lastid {
    private String _tableId;
    private Double _id;
    private String _timestamp;

    @DynamoDBHashKey(attributeName = "TableId")
    @DynamoDBAttribute(attributeName = "TableId")
    public String getTableId() {
        return _tableId;
    }

    public void setTableId(final String _tableId) {
        this._tableId = _tableId;
    }
    @DynamoDBAttribute(attributeName = "Id")
    public Double getId() {
        return _id;
    }

    public void setId(final Double _id) {
        this._id = _id;
    }
    @DynamoDBAttribute(attributeName = "Timestamp")
    public String getTimestamp() {
        return _timestamp;
    }

    public void setTimestamp(final String _timestamp) {
        this._timestamp = _timestamp;
    }

}
