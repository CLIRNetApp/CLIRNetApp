package app.clirnet.com.clirnetapp.models;

/**
 * Created by Ashish on 8/6/2016.
 */
//model class
public class RegistrationModel {


    private String uid;
    private String referedBy;
    private String referedTo;
    private String modiedCounter;

    private String speciality;
    private String associateType;

    private String email;
    private String action;
    private String flag;
    private String phadded_on;
    private String id;
    private String pat_id;
    private String firstName;
    private String actualFollowupDate;
    private String added_Time;

    private String lastName;
    private String middleName;
    private String dob;
    private String mobileNumber;
    private String phone_type;

    private String process;
    private String start_time;
    private String end_time;


    private String gender;
    private String age;
    private String language;
    private String photo;
    private String followUpDate;
    private String followUpdays;
    private String followUpWeek;
    private String followUpMonth;
    private String patient_createdDate;
    private String pres_img;
    private String clinicalNotes;
    private String ailments;
    private String key_visit_id;
    private String added_on;
    private String added_by;
    private String modified_by;
    private String modified_on;
    private String is_disabled;
    private String disabled_by;
    private String disabled_on;
    private String is_deleted;

    private String deleted_by;
    private String deleted_on;
    private String status;
    private String special_instruction;
    private String pin_code;
    private String district;
    private String consent;
    private String doctor_id;
    private String doc_mem_id;
    private String patient_info_typ;
    private String patient_address;
    private String patient_city_town;

    private String address;
    private String cityortown;
    private String state;
    private String weight;
    private String pulse;
    private String bp;
    private String lowBp;
    private String temprature;
    private String symptoms;
    private String dignosis;
    private String tests;
    private String drugs;
    private String bmi;
    private String alternatePhoneType;
    private String alternatePhoneNumber;

    private String height;
    private String alternate_isd_code;
    private String isd_code;

    private String banner_image_name;
    private String banner_id;

    private String title;
    private String name;

    private String contactForPatient;
    private String altContactPatientIsdCodeType;
    private String selectedContactPhoneType;
    private String sugar;
    private String sugarFasting;
    private String ecg;
    private String hba1c;
    private String followUpStatus;

    private String acer;
    private String pft;
    private String seremUrea;
    private String lipidProfileTc;
    private String lipidProfileTg;
    private String lipidProfileLdl;
    private String lipidProfileVhdl;
    private String lipidProfileHdl;
    private String alocholConsumption;
    private String packPerWeek;
    private String stressLevel;
    private String smokerType;
    private String stickCount;

    private String lifeSyle;
    private String lactoseTolerance;
    private String foodPreference;

    private String excercise;
    private String foodHabit;
    private String chewingTobaco;
    private String bingeEating;
    private String allergies;
    private String sexuallyActive;
    private String sticksGapSelected;
    private String pegsGapSelected;
    private String otherTobacoConsumption;
    private String drugConsumption;
    private String otherDrugConsumption;
    private String sleep;
    private String lastSmokeYear;
    private String lastDrinkYear;
    private String pallor;
    private String pallorDescription;
    private String cyanosisDescription;
    private String cyanosis;
    private String tremors;
    private String tremorsDescription;
    private String icterus;
    private String icterusDescription;
    private String clubbing;
    private String clubbingDescription;
    private String oedema;
    private String oedemaDescription;
    private String calfTenderness;
    private String calfTendernessDescription;
    private String lymphadenopathy;
    private String lymphadenopathyDescription;

    private String respirataion;
    private String spo2;
    private String familyHistory;
    private String hospitalizationSurgery;
    private String obesity;

    private String msId;
    private String msDateTime;
    private String msTopic;
    private String msBrief;
    private String msDoctorName;
    private String msSpeciality;
    private String msCatagoryName;
    private String msUrl;
    private String sugarRbs;
    private String lipidProfileTch;
    private String hdlLdlRatio;
    private String hb;
    private String plateletCount;
    private String esr;
    private String dcl;
    private String dcn;
    private String dce;
    private String dcm;
    private String dcb;

    private String indirectBilirubin ;
    private String totalBilirubin;
    private String directBilirubin;
    private String sgpt;
    private String sgot;
    private String ggt;

    private String total_protein;
    private String albumin;
    private String globulin;
    private String ag_ratio;
    private String urinePusCell;
    private String urineRbc;
    private String urineCast;
    private String urineProtein;
    private String urineCrystal;
    private String microalbuminuria;
    private String serumCreatinine;
    private String acr;
    private String tsh;
    private String t3;

    public String getSugarRbs() {
        return sugarRbs;
    }

    public String getLipidProfileTch() {
        return lipidProfileTch;
    }

    public String getHdlLdlRatio() {
        return hdlLdlRatio;
    }

    public String getHb() {
        return hb;
    }

    public String getPlateletCount() {
        return plateletCount;
    }

