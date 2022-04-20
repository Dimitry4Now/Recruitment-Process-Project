package finki.ukim.mk.projectv2.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DOCS")
public class Doc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String docName;
    private String docType;

    @Lob
    private byte[] data;

    public Doc(){}

    public Doc(String docname, String contentType, byte[] bytes) {
        this.docName = docname;
        this.docType = contentType;
        this.data = bytes;
    }
}
