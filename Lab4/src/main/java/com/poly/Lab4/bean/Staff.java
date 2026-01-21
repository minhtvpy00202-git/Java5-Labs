package com.poly.Lab4.bean;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Staff {
    @NotBlank(message = "Chưa nhập Email")
    @Email(message = "Email không đúng định dạng")
    private String id;

    @NotBlank(message = "Chưa nhập họ và tên")
    private String fullname;

    @Builder.Default
    private String photo="photo.jpg";


    @NotNull(message = "Chưa chọn giới tính")
    private Boolean gender;

    @NotNull(message = "Chưa chọn ngày sinh")
    @Past(message = "Ngày sinh không hợp lệ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;



    @DecimalMin(value = "1000", message = "Lương tối thiểu phải là 1000")
    @NotNull(message = "Chưa nhập lương")
    private Double salary = 12345.6789;

    private Integer level;
}