    public String getEsr() {
        return esr;
    }

    public String getDcl() {
        return dcl;
    }

    public String getDcn() {
        return dcn;
    }

    public String getDce() {
        return dce;
    }

    public String getDcm() {
        return dcm;
    }

    public String getDcb() {
        return dcb;
    }

    public String getIndirectBilirubin() {
        return indirectBilirubin;
    }

    public String getTotalBilirubin() {
        return totalBilirubin;
    }

    public String getDirectBilirubin() {
        return directBilirubin;
    }

    public String getSgpt() {
        return sgpt;
    }

    public String getSgot() {
        return sgot;
    }

    public String getGgt() {
        return ggt;
    }

    public String getTotal_protein() {
        return total_protein;
    }

    public String getAlbumin() {
        return albumin;
    }

    public String getGlobulin() {
        return globulin;
    }

    public String getAg_ratio() {
        return ag_ratio;
    }

    public String getUrinePusCell() {
        return urinePusCell;
    }

    public String getUrineRbc() {
        return urineRbc;
    }

    public String getUrineCast() {
        return urineCast;
    }

    public String getUrineProtein() {
        return urineProtein;
    }

    public String getUrineCrystal() {
        return urineCrystal;
    }

    public String getMicroalbuminuria() {
        return microalbuminuria;
    }

    public String getSerumCreatinine() {
        return serumCreatinine;
    }

    public String getAcr() {
        return acr;
    }

    public String getTsh() {
        return tsh;
    }

    public String getT3() {
        return t3;
    }

    public String getT4() {
        return t4;
    }

    private String t4;



    public String getMsId() {
        return msId;
    }

    public String getMsDateTime() {
        return msDateTime;
    }

    public String getMsTopic() {
        return msTopic;
    }

    public String getMsBrief() {
        return msBrief;
    }

    public String getMsDoctorName() {
        return msDoctorName;
    }

    public String getMsSpeciality() {
        return msSpeciality;
    }

    public String getMsCatagoryName() {
        return msCatagoryName;
    }

    public String getMsUrl() {
        return msUrl;
    }

    public String getFollowUpStatus() {
        return followUpStatus;
    }


    public String getLipidProfileHdl() {
        return lipidProfileHdl;
    }

    public String getLipidProfileVhdl() {
        return lipidProfileVhdl;
    }

    public String getLipidProfileLdl() {
        return lipidProfileLdl;
    }

    public String getLipidProfileTg() {
        return lipidProfileTg;
    }

    public String getLipidProfileTc() {
        return lipidProfileTc;
    }

    public String getSeremUrea() {
        return seremUrea;
    }

    public String getPft() {
        return pft;
    }

    public String getAcer() {
        return acer;
    }

    public String getHba1c() {
        return hba1c;
    }

    public String getEcg() {
        return ecg;
    }


