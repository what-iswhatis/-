package com.rocky.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseModel {
    private Integer id;
    private String name;
    private String img;
    private Integer parentId;
    private Integer seq;
    private Integer status = 1;
}
