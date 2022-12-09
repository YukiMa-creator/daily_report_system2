package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
    * いいね情報について画面の入力値・出力値を扱うViewモデル
    *
    */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodView {

    /**
     * id
     */

    private Integer id;

    /**
     * いいねを登録した従業員
     */
    private EmployeeView employee;

    /**
     * いいねを登録した日報
     */
    private ReportView report;

    /**
     *内容
     */
    private String content;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;
}