    public String getObesity() {
        return obesity;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public String getHospitalizationSurgery() {
        return hospitalizationSurgery;
    }


    public String getRespirataion() {
        return respirataion;
    }

    public String getSpo2() {
        return spo2;
    }


    public String getPallor() {
        return pallor;
    }

    public String getPallorDescription() {
        return pallorDescription;
    }

    public String getCyanosisDescription() {
        return cyanosisDescription;
    }

    public String getCyanosis() {
        return cyanosis;
    }

    public String getTremors() {
        return tremors;
    }

    public String getTremorsDescription() {
        return tremorsDescription;
    }

    public String getIcterus() {
        return icterus;
    }

    public String getIcterusDescription() {
        return icterusDescription;
    }

    public String getClubbing() {
        return clubbing;
    }

    public String getClubbingDescription() {
        return clubbingDescription;
    }

    public String getOedema() {
        return oedema;
    }

    public String getOedemaDescription() {
        return oedemaDescription;
    }

    public String getCalfTenderness() {
        return calfTenderness;
    }

    public String getCalfTendernessDescription() {
        return calfTendernessDescription;
    }

    public String getLymphadenopathy() {
        return lymphadenopathy;
    }

    public String getLymphadenopathyDescription() {
        return lymphadenopathyDescription;
    }

    public String getLastSmokeYear() {
        return lastSmokeYear;
    }

    public String getLastDrinkYear() {
        return lastDrinkYear;
    }

    public String getSleep() {
        return sleep;
    }

    public String getOtherTobacoConsumption() {
        return otherTobacoConsumption;
    }

    public String getDrugConsumption() {
        return drugConsumption;
    }

    public String getOtherDrugConsumption() {
        return otherDrugConsumption;
    }

    public String getSticksGapSelected() {
        return sticksGapSelected;
    }

    public String getPegsGapSelected() {
        return pegsGapSelected;
    }

    public String getSexuallyActive() {
        return sexuallyActive;
    }

    public String getAlocholConsumption() {
        return alocholConsumption;
    }

    public String getPackPerWeek() {
        return packPerWeek;
    }

    public String getStressLevel() {
        return stressLevel;
    }

    public String getSmokerType() {
        return smokerType;
    }

    public String getStickCount() {
        return stickCount;
    }

    public String getLifeSyle() {
        return lifeSyle;
    }

    public String getLactoseTolerance() {
        return lactoseTolerance;
    }

    public String getFoodPreference() {
        return foodPreference;
    }

    public String getExcercise() {
        return excercise;
    }

    public String getFoodHabit() {
        return foodHabit;
    }

    public String getChewingTobaco() {
        return chewingTobaco;
    }

    public String getBingeEating() {
        return bingeEating;
    }

    public String getAllergies() {
        return allergies;
    }


    public String getModiedCounter() {
        return modiedCounter;
    }


    public String getContactForPatient() {
        return contactForPatient;
    }

    public String getAltContactPatientIsdCodeType() {
        return altContactPatientIsdCodeType;
    }

    public String getSelectedContactPhoneType() {
        return selectedContactPhoneType;
    }


    public String getDrugs() {
        return drugs;
    }

    public String getTests() {
        return tests;
    }

    public String getDignosis() {
        return dignosis;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getSugar() {
        return sugar;
    }

    public String getTemprature() {
        return temprature;
    }


    public String getlowBp() {
        return lowBp;
    }

    public String getBp() {
        return bp;
    }

    public String getPulse() {
        return pulse;
    }

    public String getWeight() {
        return weight;
    }

    public String getState() {
        return state;
    }

    public String getCityortown() {
        return cityortown;
    }

    public String getAddress() {
        return address;
    }

    public String getPatient_city_town() {
        return patient_city_town;
    }

    public String getPatient_info_typ() {
        return patient_info_typ;
    }

    public String getPatient_address() {
        return patient_address;
    }

    public String getPin_code() {
        return pin_code;
    }

    public String getDistrict() {
        return district;
    }

    public String getConsent() {
        return consent;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public String getDoc_mem_id() {
        return doc_mem_id;
    }

    public String getPhone_type() {
        return phone_type;
    }


    public String getStatus() {
        return status;
    }

    public String getAssociateType() {
        return associateType;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getName() {
        return name;
    }


    public String getReferedBy() {
        return referedBy;
    }

    public String getReferedTo() {
        return referedTo;
    }


    public String getSugarFasting() {
        return sugarFasting;
    }

    public String getHeight() {
        return height;
    }

    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public String getAlternatePhoneType() {
        return alternatePhoneType;
    }

    public String getBmi() {
        return bmi;
    }

    public String getAction() {
        return action;
    }

    public String getActualFollowupDate() {
        return actualFollowupDate;
    }


    public String getEmail() {
        return email;
    }

    public String getProcess() {
        return process;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }


    public String getPat_id() {
        return pat_id;
    }


    public String getKey_visit_id() {
        return key_visit_id;
    }

    public String getVisit_date() {
        return visit_date;
    }


    private String visit_date;


    public String getAdded_on() {
        return added_on;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public String getBanner_image_name() {
        return banner_image_name;
    }
    //to get data from db 123


    public String getClinicalNotes() {
        return clinicalNotes;
    }


    public String getAilments() {

        return ailments;
    }

    public String getUid() {
        return uid;
    }

    public void setAilments(String ailments) {
        this.ailments = ailments;
    }


    public String getPres_img() {
        return pres_img;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMiddleName() {
        return middleName;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPhoto() {
        return photo;
    }


    public String getFollowUpdays() {
        return followUpdays;
    }


    public String getFollowUpDate() {
        return followUpDate;
    }


    public String getFollowUpWeek() {
        return followUpWeek;
    }


    public String getFollowUpMonth() {
        return followUpMonth;
    }

    public String getAlternate_isd_code() {
        return alternate_isd_code;
    }

    public String getIsd_code() {
        return isd_code;
    }

    public RegistrationModel(String id) {

        this.pat_id = id;
    }

    public RegistrationModel() {

    }

    public String getTitle() {
        return title;
    }

    public RegistrationModel(String id, String visitId) {
        this.pat_id = id;
        this.key_visit_id = visitId;
    }

    public RegistrationModel(String pat_id, String doctor_id, String doc_membership_id, String patient_info_type_form, String pat_first_name,
                             String pat_middle_name, String pat_last_name, String pat_gender, String pat_date_of_birth,
                             String pat_age, String pat_mobile_no, String pat_address, String pat_city_town, String pat_pincode,
                             String pat_district, String pref_lang, String photo_name, String consent, String special_instruction,
                             String added_by, String added_on, String addedTime, String modified_by, String modified_on, String is_disabled,
                             String disabled_by, String disabled_on, String is_deleted, String deleted_by, String deleted_on, String flag) {

        this.pat_id = pat_id;
        this.doctor_id = doctor_id;
        this.doc_mem_id = doc_membership_id;
        this.patient_info_typ = patient_info_type_form;
        this.firstName = pat_first_name;
        this.middleName = pat_middle_name;
        this.lastName = pat_last_name;
        this.gender = pat_gender;
        this.dob = pat_date_of_birth;
        this.age = pat_age;
        this.mobileNumber = pat_mobile_no;
        this.patient_address = pat_address;
        this.patient_city_town = pat_city_town;
        this.pin_code = pat_pincode;
        this.district = pat_district;
        this.language = pref_lang;
        this.photo = photo_name;
        this.consent = consent;
        this.special_instruction = special_instruction;
        this.added_by = added_by;
        this.added_on = added_on;
        this.added_Time = addedTime;
        this.modified_by = modified_by;
        this.modified_on = modified_on;
        this.is_disabled = is_disabled;
        this.disabled_by = disabled_by;
        this.disabled_on = disabled_on;
        this.is_deleted = is_deleted;
        this.deleted_by = deleted_by;
        this.deleted_on = deleted_on;
        this.flag = flag;

    }

 /*   public RegistrationModel(String pat_id, String ailment, String visit_date, String fod, String clinicalNotes, String actualFod) {
        this.pat_id = pat_id;
        this.ailments = ailment;
        this.visit_date = visit_date;

        this.clinicalNotes = clinicalNotes;
        this.followUpDate = fod;
        this.actualFollowupDate = actualFod;
    }*/

    public RegistrationModel(String id, String first_name, String middle_name, String last_name, String strdate_of_birth, String current_age, String phone_number, String gender, String selectedLanguage, String patientImagePath, String follow_up_date, String daysSel, String weekSel, String monthSel, String ailmentList, String prescriptionImgPath, String clinical_note, String added_on, String visit_date, String modified_on, String key_visit_id, String actFolDate, String mSymptoms, String mDiagnosis) {

        this.pat_id = id;
        this.firstName = first_name;
        this.middleName = middle_name;
        this.lastName = last_name;
        this.dob = strdate_of_birth;
        this.gender = gender;

        this.age = current_age;
        this.mobileNumber = phone_number;
        this.language = selectedLanguage;
        this.photo = patientImagePath;
        this.followUpDate = follow_up_date;

        this.followUpdays = daysSel;
        this.followUpWeek = weekSel;
        this.followUpMonth = monthSel;
        this.ailments = ailmentList;
        this.pres_img = prescriptionImgPath;
        this.clinicalNotes = clinical_note;
        this.added_on = added_on;
        this.visit_date = visit_date;
        this.modified_on = modified_on;
        this.key_visit_id = key_visit_id;
        this.actualFollowupDate = actFolDate;
        this.symptoms = mSymptoms;
        this.dignosis = mDiagnosis;
    }


    public RegistrationModel(String id, String first_name, String middle_name, String last_name, String strdate_of_birth, String gender, String current_age, String phone_number, String selectedLanguage, String patientImagePath, String usersellectedDate, String daysSel,
                             String monthSel, String weekSel, String ailmentList, String prescriptionImgPath, String clinical_note, String added_on, String modified_on, String actfod, String action, String address, String city, String district, String pin, String state,
                             String altername_no, String altternate_phType, String phone_type, String visit_date, String email, String uid, String familyHistory, String hospitalizationSurgery) {
        this.pat_id = id;
        this.firstName = first_name;
        this.middleName = middle_name;

        this.lastName = last_name;
        this.dob = strdate_of_birth;
        this.gender = gender;

        this.age = current_age;
        this.mobileNumber = phone_number;
        this.language = selectedLanguage;
        this.photo = patientImagePath;
        this.followUpDate = usersellectedDate;

        this.followUpdays = daysSel;
        this.followUpWeek = weekSel;
        this.followUpMonth = monthSel;
        this.ailments = ailmentList;
        this.pres_img = prescriptionImgPath;
        this.clinicalNotes = clinical_note;
        this.added_on = added_on;
        this.visit_date = visit_date;
        this.modified_on = modified_on;
        this.actualFollowupDate = actfod;
        this.action = action;

        this.address = address;
        this.cityortown = city;
        this.district = district;
        this.pin_code = pin;
        this.state = state;
        this.alternatePhoneNumber = altername_no;
        this.alternatePhoneType = altternate_phType;
        this.phone_type = phone_type;
        this.email = email;
        this.uid = uid;
        this.familyHistory = familyHistory;
        this.hospitalizationSurgery = hospitalizationSurgery;

    }

    public RegistrationModel(String id, String first_name, String middle_name, String last_name, String strdate_of_birth, String current_age, String phone_number, String gender, String selectedLanguage, String patientImagePath, String follow_up_date, String daysSel, String weekSel, String monthSel, String ailmentList, String prescriptionImgPath, String clinical_note, String added_on, String visit_date, String modified_on, String key_visit_id, String actFolDate,
                             String address, String city, String district, String pin, String state, String weight, String pulse, String bp, String mmhg, String temprature,
                             String sugar, String symptoms, String dignosis, String tests, String drugs, String altername_no, String height, String bmi, String sugar_fasting, String altternate_phType, String phone_type, String isd_code, String alternate_noisd_code) {

        this.pat_id = id;
        this.firstName = first_name;
        this.middleName = middle_name;
        this.lastName = last_name;
        this.dob = strdate_of_birth;
        this.gender = gender;

        this.age = current_age;
        this.mobileNumber = phone_number;
        this.language = selectedLanguage;
        this.photo = patientImagePath;
        this.followUpDate = follow_up_date;

        this.followUpdays = daysSel;
        this.followUpWeek = weekSel;
        this.followUpMonth = monthSel;
        this.ailments = ailmentList;
        this.pres_img = prescriptionImgPath;
        this.clinicalNotes = clinical_note;
        this.added_on = added_on;
        this.visit_date = visit_date;
        this.modified_on = modified_on;
        this.key_visit_id = key_visit_id;
        this.actualFollowupDate = actFolDate;

        this.address = address;
        this.cityortown = city;
        this.district = district;
        this.pin_code = pin;
        this.state = state;

        this.weight = weight;
        this.pulse = pulse;
        this.bp = bp;
        this.lowBp = mmhg;
        this.temprature = temprature;
        this.sugar = sugar;
        this.symptoms = symptoms;
        this.dignosis = dignosis;
        this.tests = tests;
        this.drugs = drugs;
        this.bmi = bmi;
        this.height = height;
        this.alternatePhoneNumber = altername_no;
        this.alternatePhoneType = altternate_phType;
        this.sugarFasting = sugar_fasting;
        this.phone_type = phone_type;
        this.isd_code = isd_code;
        this.alternate_isd_code = alternate_noisd_code;

    }


    public RegistrationModel(String id, String first_name, String middle_name, String last_name, String strdate_of_birth, String current_age, String phone_number, String gender, String selectedLanguage, String patientImagePath, String follow_up_date, String daysSel, String weekSel, String monthSel, String ailmentList, String prescriptionImgPath, String clinical_note, String added_on, String visit_date, String modified_on, String key_visit_id, String actFolDate,
                             String address, String city, String district, String pin, String state, String weight, String pulse, String bp, String mmhg, String temprature,
                             String sugar, String symptoms, String dignosis, String email, String uid, String altername_no, String height, String bmi, String sugar_fasting, String altternate_phType, String phone_type, String isd_code, String alternate_noisd_code, String referedBy, String referedTo, String spo2, String respirataion, String hba1c, String acer, String seremUrea, String lipidProfileHdl, String lipidProfileTc, String lipidProfileTg, String lipidProfileLdl, String lipidProfileVhdl, String ecg, String pft, String pallore, String cyanosis, String tremors, String icterus, String clubbing, String oedema, String calfTenderness, String lymphadenopathy, String obesity) {

        this.pat_id = id;
        this.firstName = first_name;
        this.middleName = middle_name;
        this.lastName = last_name;
        this.dob = strdate_of_birth;
        this.gender = gender;

        this.age = current_age;
        this.mobileNumber = phone_number;
        this.language = selectedLanguage;
        this.photo = patientImagePath;
        this.followUpDate = follow_up_date;

        this.followUpdays = daysSel;
        this.followUpWeek = weekSel;
        this.followUpMonth = monthSel;
        this.ailments = ailmentList;
        this.pres_img = prescriptionImgPath;
        this.clinicalNotes = clinical_note;
        this.added_on = added_on;
        this.visit_date = visit_date;
        this.modified_on = modified_on;
        this.key_visit_id = key_visit_id;
        this.actualFollowupDate = actFolDate;

        this.address = address;
        this.cityortown = city;
        this.district = district;
        this.pin_code = pin;
        this.state = state;

        this.weight = weight;
        this.pulse = pulse;
        this.bp = bp;
        this.lowBp = mmhg;
        this.temprature = temprature;
        this.sugar = sugar;
        this.symptoms = symptoms;
        this.dignosis = dignosis;
        this.email = email;
        this.uid = uid;
        this.bmi = bmi;
        this.height = height;
        this.alternatePhoneNumber = altername_no;
        this.alternatePhoneType = altternate_phType;
        this.sugarFasting = sugar_fasting;
        this.phone_type = phone_type;
        this.isd_code = isd_code;
        this.alternate_isd_code = alternate_noisd_code;
        this.referedBy = referedBy;
        this.referedTo = referedTo;
        this.spo2 = spo2;
        this.respirataion = respirataion;

        this.hba1c = hba1c;
        this.acer = acer;
        this.seremUrea = seremUrea;
        this.lipidProfileHdl = lipidProfileHdl;
        this.lipidProfileTc = lipidProfileTc;
        this.lipidProfileTg = lipidProfileTg;
        this.lipidProfileLdl = lipidProfileLdl;
        this.lipidProfileVhdl = lipidProfileVhdl;
        this.ecg = ecg;
        this.pft = pft;
        this.pallor = pallore;
        this.cyanosis = cyanosis;
        this.tremors = tremors;
        this.icterus = icterus;
        this.clubbing = clubbing;
        this.oedema = oedema;
        this.calfTenderness = calfTenderness;
        this.lymphadenopathy = lymphadenopathy;
        this.obesity = obesity;
    }


    //This constructor for get the result from db
    public RegistrationModel(String process, String start_time, String end_time) {//, String language, String s, String s1, String s2, String s3, String s4, String ailments, String s5, String s6) {


        this.process = process;
        this.start_time = start_time;
        this.end_time = end_time;

    }

    public RegistrationModel(String banner_id, String image_name, String start_time, String end_time) {//, String language, String s, String s1, String s2, String s3, String s4, String ailments, String s5, String s6) {

        this.banner_id = banner_id;
        this.banner_image_name = image_name;
        this.start_time = start_time;
        this.end_time = end_time;

    }

    public RegistrationModel(String id, String prescription_image, String added_on, String added_by, String status, String phno, String email, String strReferredBy, String strReferredTo) {

        this.id = id;
        this.pres_img = prescription_image;
        this.added_on = added_on;
        this.added_by = added_by;
        this.status = status;
        this.mobileNumber = phno;
        this.email = email;
        this.referedBy = strReferredBy;
        this.referedTo = strReferredTo;
    }

    public RegistrationModel(String id, String name, String type, String phoneno, String speciality, String added_on, String modified_counter, String phone_type, String isd_code, String email, String associate_address, String city, String associate_state, String pin_code, String district, String nameTitle, String contactforPatient, String selectedIsd_code_altType, String selectedcontactForPatientType) {
        this.id = id;
        this.name = name;
        this.associateType = type;
        this.mobileNumber = phoneno;
        this.speciality = speciality;
        this.added_on = added_on;
        this.modiedCounter = modified_counter;
        this.email = email;
        this.phone_type = phone_type;
        this.isd_code = isd_code;
        this.address = associate_address;
        this.cityortown = city;
        this.state = associate_state;
        this.pin_code = pin_code;
        this.district = district;
        this.title = nameTitle;
        this.contactForPatient = contactforPatient;
        this.altContactPatientIsdCodeType = selectedIsd_code_altType;
        this.selectedContactPhoneType = selectedcontactForPatientType;
    }

    public RegistrationModel(String id, String first_name, String middle_name, String last_name, String strdate_of_birth, String current_age, String phone_number, String gender, String selectedLanguage, String patientImagePath, String follow_up_date, String daysSel, String weekSel, String monthSel, String ailmentList, String prescriptionImgPath, String clinical_note, String added_on, String visit_date, String modified_on, String key_visit_id, String actFolDate,
                             String address, String city, String district, String pin, String state, String weight, String pulse, String bp, String mmhg, String temprature,
                             String sugar, String symptoms, String dignosis, String uid, String drugs, String altername_no, String height, String bmi, String sugar_fasting, String altternate_phType, String phone_type, String isd_code, String alternate_noisd_code, String referedBy, String referedTo, String email) {

        this.pat_id = id;
        this.firstName = first_name;
        this.middleName = middle_name;
        this.lastName = last_name;
        this.dob = strdate_of_birth;
        this.gender = gender;

        this.age = current_age;
        this.mobileNumber = phone_number;
        this.language = selectedLanguage;
        this.photo = patientImagePath;
        this.followUpDate = follow_up_date;

        this.followUpdays = daysSel;
        this.followUpWeek = weekSel;
        this.followUpMonth = monthSel;
        this.ailments = ailmentList;
        this.pres_img = prescriptionImgPath;

        this.clinicalNotes = clinical_note;
        this.added_on = added_on;
        this.visit_date = visit_date;
        this.modified_on = modified_on;
        this.key_visit_id = key_visit_id;

        this.actualFollowupDate = actFolDate;
        this.address = address;
        this.cityortown = city;
        this.district = district;
        this.pin_code = pin;

        this.state = state;
        this.weight = weight;
        this.pulse = pulse;
        this.bp = bp;
        this.lowBp = mmhg;

        this.temprature = temprature;
        this.sugar = sugar;
        this.symptoms = symptoms;
        this.dignosis = dignosis;
        this.uid = uid;

        this.drugs = drugs;
        this.bmi = bmi;
        this.height = height;
        this.alternatePhoneNumber = altername_no;
        this.alternatePhoneType = altternate_phType;

        this.sugarFasting = sugar_fasting;
        this.phone_type = phone_type;
        this.isd_code = isd_code;
        this.alternate_isd_code = alternate_noisd_code;
        this.referedBy = referedBy;

        this.referedTo = referedTo;
        this.email = email;
    }

    public RegistrationModel(String ecg, String sugar, String sugar_fasting, String acer, String pft, String hba1c, String serem_urea, String lipid_profile_tc, String lipid_profile_tg, String lipid_profile_ldl, String lipid_profile_vhdl, String lipid_profile_hdl) {
        this.ecg = ecg;
        this.sugar = sugar;
        this.sugarFasting = sugar_fasting;
        this.acer = acer;
        this.pft = pft;
        this.hba1c = hba1c;
        this.seremUrea = serem_urea;
        this.lipidProfileTc = lipid_profile_tc;
        this.lipidProfileTg = lipid_profile_tg;
        this.lipidProfileLdl = lipid_profile_ldl;
        this.lipidProfileVhdl = lipid_profile_vhdl;
        this.lipidProfileHdl = lipid_profile_hdl;
    }

    public RegistrationModel(String alocholConsumption, String packPerWeek, String stressLevel, String smokerType, String stickCount, String lifeSyle, String lactoseTolerance, String foodPreference, String foodHabit, String excercise, String chewingTobaco, String bingeEating, String allergies, String sexuallyActive, String sticksGapSelected, String pegsGapSelected, String otherTobacoConsumption, String drugConsumption, String otherDrugConsumption, String sleep, String lastSmokeYear, String lastDrinkYear) {

        this.alocholConsumption = alocholConsumption;
        this.packPerWeek = packPerWeek;
        this.stressLevel = stressLevel;
        this.smokerType = smokerType;
        this.stickCount = stickCount;
        this.lifeSyle = lifeSyle;
        this.lactoseTolerance = lactoseTolerance;
        this.foodPreference = foodPreference;
        this.foodHabit = foodHabit;
        this.excercise = excercise;
        this.chewingTobaco = chewingTobaco;
        this.bingeEating = bingeEating;
        this.allergies = allergies;
        this.sexuallyActive = sexuallyActive;
        this.sticksGapSelected = sticksGapSelected;
        this.pegsGapSelected = pegsGapSelected;

        this.otherTobacoConsumption = otherTobacoConsumption;
        this.drugConsumption = drugConsumption;
        this.otherDrugConsumption = otherDrugConsumption;
        this.sleep = sleep;
        this.lastSmokeYear = lastSmokeYear;
        this.lastDrinkYear = lastDrinkYear;

    }

    public RegistrationModel(String id, String first_name, String middle_name, String last_name, String strdate_of_birth, String current_age, String phone_number, String gender, String selectedLanguage, String patientImagePath, String follow_up_date, String daysSel, String weekSel, String monthSel, String ailmentList, String prescriptionImgPath, String clinical_note, String added_on, String visit_date, String modified_on, String key_visit_id, String actFolDate,
                             String address, String city, String district, String pin, String state, String weight, String pulse, String bp, String mmhg, String temprature,
                             String sugar, String symptoms, String dignosis, String uid, String drugs, String altername_no, String height, String bmi, String sugar_fasting, String altternate_phType, String phone_type, String isd_code, String alternate_noisd_code, String referedBy, String referedTo, String email, String followUpStatus, String spo2, String respiration, String strFamilyHistory, String strHospitalizationSurgeru, String strObestity) {

        this.pat_id = id;
        this.firstName = first_name;
        this.middleName = middle_name;
        this.lastName = last_name;
        this.dob = strdate_of_birth;
        this.gender = gender;

        this.age = current_age;
        this.mobileNumber = phone_number;
        this.language = selectedLanguage;
        this.photo = patientImagePath;
        this.followUpDate = follow_up_date;

        this.followUpdays = daysSel;
        this.followUpWeek = weekSel;
        this.followUpMonth = monthSel;
        this.ailments = ailmentList;
        this.pres_img = prescriptionImgPath;

        this.clinicalNotes = clinical_note;
        this.added_on = added_on;
        this.visit_date = visit_date;
        this.modified_on = modified_on;
        this.key_visit_id = key_visit_id;

        this.actualFollowupDate = actFolDate;
        this.address = address;
        this.cityortown = city;
        this.district = district;
        this.pin_code = pin;

        this.state = state;
        this.weight = weight;
        this.pulse = pulse;
        this.bp = bp;
        this.lowBp = mmhg;

        this.temprature = temprature;
        this.sugar = sugar;
        this.symptoms = symptoms;
        this.dignosis = dignosis;
        this.uid = uid;

        this.drugs = drugs;
        this.bmi = bmi;
        this.height = height;
        this.alternatePhoneNumber = altername_no;
        this.alternatePhoneType = altternate_phType;

        this.sugarFasting = sugar_fasting;
        this.phone_type = phone_type;
        this.isd_code = isd_code;
        this.alternate_isd_code = alternate_noisd_code;
        this.referedBy = referedBy;

        this.referedTo = referedTo;
        this.email = email;
        this.followUpStatus = followUpStatus;
        this.spo2 = spo2;
        this.respirataion = respiration;
        this.familyHistory = strFamilyHistory;
        this.hospitalizationSurgery = strHospitalizationSurgeru;
        this.obesity = strObestity;
    }

    public RegistrationModel(String strPallore, String strPallorDescription, String strCyanosis, String strCyanosisDescription, String strClubbing, String strClubbingDescription, String strTremors, String strTremorsDescription, String strIcterus, String strIcterusDescription, String strOedema, String strOedemaDescription, String strCalfTenderness, String strCalfTendernessDescription, String strLymphadenopathy, String strLymphadenopathyDescription) {
        this.pallor = strPallore;
        this.pallorDescription = strPallorDescription;
        this.cyanosis = strCyanosis;
        this.cyanosisDescription = strCyanosisDescription;
        this.tremors = strTremors;
        this.tremorsDescription = strTremorsDescription;
        this.icterus = strIcterus;
        this.icterusDescription = strIcterusDescription;
        this.clubbing = strClubbing;
        this.clubbingDescription = strClubbingDescription;
        this.oedema = strOedema;
        this.oedemaDescription = strOedemaDescription;
        this.calfTenderness = strCalfTenderness;
        this.calfTendernessDescription = strCalfTendernessDescription;
        this.lymphadenopathy = strLymphadenopathy;
        this.lymphadenopathyDescription = strLymphadenopathyDescription;
    }

    public RegistrationModel(String msId, String msTopic, String msBrief, String msDateTime, String msDoctorName, String specialities_name, String ms_cat_name, String ms_url) {

        this.msId = msId;
        this.msTopic = msTopic;
        this.msBrief = msBrief;
        this.msDateTime = msDateTime;
        this.msDoctorName = msDoctorName;
        this.msSpeciality = specialities_name;
        this.msCatagoryName = ms_cat_name;
        this.msUrl = ms_url;

    }
    public RegistrationModel(String ecg, String sugar, String sugar_fasting,String sugar_rbs, String acer,String pft,String hba1c,String serem_urea,String lipid_profile_tc,String lipid_profile_tg,String lipid_profile_ldl,String lipid_profile_vhdl,String lipid_profile_hdl,String lipid_profile_tch,String hdl_ldl_ratio,String hb,String platelet_count,String esr,String dcl,String dcn,String dce,String dcm,String dcb,String total_bilirubin,String direct_bilirubin,String indirect_bilirubin,String sgpt,String sgot,String ggt,String total_protein,String albumin,String globulin,String ag_ratio,String urine_pus_cell,String urine_rbc,String urine_cast,String urine_protein,String urine_crystal,String microalbuminuria,String serum_creatinine,String acr,String tsh,String t3,String t4  ) {
        this.ecg = ecg;
        this.sugar = sugar;
        this.sugarFasting = sugar_fasting;
        this.acer = acer;
        this.pft = pft;
        this.hba1c = hba1c;
        this.seremUrea = serem_urea;
        this.lipidProfileTc = lipid_profile_tc;
        this.lipidProfileTg = lipid_profile_tg;
        this.lipidProfileLdl = lipid_profile_ldl;
        this.lipidProfileVhdl = lipid_profile_vhdl;
        this.lipidProfileHdl = lipid_profile_hdl;
        this.sugarRbs=sugar_rbs;
        this.lipidProfileTch=lipid_profile_tch;
        this.hdlLdlRatio=hdl_ldl_ratio;
        this.hb=hb;
        this.plateletCount=platelet_count;
        this.esr=esr;
        this.dcl=dcl;
        this.dcn=dcn;
        this.dce=dce;
        this.dcm=dcm;
        this.dcb=dcb;
        this.totalBilirubin=total_bilirubin;
        this.directBilirubin=direct_bilirubin;
        this.indirectBilirubin=indirect_bilirubin;
        this.sgpt=sgpt;
        this.sgot=sgot;
        this.ggt=ggt;
        this.total_protein=total_protein;
        this.albumin=albumin;
        this.globulin=globulin;
        this.ag_ratio=ag_ratio;
        this.urinePusCell=urine_pus_cell;
        this.urineRbc=urine_rbc;
        this.urineCast=urine_cast;
        this.urineProtein=urine_protein;
        this.urineCrystal=urine_crystal;
        this.microalbuminuria=microalbuminuria;
        this.serumCreatinine=serum_creatinine;
        this.acr=acr;
        this.tsh=tsh;
        this.t3=t3;
        this.t4=t4;
    }
}
