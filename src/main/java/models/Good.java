package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
     * いいねデータのDTOモデル
     *
     */
@Table(name = JpaConst.TABLE_GOD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Good {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.GOD_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * いいねを登録した従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.GOD_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * いいねを登録した日報
     */

    @ManyToOne
    @JoinColumn(name = JpaConst.GOD_COL_REP, nullable = false)
    private Report report;

    /**
     * 内容
     */

    @Lob
    @Column(name = JpaConst.GOD_COL_CONTENT, nullable = false)
    private String content;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.GOD_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;
}
