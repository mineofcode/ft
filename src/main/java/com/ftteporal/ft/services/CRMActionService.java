package com.ftteporal.ft.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ftteporal.ft.dto.WSO2Response;
import com.ftteporal.ft.model.ActResult;
import com.ftteporal.ft.model.ActionDataModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service("CRMAction")
public class CRMActionService implements IAction {

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    VelocityEngine velocityEngine;

    @Override
    public ActResult execute(ActionDataModel input) throws URISyntaxException {


        String d= "<SOAP-ENV:Envelope xmlns:SOAP-ENV= \\\"http://schemas.xmlsoap.org/soap/envelope/\\\"> <SOAP-ENV:Header /> <SOAP-ENV:Body> <api:Save xmlns:api= \\\"http://www.crmnext.com/api/\\\" xmlns:ns3= \\\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\\\" xmlns:ns4= \\\"http://schemas.datacontract.org/2004/07/Acidaes.CRMnext.Model\\\" xmlns:ns5= \\\"http://schemas.microsoft.com/2003/10/Serialization/\\\" xmlns:ns7= \\\"http://schemas.datacontract.org/2004/07/System\\\"> <api:userContext> <api:IsSuccess>true</api:IsSuccess> <api:Message /> <api:ClientTimeOffset /> <api:ExpiresOn>2027-06-23T10:14:09.950117+05:30</api:ExpiresOn> <api:IsUTC>false</api:IsUTC> <api:Token>qjg8clqv8mjs5njd6l2s676fk7prztsugrx5n2dwp94ejew4nadx4xl5ckyxmx3tcwl4ktxcskbsph99mqpzgaueke4mc5vnntl3zxw2wwrwjkcwmjgue7ta3qy8vlf9ej6ncvv265l2pc8ayfm3u6d8u2dvg9plgysdckayv3lwtxhr84lr8r7ucu64xdzzy7hheej7c8kpqj3x2mvkkdt9bbshsatlrdxnlj7hua6bec9c8fe58jrp8kp9ndhcvtne69pn3mzezkk8cmq9zxf5nqslnel5nuxa5cqgetxg4c9qzs9kv466z2dmmnsuvavy53j2es266zaajttlef3duqzejqu7wbzn429ntne2ykrl479yrxq8ypvhrkyspebepu44eqhgq38dgq3xlckv7dzb6cufe7yfkca8yzsszubuqcz9m2rwfnl6uempvh3pu2yjqbxebdca896wwumkfgcb4uyvq7rrp296az9umfk4wkf3za4mxyvga49445y4cuurnfzpn8gtr7cvuusx9zxs9xs7hucuesd7nvytgsemswtzsdtfcbsx9ymvt7g8adhgcjkem2asr5d5qnvpe4vhjbvgm8ealpz</api:Token> </api:userContext> <api:objects> <api:CRMnextObject xmlns:xsi= \\\"http://www.w3.org/2001/XMLSchema-instance\\\" xsi:type= \\\"api:Lead\\\"> <api:EmailOptOut>false</api:EmailOptOut> <api:PhoneOptOut>false</api:PhoneOptOut> <api:PreferredChannel>Email</api:PreferredChannel> <api:SmsOptOut>false</api:SmsOptOut> <api:AccountID>-1</api:AccountID> <api:AccountKey /> <api:AssignToCode /> <api:AssignToName>Admin</api:AssignToName> <api:AssigntoKey /> <api:CampaignKey /> <api:CampaignName /> <api:ChildAccountID>-1</api:ChildAccountID> <api:City /> <api:Comments>convenience Fee = 200</api:Comments> <api:Country>India</api:Country> <api:CreatedBy>1</api:CreatedBy> <api:CreatedByCode /> <api:CreatedOn>2021-12-15T16:28:42</api:CreatedOn> <api:CurrencyID>INR</api:CurrencyID> <api:Custom> <api:FieldID_1035 /> <api:FieldID_1248 /> <api:FieldID_994 /> <api:l_accountNumberPF /> #if($l_AnnualIncome_PAT) <api:l_AnnualIncome_PAT>$l_AnnualIncome_PAT</api:l_AnnualIncome_PAT> #end <api:l_Father_First_Name>NA</api:l_Father_First_Name> <api:l_Father_Last_Name>NA</api:l_Father_Last_Name> <api:l_Father_Prefix>1</api:l_Father_Prefix> <api:l_Incorporation_Date /> <api:l_Permanentiscurrent>Yes</api:l_Permanentiscurrent> <api:l_Permanent_Landmark /> <api:l_Per_Res_Address1 /> <api:l_Per_Res_Address2 /> <api:l_Per_Res_Address3 /> <api:l_RPCIBIL_Score /> <api:l_Spouse_First_Name /> <api:l_Spouse_Last_Name /> <api:l_Spouse_Prefix /> <api:l_Type_Permanent_Res /> <api:l_Tier>Metro</api:l_Tier> <api:l_AIP_Amount /> <api:l_Branch_SPOC>1</api:l_Branch_SPOC> #if($l_AIP_Tenure) <api:l_AIP_Tenure>$l_AIP_Tenure</api:l_AIP_Tenure> #end <api:l_RiskStatus /> <api:l_RpPowerRiskStatus /> <api:l_RiskGrade /> <api:l_Applicant_Type>1</api:l_Applicant_Type> #if($l_DOB) <api:l_DOB>$l_DOB</api:l_DOB> #end #if($l_PAN) <api:l_PAN>$l_PAN</api:l_PAN> #end #if($l_Gross_Monthly_Income) <api:l_Gross_Monthly_Income>$l_Gross_Monthly_Income</api:l_Gross_Monthly_Income> #end  <api:l_Gender>F</api:l_Gender> <api:l_MaritalStatus>BVN</api:l_MaritalStatus> <api:FieldID_67>Apartment Number 45 behind Tower B</api:FieldID_67> <api:FieldID_68 /> <api:FieldID_69 /> <api:l_Current_Landmark /> <api:l_Current_Country>India</api:l_Current_Country> <api:l_Res_STD_Code /> <api:l_Yrs_Current_Add>2</api:l_Yrs_Current_Add> <api:l_Type_Current_Res>REN</api:l_Type_Current_Res> <api:l_Monthly_Repayment /> <api:l_Office_Address1>A wing A wing, first floor Supreme business park</api:l_Office_Address1> <api:l_Office_Address2 /> <api:l_Office_Address3 /> <api:l_Office_Landmark /> <api:l_Office_Country>India</api:l_Office_Country> <api:l_Office_Mobile_No /> <api:l_Res_Mobile_No>8826702753</api:l_Res_Mobile_No> <api:l_CIBIL_Score /> <api:l_OrganizationType>1</api:l_OrganizationType> <api:l_Total_Exp_Busines /> <api:l_BusinessType /> <api:l_CompanyName>Employer Name</api:l_CompanyName> <api:l_IndustryType>26</api:l_IndustryType> <api:l_Designation /> <api:l_Net_Monthly_Income>50000</api:l_Net_Monthly_Income> <api:l_Customertype /> <api:l_PreferredIRR>0</api:l_PreferredIRR> <api:l_Purpose_loan>12</api:l_Purpose_loan> <api:l_Qualification>11</api:l_Qualification> <api:l_RequiredLoanAmt>58500</api:l_RequiredLoanAmt> <api:l_Mnths_Current_Add>0</api:l_Mnths_Current_Add> <api:l_Branch_Disposition>Interested</api:l_Branch_Disposition> <api:l_DL_No /> <api:l_Voter_No /> <api:l_Passport_No /> <api:l_GST_Registration>No</api:l_GST_Registration> <api:l_Emp_Type>K</api:l_Emp_Type> <api:l_Total_Work_Exp_Mnths /> <api:l_Total_Work_Exp_Yrs /> <api:l_Company_Type_Salaried /> <api:l_Company_Name_salaried /> <api:l_Lead_Source>Dealer</api:l_Lead_Source> <api:l_Lead_Sub_source>Mass Channels</api:l_Lead_Sub_source> <api:l_SchemeCode>182_BYJU'S</api:l_SchemeCode> <api:l_Office_STD_Code /> <api:l_City_productwise>New Delhi</api:l_City_productwise> <api:l_CopyEmp_Type>K</api:l_CopyEmp_Type> <api:l_CopyApplicant_Type /> <api:l_CopyProduct>DPL</api:l_CopyProduct> <api:l_Lead_Status_Summary>1</api:l_Lead_Status_Summary> <api:l_Type>1</api:l_Type> <api:l_Currentpincode>110057</api:l_Currentpincode> <api:l_PEP>No</api:l_PEP> <api:l_NRI>No</api:l_NRI> <api:l_OfficePincode>400076</api:l_OfficePincode> <api:NRI_Share_Holding>Less Than 15</api:NRI_Share_Holding> <api:FieldID_110>jyotsna.mishra@aaaa.com</api:FieldID_110> <api:FieldID_18777>Low</api:FieldID_18777> <api:l_Report_EmpType /> <api:FieldID_1070>OS OWN SOURCING</api:FieldID_1070> <api:l_Lead_Created_On /> <api:l_Doc_Chk_ApplicationForm1>55</api:l_Doc_Chk_ApplicationForm1> <api:l_DocChk_ApplicationForm_Status1>2</api:l_DocChk_ApplicationForm_Status1> <api:l_ApplicationForm_Doc_Upload1>195738</api:l_ApplicationForm_Doc_Upload1> <api:l_Professional_Status1>1</api:l_Professional_Status1> <api:l_Relationship_Officer>999999</api:l_Relationship_Officer> <api:l_CIBILID /> <api:l_Salary_Credit_Mode /> <api:l_Office_Landline_No /> <api:l_EmployerName /> <api:PAN_Status>VALID</api:PAN_Status> <api:l_TAN /> <api:l_ApplicantCompany /> <api:PAN_First_Name /> <api:PAN_Middle_Name /> <api:l_Program_Type /> <api:l_appscore /> <api:l_Program_Variant /> <api:FieldID_18730>N</api:FieldID_18730> <api:PAN_Last_Name /> <api:Aadhar_Status /> <api:FieldID_18731>01</api:FieldID_18731> <api:FieldID_18732>R</api:FieldID_18732> <api:l_Alternate_Email>jyotsna.mishra@fff.com</api:l_Alternate_Email> <api:l_Annual_Turnover>50000</api:l_Annual_Turnover> <api:FieldID_18733>AAD</api:FieldID_18733> <api:l_Pan_Status>1</api:l_Pan_Status> <api:FieldID_18734>AD</api:FieldID_18734> <api:l_VPA_Required>2</api:l_VPA_Required> <api:l_Form60_Validity_Period /> <api:l_IDDocument_name>Test</api:l_IDDocument_name> <api:l_Aadhaar_Type /> <api:l_ID_Doc>Test</api:l_ID_Doc> <api:l_AadharCardNo /> <api:l_Reference_1_Name>Not Applicable</api:l_Reference_1_Name> <api:l_Date_of_Enrolment /> <api:l_GreenChannelLead>1</api:l_GreenChannelLead> <api:l_Ref_1_Phone>11111111</api:l_Ref_1_Phone> <api:l_EnachAvaliableLead>1</api:l_EnachAvaliableLead> <api:l_Existing_Customer_ID /> <api:l_U_Bank_Name>STATE BANK OF INDIA</api:l_U_Bank_Name> <api:l_U_Bank_Avg_Balance /> <api:l_LOS_PDOC_Agreement_Date /> <api:l_U_Final_Risk_Cat>LOW</api:l_U_Final_Risk_Cat> <api:l_U_Final_Loan_Amount>58500</api:l_U_Final_Loan_Amount> <api:l_U_EMI /> <api:l_Application_Date /> <api:l_LOS_PDOC_Sanction_Limit>58500</api:l_LOS_PDOC_Sanction_Limit> <api:l_LOS_PDOC_NACH_End_Date /> <api:l_U_Tenure>12</api:l_U_Tenure> <api:l_U_Rate>0</api:l_U_Rate> <api:l_LOS_PDOC_Disb_Name_as_per_Rec>SBI</api:l_LOS_PDOC_Disb_Name_as_per_Rec> <api:l_LOS_PDOC_VPA_Address /> <api:l_LOS_PDOC_DISB_Account_No>1234567879</api:l_LOS_PDOC_DISB_Account_No> <api:l_LOS_PDOC_Disbursal_Amt>58500</api:l_LOS_PDOC_Disbursal_Amt> <api:l_LOS_PDOC_NACH_until_Cancelled>No</api:l_LOS_PDOC_NACH_until_Cancelled> <api:l_LOS_PDOC_NACH_Open_Date /> <api:l_LOS_PDOC_Account_Type_Master>SAVINGS</api:l_LOS_PDOC_Account_Type_Master> <api:l_LOS_PDOC_Customer_Acc_Number>1234567879</api:l_LOS_PDOC_Customer_Acc_Number> <api:l_LOS_PDOC_Repayment_Mode>E</api:l_LOS_PDOC_Repayment_Mode> <api:l_LOS_PDOC_LOC_Amount /> <api:l_LOS_PDOC_Final_Tenure>12</api:l_LOS_PDOC_Final_Tenure> <api:l_LOS_PDOC_Final_Rate>0</api:l_LOS_PDOC_Final_Rate> <api:l_LOS_PDOC_Final_Limit>58500</api:l_LOS_PDOC_Final_Limit> <api:l_Application_Form_No>8987654</api:l_Application_Form_No> <api:l_Preferred_Language>7</api:l_Preferred_Language> <api:l_Pref_Commn_Address>3</api:l_Pref_Commn_Address> <api:l_DocChk_Address_Statu>2</api:l_DocChk_Address_Statu> <api:FieldID_1971>2</api:FieldID_1971> <api:l_Address_Remarks /> <api:l_LOS_PDOC_Disb_IFSC>SBIN0000773</api:l_LOS_PDOC_Disb_IFSC> <api:l_LOS_PDOC_Disb_Bank_Name>STATE BANK OF INDIA</api:l_LOS_PDOC_Disb_Bank_Name> <api:l_LOS_PDOC_Repay_IFSC_Value>SBIN0000773</api:l_LOS_PDOC_Repay_IFSC_Value> <api:l_LOS_PDOC_Repay_Bank_Br /> <api:l_LOS_PDOC_Repay_Bank>STATE BANK OF INDIA</api:l_LOS_PDOC_Repay_Bank> <api:l_LOS_PDOC_Repay_City /> <api:l_LOS_PDOC_Repay_MICR /> <api:L_Hunter_Check_Date_Time /> <api:L_Hunter_Error_Message /> <api:L_Initial_Hunter_Check /> </api:Custom> <api:District /> <api:Email>$!{Email}</api:Email> <api:EmployeeCount /> <api:ExternalSLAOn>0001-01-01T00:00:00</api:ExternalSLAOn> <api:Fax /> <api:FirstName>Jyotsna</api:FirstName> <api:IndustryName /> <api:InternalSLA>0</api:InternalSLA> <api:InternalSLAOn>0001-01-01T00:00:00</api:InternalSLAOn> <api:IsAssignmentRule>false</api:IsAssignmentRule> <api:IsAutoResponse>false</api:IsAutoResponse> <api:IsChildLead>false</api:IsChildLead> <api:IsDedupeSearch>false</api:IsDedupeSearch> <api:IsInsideBHR>false</api:IsInsideBHR> <api:LastModifiedBy>0</api:LastModifiedBy> <api:LastModifiedOn>0001-01-01T00:00:00</api:LastModifiedOn> <api:LastName>Mishra</api:LastName> <api:LastPrintedBy>0</api:LastPrintedBy> <api:LastPrintedByName /> <api:LastPrintedOn>0001-01-01T00:00:00</api:LastPrintedOn> <api:LayoutKey>2061</api:LayoutKey> <api:LeadAmount>0</api:LeadAmount> <api:LeadAmountDefault>0</api:LeadAmountDefault> <api:LeadKey>13457903</api:LeadKey> <api:LeadName>Jyotsna Mishra</api:LeadName> <api:LeadOwnerKey>10289</api:LeadOwnerKey> <api:LeadOwnerName /> <api:LeadOwnerTypeID>0</api:LeadOwnerTypeID> <api:LeadParentId>0</api:LeadParentId> <api:LeadParentName /> <api:LeadRating>Warm</api:LeadRating> <api:LeadSource /> <api:LeadSourceKey>0</api:LeadSourceKey> <api:Locality /> <api:MiddleName /> <api:MobilePhone>8826702753</api:MobilePhone> <api:ObjectUniqueId /> <api:OfferID>0</api:OfferID> <api:OfferName /> <api:OfficePhone /> <api:OwnerCode /> <api:Phone /> <api:PreferredChannelKey>1</api:PreferredChannelKey> <api:PrevAssignTo>0</api:PrevAssignTo> <api:PrevOwnerId>0</api:PrevOwnerId> <api:PreviousStageId>0</api:PreviousStageId> <api:PrintStatus>false</api:PrintStatus> <api:ProductCategory>Unsecured</api:ProductCategory> <api:ProductCategoryID>0</api:ProductCategoryID> <api:ProductCode>162</api:ProductCode> <api:ProductKey>116</api:ProductKey> <api:ProductName>Flexi Credit</api:ProductName> <api:QualifiedByKey /> <api:QualifiedOn>0001-01-01T00:00:00</api:QualifiedOn> <api:RatingKey>2</api:RatingKey> <api:SalutationKey>2</api:SalutationKey> <api:SalutationName>Ms.</api:SalutationName> <api:SecEmail /> <api:SecMobile /> <api:StageID>0</api:StageID> <api:StageName>Active</api:StageName> <api:State /> <api:StatusCode /> <api:StatusCodeDisplayText /> <api:StatusCodeInOn>0001-01-01T00:00:00</api:StatusCodeInOn> <api:StatusCodeKey>128</api:StatusCodeKey> <api:StatusCodeName>New Lead</api:StatusCodeName> <api:TeamID>0</api:TeamID> <api:TerritoryCode>2200</api:TerritoryCode> <api:TerritoryKey>2818</api:TerritoryKey> <api:Title /> <api:WebsiteUrl /> <api:ZipCode /> </api:CRMnextObject> </api:objects> <api:returnObjectOnSave>true</api:returnObjectOnSave> </api:Save> </SOAP-ENV:Body> </SOAP-ENV:Envelope>";
        StringResourceRepository repository = (StringResourceRepository) velocityEngine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
        repository.putStringResource("d", d);


        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : input.getData().entrySet()) {
            context.put(entry.getKey(), entry.getValue()); // adding key as parameter in template and value as value
        }

