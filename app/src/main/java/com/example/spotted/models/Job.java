package com.example.spotted.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Job {
    private String Id;
    private String Title;
    private String Description;
    private String Source;
    private String Type;
    private String Location;
    private String Company;
    private Date Closing;
    private Date Posted;
    private boolean Liked;
    private List<String> Contacts;
    private List<String> Competencies;
    private List<String> Qualifications;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public Date getClosing() {
        return Closing;
    }

    public void setClosing(Date closing) {
        Closing = closing;
    }

    public Date getPosted() {
        return Posted;
    }

    public void setPosted(Date posted) {
        Posted = posted;
    }

    public boolean isLiked() {
        return Liked;
    }

    public void setLiked(boolean liked) {
        Liked = liked;
    }

    public List<String> getContacts() {
        return Contacts;
    }

    public void setContacts(List<String> contacts) {
        Contacts = contacts;
    }

    public List<String> getCompetencies() {
        return Competencies;
    }

    public void setCompetencies(List<String> competencies) {
        Competencies = competencies;
    }

    public List<String> getQualifications() {
        return Qualifications;
    }

    public void setQualifications(List<String> qualifications) {
        Qualifications = qualifications;
    }
    public String getClosingString(){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(getClosing());
    }
    public String getPostedString(){
        String pattern = "EEE, d MMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(getPosted());
    }
}
