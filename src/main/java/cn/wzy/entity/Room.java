package cn.wzy.entity;

import cn.wzy.annotation.MGColName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Create by Wzy
 * on 2018/7/29 15:44
 * 不短不长八字刚好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@MGColName("test")
public class Room {
    private Integer id;

    private String name;

    private Integer age;

    private Date birth;
}
