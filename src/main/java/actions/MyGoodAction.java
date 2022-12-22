package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.GoodView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.GoodService;

public class MyGoodAction extends ActionBase {


    private GoodService service;

    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new GoodService();
        //メソッドを実行
        invoke();

        service.close(); //追記

    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */

    public void index() throws ServletException, IOException {

        // 以下追記

        //セッションからログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //ログイン中の従業員が作成したいいねデータを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<GoodView> goods = service.getMinePerPage(loginEmployee, page);

        //ログイン中の従業員が作成した日報データの件数を取得
        long myGoodsCount = service.countAllMine(loginEmployee);

        putRequestScope(AttributeConst.GOODS, goods); //取得した日報データ
        putRequestScope(AttributeConst.GOD_COUNT, myGoodsCount); //ログイン中の従業員が作成した日報の数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //↑ここまで追記

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }
  //一覧画面を表示
    forward(ForwardConst.FW_MGOD_INDEX);
    }
}
