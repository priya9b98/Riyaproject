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

@DynamoDBTable(tableName = "hungrymind-mobilehub-593518188-Request_Table")

public class RequestTableDO {
    private Double _requestId;
    private String _accepted;
    private Double _bookId;
    private String _custEmail;
    private Double _custId;
    private String _dateClaimToRet;
    private String _dateOfBorrow;
    private String _imageUrl;

    @DynamoDBHashKey(attributeName = "RequestId")
    @DynamoDBAttribute(attributeName = "RequestId")
    public Double getRequestId() {
        return _requestId;
    }

    public void setRequestId(final Double _requestId) {
        this._requestId = _requestId;
    }
    @DynamoDBAttribute(attributeName = "Accepted")
    public String getAccepted() {
        return _accepted;
    }

    public void setAccepted(final String _accepted) {
        this._accepted = _accepted;
    }
    @DynamoDBAttribute(attributeName = "Book_Id")
    public Double getBookId() {
        return _bookId;
    }

    public void setBookId(final Double _bookId) {
        this._bookId = _bookId;
    }
    @DynamoDBAttribute(attributeName = "Cust_email")
    public String getCustEmail() {
        return _custEmail;
    }

    public void setCustEmail(final String _custEmail) {
        this._custEmail = _custEmail;
    }
    @DynamoDBAttribute(attributeName = "Cust_id")
    public Double getCustId() {
        return _custId;
    }

    public void setCustId(final Double _custId) {
        this._custId = _custId;
    }
    @DynamoDBAttribute(attributeName = "DateClaimToRet")
    public String getDateClaimToRet() {
        return _dateClaimToRet;
    }

    public void setDateClaimToRet(final String _dateClaimToRet) {
        this._dateClaimToRet = _dateClaimToRet;
    }
    @DynamoDBAttribute(attributeName = "DateOfBorrow")
    public String getDateOfBorrow() {
        return _dateOfBorrow;
    }

    public void setDateOfBorrow(final String _dateOfBorrow) {
        this._dateOfBorrow = _dateOfBorrow;
    }
    @DynamoDBAttribute(attributeName = "Image_url")
    public String getImageUrl() {
        return _imageUrl;
    }

    public void setImageUrl(final String _imageUrl) {
        this._imageUrl = _imageUrl;
    }

}