        // get template from repository
        Template template = velocityEngine.getTemplate("d");
        StringWriter writer = new StringWriter();

        // attach paramter data to template
        template.merge(context, writer);



        JsonObject personJsonObject = new JsonObject();
        personJsonObject.addProperty("key","");
        personJsonObject.addProperty("DataRequest",writer.toString());

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "https://uatapi.xxx.com:8243/ificc/crmgreenchannel/1.0.0/crmgreenChannel";
        URI uri = new URI(baseUrl);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer b6b3d9fa-e9b0-3f95-bee8-0616f5356ef7");
        headers.set("Username", "FICCTester");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), headers);



        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        Gson g = new Gson();
        WSO2Response k = g.fromJson(result.getBody(), WSO2Response.class);
        System.out.println(k.getDataResponse());

        String []elements = {"IsSuccess",  "Message", "LeadKey", "StatusCodeName", "StatusCodeKey", "StatusCode"};
        HashMap resultMap = new HashMap();

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in =  new ByteArrayInputStream(k.getDataResponse().getBytes());
        XMLStreamReader streamReader = null;
        try {
             streamReader = inputFactory.createXMLStreamReader(in);
                while (streamReader.hasNext()) {
                    if (streamReader.isStartElement()) {
                        for (String key :
                        elements) {
                            if(streamReader.getLocalName().equals(key)) {
                                resultMap.put(key, streamReader.getElementText());
                            }
                        }
                    }
                streamReader.next();
            }


        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }

        if(resultMap.get("IsSuccess").equals("false")){
            try {
                Exception exp = new Exception("Failed to CRM Operation : " + resultMap.get("Message"));
                throw  exp;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        input.getPersistData().putAll(resultMap);
        return null;

    }




}
