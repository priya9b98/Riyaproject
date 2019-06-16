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

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "hungrymind-mobilehub-593518188-Book_Borrow")

public class Borrow {
    private Double _borrowId;
    private String _actualRetDate;
    private Double _bookID;
    private Double _custID;
    private String _dateClaimToRet;
    private String _dateOfBorrow;
    private Double _rating;
    private String _supplierID;

    @DynamoDBHashKey(attributeName = "BorrowId")
    @DynamoDBAttribute(attributeName = "BorrowId")
    public Double getBorrowId() {
        return _borrowId;
    }

    public void setBorrowId(final Double _borrowId) {
        this._borrowId = _borrowId;
    }
    @DynamoDBAttribute(attributeName = "ActualRetDate")
    public String getActualRetDate() {
        return _actualRetDate;
    }

    public void setActualRetDate(final String _actualRetDate) {
        this._actualRetDate = _actualRetDate;
    }
    @DynamoDBAttribute(attributeName = "BookID")
    public Double getBookID() {
        return _bookID;
    }

    public void setBookID(final Double _bookID) {
        this._bookID = _bookID;
    }
    @DynamoDBAttribute(attributeName = "CustID")
    public Double getCustID() {
        return _custID;
    }

    public void setCustID(final Double _custID) {
        this._custID = _custID;
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
    @DynamoDBAttribute(attributeName = "Rating")
    public Double getRating() {
        return _rating;
    }

    public void setRating(final Double _rating) {
        this._rating = _rating;
    }
    @DynamoDBAttribute(attributeName = "SupplierID")
    public String getSupplierID() {
        return _supplierID;
    }

    public void setSupplierID(final String _supplierID) {
        this._supplierID = _supplierID;
    }

}
