// POJO Class to read the tidata.xml file
package com.tinitiate.jersey.rs;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;


// Class to read XML Course level element
class Courses {

    private String id;
    private String name;
    private String type;

    // Getter and Setter
    @XmlAttribute
    public String getId () { return id; }
    public void setId (String id) { this.id = id; }

    @XmlElement
    public String getName () { return name; }
    public void setName (String name) { this.name = name; }

    @XmlElement
    public String getType () { return type; }
    public void setType (String type) { this.type = type; }
}


// Class to read XML Social level element
class Social {

    private String id;
    private String Social;

    // Getter and Setter
    @XmlAttribute
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @XmlValue
    public String getSocial() { return Social; }
    public void setSocial(String Social) { this.Social = Social; }
}


// Class to read XML TiData level element
@XmlRootElement
public class TiData {

    private String website;
    private List<Courses> courses;
    private List<Social> social;

    // Getters and Setters
    @XmlElement
    public void setWebsite(String website) { this.website = website; }
    public String getWebsite() { return website; }

    @XmlElement
    public void setCourses(List<Courses> courses) { this.courses = courses; }
    public List<Courses> getCourses() { return courses; }

    @XmlElement
    public void setSocial(List<Social> social) { this.social = social; }
    public List<Social> getSocial() { return social; }
}
