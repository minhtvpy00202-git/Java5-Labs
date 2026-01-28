package com.poly.Lab3.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    private String id;
    private String fullname;
    
    @Builder.Default
    private String photo = "p092.jpg";
    
    @Builder.Default
    private Boolean gender = true;
    
    @Builder.Default
    private Date birthday = new Date();
    
    @Builder.Default
    private Double salary = 12345.6789;
    
    @Builder.Default
    private Integer level = 0;
}
