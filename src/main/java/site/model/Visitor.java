package site.model;

import javax.persistence.*;

/**
 * A person visiting the conference.
 * @author Mihail Stoynov
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Visitor extends AbstractEntity {

    @ManyToOne(optional = false)
    private Registrant registrant;
    private String name;
    private String email;

    private String company;

    @Enumerated(EnumType.STRING)
    private VisitorStatus status;

    public Visitor() {}

    public Visitor(Registrant invoiceTo, String name, String email,String company) {
        this.registrant = invoiceTo;
        this.name = name;
        this.email = email;
        this.company = company;
    }

    public Registrant getRegistrant() {
        return registrant;
    }

    public void setRegistrant(Registrant registrant) {
        this.registrant = registrant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * retrieves the company related to the visitor. <br />
     * Check https://github.com/bgjug/jprime/issues/28
     */
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public VisitorStatus getStatus() {
        //some fuzzy logic here: if registrant has payed EPAY invоice it means the the visitor has payed
        if(registrant!=null && registrant.getRealInvoiceNumber()!=0) return VisitorStatus.PAYED;
        return status;
    }

    public void setStatus(VisitorStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}