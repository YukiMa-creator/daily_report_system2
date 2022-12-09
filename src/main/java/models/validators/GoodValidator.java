package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.GoodView;
import constants.MessageConst;

/**
 *いいねインスタンスに設定されている値のバリテーションを行うクラス
 */

public class GoodValidator {

    /**
     * いいねインスタンスの各項目についてバリテーションを行う
     *@param gv いいねインスタンス
     *@return エラーリスト
     */
    public static List<String> validate(GoodView gv) {
        List<String> errors = new ArrayList<String>();

        //内容のチェック
        String contentError = validateContent(gv.getContent());
        if (!contentError.equals("")) {
            errors.add(contentError);
        }
        return errors;
    }

    /**
     * 内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     *@param content 内容
     *@return エラーメッセージ
     */
    private static String validateContent(String content) {
        if (content == null || content.equals("")) {
            return MessageConst.E_NOCONTENT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }
}
