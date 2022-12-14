<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actGod" value="${ForwardConst.ACT_GOD.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>日報 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${report.employee.name}" /></td>
                </tr>
                <tr>
                    <th>日付</th>
                    <fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd"
                        var="reportDay" type="date" />
                    <td><fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' /></td>
                </tr>
                <tr>
                    <th>内容</th>
                    <td><pre><c:out value="${report.content}" /></pre></td>
                </tr>
                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${report.createdAt}"
                        pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}"
                            pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${report.updatedAt}"
                        pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}"
                            pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <c:if test="${sessionScope.login_employee.id == report.employee.id}">
            <p>
                <a
                    href="<c:url value='?action=${actRep}&command=${commEdt}&id=${report.id}' />">この日報を編集する</a>
            </p>
        </c:if>

        <p>
            <a href="<c:url value='?action=${actRep}&command=${commIdx}' />">一覧に戻る</a>
        </p>

            <h3>いいね 詳細</h3>
            <table id="good_list">
                <tbody>
                    <tr>
                        <th class="good_name">氏名</th>
                        <th class="good_content">内容</th>
                        <th class="good_date">登録日時</th>
                    </tr>
                    <c:forEach var="good" items="${goods}" varStatus="status">
                        <tr class="row${status.count % 2}">
                            <td class="good_name"><c:out value="${good.employee.name}" /></td>
                            <td class="good_content"><pre><c:out value="${good.content}" /></pre></td>
                            <td class="good_date"><fmt:parseDate
                                    value="${good.createdAt}" pattern="yyyy-MM-dd" var="createDay"
                                    type="date" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <c:if test="${sessionScope.login_employee.id == good.employee.id}">
                <p>
                    <a
                        href="<c:url value='?action=${actGod}&command=${commEdt}&id=${good.id}' />">いいねの内容を編集する</a>
                </p>
            </c:if>

            <p>
                <a
                    href="<c:url value='?action=${actGod}&command=${commNew}&id=${good.id}' />">いいね</a>
            </p>
    </c:param>
</c:import>