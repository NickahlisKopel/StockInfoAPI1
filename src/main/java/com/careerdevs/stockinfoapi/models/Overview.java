package com.careerdevs.stockinfoapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;


public class Overview {

    private String Symbol;
    private String AssetType;
    private String Name;
    @Column(length = 512)
    private String Description;
    private String CIK;
    private String Exchange;
    private String Currency;
    private String Country;
    private String Sector;
    private String Industry;
    private String Address;
    private String FiscalYearEnd;
    private String LatestQuarter;
    private String MarketCapitalization;
    private String EBITDA;
    private String PERatio;
    private String PEGRatio;
    private String BookValue;
    private String DividendPerShare;
    private String DividendYield;
    private String EPS;
    private String RevenuePerShareTTM;
    private String ProfitMargin;
    private String OperatingMarginTTM;
    private String ReturnOnAssetsTTM;
    private String ReturnOnEquityTTM;
    private String RevenueTTM;
    private String GrossProfitTTM;
    private String DilutedEPSTTM;
    private String QuarterlyEarningsGrowthYOY;
    private String QuarterlyRevenueGrowthYOY;
    private String AnalystTargetPrice;
    private String TrailingPE;
    private String ForwardPE;
    private String PriceToSalesRatioTTM;
    private String PriceToBookRatio;
    private String EVToRevenue;
    private String EVToEBITDA;
    private String Beta;
    @JsonProperty("52WeekHigh")
    private String FiftyTwoWeekHigh;
    @JsonProperty("52WeekLow")
    private String FiftyTwoWeekLow;
    @JsonProperty("50DayMovingAverage")
    private String FiftyDayMovingAverage;
    @JsonProperty("200DayMovingAverage")
    private String TwoHundredDayMovingAverage;
    private String SharesOutstanding;
    private String DividendDate;
    private String ExDividendDate;











}
