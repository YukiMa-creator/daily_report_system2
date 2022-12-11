package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.GoodView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.GoodService;

/**
 * 日報に関する処理を行うActionクラス
 *
 */
public class GoodAction extends ActionBase {

    private GoodService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new GoodService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する日報データを取得
        int page = getPage();

        List<GoodView> goods = service.getAllPerPage(page);

        //全日報データの件数を取得
        long goodsCount = service.countAll();

        putRequestScope(AttributeConst.GOODS, goods); //取得した日報データ
        putRequestScope(AttributeConst.GOD_COUNT, goodsCount); //全ての日報データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_GOD_INDEX);
    }

    /**
    *新規登録画面を表示する
    *@throws ServletException
    *@throws IOException
    */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //**いいね情報の空インスタンスに、いいねの日付＝今日の日付を設定する（登録日時）
        //GoodView gv = new GoodView();
        //gv.setCreatedAt(LocalDate.now());
        //putRequestScope(AttributeConst.GOOD, gv); //日付のみ設定済みのいいねインスタンス
        //新規登録画面を表示
        forward(ForwardConst.FW_GOD_NEW);
    }

}