package services;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.GoodConverter;
import actions.views.GoodView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Good;
import models.validators.GoodValidator;

/**
 *いいねテーブルの操作に関わる処理を行うクラス
 */
public class GoodService extends ServiceBase {

    /**
     * 指定した従業員が作成したいいねデータを、指定されたページ数の一覧画面に表示する分取得しGoodViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<GoodView> getMinePerPage(EmployeeView employee, int page) {

        List<Good> goods = em.createNamedQuery(JpaConst.Q_GOD_GET_ALL_MINE, Good.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return GoodConverter.toViewList(goods);
    }

    /**
     * 指定した日報が作成したいいねデータを、指定されたページ数の一覧画面に表示する分取得しGoodViewのリストで返却する
     * @param report 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<GoodView> getMinePerPageReport(ReportView report, int page) {

        List<Good> goodReports = em.createNamedQuery(JpaConst.Q_GOD_GET_ALL_MINE_REPORT, Good.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return GoodConverter.toViewList(goodReports);
    }

    /**
     * 指定した従業員が作成したいいねデータの件数を取得し、返却する
     * @param employee
     * @return いいねデータの件数
     */
    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_GOD_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }


   /** 日報が作成したいいねデータの件数を取得し、返却する
    * @param report
    * @return いいねデータの件数
    */
   public long countAllMineGood(ReportView report) {

       long countGood = (long) em.createNamedQuery(JpaConst.Q_GOD_COUNT_ALL_MINE_REPORT, Long.class)
               .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
               .getSingleResult();

       return countGood;
   }

    /**
     * 指定した日報が作成したいいねデータの件数を取得し、返却する
     * @param employee
     * @return いいねデータの件数
     */
    public long countAllMineReport(ReportView report) {

        long countReport = (long) em.createNamedQuery(JpaConst.Q_GOD_GET_ALL_MINE_REPORT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .getSingleResult();

        return countReport;
    }

    /**
     * report_idを条件に取得したデータをGoodViewのインスタンスで返却する
     * @param report 日報
     * @return 取得データのインスタンス 取得できない場合null
     */
    public GoodView findOne(String report) {
        Good g = null;
        try {
            //report_idを条件に未削除のいいねを1件取得する
            g = em.createNamedQuery(JpaConst.Q_GOD_GET_BY_REPORT_ID, Good.class)
                    .setParameter(JpaConst.JPQL_PARM_REPORT, report)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return GoodConverter.toView(g);

    }

    /**
     * 指定されたページ数の一覧画面に表示するいいねデータを取得し、GoodViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<GoodView> getAllPerPage(int page) {

        List<Good> goods = em.createNamedQuery(JpaConst.Q_GOD_GET_ALL, Good.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return GoodConverter.toViewList(goods);
    }

    /**
     * いいねテーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long goods_count = (long) em.createNamedQuery(JpaConst.Q_GOD_COUNT, Long.class)
                .getSingleResult();
        return goods_count;
    }

    /**
     * idを条件に取得したデータをGoodViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public GoodView findOne(int id) {
        return GoodConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力されたいいねの登録内容を元にデータを１件作成し、いいねテーブルに登録する
     * @param gv いいねの登録内容
     * @return バリテーションで発生したエラーのリスト
     */
    public List<String> create(GoodView gv) {
        List<String> errors = GoodValidator.validate(gv);
        if (errors.size() == 0) {
            LocalDate ldt = LocalDate.now();
            gv.setCreatedAt(ldt);
            createInternal(gv);
        }

        //バリテーションで発生したエラーを返却（エラーがなければ０件の空リスト）
        return errors;
    }

    /**
     * 画面から入力されたいいねの登録内容を元に、いいねデータを更新する
     * @param gv いいねの更新内容
     * @return バリテーションで発生したエラーのリスト
     */
    public List<String> update(GoodView gv) {

        //バリテーションを行う
        List<String> errors = GoodValidator.validate(gv);

        if (errors.size() == 0) {

            //登録日時を現在時刻に設定
            LocalDate ldt = LocalDate.now();
            gv.setCreatedAt(ldt);

            updateInternal(gv);
        }

        //バリテーションで発生したエラーを返却（エラーがなければ０件の空リスト）
        return errors;
    }

    /**
     * いいねデータを削除する
     * @param id
     */
    public GoodView destroy(GoodView gv) {
        em.getTransaction().begin();
        Good g = findOneInternal(gv.getId());
        em.remove(g);
        em.getTransaction().commit();
        return null;
    }


    /**
     * idを条件にデータを１件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Good findOneInternal(int id) {
        return em.find(Good.class, id);
    }

    /**
     *いいねデータを１件登録する
     *@param gv いいねデータ
     */
    private void createInternal(GoodView gv) {

        em.getTransaction().begin();
        em.persist(GoodConverter.toModel(gv));
        em.getTransaction().commit();
    }

    /**
     * いいねデータを更新する
     * @param gv いいねデータ
     */
    private void updateInternal(GoodView gv) {
        em.getTransaction().begin();
        Good g = findOneInternal(gv.getId());
        GoodConverter.copyViewToModel(g, gv);
        em.getTransaction().commit();
    }

    /**
     * いいねデータを削除する
     * @param gv いいねデータ
    private void destroyInternal(GoodView gv) {
        em.getTransaction().begin();
        Good g = findOneInternal(gv.getId());
        em.remove(g);
        em.getTransaction().commit();
    }
         */

}
