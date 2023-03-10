package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.GoodView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.GoodService;
import services.ReportService;

/**
 * 日報に関する処理を行うActionクラス
 *
 */
public class GoodAction extends ActionBase {

    private GoodService service;
    private ReportService rservice;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new GoodService();
        rservice = new ReportService();

        //メソッドを実行
        invoke();
        service.close();
        rservice.close();
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

        //全いいねデータの件数を取得
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
        GoodView gv = new GoodView();
        putRequestScope(AttributeConst.GOOD, gv); //空のいいねインスタンス
        ReportView rv = (ReportView) getSessionScope(AttributeConst.REPORT);
        putRequestScope(AttributeConst.REPORT, rv);
        //新規登録画面を表示
        forward(ForwardConst.FW_GOD_NEW);
    }

    /**
    *新規登録を行う
    *@throws ServletException
    *@throws IOException
    */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //セッションからログイン中の従業員を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //セッションからいいねを押したい日報を取得
            ReportView rv = (ReportView) getSessionScope(AttributeConst.REPORT);

            if (ev.getId() == rv.getEmployee().getId()) {
                //ログインしている従業員が日報の作成者の場合はエラー画面を表示
                //エラー画面を再表示
                forward(ForwardConst.FW_ERR_NERROR);

            } else {

                //パラメータの値をもとに日報インスタンスを作成する
                GoodView gv = new GoodView(
                        null,
                        ev, //ログインしている従業員を、いいね作成者として登録する
                        rv, //いいねしたい日報を、いいね押した日報として登録する
                        getRequestParam(AttributeConst.GOD_CONTENT),
                        null);

                //いいね情報登録
                List<String> errors = service.create(gv);

                if (errors.size() > 0) {
                    //登録中にエラーがあった場合
                    putRequestScope(AttributeConst.GOOD, gv); //入力されたいいね情報
                    putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                    //編集画面を再表示
                    forward(ForwardConst.FW_GOD_NEW);

                } else {
                    //更新中にエラーがなかった場合

                    //セッションに更新完了のフラッシュメッセージを設定
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());
                    //一覧画面にリダイレクト
                    redirect(ForwardConst.ACT_GOD, ForwardConst.CMD_INDEX);
                }
            }
        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */

    public void show() throws ServletException, IOException {

        //idを条件に日報データを取得する
        GoodView gv = service.findOne(toNumber(getRequestParam(AttributeConst.GOD_ID)));
        ReportView rv = rservice.findOne(toNumber(getRequestParam(AttributeConst.GOD_ID)));

        if (gv == null && rv == null) {
            //該当の日報データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putSessionScope(AttributeConst.GOOD, gv); //取得したいいねデータ
            putSessionScope(AttributeConst.REPORT, rv); //取得した日報データ

            //詳細画面を表示
            forward(ForwardConst.FW_GOD_SHOW);
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件にいいねデータを取得する
        GoodView gv = service.findOne(toNumber(getRequestParam(AttributeConst.GOD_ID)));

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //ReportView rv = (ReportView) getSessionScope(AttributeConst.REPORT);

        if (gv == null || ev.getId() != gv.getEmployee().getId()) {
            //該当のいいねデータが存在しない、または
            //ログインしている従業員がいいねの作成者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        }else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.GOOD, gv); //取得した日報データ
            //putRequestScope(AttributeConst.REPORT, rv);

            //編集画面を表示
            forward(ForwardConst.FW_GOD_EDIT);
        }
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件にいいねデータを取得する
            GoodView gv = service.findOne(toNumber(getRequestParam(AttributeConst.GOD_ID)));

            //入力されたいいね内容を設定する
            gv.setContent(getRequestParam(AttributeConst.GOD_CONTENT));

            //いいねデータを更新する
            List<String> errors = service.update(gv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.GOOD, gv); //入力されたいいね情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_GOD_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_GOD, ForwardConst.CMD_INDEX);
            }
        }
    }

    public void destroy() throws ServletException, IOException {

        GoodView gv = getSessionScope(AttributeConst.GOOD);

        //いいねデータを削除する
        service.destroy(gv);

        removeSessionScope(AttributeConst.GOOD);

      //セッションに更新完了のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_GOD, ForwardConst.CMD_INDEX);

    }
}
