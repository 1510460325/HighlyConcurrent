package cn.wzy.entity;

import cn.wzy.annotation.MGColName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Create by Wzy
 * on 2018/7/28 18:40
 * 不短不长八字刚好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

@MGColName("user")
public class User_info {

    @Id
    private Integer id;
    @Field
    private String name;

    @Field
    private Integer age;

    @Field
    private Integer role;
}
