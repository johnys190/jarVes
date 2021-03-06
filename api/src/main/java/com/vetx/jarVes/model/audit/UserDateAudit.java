package com.vetx.jarVes.model.audit;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
@Getter
@Setter
public abstract class UserDateAudit extends DateAudit{
    @CreatedBy
    @Column(updatable = false)
//    @Setter(onMethod = @__( @JsonIgnore))
    private String createdBy;

    @LastModifiedBy
//    @Setter(onMethod = @__( @JsonIgnore))
    private String updatedBy;
}

