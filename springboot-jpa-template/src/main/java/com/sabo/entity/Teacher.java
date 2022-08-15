package com.sabo.entity;

import com.sabo.entity.base.BaseEntity;
import com.sabo.entity.base.TeacherId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author zhangcanbin@hongwangweb.com
 * @date 2022/7/28 15:47
 */
@Entity
@Table(name = "teacher")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@IdClass(TeacherId.class)
public final class Teacher extends BaseEntity {
    @Column
    private String name;
}
